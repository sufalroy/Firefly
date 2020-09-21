package org.firefly.gl.components.filter.gaussianblur;

import org.firefly.core.gl.pipeline.GLShaderProgram;
import org.firefly.core.util.ResourceLoader;

public class GaussianBlurVerticalShader extends GLShaderProgram{

	private static GaussianBlurVerticalShader instance = null;
	
	public static GaussianBlurVerticalShader getInstance() 
	{
	    if(instance == null) 
	    {
	    	instance = new GaussianBlurVerticalShader();
	    }
	      return instance;
	}
	
	protected GaussianBlurVerticalShader()
	{
		super();
		
		addComputeShader(ResourceLoader.loadShader("shaders/filter/gaussian_blur/vertical_gaussian_blur.comp"));
		
		compileShader();
	}
}
