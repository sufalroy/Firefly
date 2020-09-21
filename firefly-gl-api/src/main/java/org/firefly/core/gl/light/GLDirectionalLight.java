package org.firefly.core.gl.light;

import org.firefly.core.gl.memory.GLUniformBuffer;
import org.firefly.core.light.DirectionalLight;
import org.firefly.core.util.Constants;

import lombok.Getter;

@Getter
public class GLDirectionalLight extends DirectionalLight{
	
	private GLUniformBuffer ubo_light;
	private GLUniformBuffer ubo_matrices;
	
	public GLDirectionalLight(){
		
		super(); 
		
		ubo_light = new GLUniformBuffer();
		getUbo_light().setBinding_point_index(Constants.DirectionalLightUniformBlockBinding);
		getUbo_light().bindBufferBase();
		getUbo_light().allocate(getLightBufferSize());
		getUbo_light().updateData(getFloatBufferLight(), getLightBufferSize());
		
		ubo_matrices = new GLUniformBuffer();
		getUbo_matrices().setBinding_point_index(Constants.LightMatricesUniformBlockBinding);
		getUbo_matrices().bindBufferBase();
		getUbo_matrices().allocate(getMatricesBufferSize());
	
		getUbo_matrices().updateData(getFloatBufferMatrices(), getMatricesBufferSize());
	}
	
	public void updateLightUbo(){
		
		getUbo_light().updateData(getFloatBufferLight(), getLightBufferSize());
	}
	
	public void updateMatricesUbo(){
		getUbo_matrices().updateData(getFloatBufferMatrices(), getMatricesBufferSize());
	}
	
}
