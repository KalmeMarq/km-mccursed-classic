package me.kalmemarq.minecraft.render;

import java.awt.image.BufferedImage;
import java.io.IOException;

import javax.imageio.ImageIO;

import com.mojang.minecraft.renderer.Textures;

import me.kalmemarq.minecraft.util.Matrix4f;
import me.kalmemarq.minecraft.util.MatrixStack;

public class FontRenderer {
	private static final Glyph EMPTY_GLYPH = new Glyph(0, 0, 0, 0, 0);
	private Glyph[] glyphs = new Glyph[256];
	private int fontTextureId = 0;
	public int fontHeight = 8;
	
	public FontRenderer(String path, Textures textures) {
		BufferedImage image;
		try {
			image = ImageIO.read(Textures.class.getResourceAsStream(path));
		} catch (IOException iOException13) {
			throw new RuntimeException(iOException13);
		}

		int width = image.getWidth();
		int height = image.getHeight();
		int[] pixels = new int[width * height];
		image.getRGB(0, 0, width, height, pixels, 0, width);

		for(int i = 0; i < 128; ++i) {
			int u = i % 16;
			int v = i / 16;
			int advance = 0;

			for(boolean z9 = false; advance < 8 && !z9; ++advance) {
				int i10 = (u << 3) + advance;
				z9 = true;

				for(int i11 = 0; i11 < 8 && z9; ++i11) {
					int i12 = ((v << 3) + i11) * width;
					if((pixels[i10 + i12] & 255) > 128) {
						z9 = false;
					}
				}
			}

			if(i == 32) {
				advance = 4;
			}

			this.glyphs[i] = new Glyph((u * 8) / 128.0f, (v * 8) / 128.0f, (u * 8 + 8) / 128.0f, (v * 8 + 8) / 128.0f, advance);
		}

		this.fontTextureId = textures.getTextureId(path);
	}
	
	public void drawString(MatrixStack matrices, String text, int x, int y, int color) {
		if (text == null || text.length() == 0) return;
		color = fixColorAlpha(color);
		
		Matrix4f matrix = matrices.peek();
		
		RenderHelper.enableTexture();
		Textures.bindTexture(this.fontTextureId);
		
		Tessellator tessellator = Tessellator.getInstance();
		tessellator.begin(DrawMode.QUADS, VertexFormat.POSITION_TEXTURE_COLOR);
		
		this.draw(matrix, tessellator, text, x, y, color, false);
		
		tessellator.draw();
		RenderHelper.disableTexture();
	}
	
	public void drawStringWithShadow(MatrixStack matrices, String text, int x, int y, int color) {
		if (text == null || text.length() == 0) return;
		color = fixColorAlpha(color);
		
		Matrix4f matrix = matrices.peek();
		
		RenderHelper.enableTexture();
		Textures.bindTexture(this.fontTextureId);
		
		Tessellator tessellator = Tessellator.getInstance();
		tessellator.begin(DrawMode.QUADS, VertexFormat.POSITION_TEXTURE_COLOR);
		
		this.draw(matrix, tessellator, text, x + 1, y + 1, color, true);
		this.draw(matrix, tessellator, text, x, y, color, false);
		
		tessellator.draw();
		RenderHelper.disableTexture();
	}
	
	private void draw(Matrix4f matrix, Tessellator tessellator, String text, int x, int y, int color, boolean shadow) {
		char[] chars = text.toCharArray();
	
		int cr = (color >> 16) & 0xFF;
		int cg = (color >> 8) & 0xFF;
		int cb = color & 0xFF;
		int ca = (color >> 24) & 0xFF;

		if(shadow) {
			cr = (int)(cr * 0.25f);
			cg = (int)(cg * 0.25f);
			cb = (int)(cb * 0.25f);
		}
		
		int xx = 0;
		
		for (int i = 0; i < chars.length; i++) {
			if (chars[i] == 38 && chars.length > i + 1) {
				ColorCode code = ColorCode.byCode(chars[i + 1]);
				i += 2;
				
				cr = (code.color >> 16) & 0xFF;
				cg = (code.color >> 8) & 0xFF;
				cb = code.color & 0xFF;
				
				if(shadow) {
					cr = (int)(cr * 0.25f);
					cg = (int)(cg * 0.25f);
					cb = (int)(cb * 0.25f);
				}
			}
			
			Glyph glyph = this.getGlyph(chars[i]);
			glyph.render(matrix, tessellator, x + xx, y, cr, cg, cb, ca);
			xx += glyph.advance;
		}
	}
	
