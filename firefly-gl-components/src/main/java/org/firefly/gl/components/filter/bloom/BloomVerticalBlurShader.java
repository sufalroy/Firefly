package org.firefly.gl.components.filter.bloom;

import org.firefly.core.gl.pipeline.GLShaderProgram;
import org.firefly.core.util.ResourceLoader;

public class BloomVerticalBlurShader extends GLShaderProgram{

	private static BloomVerticalBlurShader instance = null;
	
	public static BloomVerticalBlurShader getInstance() 
	{
	    if(instance == null) 
	    {
	    	instance = new BloomVerticalBlurShader();
	    }
	      return instance;
	}
	
	protected BloomVerticalBlurShader()
	{
		super();
		
		addComputeShader(ResourceLoader.loadShader("shaders/filter/bloom/bloom_verticalGaussianBlur.comp"));
		compileShader();
	}
}
