package org.firefly.gl.engine.antialiasing;

import static org.lwjgl.opengl.GL15.GL_READ_ONLY;
import static org.lwjgl.opengl.GL15.GL_WRITE_ONLY;
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

public class SampleCoverage {

	@Getter
	private GLTexture sampleCoverageMask;
	@Getter
	private GLTexture lightScatteringMaskSingleSample;
	@Getter
	private GLTexture specularEmissionBloomMaskSingleSample;
	
	private SampleCoverageShader shader;
	
	public SampleCoverage(int width, int height) {
		
		shader = SampleCoverageShader.getInstance();
		
		sampleCoverageMask = new TextureImage2D(width, height,
				ImageFormat.R8, SamplerFilter.Nearest, TextureWrapMode.ClampToEdge);
		lightScatteringMaskSingleSample = new TextureImage2D(width, height,
				ImageFormat.RGBA16FLOAT, SamplerFilter.Nearest, TextureWrapMode.ClampToEdge);
		specularEmissionBloomMaskSingleSample = new TextureImage2D(width, height,
				ImageFormat.RGBA16FLOAT, SamplerFilter.Nearest, TextureWrapMode.ClampToEdge);
	}
	
	public void render(GLTexture worldPositionTexture, GLTexture lightScatteringMask,
			GLTexture specularEmissionBloomMask) {
		
		shader.bind();
		glBindImageTexture(0, sampleCoverageMask.getHandle(), 0, false, 0, GL_WRITE_ONLY, GL_R8);
		glBindImageTexture(1, worldPositionTexture.getHandle(), 0, false, 0, GL_READ_ONLY, GL_RGBA32F);
		glBindImageTexture(2, lightScatteringMaskSingleSample.getHandle(), 0, false, 0, GL_WRITE_ONLY, GL_RGBA16F);
		glBindImageTexture(3, lightScatteringMask.getHandle(), 0, false, 0, GL_READ_ONLY, GL_RGBA16F);
		glBindImageTexture(4, specularEmissionBloomMaskSingleSample.getHandle(), 0, false, 0, GL_WRITE_ONLY, GL_RGBA16F);
		glBindImageTexture(5, specularEmissionBloomMask.getHandle(), 0, false, 0, GL_READ_ONLY, GL_RGBA16F);
		shader.updateUniforms();
		glDispatchCompute(BaseContext.getConfig().getFrameWidth()/16, BaseContext.getConfig().getFrameHeight()/16, 1);	
	}

}
