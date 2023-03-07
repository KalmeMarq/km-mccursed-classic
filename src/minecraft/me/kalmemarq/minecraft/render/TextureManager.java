package me.kalmemarq.minecraft.render;

import java.util.HashMap;
import java.util.Map;

import org.lwjgl.opengl.GL11;

public class TextureManager {
	private Map<String, Texture> textures = new HashMap<String, Texture>();
	
	public void bindTexture(String path) {
		if (textures.containsKey(path)) {
			Texture texture = textures.get(path);
			texture.bind();
		} else {
		}
	}
	
	public Texture getTexture(String path) {
		return this.textures.get(path);
	}
	
	public void destroy() {
		for (Texture texture : textures.values()) {
			texture.destroy();
		}
	}
	
	public class Texture {
		public int textureId;
		public int width;
		public int height;
		
		public void load() {
			this.textureId = GL11.glGenTextures();
			
			GL11.glTexParameteri(GL11.GL_TEXTURE_2D, GL11.GL_TEXTURE_MIN_FILTER, GL11.GL_NEAREST);
			GL11.glTexParameteri(GL11.GL_TEXTURE_2D, GL11.GL_TEXTURE_MAG_FILTER, GL11.GL_NEAREST);
		}
		
		public void bind() {
			if (this.textureId == -1) return;
			GL11.glBindTexture(GL11.GL_TEXTURE_2D, this.textureId);
		}
		
		public void destroy() {
			if (textureId == -1) return;
			GL11.glDeleteTextures(this.textureId);
			this.textureId = -1;
		}
	}
	
	public class AtlasTexture extends Texture {
		public void tick() {
		}
	}
}
