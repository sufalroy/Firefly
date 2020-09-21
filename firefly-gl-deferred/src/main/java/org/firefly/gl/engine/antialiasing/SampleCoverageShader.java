package org.firefly.gl.engine.antialiasing;

import org.firefly.core.context.BaseContext;
import org.firefly.core.gl.pipeline.GLShaderProgram;
import org.firefly.core.util.ResourceLoader;

public class SampleCoverageShader extends GLShaderProgram{
	
	private static SampleCoverageShader instance = null;

	public static SampleCoverageShader getInstance() 
	{
	    if(instance == null) 
	    {
	    	instance = new SampleCoverageShader();
	    }
	      return instance;
	}
	
	protected SampleCoverageShader()
	{
		super();
		
		addComputeShader(ResourceLoader.loadShader("shaders/sampleCoverage.comp"));
		
		compileShader();
		
		addUniform("multisamples");
	}
	
	public void updateUniforms(){
		
		setUniformi("multisamples", BaseContext.getConfig().getMultisampling_sampleCount());
	}

}
