package org.firefly.gl.engine;

import static org.lwjgl.opengl.GL30.GL_COLOR_ATTACHMENT0;
import static org.lwjgl.opengl.GL30.GL_COLOR_ATTACHMENT1;
import static org.lwjgl.opengl.GL30.GL_COLOR_ATTACHMENT2;
import static org.lwjgl.opengl.GL30.GL_COLOR_ATTACHMENT3;
import static org.lwjgl.opengl.GL30.GL_COLOR_ATTACHMENT4;

import java.nio.IntBuffer;

import org.firefly.core.gl.framebuffer.GLFrameBufferObject;
import org.firefly.core.gl.framebuffer.GLFramebuffer;
import org.firefly.core.gl.texture.GLTexture;
import org.firefly.core.gl.wrapper.texture.TextureImage2D;
import org.firefly.core.image.Image.ImageFormat;
import org.firefly.core.image.Image.SamplerFilter;
import org.firefly.core.image.Image.TextureWrapMode;
import org.firefly.core.util.BufferUtil;

public class OffScreenFbo extends GLFrameBufferObject{

	public OffScreenFbo(int width, int height, int samples) {
		
		GLTexture albedoAttachment = null;
		GLTexture worldPositionAttachment = null;
		GLTexture normalAttachment = null;
		GLTexture specularEmissionDiffuseSsaoBloomAttachment = null;
		GLTexture lightScatteringAttachment = null;
		GLTexture depthAttachment = null;
		
		albedoAttachment = new TextureImage2D(width, height, samples,
				ImageFormat.RGBA16FLOAT, SamplerFilter.Nearest, TextureWrapMode.ClampToEdge);
		worldPositionAttachment = new TextureImage2D(width, height, samples,
				ImageFormat.RGBA32FLOAT, SamplerFilter.Nearest, TextureWrapMode.ClampToEdge);
		normalAttachment = new TextureImage2D(width, height, samples,
				ImageFormat.RGBA16FLOAT, SamplerFilter.Nearest, TextureWrapMode.ClampToEdge);
		specularEmissionDiffuseSsaoBloomAttachment = new TextureImage2D(width, height, samples,
				ImageFormat.RGBA16FLOAT, SamplerFilter.Nearest, TextureWrapMode.ClampToEdge);
		lightScatteringAttachment = new TextureImage2D(width, height, samples,
				ImageFormat.RGBA16FLOAT, SamplerFilter.Nearest, TextureWrapMode.ClampToEdge);
		depthAttachment = new TextureImage2D(width, height, samples,
				ImageFormat.DEPTH32FLOAT, SamplerFilter.Nearest, TextureWrapMode.ClampToEdge);
			
		attachments.put(Attachment.COLOR, albedoAttachment);
		attachments.put(Attachment.POSITION, worldPositionAttachment);
		attachments.put(Attachment.NORMAL, normalAttachment);
		attachments.put(Attachment.SPECULAR_EMISSION_DIFFUSE_SSAO_BLOOM, specularEmissionDiffuseSsaoBloomAttachment);
		attachments.put(Attachment.LIGHT_SCATTERING, lightScatteringAttachment);
		attachments.put(Attachment.DEPTH, depthAttachment);
		
		IntBuffer drawBuffers = BufferUtil.createIntBuffer(5);
		drawBuffers.put(GL_COLOR_ATTACHMENT0);
		drawBuffers.put(GL_COLOR_ATTACHMENT1);
		drawBuffers.put(GL_COLOR_ATTACHMENT2);
		drawBuffers.put(GL_COLOR_ATTACHMENT3);
		drawBuffers.put(GL_COLOR_ATTACHMENT4);
		drawBuffers.flip();
		
		frameBuffer = new GLFramebuffer();
		frameBuffer.bind();
		
		frameBuffer.createColorTextureAttachment(albedoAttachment.getHandle(),0,(samples > 1));
		frameBuffer.createColorTextureAttachment(worldPositionAttachment.getHandle(),1,(samples > 1));
		frameBuffer.createColorTextureAttachment(normalAttachment.getHandle(),2,(samples > 1));
		frameBuffer.createColorTextureAttachment(specularEmissionDiffuseSsaoBloomAttachment.getHandle(),3,(samples > 1));
		frameBuffer.createColorTextureAttachment(lightScatteringAttachment.getHandle(),4,(samples > 1));
		frameBuffer.createDepthTextureAttachment(depthAttachment.getHandle(),(samples > 1));
		
		frameBuffer.setDrawBuffers(drawBuffers);
		frameBuffer.checkStatus();
		frameBuffer.unbind();
	}
}
