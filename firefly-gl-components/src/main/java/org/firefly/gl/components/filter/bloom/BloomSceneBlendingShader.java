package org.firefly.gl.components.filter.bloom;

import org.firefly.core.gl.pipeline.GLShaderProgram;
import org.firefly.core.util.ResourceLoader;

public class BloomSceneBlendingShader extends GLShaderProgram{

	private static BloomSceneBlendingShader instance = null;
	
	public static BloomSceneBlendingShader getInstance() 
	{
	    if(instance == null) 
	    {
	    	instance = new BloomSceneBlendingShader();
	    }
	      return instance;
	}
	
	protected BloomSceneBlendingShader()
	{
		super();
		
		addComputeShader(ResourceLoader.loadShader("shaders/filter/bloom/bloomSceneBlending.comp"));
		
		compileShader();
	}
}
