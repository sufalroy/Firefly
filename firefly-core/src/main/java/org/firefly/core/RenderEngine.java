package org.firefly.core;

import lombok.Getter;
import org.firefly.core.context.BaseContext;
import org.firefly.core.context.Config;
import org.firefly.core.scenegraph.Camera;
import org.firefly.core.scenegraph.Scenegraph;

public abstract class RenderEngine {

    @Getter
    protected Scenegraph sceneGraph;
    protected Config config;
    protected Camera camera;

    public void init() {
        sceneGraph = new Scenegraph();
        config = BaseContext.getConfig();
        camera = BaseContext.getCamera();
        camera.init();
    }

    public abstract void render();

    public void update() {
        camera.update();
        sceneGraph.update();
        sceneGraph.updateLights();
    }

    public void shutdown() {
        sceneGraph.shutdown();
    }

}
