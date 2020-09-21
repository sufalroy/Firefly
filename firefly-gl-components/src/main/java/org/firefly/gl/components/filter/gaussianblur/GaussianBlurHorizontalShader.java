package org.firefly.gl.components.filter.gaussianblur;

import org.firefly.core.gl.pipeline.GLShaderProgram;
import org.firefly.core.util.ResourceLoader;

public class GaussianBlurHorizontalShader extends GLShaderProgram{
	
	private static GaussianBlurHorizontalShader instance = null;
	
	public static GaussianBlurHorizontalShader getInstance() 
	{
	    if(instance == null) 
	    {
	    	instance = new GaussianBlurHorizontalShader();
	    }
	      return instance;
	}
	
	protected GaussianBlurHorizontalShader()
	{
		super();
		
		addComputeShader(ResourceLoader.loadShader("shaders/filter/gaussian_blur/horizontal_gaussian_blur.comp"));
		
		compileShader();
	}

}
