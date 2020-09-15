package org.firefly.core.platform;

import org.firefly.core.util.ResourceLoader;
import org.lwjgl.glfw.GLFWImage;

import java.nio.ByteBuffer;

import static org.lwjgl.glfw.GLFW.glfwSetWindowIcon;

public abstract class Window {

    private long id;
    private int width, height;
    private String title;

    public abstract void create();

    public abstract void show();

    public abstract void draw();

    public abstract void shutdown();

    public abstract boolean isCloseRequested();

    public abstract void resize(int x, int y);

    public Window(String title, int width, int height){
        this.width = width;
        this.height = height;
        this.title = title;
    }

    public void setIcon(String path){

        ByteBuffer bufferedImage = ResourceLoader.loadImageToByteBuffer(path);
        GLFWImage image = GLFWImage.malloc();
        image.set(32, 32, bufferedImage);

        GLFWImage.Buffer images = GLFWImage.malloc(1);
        images.put(0, image);
        glfwSetWindowIcon(getId(), images);
    }

    public void setId(long id) {
        this.id = id;
    }

    public void setWidth(int width) {
        this.width = width;
    }

    public void setHeight(int height) {
        this.height = height;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public long getId() {
        return id;
    }

    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }

    public String getTitle() {
        return title;
    }
}
