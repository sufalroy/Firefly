package org.firefly.gl.components.filter.contrast;

import org.firefly.core.gl.pipeline.GLShaderProgram;
import org.firefly.core.util.ResourceLoader;

public class ContrastShader extends GLShaderProgram{

	private static ContrastShader instance = null;

	public static ContrastShader getInstance() 
	{
	    if(instance == null) 
	    {
	    	instance = new ContrastShader();
	    }
	      return instance;
	}
	
	protected ContrastShader()
	{
		super();
		
		addComputeShader(ResourceLoader.loadShader("shaders/filter/contrast/Contrast_CS.glsl"));
		
		compileShader();
		
		addUniform("contrastFactor");
		addUniform("brightnessFactor");
	}
	
	public void updateUniforms(float contrastFactor, float brightnessFactor){
		setUniformf("contrastFactor", contrastFactor);
		setUniformf("brightnessFactor", brightnessFactor);
	}
}
