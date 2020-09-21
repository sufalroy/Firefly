package org.firefly.gl.components.filter.ssao;

import org.firefly.core.gl.pipeline.GLShaderProgram;
import org.firefly.core.util.ResourceLoader;

public class SSAOBlurShader extends GLShaderProgram{

	private static SSAOBlurShader instance = null;
	
	public static SSAOBlurShader getInstance()
	{
		if (instance == null){
			
			instance = new SSAOBlurShader();
		}
		return instance;
	}
	
	protected SSAOBlurShader(){
		
		super();
		
		addComputeShader(ResourceLoader.loadShader("shaders/filter/ssao/ssao_blur.comp"));
		compileShader();
	}
}
