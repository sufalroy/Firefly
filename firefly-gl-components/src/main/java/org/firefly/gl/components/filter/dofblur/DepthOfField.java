package org.firefly.gl.components.filter.dofblur;

import static org.lwjgl.opengl.GL11.glFinish;
import static org.lwjgl.opengl.GL15.GL_READ_ONLY;
import static org.lwjgl.opengl.GL15.GL_WRITE_ONLY;
import static org.lwjgl.opengl.GL30.GL_RGBA16F;
import static org.lwjgl.opengl.GL42.glBindImageTexture;
import static org.lwjgl.opengl.GL43.glDispatchCompute;

import org.firefly.core.context.BaseContext;
import org.firefly.core.gl.framebuffer.GLFramebuffer;
import org.firefly.core.gl.surface.FullScreenQuad;
import org.firefly.core.gl.texture.GLTexture;
import org.firefly.core.gl.wrapper.texture.TextureImage2D;
import org.firefly.core.gl.wrapper.texture.TextureStorage2D;
import org.firefly.core.image.Image.ImageFormat;
import org.firefly.core.image.Image.SamplerFilter;
import org.firefly.core.image.Image.TextureWrapMode;

import lombok.Getter;

public class DepthOfField {
	
	@Getter
	private GLTexture horizontalBlurSceneTexture;
	@Getter
	private GLTexture verticalBlurSceneTexture;
	
	private DepthOfFieldHorizontalBlurShader horizontalBlurShader;
	private DepthOfFieldVerticalBlurShader verticalBlurShader;
	
	private GLFramebuffer lowResFbo;
	@SuppressWarnings("unused")
	private FullScreenQuad fullScreenQuad;
	@Getter
	private GLTexture downsampledSceneSampler;
		
	public DepthOfField() {
		
		horizontalBlurShader = DepthOfFieldHorizontalBlurShader.getInstance();
		verticalBlurShader = DepthOfFieldVerticalBlurShader.getInstance();
		
		horizontalBlurSceneTexture = new TextureImage2D(BaseContext.getConfig().getFrameWidth(),
				BaseContext.getConfig().getFrameHeight(), ImageFormat.RGBA16FLOAT,
				SamplerFilter.Bilinear, TextureWrapMode.ClampToEdge);
		
		verticalBlurSceneTexture = new TextureImage2D(BaseContext.getConfig().getFrameWidth(),
				BaseContext.getConfig().getFrameHeight(), ImageFormat.RGBA16FLOAT,
				SamplerFilter.Bilinear, TextureWrapMode.ClampToEdge); 
				
				new TextureStorage2D(BaseContext.getConfig().getFrameWidth(),
				BaseContext.getConfig().getFrameHeight(), 1, ImageFormat.RGBA16FLOAT);
		
		downsampledSceneSampler = new TextureImage2D((int)(BaseContext.getConfig().getFrameWidth()/2f),
				(int)(BaseContext.getConfig().getFrameWidth()/2f), ImageFormat.RGBA16FLOAT,
				SamplerFilter.Bilinear, TextureWrapMode.ClampToEdge);
		
		fullScreenQuad = new FullScreenQuad();
		lowResFbo = new GLFramebuffer();
		lowResFbo.bind();
		lowResFbo.createColorTextureAttachment(downsampledSceneSampler.getHandle(), 0);
		lowResFbo.checkStatus();
		lowResFbo.unbind();
	}
	
	public void render(GLTexture depthmap, GLTexture sceneSampler) {
		
//		glFinish();
//		lowResFbo.bind();
//		fullScreenQuad.setTexture(sceneSampler);
//		glViewport(0,0,(int)(BaseContext.getConfig().getX_ScreenResolution()/2f),
//				(int)(BaseContext.getConfig().getY_ScreenResolution()/2f));
//		fullScreenQuad.render();
//		lowResFbo.unbind();
//		glViewport(0,0, BaseContext.getConfig().getX_ScreenResolution(), BaseContext.getConfig().getY_ScreenResolution());
		glFinish();
		
		horizontalBlurShader.bind();
		glBindImageTexture(0, horizontalBlurSceneTexture.getHandle(), 0, false, 0, GL_WRITE_ONLY, GL_RGBA16F);
		horizontalBlurShader.updateUniforms(depthmap, sceneSampler, downsampledSceneSampler);
		glDispatchCompute(BaseContext.getConfig().getFrameWidth()/8, BaseContext.getConfig().getFrameHeight()/8, 1);	
		glFinish();
		
		verticalBlurShader.bind();
		glBindImageTexture(0, horizontalBlurSceneTexture.getHandle(), 0, false, 0, GL_READ_ONLY, GL_RGBA16F);
		glBindImageTexture(1, verticalBlurSceneTexture.getHandle(), 0, false, 0, GL_WRITE_ONLY, GL_RGBA16F);
		verticalBlurShader.updateUniforms(depthmap);
		glDispatchCompute(BaseContext.getConfig().getFrameWidth()/8, BaseContext.getConfig().getFrameHeight()/8, 1);
		glFinish();
	}

}
