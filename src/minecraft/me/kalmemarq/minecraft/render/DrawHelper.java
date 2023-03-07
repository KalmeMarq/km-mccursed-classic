package me.kalmemarq.minecraft.render;

import com.mojang.minecraft.renderer.RenderHelper;

import me.kalmemarq.minecraft.util.Matrix4f;
import me.kalmemarq.minecraft.util.MatrixStack;

public class DrawHelper {	
	public static void drawString(MatrixStack matrices, FontRenderer fontRenderer, String text, int x, int y, int color) {
		fontRenderer.drawString(matrices, text, x, y, color);
	}
	
	public static void drawStringWithShadow(MatrixStack matrices, FontRenderer fontRenderer, String text, int x, int y, int color) {
		fontRenderer.drawStringWithShadow(matrices, text, x, y, color);
	}
	
	public static void drawCenteredString(MatrixStack matrices, FontRenderer fontRenderer, String text, int x, int y, int color) {
		drawString(matrices, fontRenderer, text, x - fontRenderer.getWidth(text, false) / 2, y, color);
	}
	
	public static void drawCenteredStringWithShadow(MatrixStack matrices, FontRenderer fontRenderer, String text, int x, int y, int color) {
		drawStringWithShadow(matrices, fontRenderer, text, x - fontRenderer.getWidth(text, false) / 2, y, color);
	}
	
	public static void drawLeftStringWithShadow(MatrixStack matrices, FontRenderer fontRenderer, String text, int x, int y, int color) {
		drawStringWithShadow(matrices, fontRenderer, text, x - fontRenderer.getWidth(text, false), y, color);
	}
	
	public static void fill(MatrixStack matrices, int x0, int y0, int x1, int y1, int z, int color) {
		int a = color >> 24 & 0xFF;
		int r = color >> 16 & 0xFF;
		int g = color >> 8 & 0xFF;
		int b = color & 0xFF;

		int temp;
		if (x0 > x1) {
			temp = x1;
			x1 = x0;
			x0 = temp;
		}
		
		if (y0 > y1) {
			temp = y1;
			y1 = y0;
			y0 = temp;
		}
		
		Matrix4f matrix = matrices.peek();
		
		RenderHelper.enableBlend();
		Tessellator tessellator = Tessellator.getInstance();
		tessellator.begin(DrawMode.QUADS, VertexFormat.POSITION_COLOR);
		tessellator.vertex(matrix, x0, y1, z).color(r, g, b, a).next();
		tessellator.vertex(matrix, x1, y1, z).color(r, g, b, a).next();
		tessellator.vertex(matrix, x1, y0, z).color(r, g, b, a).next();
		tessellator.vertex(matrix, x0, y0, z).color(r, g, b, a).next();
		tessellator.draw();
		RenderHelper.disableBlend();
	}
	
	public static void fillVGradient(MatrixStack matrices, int x0, int y0, int x1, int y1, int z, int colorStart, int colorEnd) {
		int aS = colorStart >> 24 & 0xFF;
		int rS = colorStart >> 16 & 0xFF;
		int gS = colorStart >> 8 & 0xFF;
		int bS = colorStart & 0xFF;
		int aE = colorEnd >> 24 & 0xFF;
		int rE = colorEnd >> 16 & 0xFF;
		int gE = colorEnd >> 8 & 0xFF;
		int bE = colorEnd & 0xFF;

		int temp;
		if (x0 > x1) {
			temp = x1;
			x1 = x0;
			x0 = temp;
		}
		
		if (y0 > y1) {
			temp = y1;
			y1 = y0;
			y0 = temp;
		}
		
		Matrix4f matrix = matrices.peek();
		
		RenderHelper.enableBlend();
		RenderHelper.defaultBlendFunc();
		Tessellator tessellator = Tessellator.getInstance();
		tessellator.begin(DrawMode.QUADS, VertexFormat.POSITION_COLOR);
		tessellator.vertex(matrix, x0, y1, z).color(rE, gE, bE, aE).next();
		tessellator.vertex(matrix, x1, y1, z).color(rE, gE, bE, aE).next();
		tessellator.vertex(matrix, x1, y0, z).color(rS, gS, bS, aS).next();
		tessellator.vertex(matrix, x0, y0, z).color(rS, gS, bS, aS).next();
		tessellator.draw();
		RenderHelper.disableBlend();
	}
	
	public static void fillHGradient(MatrixStack matrices, int x0, int y0, int x1, int y1, int z, int colorStart, int colorEnd) {
		int aS = colorStart >> 24 & 0xFF;
		int rS = colorStart >> 16 & 0xFF;
		int gS = colorStart >> 8 & 0xFF;
		int bS = colorStart & 0xFF;
		int aE = colorEnd >> 24 & 0xFF;
		int rE = colorEnd >> 16 & 0xFF;
		int gE = colorEnd >> 8 & 0xFF;
		int bE = colorEnd & 0xFF;

		int temp;
		if (x0 > x1) {
			temp = x1;
			x1 = x0;
			x0 = temp;
		}
		
		if (y0 > y1) {
			temp = y1;
			y1 = y0;
			y0 = temp;
		}
		
		Matrix4f matrix = matrices.peek();
		
		RenderHelper.enableBlend();
		Tessellator tessellator = Tessellator.getInstance();
		tessellator.begin(DrawMode.QUADS, VertexFormat.POSITION_COLOR);
		tessellator.vertex(matrix, x0, y1, z).color(rS, gS, bS, aS).next();
		tessellator.vertex(matrix, x1, y1, z).color(rE, gE, bE, aE).next();
		tessellator.vertex(matrix, x1, y0, z).color(rE, gE, bE, aE).next();
		tessellator.vertex(matrix, x0, y0, z).color(rS, gS, bS, aS).next();
		tessellator.draw();
		RenderHelper.disableBlend();
	}
	
	public static void drawTexture(MatrixStack matrices, int x, int y, int width, int height, int u, int v) {
		drawTexture(matrices, x, y, width, height, u, v, width, height);
	}
	
	public static void drawTexture(MatrixStack matrices, int x, int y, int width, int height, int u, int v, int regionWidth, int regionHeight) {
		drawTexture(matrices, x, y, 0, width, height, u, v, regionWidth, regionHeight, 256, 256);
	}
	
	public static void drawTexture(MatrixStack matrices, int x, int y, int z, int width, int height, int u, int v, int regionWidth, int regionHeight, int textureWidth, int textureHeight) {
		int x1 = x + width;
		int y1 = y + height;
		
		float u0 = u / (float)textureWidth;
		float v0 = v / (float)textureHeight;
		float u1 = (u + regionWidth) / (float)textureWidth;
		float v1 = (v + regionHeight) / (float)textureHeight;
		
		Matrix4f matrix = matrices.peek();
		
		RenderHelper.enableTexture();
		Tessellator tessellator = Tessellator.getInstance();
		tessellator.begin(DrawMode.QUADS, VertexFormat.POSITION_TEXTURE);
		tessellator.vertex(matrix, x, y1, z).texture(u0, v1).next();
		tessellator.vertex(matrix, x1, y1, z).texture(u1, v1).next();
		tessellator.vertex(matrix, x1, y, z).texture(u1, v0).next();
		tessellator.vertex(matrix, x, y, z).texture(u0, v0).next();
		tessellator.draw();		
		RenderHelper.disableTexture();
	}
}
