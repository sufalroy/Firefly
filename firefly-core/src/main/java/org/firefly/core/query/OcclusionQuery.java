package org.firefly.core.query;

import org.firefly.core.light.Light;
import org.firefly.core.scenegraph.Renderable;

import java.nio.IntBuffer;

public abstract class OcclusionQuery {

    private int id;
    private IntBuffer buffer;
    private int occlusionFactor;

    public abstract void doQuery(Renderable object);

    public abstract void doQuery(Light light);

    public abstract void delete();

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getOcclusionFactor() {
        return occlusionFactor;
    }

    public void setOcclusionFactor(int occlusionFactor) {
        this.occlusionFactor = occlusionFactor;
    }

    public IntBuffer getBuffer() {
        return buffer;
    }

    public void setBuffer(IntBuffer buffer) {
        this.buffer = buffer;
    }
}
