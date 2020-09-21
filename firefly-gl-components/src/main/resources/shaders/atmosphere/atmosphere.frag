#version 450
#extension GL_ARB_separate_shader_objects : enable
#define M_PI 3.1415926535897932384626433832795

layout (location = 0) in vec3 modelPosition;

layout(location = 0) out vec4 albedo_out;
layout(location = 1) out vec4 worldPosition_out;
layout(location = 2) out vec4 normal_out;
layout(location = 3) out vec4 specular_emission_diffuse_ssao_bloom_out;
layout(location = 4) out vec4 lightScattering_out;

uniform mat4 m_ViewProjection;
uniform vec3 v_SunWorld;
uniform float r_Sun;
uniform int width;
uniform int height;
uniform int isReflection;
uniform float bloom;
const vec3 sunBaseColor = vec3(1.0f,0.79f,0.43f);

void main()
{
	float red = -1*(modelPosition.y-0.75);
	float green = -1.2*(modelPosition.y-0.8);
	float blue = -0.9*(modelPosition.y-1.1);
	
	vec3 out_Color = vec3(red, green, blue);
	vec3 out_LightScattering = vec3(0);

	// no sun rendering when scene reflection
	if (isReflection == 0)
	{
		vec4 ndc = vec4(
        (gl_FragCoord.x / width - 0.5) * 2.0,
        (gl_FragCoord.y / height - 0.5) * 2.0,
        (gl_FragCoord.z - 0.5) * 2.0,
        1.0);
	
		vec4 clip = inverse(m_ViewProjection) * ndc;
		vec3 v_World = (clip / clip.w).xyz;
		
		float sunRadius = length(normalize(v_World)- normalize(v_SunWorld));

		if(sunRadius < r_Sun)
		{
			sunRadius /= r_Sun;
			float smoothRadius = smoothstep(0,1,0.1f/sunRadius-0.1f);
			out_Color = mix(out_Color, sunBaseColor * 4, smoothRadius);
			
			smoothRadius = smoothstep(0,1,0.18f/sunRadius-0.2f);
			out_LightScattering = mix(vec3(0), sunBaseColor, smoothRadius);
		}
	}
	
	albedo_out = vec4(out_Color,1);
	worldPosition_out = vec4(0,0,0,0);
	normal_out = vec4(0,0,0,0);
	specular_emission_diffuse_ssao_bloom_out = vec4(0,0,0,bloom);
	lightScattering_out = vec4(out_LightScattering,0);
}