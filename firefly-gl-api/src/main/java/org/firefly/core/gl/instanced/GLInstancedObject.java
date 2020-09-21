package org.firefly.core.gl.instanced;

import java.nio.FloatBuffer;

import org.firefly.core.context.BaseContext;
import org.firefly.core.gl.memory.GLUniformBuffer;
import org.firefly.core.instanced.InstancedObject;
import org.firefly.core.math.Matrix4f;
import org.firefly.core.util.BufferUtil;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class GLInstancedObject extends InstancedObject{

	private GLUniformBuffer modelMatricesBuffer;
	private GLUniformBuffer worldMatricesBuffer;
	
	public void initMatricesBuffers() {
		
		int buffersize = Float.BYTES * 16 * getInstanceCount();
		
		setModelMatricesBuffer(new GLUniformBuffer());
		getModelMatricesBuffer().allocate(buffersize);
		
		setWorldMatricesBuffer(new GLUniformBuffer());
		getWorldMatricesBuffer().allocate(buffersize);	
		
		int size = Float.BYTES * 16 * getInstanceCount();
		
		FloatBuffer worldMatricesFloatBuffer = BufferUtil.createFloatBuffer(size);
		FloatBuffer modelMatricesFloatBuffer = BufferUtil.createFloatBuffer(size);
		
		for(Matrix4f matrix : getWorldMatrices()){
			worldMatricesFloatBuffer.put(BufferUtil.createFlippedBuffer(matrix));
		}
		for(Matrix4f matrix : getModelMatrices()){
			modelMatricesFloatBuffer.put(BufferUtil.createFlippedBuffer(matrix));
		}
		
		getWorldMatricesBuffer().updateData(worldMatricesFloatBuffer, size);
		getModelMatricesBuffer().updateData(modelMatricesFloatBuffer, size);
	}
	
	public void renderShadows(){

		getWorldMatricesBuffer().bindBufferBase(0);
		if (getHighPolyInstanceCount().getValue() == 0){
			renderLowPolyShadows();
		}
		else{
			super.renderShadows();
		}
	}
	
	public void render() {
		
		getWorldMatricesBuffer().bindBufferBase(0);
		getModelMatricesBuffer().bindBufferBase(1);
		

		if (BaseContext.getConfig().isRenderRefraction()){
			renderLowPoly();
		}
		else if (BaseContext.getConfig().isRenderReflection()){
			renderLowPoly();
		}
		else{
			if (getHighPolyInstanceCount().getValue() == 0){
				renderLowPoly();
			}
			else{
				super.render();
			}
		}
	}
	
	public void renderWireframe(){

		getWorldMatricesBuffer().bindBufferBase(0);
		getWorldMatricesBuffer().bindBufferBase(1);

		super.renderWireframe();
	}
}
