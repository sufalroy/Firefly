package org.firefly.gl.engine.antialiasing;

import static org.lwjgl.opengl.GL13.GL_TEXTURE0;
import static org.lwjgl.opengl.GL13.glActiveTexture;

import org.firefly.core.context.BaseContext;
import org.firefly.core.gl.pipeline.GLShaderProgram;
import org.firefly.core.gl.texture.GLTexture;
import org.firefly.core.util.ResourceLoader;

public class FXAAShader extends GLShaderProgram{

	private static FXAAShader instance = null;
	
	public static FXAAShader getInstance() 
	{
		if(instance == null) 
		{
			instance = new FXAAShader();
		}
		return instance;
	}
		
	protected FXAAShader()
	{
		super();
		
		addComputeShader(ResourceLoader.loadShader("shaders/fxaa.comp"));
		
		compileShader();
		
		addUniform("sceneSampler");
		addUniform("width");
		addUniform("height");
	} 
	
	public void updateUniforms(GLTexture sceneTexture){
		
		glActiveTexture(GL_TEXTURE0);
		sceneTexture.bind();
		sceneTexture.bilinearFilter();
		setUniformi("sceneSampler", 0);
		setUniformf("width", (float) BaseContext.getWindow().getWidth());
		setUniformf("height", (float) BaseContext.getWindow().getHeight());
	}
}
