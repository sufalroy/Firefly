package org.firefly.gl.engine;

import org.firefly.core.RenderEngine;
import org.firefly.core.gl.util.GLUtil;

public class GLDeferredEngine extends RenderEngine {

    @Override
    public void init(){
        super.init();
    }

    @Override
    public void render() {
        GLUtil.clearScreen();
    }

    @Override
    public void update() {

        super.update();
    }

    @Override
    public void shutdown() {

        super.shutdown();

    }
}
