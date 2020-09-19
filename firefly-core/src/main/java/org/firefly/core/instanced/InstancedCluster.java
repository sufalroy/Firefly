package org.firefly.core.instanced;

import org.firefly.core.math.Matrix4f;
import org.firefly.core.math.Vec3f;
import org.firefly.core.scenegraph.Node;
import org.firefly.core.util.IntegerReference;

import java.util.ArrayList;
import java.util.List;

public abstract class InstancedCluster extends Node {

    private List<Matrix4f> worldMatrices = new ArrayList<Matrix4f>();
    private List<Matrix4f> modelMatrices = new ArrayList<Matrix4f>();

    private List<Integer> highPolyIndices = new ArrayList<Integer>();
    private List<Integer> lowPolyIndices = new ArrayList<Integer>();

    private IntegerReference highPolyInstances;
    private IntegerReference lowPolyInstances;

    private Vec3f center;

    public void updateUBOs(){};

    public void placeObject(){};


    public List<Integer> getHighPolyIndices(){
        return highPolyIndices;
    }

    public List<Integer> getLowPolyIndices(){
        return lowPolyIndices;
    }

    public void setHighPolyIndices(List<Integer> highPolyIndices) {
        this.highPolyIndices = highPolyIndices;
    }

    public void setLowPolyIndices(List<Integer> lowPolyIndices) {
        this.lowPolyIndices = lowPolyIndices;
    }

    public List<Matrix4f> getWorldMatrices() {
        return worldMatrices;
    }

    public void setWorldMatrices(List<Matrix4f> matrices) {
        this.worldMatrices = matrices;
    }

    public Vec3f getCenter() {
        return center;
    }

    public void setCenter(Vec3f center) {
        this.center = center;
    }

    public IntegerReference getHighPolyInstances() {
        return highPolyInstances;
    }

    public void setHighPolyInstances(IntegerReference instances) {
        this.highPolyInstances = instances;
    }

    public IntegerReference getLowPolyInstances() {
        return lowPolyInstances;
    }

    public void setLowPolyInstances(IntegerReference lowPolyInstances) {
        this.lowPolyInstances = lowPolyInstances;
    }

    public List<Matrix4f> getModelMatrices() {
        return modelMatrices;
    }

    public void setModelMatrices(List<Matrix4f> modelMatrices) {
        this.modelMatrices = modelMatrices;
    }
}
