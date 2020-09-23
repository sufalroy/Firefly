package org.firefly.common.quadtree;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import org.firefly.core.math.Vec2f;

@Getter
@Setter
@AllArgsConstructor
public class ChunkConfig {

    private int lod;
    private Vec2f location;
    private Vec2f index;
    private float gap;
}
