package org.firefly.core.platform;

import org.firefly.core.math.Vec2f;
import org.lwjgl.glfw.*;

import java.util.HashSet;
import java.util.Set;

import static org.lwjgl.glfw.GLFW.*;

public class GLFWInput implements Input {

    private Set<Integer> pressedKeys = new HashSet<>();
    private Set<Integer> keysHolding = new HashSet<>();
    private Set<Integer> releasedKeys = new HashSet<>();

    private Set<Integer> pressedMouseButtons = new HashSet<>();
    private Set<Integer> mouseButtonsHolding = new HashSet<>();
    private Set<Integer> releasedMouseButtons = new HashSet<>();

    private Vec2f cursorPosition;
    private Vec2f lockedCursorPosition;
    private float scrollOffset;

    private boolean pause = false;

    private GLFWKeyCallback keyCallback;

    private GLFWCursorPosCallback cursorPosCallback;

    private GLFWMouseButtonCallback mouseButtonCallback;

    private GLFWScrollCallback scrollCallback;

    private GLFWFramebufferSizeCallback framebufferSizeCallback;

    public GLFWInput() {
        cursorPosition = new Vec2f();
    }

    @Override
    public void create(long windowId) {
        glfwSetFramebufferSizeCallback(windowId, (framebufferSizeCallback = new GLFWFramebufferSizeCallback() {
            @Override
            public void invoke(long window, int width, int height) {
                // TODO
            }
        }));

        glfwSetKeyCallback(windowId, (keyCallback = new GLFWKeyCallback() {
            @Override
            public void invoke(long windowId, int key, int scancode, int action, int mods) {
                if(action == GLFW_PRESS){
                    if(!pressedKeys.contains(key)){
                        pressedKeys.add(key);
                        keysHolding.add(key);
                    }
                }

                if(action == GLFW_RELEASE){
                    keysHolding.remove(Integer.valueOf(key));
                    releasedKeys.add(key);
                }
            }
        }));

        glfwSetMouseButtonCallback(windowId, (mouseButtonCallback = new GLFWMouseButtonCallback() {
            @Override
            public void invoke(long windowId, int button, int action, int mods) {
                if((button == 2 || button == 0) && action == GLFW_PRESS){
                    lockedCursorPosition = new Vec2f(cursorPosition);
                    glfwSetInputMode(windowId, GLFW_CURSOR, GLFW_CURSOR_HIDDEN);
                }

                if((button == 2 || button == 0) && action == GLFW_RELEASE){
                    glfwSetInputMode(windowId,  GLFW_CURSOR, GLFW_CURSOR_NORMAL);
                }

                if(action == GLFW_PRESS){
                    if(!pressedMouseButtons.contains(button)){
                        pressedMouseButtons.add(button);
                        mouseButtonsHolding.add(button);
                    }
                }

                if(action == GLFW_RELEASE){
                    releasedMouseButtons.add(button);
                    mouseButtonsHolding.remove(Integer.valueOf(button));
                }
            }
        }));

        glfwSetCursorPosCallback(windowId, (cursorPosCallback = new GLFWCursorPosCallback() {
            @Override
            public void invoke(long windowId, double xpos, double ypos) {
                cursorPosition.setX((float) xpos);
                cursorPosition.setY((float) ypos);
            }
        }));

        glfwSetScrollCallback(windowId, (scrollCallback = new GLFWScrollCallback() {
            @Override
            public void invoke(long windowId, double xoffset, double yoffset) {
                setScrollOffset((float) yoffset);
            }
        }));
    }


    @Override
    public void update() {
        setScrollOffset(0);
        pressedKeys.clear();
        releasedKeys.clear();
        pressedMouseButtons.clear();
        releasedMouseButtons.clear();

        glfwPollEvents();
    }

    @Override
    public void shutdown() {
        keyCallback.free();
        cursorPosCallback.free();
        mouseButtonCallback.free();
        scrollCallback.free();
        framebufferSizeCallback.free();
    }

    @Override
    public boolean isKeyPressed(int key) {
        return pressedKeys.contains(key);
    }

    @Override
    public boolean isKeyHolding(int key) {
        return keysHolding.contains(key);
    }

    @Override
    public boolean isKeyReleased(int key) {
        return releasedKeys.contains(key);
    }

    @Override
    public boolean isMouseButtonPressed(int button) {
        return pressedMouseButtons.contains(button);
    }

    @Override
    public boolean isMouseButtonHolding(int button) {
        return mouseButtonsHolding.contains(button);
    }

    @Override
    public boolean isMouseButtonReleased(int button) {
        return releasedMouseButtons.contains(button);
    }

    public boolean isPause() {
        return pause;
    }

    public void setPause(boolean pause){
        this.pause = pause;
    }

    @Override
    public float getScrollOffset() {
        return scrollOffset;
    }

    public void setScrollOffset(float scrollOffset){
        this.scrollOffset = scrollOffset;
    }

    @Override
    public Vec2f getCursorPosition() {
        return cursorPosition;
    }

    public void setCursorPosition(Vec2f cursorPosition, long windowId){
        this.cursorPosition = cursorPosition;

        glfwSetCursorPos(windowId, cursorPosition.getX(), cursorPosition.getY());
    }

    @Override
    public Vec2f getLockedCursorPosition() {
        return lockedCursorPosition;
    }

    public void  setLockedCursorPosition(Vec2f lockedCursorPosition){
        this.lockedCursorPosition = lockedCursorPosition;
    }

    public Set<Integer> getPressedKeys(){
        return pressedKeys;
    }

    public Set<Integer> getMouseButtonsHolding(){
        return mouseButtonsHolding;
    }

    public Set<Integer> getKeysHolding(){
        return keysHolding;
    }

    public Set<Integer> getPressedMouseButtons(){
        return pressedMouseButtons;
    }
}
