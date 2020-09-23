package org.firefly.gl.components.terrain.shader;

import static org.lwjgl.opengl.GL13.GL_TEXTURE0;
import static org.lwjgl.opengl.GL13.GL_TEXTURE1;
import static org.lwjgl.opengl.GL13.GL_TEXTURE2;
import static org.lwjgl.opengl.GL13.GL_TEXTURE3;
import static org.lwjgl.opengl.GL13.GL_TEXTURE4;
import static org.lwjgl.opengl.GL13.glActiveTexture;

import org.firefly.common.quadtree.ChunkConfig;
import org.firefly.common.quadtree.QuadtreeNode;
import org.firefly.core.context.BaseContext;
import org.firefly.core.gl.context.GLContext;
import org.firefly.core.gl.pipeline.GLShaderProgram;
import org.firefly.core.math.Vec2f;
import org.firefly.core.scenegraph.NodeComponentType;
import org.firefly.core.scenegraph.Renderable;
import org.firefly.core.util.Constants;
import org.firefly.core.util.ResourceLoader;
import org.firefly.gl.components.terrain.GLTerrainConfig;

public class TerrainShader extends GLShaderProgram {

	private static TerrainShader instance = null;

	public static TerrainShader getInstance() {
		
		if (instance == null) {
			instance = new TerrainShader();
		}
		return instance;
	}

	protected TerrainShader() {
		
		super();

		addVertexShader(ResourceLoader.loadShader("shaders/terrain/terrain.vert", "lib.glsl"));
		addTessellationControlShader(ResourceLoader.loadShader("shaders/terrain/terrain.tesc", "lib.glsl"));
		addTessellationEvaluationShader(ResourceLoader.loadShader("shaders/terrain/terrain.tese", "lib.glsl"));
		addGeometryShader(ResourceLoader.loadShader("shaders/terrain/terrain.geom", "lib.glsl"));
		addFragmentShader(ResourceLoader.loadShader("shaders/terrain/terrain.frag", "lib.glsl"));
		compileShader();

		addUniform("localMatrix");
		addUniform("worldMatrix");

		addUniform("index");
		addUniform("gap");
		addUniform("lod");
		addUniform("location");
		addUniform("isRefraction");
		addUniform("isReflection");
		addUniform("isCameraUnderWater");

		addUniform("caustics");
		addUniform("dudvCaustics");
		addUniform("distortionCaustics");
		addUniform("underwaterBlurFactor");

		addUniform("heightmap");
		addUniform("normalmap");
		addUniform("splatmap");
		addUniform("yScale");
		addUniform("reflectionOffset");

		for (int i = 0; i < 3; i++) {
			addUniform("materials[" + i + "].diffusemap");
			addUniform("materials[" + i + "].normalmap");
			addUniform("materials[" + i + "].heightmap");
			addUniform("materials[" + i + "].heightScaling");
			addUniform("materials[" + i + "].uvScaling");
		}
		
		addUniform("clipplane");

		addUniformBlock("Camera");
		addUniformBlock("DirectionalLight");
	}

	@Override
	public void updateUniforms(Renderable object)
	{	
		bindUniformBlock("Camera", Constants.CameraUniformBlockBinding);
		bindUniformBlock("DirectionalLight", Constants.DirectionalLightUniformBlockBinding);
		
		setUniform("clipplane", BaseContext.getConfig().getClipplane());
		setUniformi("isRefraction", BaseContext.getConfig().isRenderRefraction() ? 1 : 0);
		setUniformi("isReflection", BaseContext.getConfig().isRenderReflection() ? 1 : 0);
		setUniformi("isCameraUnderWater", BaseContext.getConfig().isRenderUnderwater() ? 1 : 0);
		
		GLTerrainConfig terrConfig = object.getComponent(NodeComponentType.CONFIGURATION);
		ChunkConfig vChunkConfig = ((QuadtreeNode) object).getChunkConfig();
		
		int lod = vChunkConfig.getLod();
		Vec2f index = vChunkConfig.getIndex();
		float gap = vChunkConfig.getGap();
		Vec2f location = vChunkConfig.getLocation();
		
		setUniform("localMatrix", object.getLocalTransform().getWorldMatrix());
		setUniform("worldMatrix", object.getWorldTransform().getWorldMatrix());
			
		glActiveTexture(GL_TEXTURE0);
		terrConfig.getHeightmap().bind();
		setUniformi("heightmap", 0);
		
		glActiveTexture(GL_TEXTURE1);
		terrConfig.getNormalmap().bind();
		setUniformi("normalmap", 1);
		
		glActiveTexture(GL_TEXTURE2);
		terrConfig.getSplatmap().bind();
		setUniformi("splatmap", 2);
		
		setUniformi("lod", lod);
		setUniform("index", index);
		setUniformf("gap", gap);
		setUniform("location", location);
		
		setUniformf("yScale", terrConfig.getVerticalScaling());
		setUniformf("reflectionOffset", terrConfig.getReflectionOffset());

		/*
		glActiveTexture(GL_TEXTURE3);
		GLContext.getResources().getUnderwaterCausticsMap().bind();
		setUniformi("caustics", 3);
		glActiveTexture(GL_TEXTURE4);
		GLContext.getResources().getUnderwaterDudvMap().bind();
		setUniformi("dudvCaustics", 4);
		if (GLContext.getResources().getWaterConfig() != null){
			setUniformf("distortionCaustics", GLContext.getResources().getWaterConfig().getDistortion());
			setUniformf("underwaterBlurFactor", GLContext.getResources().getWaterConfig().getUnderwaterBlur());
		}
		 */
		
		int texUnit = 5;
		for (int i=0; i<3; i++){
			
			glActiveTexture(GL_TEXTURE0 + texUnit);
			terrConfig.getMaterials().get(i).getDiffusemap().bind();
			setUniformi("materials[" + i + "].diffusemap", texUnit);
			texUnit++;
			
			glActiveTexture(GL_TEXTURE0 + texUnit);
			terrConfig.getMaterials().get(i).getHeightmap().bind();
			setUniformi("materials[" + i + "].heightmap", texUnit);
			texUnit++;
			
			glActiveTexture(GL_TEXTURE0 + texUnit);
			terrConfig.getMaterials().get(i).getNormalmap().bind();
			setUniformi("materials[" + i + "].normalmap", texUnit);
			texUnit++;
			
			setUniformf("materials[" + i + "].heightScaling", terrConfig.getMaterials().get(i).getHeightScaling());
			setUniformf("materials[" + i + "].uvScaling", terrConfig.getMaterials().get(i).getHorizontalScaling());
		}
	}
}
