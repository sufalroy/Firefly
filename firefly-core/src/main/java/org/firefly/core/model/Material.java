package org.firefly.core.model;

import lombok.Getter;
import lombok.Setter;
import org.firefly.core.image.Image;
import org.firefly.core.math.Vec3f;
import org.firefly.core.scenegraph.NodeComponent;

@Getter
@Setter
public class Material extends NodeComponent {

    private String name;
    private Image diffusemap;
    private Image normalmap;
    private Image heightmap;
    private Image ambientmap;
    private Image specularmap;
    private Image alphamap;
    private Vec3f color;
    private float heightScaling;
    private float horizontalScaling;
    private float emission;
    private float shininess;
}
