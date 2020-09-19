package org.firefly.core.query;

import org.firefly.core.light.Light;

import java.nio.IntBuffer;

public abstract class OcclusionQuery {

    private int id;
    private IntBuffer buffer;
    private int occlusionFactor;

    public abstract void doQuery(Readable object);

    public abstract void doQuery(Light light);

    public abstract void delete();

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public IntBuffer getBuffer() {
        return buffer;
    }

    public void setBuffer(IntBuffer buffer) {
        this.buffer = buffer;
    }

    public int getOcclusionFactor() {
        return occlusionFactor;
    }

    public void setOcclusionFactor(int occlusionFactor) {
        this.occlusionFactor = occlusionFactor;
    }
}
