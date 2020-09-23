package org.firefly.sandbox.gl.openworld;

import org.firefly.core.gl.context.GLContext;
import org.firefly.gl.components.atmosphere.Atmosphere;
import org.firefly.gl.components.terrain.shader.TerrainShader;
import org.firefly.gl.components.terrain.shader.TerrainShadowShader;
import org.firefly.gl.components.terrain.shader.TerrainWireframeShader;
import org.firefly.gl.engine.GLDeferredEngine;
import org.firefly.sandbox.gl.openworld.terrain.Terrain;

public class OpenWorld {

    public static void main(String[] args) {
        GLContext.create();

        GLDeferredEngine renderEngine = new GLDeferredEngine();
        renderEngine.init();

        renderEngine.getSceneGraph().addObject(new Atmosphere());
        renderEngine.getSceneGraph().setTerrain(new Terrain(TerrainShader.getInstance(),
                TerrainWireframeShader.getInstance(), TerrainShadowShader.getInstance()));


        GLContext.setRenderEngine(renderEngine);
        GLContext.getCoreEngine().start();
    }
}
