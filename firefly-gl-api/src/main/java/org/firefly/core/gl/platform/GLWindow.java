package org.firefly.core.gl.platform;


import org.firefly.core.context.BaseContext;
import org.firefly.core.gl.context.GLContext;
import org.firefly.core.platform.Window;
import org.lwjgl.opengl.GL;
import org.lwjgl.opengl.WGLEXTSwapControl;

import static org.lwjgl.glfw.GLFW.*;
import static org.lwjgl.opengl.GL11C.GL_TRUE;

public class GLWindow extends Window {
    public GLWindow() {
        super(GLContext.getConfig().getDisplayTitle(),
                GLContext.getConfig().getWindowWidth(),
                GLContext.getConfig().getWindowHeight());
    }

    @Override
    public void create() {
        glfwWindowHint(GLFW_RESIZABLE, GL_TRUE);
        glfwWindowHint(GLFW_CONTEXT_VERSION_MAJOR, 4);
        glfwWindowHint(GLFW_CONTEXT_VERSION_MINOR, 3);
        glfwWindowHint(GLFW_OPENGL_PROFILE, GLFW_OPENGL_CORE_PROFILE);

        setId(glfwCreateWindow(getWidth(), getHeight(), getTitle(), 0, 0));

        if(getId() == 0){
            throw new RuntimeException("Failed to create window");
        }

        setIcon("");

        glfwMakeContextCurrent(getId());

        glfwSwapInterval(0);

        if(BaseContext.getConfig().isGlfwGLVSync()){
            WGLEXTSwapControl.wglSwapIntervalEXT(1);
            glfwSwapInterval(1);
        }

        GL.createCapabilities();
    }

    @Override
    public void show() {
        glfwShowWindow(getId());
    }

    @Override
    public void draw() {
        glfwSwapBuffers(getId());
    }

    @Override
    public void shutdown() {
        glfwDestroyWindow(getId());
    }

    @Override
    public boolean isCloseRequested() {
        return glfwWindowShouldClose(getId());
    }

    @Override
    public void resize(int x, int y) {
        glfwSetWindowSize(getId(), x, y);
        setHeight(y);
        setWidth(x);
        BaseContext.getConfig().setWindowWidth(x);
        BaseContext.getConfig().setWindowHeight(y);
        // TODO set camera projection
    }
}
