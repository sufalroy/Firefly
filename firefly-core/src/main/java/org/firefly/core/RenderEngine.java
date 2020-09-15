package org.firefly.core;

import lombok.Getter;
import org.firefly.core.context.BaseContext;
import org.firefly.core.context.Config;

public abstract class RenderEngine {

    @Getter
    protected Config config;

    public void init() {
        config = BaseContext.getConfig();
    }

    public abstract void render();

    public void update() {

    }

    public void shutdown() {

    }

}
