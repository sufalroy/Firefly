package org.firefly.core.gl.instanced;

import org.firefly.core.gl.memory.GLUniformBuffer;
import org.firefly.core.instanced.InstancedCluster;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class GLInstancedCluster extends InstancedCluster{

	private GLUniformBuffer modelMatricesBuffer;
	private GLUniformBuffer worldMatricesBuffer;
}
