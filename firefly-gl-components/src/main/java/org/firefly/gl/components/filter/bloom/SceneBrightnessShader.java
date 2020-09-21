package org.firefly.gl.components.filter.bloom;

import org.firefly.core.gl.pipeline.GLShaderProgram;
import org.firefly.core.util.ResourceLoader;

public class SceneBrightnessShader extends GLShaderProgram{
	
	private static SceneBrightnessShader instance = null;
	
	public static SceneBrightnessShader getInstance() 
	{
	    if(instance == null) 
	    {
	    	instance = new SceneBrightnessShader();
	    }
	      return instance;
	}
	
	protected SceneBrightnessShader()
	{
		super();
		
		addComputeShader(ResourceLoader.loadShader("shaders/filter/bloom/brightness.comp"));
		compileShader();
	}
}
