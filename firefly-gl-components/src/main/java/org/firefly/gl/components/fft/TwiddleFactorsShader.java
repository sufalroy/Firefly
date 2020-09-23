package org.firefly.gl.components.fft;

import org.firefly.core.gl.pipeline.GLShaderProgram;
import org.firefly.core.util.ResourceLoader;

public class TwiddleFactorsShader extends GLShaderProgram{

	private static TwiddleFactorsShader instance = null;
	
	public static TwiddleFactorsShader getInstance() 
	{
	    if(instance == null) 
	    {
	    	instance = new TwiddleFactorsShader();
	    }
	      return instance;
	}
	
	protected TwiddleFactorsShader()
	{
		super();
		
		addComputeShader(ResourceLoader.loadShader("shaders/fft/twiddleFactors.comp"));
		compileShader();
		
		addUniform("N");
	}
	

	public void updateUniforms(int N)
	{
		setUniformi("N", N);
	}
}
