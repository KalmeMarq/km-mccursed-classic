package me.kalmemarq.minecraft.render;

import org.lwjgl.opengl.GL11;

public enum DrawMode {
	QUADS(GL11.GL_QUADS),
	LINES(GL11.GL_LINES),
	LINE_STRIP(GL11.GL_LINE_STRIP),
	TRIANGLES(GL11.GL_TRIANGLES);
	
	public final int glType;
	
	DrawMode(int glType) {
		this.glType = glType;
	}
}
