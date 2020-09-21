package org.firefly.core.gl.context;

import org.firefly.core.gl.framebuffer.GLFrameBufferObject;
import org.firefly.core.gl.memory.GLUniformBuffer;
import org.firefly.core.gl.texture.GLTexture;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class GLResources {
	
	private GLFrameBufferObject primaryFbo;
	private GLTexture sceneDepthMap;

	private GLUniformBuffer GlobalShaderParameters;
}
