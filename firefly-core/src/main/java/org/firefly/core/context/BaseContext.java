package org.firefly.core.context;

import lombok.Getter;
import lombok.Setter;
import org.firefly.core.CoreEngine;
import org.firefly.core.RenderEngine;
import org.firefly.core.platform.GLFWInput;
import org.firefly.core.platform.Window;
import org.firefly.core.scenegraph.Camera;

public abstract class BaseContext {

    @Getter
    protected static Config config;
    @Getter
    protected static GLFWInput input;
    @Getter
    protected static Camera camera;
    @Getter
    protected static Window window;
    @Getter
    protected static CoreEngine coreEngine;
    @Getter
    @Setter
    protected static RenderEngine renderEngine;

    public static void init(){

        config = new Config();
        input = new GLFWInput();
        coreEngine = new CoreEngine();
    }
}
