package org.firefly.core.model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import org.firefly.core.math.Vec2f;
import org.firefly.core.math.Vec3f;

@NoArgsConstructor
@Getter
public class Vertex {

    public static final int BYTES = 14 * Float.BYTES;
    public static final int FLOATS = 14;

    private Vec3f position;
    private Vec3f normal;
    private Vec2f uvCoord;
    private Vec3f tangent;
    private Vec3f bitangent;

    public enum VertexLayout{

        POS_NORMAL_UV_TAN_BITAN,
        POS_NORMAL,
        POS_UV,
        POS,
        POS_NORMAL_UV,
        POS2D,
        POS2D_UV;
    }

    public Vertex(Vec3f pos){
        this.setPosition(pos);
        this.setUVCoord(new Vec2f(0,0));
        this.setNormal(new Vec3f(0, 0, 0));
    }

    public Vertex(Vec3f pos, Vec2f texture){
        this.setPosition(pos);
        this.setUVCoord(texture);
        this.setNormal(new Vec3f(0,0,0));
    }

    public Vec3f getPosition() {
        return position;
    }

    public void setPosition(Vec3f position) {
        this.position = position;
    }

    public Vec3f getNormal() {
        return normal;
    }

    public void setNormal(Vec3f normal) {
        this.normal = normal;
    }

    public Vec2f getUVCoord() {
        return uvCoord;
    }

    public void setUVCoord(Vec2f uvCoord) {
        this.uvCoord = uvCoord;
    }

    public Vec3f getTangent() {
        return tangent;
    }

    public void setTangent(Vec3f tangent) {
        this.tangent = tangent;
    }

    public Vec3f getBitangent() {
        return bitangent;
    }

    public void setBitangent(Vec3f bitangent) {
        this.bitangent = bitangent;
    }
}
