package org.firefly.core.gl.scenegraph;

import org.firefly.core.gl.memory.VBO;
import org.firefly.core.gl.pipeline.GLShaderProgram;
import org.firefly.core.gl.pipeline.RenderParameter;
import org.firefly.core.scenegraph.NodeComponent;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class GLRenderInfo extends NodeComponent{
	
	private GLShaderProgram shader;
	private RenderParameter config;
	private VBO vbo;
	
	@Override
	public void render(){
		
		config.enable();
		shader.bind();			
		shader.updateUniforms(getParent());
		vbo.draw();
		config.disable();
	}

}
