package org.firefly.core.platform;

import org.firefly.core.math.Vec2f;

public interface Input {

    public void create(long windowId);
    public void update();
    public void shutdown();

    public boolean isKeyPressed(int key);
    public boolean isKeyHolding(int key);
    public boolean isKeyReleased(int key);
    public boolean isMouseButtonPressed(int button);
    public boolean isMouseButtonHolding(int button);
    public boolean isMouseButtonReleased(int button);

    public float getScrollOffset();
    public Vec2f getCursorPosition();
    public Vec2f getLockedCursorPosition();
}
