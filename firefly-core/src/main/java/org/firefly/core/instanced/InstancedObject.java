package org.firefly.core.instanced;

import lombok.Getter;
import lombok.Setter;
import org.firefly.core.context.BaseContext;
import org.firefly.core.math.Matrix4f;
import org.firefly.core.math.Vec3f;
import org.firefly.core.scenegraph.RenderList;
import org.firefly.core.scenegraph.Renderable;
import org.firefly.core.util.IntegerReference;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
public abstract class InstancedObject extends Renderable {

    private int instanceCount;
    private Vec3f[] positions;

    private List<Matrix4f> worldMatrices = new ArrayList<Matrix4f>();
    private List<Matrix4f> modelMatrices = new ArrayList<Matrix4f>();

    private List<Integer> highPolyIndices = new ArrayList<Integer>();
    private List<Integer> lowPolyIndices = new ArrayList<Integer>();

    private List<Renderable> lowPolyObjects = new ArrayList<Renderable>();
    private List<Renderable> highPolyObjects = new ArrayList<Renderable>();

    private IntegerReference highPolyInstanceCount;
    private IntegerReference lowPolyInstanceCount;
    private int highPolyRange;

    public void update(){

        getHighPolyIndices().clear();

        int index = 0;

        for (Matrix4f transform : getWorldMatrices()){
            if (transform.getTranslation().sub(BaseContext.getCamera().getPosition()).length() < highPolyRange){
                getHighPolyIndices().add(index);
            }

            index++;
        }
        getHighPolyInstanceCount().setValue(getHighPolyIndices().size());
    }

    public void record(RenderList renderList){

        if (render){
            if (!renderList.contains(id)){
                renderList.add(this);
                renderList.setChanged(true);
            }
        }
        else {
            if (renderList.contains(id)){
                renderList.remove(this);
                renderList.setChanged(true);
            }
        }
    }

    public void renderLowPoly(){
        lowPolyObjects.forEach(object ->{
            object.render();
        });
    }

    public void renderHighPoly(){
        highPolyObjects.forEach(object ->{
            object.render();
        });
    }

    public void renderLowPolyShadows(){
        lowPolyObjects.forEach(object ->{
            object.renderShadows();
        });
    }

    public void renderHighPolyShadows(){
        highPolyObjects.forEach(object ->{
            object.renderShadows();
        });
    }
}
