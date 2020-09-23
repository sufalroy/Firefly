package org.firefly.common.terrain;

import org.firefly.common.quadtree.Quadtree;
import org.firefly.core.math.Transform;
import org.firefly.core.math.Vec2f;
import org.firefly.core.scenegraph.NodeComponent;
import org.firefly.core.scenegraph.NodeComponentType;

import java.util.Map;

public abstract class TerrainQuadtree extends Quadtree {

    public TerrainQuadtree(Map<NodeComponentType, NodeComponent> components,
                           int rootChunkCount, float horizontalScaling){
        super();

        Transform worldTransform = new Transform();
        worldTransform.setTranslation(-0.5f * horizontalScaling, 0, -0.5f * horizontalScaling);

        worldTransform.setScaling(horizontalScaling, 0 ,horizontalScaling);

        for(int i = 0; i < rootChunkCount; i++){
            for(int j = 0; j < rootChunkCount; j++){
                addChild(createChildChunk(components, quadtreeCache, worldTransform, new Vec2f(1f * i/(float)rootChunkCount, 1f * j/(float)rootChunkCount), 0, new Vec2f(i,j)));
            }
        }
    }
}
