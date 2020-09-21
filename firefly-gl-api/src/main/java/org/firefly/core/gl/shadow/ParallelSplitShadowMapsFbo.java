package org.firefly.core.gl.shadow;

import static org.lwjgl.opengl.GL11.GL_NONE;
import static org.lwjgl.opengl.GL20.glDrawBuffers;
import static org.lwjgl.opengl.GL30.GL_DEPTH_ATTACHMENT;
import static org.lwjgl.opengl.GL30.GL_FRAMEBUFFER;
import static org.lwjgl.opengl.GL32.glFramebufferTexture;

import org.firefly.core.context.BaseContext;
import org.firefly.core.gl.framebuffer.GLFramebuffer;
import org.firefly.core.gl.pipeline.RenderParameter;
import org.firefly.core.gl.texture.GLTexture;
import org.firefly.core.gl.wrapper.parameter.ShadowConfig;
import org.firefly.core.gl.wrapper.texture.TextureImage2DArrray;
import org.firefly.core.image.Image.ImageFormat;
import org.firefly.core.image.Image.SamplerFilter;
import org.firefly.core.image.Image.TextureWrapMode;
import org.firefly.core.util.Constants;

import lombok.Getter;

@Getter
public class ParallelSplitShadowMapsFbo {

	private GLFramebuffer fbo;
	private GLTexture depthMap;
	private RenderParameter config;

	public ParallelSplitShadowMapsFbo(){
		
		config = new ShadowConfig();
		
		depthMap = new TextureImage2DArrray(BaseContext.getConfig().getShadowMapResolution(),
				BaseContext.getConfig().getShadowMapResolution(), Constants.PSSM_SPLITS,
				ImageFormat.DEPTH32FLOAT, SamplerFilter.Bilinear, TextureWrapMode.ClampToEdge); 
		
		fbo = new GLFramebuffer();
		fbo.bind();
		glFramebufferTexture(GL_FRAMEBUFFER,
				GL_DEPTH_ATTACHMENT,
				depthMap.getHandle(),
				0);
		glDrawBuffers(GL_NONE);
		fbo.checkStatus();
		fbo.unbind();	
	}

}
