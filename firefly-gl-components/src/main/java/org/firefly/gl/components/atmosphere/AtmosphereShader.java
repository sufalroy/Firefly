package org.firefly.gl.components.atmosphere;

import org.firefly.core.context.BaseContext;
import org.firefly.core.gl.pipeline.GLShaderProgram;
import org.firefly.core.scenegraph.Renderable;
import org.firefly.core.util.Constants;
import org.firefly.core.util.ResourceLoader;

public class AtmosphereShader extends GLShaderProgram{

	private static AtmosphereShader instance = null;
	
	public static AtmosphereShader getInstance() 
	{
		if(instance == null) 
		{
			instance = new AtmosphereShader();
		}
		return instance;
	}
		
	protected AtmosphereShader()
	{
		super();

		addVertexShader(ResourceLoader.loadShader("shaders/atmosphere/atmosphere.vert"));
		addFragmentShader(ResourceLoader.loadShader("shaders/atmosphere/atmosphere.frag"));
		compileShader();
			
		addUniform("modelViewProjectionMatrix");
		addUniform("modelMatrix");
		addUniform("m_ViewProjection");
		addUniform("v_SunWorld");
		addUniform("r_Sun");
		addUniform("width");
		addUniform("height");
		addUniform("isReflection");
		addUniform("bloom");
	}
		
	public void updateUniforms(Renderable object)
	{
		setUniform("modelViewProjectionMatrix", object.getWorldTransform().getModelViewProjectionMatrix());
		setUniform("modelMatrix", object.getWorldTransform().getModelMatrix());
		setUniform("m_ViewProjection", BaseContext.getCamera().getOriginViewProjectionMatrix());
		setUniform("v_SunWorld", BaseContext.getConfig().getSunPosition().mul(-Constants.ZFAR));
		setUniformf("r_Sun", BaseContext.getConfig().getSunRadius());
		setUniformi("width", BaseContext.getConfig().getFrameWidth());
		setUniformi("height", BaseContext.getConfig().getFrameHeight());
		setUniformi("isReflection", BaseContext.getConfig().isRenderReflection() ? 1 : 0);
		setUniformf("bloom", BaseContext.getConfig().getAtmosphereBloomFactor());
	}
}