	private int fixColorAlpha(int color) {
		int r = (color >> 16) & 0xFF;
		int g = (color >> 8) & 0xFF;
		int b = color & 0xFF;
		int a = (color >> 24) & 0xFF;
		
		if (a == 0) {
			a = 255;
		}
		return a << 24 | r << 16 | g << 8 | b;
	}
	
	public int getWidth(String text, boolean ignoreCodeIds) {
		if (text == null) return 0;
		
		char[] chars = text.toCharArray();
		int width = 0;

		for(int i = 0; i < chars.length; ++i) {
			if(chars[i] == 38) {
				++i;
				if (ignoreCodeIds) ++i;
			} else {
				width += getGlyphWidth(chars[i]);
			}
		}

		return width;
	}
	
	private int getGlyphWidth(char chr) {
		Glyph glyph = glyphs[chr];
		if (glyph == null) return 0;
		return glyph.advance;
	}
	
	private Glyph getGlyph(char chr) {
		Glyph glyph = glyphs[chr];
		if (glyph == null) return EMPTY_GLYPH;
		return glyph;
	}
	
	public static String removeColorCodes(String text, boolean removeCodeIds) {
		char[] chars = text.toCharArray();
		StringBuilder builder = new StringBuilder();
	
		for(int i = 0; i < chars.length; ++i) {
			if(chars[i] == 38) {
				++i;
				if (removeCodeIds) ++i;
			} else {
				builder.append(chars[i]);
			}
		}
		
		return builder.toString();
	}
	
	public static class Glyph {
		public float u0;
		public float v0;
		public float u1;
		public float v1;
		public int advance;
		
		public Glyph(float u0, float v0, float u1, float v1, int advance) {
			this.u0 = u0;
			this.v0 = v0;
			this.u1 = u1;
			this.v1 = v1;
			this.advance = advance;
		}
		
		public void render(Matrix4f matrix, Tessellator tessellator, int x, int y, int red, int green, int blue, int alpha) {
			if (this == EMPTY_GLYPH) return;
			tessellator.vertex(x, y + 8, 0.0F).texture(this.u0, this.v1).color(red, green, blue, alpha).next();
			tessellator.vertex(x + 8, y + 8, 0.0F).texture(this.u1, this.v1).color(red, green, blue, alpha).next();
			tessellator.vertex(x + 8, y, 0.0F).texture(this.u1, this.v0).color(red, green, blue, alpha).next();
			tessellator.vertex(x, y, 0.0F).texture(this.u0, this.v0).color(red, green, blue, alpha).next();
		}
	}
	
	public enum ColorCode {
		BLACK('0', 0x000000),
		DARK_BLUE('1', 0x0000BF),
		DARK_GREEN('2', 0x00BF00),
		DARK_AQUA('3', 0x00BFBF),
		DARK_RED('4', 0xBF0000),
		DARK_PURPLE('5', 0xBF00BF),
		DARK_YELLOW('6', 0xBFBF00),
		GRAY('7', 0xBFBFBF),
		DARK_GRAY('8', 0x404040),
		BLUE('9', 0x4040FF),
		GREEN('a', 0x40FF40),
		AQUA('b', 0x40FFFF),
		RED('c', 0xFF4040),
		LIGHT_PURPLE('d', 0xFF40FF),
		YELLOW('e', 0xFFFF40),
		WHITE('f', 0xFFFFFF);
		
		public final char code;
		public final int color;
		
		private ColorCode(char code, int color) {
			this.code = code;
			this.color = color;
		}
		
		public static ColorCode byCode(char code) {
			for (ColorCode color : values()) {
				if (color.code == code) {
					return color;
				}
			}
			return ColorCode.WHITE;
		}
	}
}