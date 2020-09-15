package org.firefly.sandbox.gl.openworld;

import org.firefly.core.gl.context.GLContext;
import org.firefly.gl.engine.GLDeferredEngine;

public class OpenWorld {

    public static void main(String[] args) {
        GLContext.create();

        GLDeferredEngine renderEngine = new GLDeferredEngine();
        renderEngine.init();

        GLContext.setRenderEngine(renderEngine);
        GLContext.getCoreEngine().start();

    }
}
