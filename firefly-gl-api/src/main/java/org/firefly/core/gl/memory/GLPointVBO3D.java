package org.firefly.core.gl.memory;

import static org.lwjgl.opengl.GL11.GL_FLOAT;
import static org.lwjgl.opengl.GL11.GL_POINTS;
import static org.lwjgl.opengl.GL11.glDrawArrays;
import static org.lwjgl.opengl.GL15.GL_ARRAY_BUFFER;
import static org.lwjgl.opengl.GL15.GL_STATIC_DRAW;
import static org.lwjgl.opengl.GL15.glBindBuffer;
import static org.lwjgl.opengl.GL15.glBufferData;
import static org.lwjgl.opengl.GL15.glDeleteBuffers;
import static org.lwjgl.opengl.GL15.glGenBuffers;
import static org.lwjgl.opengl.GL20.glDisableVertexAttribArray;
import static org.lwjgl.opengl.GL20.glEnableVertexAttribArray;
import static org.lwjgl.opengl.GL20.glVertexAttribPointer;
import static org.lwjgl.opengl.GL30.glBindVertexArray;
import static org.lwjgl.opengl.GL30.glDeleteVertexArrays;
import static org.lwjgl.opengl.GL30.glGenVertexArrays;

import org.firefly.core.math.Vec3f;
import org.firefly.core.util.BufferUtil;

public class GLPointVBO3D implements VBO{

	private int vbo;
	private int vaoId;
	private int size;
		
	
	public GLPointVBO3D()
	{
		vbo = glGenBuffers();
		vaoId = glGenVertexArrays();
		size = 0;
	}
	public void addData(Vec3f[] points)
	{
			size = points.length;
		
			glBindVertexArray(vaoId);
			
			glBindBuffer(GL_ARRAY_BUFFER, vbo);
			glBufferData(GL_ARRAY_BUFFER, BufferUtil.createFlippedBuffer(points), GL_STATIC_DRAW);
			
			glVertexAttribPointer(0, 3, GL_FLOAT, false, Float.BYTES*3, 0);
			
			glBindVertexArray(0);
	}
	
	
	public void draw()
	{
			glBindVertexArray(vaoId);
			
			glEnableVertexAttribArray(0);
			
			glDrawArrays(GL_POINTS, 0, size);
			
			glDisableVertexAttribArray(0);
			
			glBindVertexArray(0);
	}

	public void delete() {
		glBindVertexArray(vaoId);
		glDeleteBuffers(vbo);
		glDeleteVertexArrays(vaoId);
		glBindVertexArray(0);
	}
}
