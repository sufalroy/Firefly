package org.firefly.gl.components.atmosphere;

import org.firefly.core.context.BaseContext;
import org.firefly.core.gl.memory.GLMeshVBO;
import org.firefly.core.gl.scenegraph.GLRenderInfo;
import org.firefly.core.gl.util.GLAssimpModelLoader;
import org.firefly.core.gl.wrapper.parameter.CullFaceDisable;
import org.firefly.core.model.Mesh;
import org.firefly.core.scenegraph.NodeComponentType;
import org.firefly.core.scenegraph.Renderable;
import org.firefly.core.util.Constants;
import org.firefly.core.util.ProceduralTexturing;


public class Atmosphere extends Renderable{
	
	public Atmosphere()
	{
		getWorldTransform().setLocalScaling(Constants.ZFAR, Constants.ZFAR, Constants.ZFAR);
		getWorldTransform().setLocalTranslation(0,0,0);

		Mesh mesh = GLAssimpModelLoader.loadModel("models/obj/dome", "dome.obj").get(0).getMesh();
		ProceduralTexturing.dome(mesh);
		GLMeshVBO meshBuffer = new GLMeshVBO();
		meshBuffer.addData(mesh);
		GLRenderInfo renderInfo = new GLRenderInfo(BaseContext.getConfig().isAtmosphericScatteringEnable()
				? AtmosphericScatteringShader.getInstance() : AtmosphereShader.getInstance(),
				new CullFaceDisable(), meshBuffer);
		
		addComponent(NodeComponentType.MAIN_RENDERINFO, renderInfo);
		addComponent(NodeComponentType.WIREFRAME_RENDERINFO, renderInfo);
	}
	
	public void render() {
		
		// prevent refraction rendering of atmosphere when camera is above water surface
		if (BaseContext.getConfig().isRenderRefraction() && !BaseContext.getConfig().isRenderUnderwater()){
			return;
		}
		else {
			super.render();
		}
	}

}
