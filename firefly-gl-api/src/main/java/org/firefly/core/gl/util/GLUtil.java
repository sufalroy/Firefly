package org.firefly.core.gl.util;

import static org.lwjgl.opengl.GL11.*;

public class GLUtil {

    public static void init(){

    }

    public static void clearScreen(){
        glClearColor(0.1f,0.1f,0.1f,1.0f);
        glClearDepth(1.0);
        glClear(GL_COLOR_BUFFER_BIT | GL_DEPTH_BUFFER_BIT);
    }
}
