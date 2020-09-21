package org.firefly.gl.engine.deferred;

import static org.lwjgl.opengl.GL11.glFinish;
import static org.lwjgl.opengl.GL15.GL_READ_ONLY;
import static org.lwjgl.opengl.GL15.GL_WRITE_ONLY;
import static org.lwjgl.opengl.GL30.GL_R16F;
import static org.lwjgl.opengl.GL30.GL_R8;
import static org.lwjgl.opengl.GL30.GL_RGBA16F;
import static org.lwjgl.opengl.GL30.GL_RGBA32F;
import static org.lwjgl.opengl.GL42.glBindImageTexture;
import static org.lwjgl.opengl.GL43.glDispatchCompute;

import org.firefly.core.context.BaseContext;
import org.firefly.core.gl.texture.GLTexture;
import org.firefly.core.gl.wrapper.texture.TextureImage2D;
import org.firefly.core.image.Image.ImageFormat;
import org.firefly.core.image.Image.SamplerFilter;
import org.firefly.core.image.Image.TextureWrapMode;

import lombok.Getter;

public class DeferredLighting {

	@Getter
	private GLTexture deferredLightingSceneTexture;
	private DeferredLightingShader shader;
	private int width;
	private int height;
	
	public DeferredLighting(int width, int height) {
		
		this.width = width;
		this.height = height;
		
		shader = DeferredLightingShader.getInstance();

		deferredLightingSceneTexture = new TextureImage2D(width, height, 
				ImageFormat.RGBA16FLOAT, SamplerFilter.Bilinear, TextureWrapMode.ClampToEdge);
	}
	
	public void render(GLTexture sampleCoverageMask, GLTexture ssaoBlurTexture, GLTexture shadowmap,
			GLTexture albedoTexture, GLTexture worldPositionTexture, GLTexture normalTexture,
			GLTexture specularEmissionDiffuseSsaoBloomTexture){
		
		glFinish();
		
		shader.bind();
		glBindImageTexture(0, deferredLightingSceneTexture.getHandle(), 0, false, 0, GL_WRITE_ONLY, GL_RGBA16F);
		glBindImageTexture(2, albedoTexture.getHandle(), 0, false, 0, GL_READ_ONLY, GL_RGBA16F);
		glBindImageTexture(3, worldPositionTexture.getHandle(), 0, false, 0, GL_READ_ONLY, GL_RGBA32F);
		glBindImageTexture(4, normalTexture.getHandle(), 0, false, 0, GL_READ_ONLY, GL_RGBA16F);
		glBindImageTexture(5, specularEmissionDiffuseSsaoBloomTexture.getHandle(), 0, false, 0, GL_READ_ONLY, GL_RGBA16F);
		glBindImageTexture(6, sampleCoverageMask.getHandle(), 0, false, 0, GL_READ_ONLY, GL_R8);
		if (BaseContext.getConfig().isSsaoEnabled())
			glBindImageTexture(7, ssaoBlurTexture.getHandle(), 0, false, 0, GL_READ_ONLY, GL_R16F);
		shader.updateUniforms(shadowmap);
		glDispatchCompute(width/16, height/16,1);
	}

}
