package org.firefly.sandbox.gl.openworld.terrain;

import org.firefly.core.gl.pipeline.GLShaderProgram;
import org.firefly.gl.components.terrain.GLTerrain;

public class Terrain extends GLTerrain {

    public Terrain(GLShaderProgram shader, GLShaderProgram wireframeShader, GLShaderProgram shadowShader) {
        super(shader, wireframeShader, shadowShader);
    }

    @Override
    public void update(){
        super.update();
    }
}
