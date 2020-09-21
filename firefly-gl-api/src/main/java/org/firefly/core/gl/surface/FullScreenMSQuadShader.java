package org.firefly.core.gl.surface;

import static org.lwjgl.opengl.GL13.GL_TEXTURE0;
import static org.lwjgl.opengl.GL13.glActiveTexture;

import org.firefly.core.context.BaseContext;
import org.firefly.core.gl.pipeline.GLShaderProgram;
import org.firefly.core.gl.texture.GLTexture;
import org.firefly.core.util.ResourceLoader;

public class FullScreenMSQuadShader extends GLShaderProgram{

	private static FullScreenMSQuadShader instance = null;
	
	public static FullScreenMSQuadShader getInstance() 
	{
	    if(instance == null) 
	    {
	    	instance = new FullScreenMSQuadShader();
	    }
	    return instance;
	}
	
	protected FullScreenMSQuadShader()
	{
		super();

		addVertexShader(ResourceLoader.loadShader("shaders/quad/quad_VS.glsl"));
		addFragmentShader(ResourceLoader.loadShader("shaders/quad/quadMS_FS.glsl"));
		compileShader();
		
		addUniform("texture");
		addUniform("width");
		addUniform("height");
		addUniform("multisamples");
	}
	
	public void updateUniforms(GLTexture texture)
	{
		glActiveTexture(GL_TEXTURE0);
		texture.bind();
		setUniformi("texture", 0);
		
		setUniformi("width", BaseContext.getConfig().getFrameWidth());
		setUniformi("height", BaseContext.getConfig().getFrameHeight());
		setUniformi("multisamples", BaseContext.getConfig().getMultisampling_sampleCount());		
	}
}
