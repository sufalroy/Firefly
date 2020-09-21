package org.firefly.gl.components.filter.ssao;

import static org.lwjgl.opengl.GL15.GL_READ_ONLY;
import static org.lwjgl.opengl.GL15.GL_WRITE_ONLY;
import static org.lwjgl.opengl.GL30.GL_R16F;
import static org.lwjgl.opengl.GL30.GL_RGBA16F;
import static org.lwjgl.opengl.GL30.GL_RGBA32F;
import static org.lwjgl.opengl.GL42.glBindImageTexture;
import static org.lwjgl.opengl.GL43.glDispatchCompute;

import org.firefly.core.context.BaseContext;
import org.firefly.core.gl.texture.GLTexture;
import org.firefly.core.gl.wrapper.texture.TextureImage2D;
import org.firefly.core.gl.wrapper.texture.TextureStorage2D;
import org.firefly.core.image.Image.ImageFormat;
import org.firefly.core.image.Image.SamplerFilter;
import org.firefly.core.image.Image.TextureWrapMode;
import org.firefly.core.math.Vec3f;
import org.firefly.core.util.Util;

import lombok.Getter;

public class SSAO {
	
	@Getter
	private GLTexture ssaoBlurSceneTexture;
	@Getter
	private GLTexture ssaoSceneTexture;
	
	
	private int kernelSize;
	private Vec3f[] kernel;
	private float[] randomx;
	private float[] randomy;
	private GLTexture noiseTexture;
	
	private NoiseTextureShader noiseTextureShader;
	private SSAOShader ssaoShader;
	private SSAOBlurShader blurShader;
	
	private int width;
	private int height;
	
	public SSAO(int width, int height) {

		this.width = width;
		this.height = height;
		
		kernelSize = 64;
		
		randomx = new float[16];
		randomy = new float[16];
		
		for (int i=0; i<16; i++){
			randomx[i] = (float) Math.random() * 2 - 1;
			randomy[i] = (float) Math.random() * 2 - 1;
		}
	
		kernel = Util.generateRandomKernel3D(kernelSize);
		
		noiseTextureShader = NoiseTextureShader.getInstance();
		ssaoShader = SSAOShader.getInstance();
		blurShader = SSAOBlurShader.getInstance();
		
		noiseTexture = new TextureStorage2D(4,4,1,ImageFormat.RGBA16FLOAT);
		ssaoSceneTexture = new TextureImage2D(width, height,
				ImageFormat.R16FLOAT, SamplerFilter.Bilinear, TextureWrapMode.ClampToEdge);
		ssaoBlurSceneTexture = new TextureImage2D(width, height,
				ImageFormat.R16FLOAT, SamplerFilter.Bilinear, TextureWrapMode.ClampToEdge);
		
		// generate Noise
		noiseTextureShader.bind();
		glBindImageTexture(0, noiseTexture.getHandle(), 0, false, 0, GL_WRITE_ONLY, GL_RGBA16F);
		noiseTextureShader.updateUniforms(randomx, randomy);
		glDispatchCompute(1,1,1);
	}
	
	public void render(GLTexture worldPositionSceneTexture,
			GLTexture normalSceneTexture){
		
		ssaoShader.bind();
		glBindImageTexture(0, ssaoSceneTexture.getHandle(), 0, false, 0, GL_WRITE_ONLY, GL_R16F);
		glBindImageTexture(1, worldPositionSceneTexture.getHandle(), 0, false, 0, GL_READ_ONLY, GL_RGBA32F);
		glBindImageTexture(2, normalSceneTexture.getHandle(), 0, false, 0, GL_READ_ONLY, GL_RGBA16F);
		glBindImageTexture(3, noiseTexture.getHandle(), 0, false, 0, GL_READ_ONLY, GL_RGBA16F);
		ssaoShader.updateUniforms(BaseContext.getCamera().getViewMatrix(),
								  BaseContext.getCamera().getProjectionMatrix(),
								  width, height, kernel);
		glDispatchCompute(width/16,height/16,1);
		
		blurShader.bind();
		glBindImageTexture(0, ssaoBlurSceneTexture.getHandle(), 0, false, 0, GL_WRITE_ONLY, GL_R16F);
		glBindImageTexture(1, ssaoSceneTexture.getHandle(), 0, false, 0, GL_READ_ONLY, GL_R16F);
		glDispatchCompute(width/16,height/16,1);
	}

}
