package org.firefly.gl.components.filter.lightscattering;

import org.firefly.core.context.BaseContext;
import org.firefly.core.gl.pipeline.GLShaderProgram;
import org.firefly.core.math.Matrix4f;
import org.firefly.core.util.Constants;
import org.firefly.core.util.ResourceLoader;

public class SunLightScatteringShader extends GLShaderProgram{
	
	private static SunLightScatteringShader instance = null;
	
	public static SunLightScatteringShader getInstance() 
	{
	    if(instance == null) 
	    {
	    	instance = new SunLightScatteringShader();
	    }
	      return instance;
	}
	
	protected SunLightScatteringShader()
	{
		super();
		
		addComputeShader(ResourceLoader.loadShader("shaders/filter/light_scattering/lightScattering.comp"));
		
		compileShader();
		
		addUniform("sunWorldPosition");
		addUniform("windowWidth");
		addUniform("windowHeight");
		addUniform("viewProjectionMatrix");
		addUniform("num_samples");
		addUniform("decay");
	}
	
	public void updateUniforms(int windowWidth, int windowHeight, Matrix4f viewProjectionMatrix) {
		
		setUniformf("windowWidth", windowWidth);
		setUniformf("windowHeight", windowHeight);
		setUniform("viewProjectionMatrix", viewProjectionMatrix);
		setUniform("sunWorldPosition", BaseContext.getConfig().getSunPosition().mul(-Constants.ZFAR));
		setUniformi("num_samples", BaseContext.getConfig().getLightscatteringSampleCount());
		setUniformf("decay", BaseContext.getConfig().getLightscatteringDecay());
	}
}
