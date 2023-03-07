package me.kalmemarq.minecraft.render;

import com.mojang.minecraft.Minecraft;
import com.mojang.minecraft.level.Level;
import com.mojang.minecraft.level.tile.Bush;
import com.mojang.minecraft.level.tile.Tile;

import me.kalmemarq.minecraft.util.Matrix4f;
import me.kalmemarq.minecraft.util.MatrixStack;

public class TileRenderer {
	public static boolean renderInLevel(Tessellator tessellator, Level level, Tile tile, int x, int y, int z, int pass) {
		if (tile instanceof Bush) {
			if(level.isLit(x, y, z) ^ pass != 1) {
				return false;
			} else {
				int i15;
				float f17;
				float f18 = (f17 = (float)((i15 = tile.getTexture(15)) % 16) / 16.0F) + 0.0624375F;
				float f16;
				float f7 = (f16 = (float)(i15 / 16) / 16.0F) + 0.0624375F;

				for(int i8 = 0; i8 < 2; ++i8) {
					float x1 = (float)(Math.sin((double)i8 * Math.PI / (double)2 + 0.7853981633974483D) * 0.5D);
					float f10 = (float)(Math.cos((double)i8 * Math.PI / (double)2 + 0.7853981633974483D) * 0.5D);
					float x0 = (float)x + 0.5F - x1;
					x1 += (float)x + 0.5F;
					float y0 = (float)y;
					float y1 = (float)y + 1.0F;
					float f14 = (float)z + 0.5F - f10;
					f10 += (float)z + 0.5F;
					
					tessellator.vertex(x0, y1, f14).texture(f18, f16).color(255, 255, 255).next();
					tessellator.vertex(x1, y1, f10).texture(f17, f16).color(255, 255, 255).next();
					tessellator.vertex(x1, y0, f10).texture(f17, f7).color(255, 255, 255).next();
					tessellator.vertex(x0, y0, f14).texture(f18, f7).color(255, 255, 255).next();
					
					tessellator.vertex(x1, y1, f10).texture(f18, f16).color(255, 255, 255).next();
					tessellator.vertex(x0, y1, f14).texture(f17, f16).color(255, 255, 255).next();
					tessellator.vertex(x0, y0, f14).texture(f17, f7).color(255, 255, 255).next();
					tessellator.vertex(x1, y0, f10).texture(f18, f7).color(255, 255, 255).next();
				}

				return true;
			}	
		}
		
		float nD = 0.8f;
		float sD = 0.8f;
		float uD = 1.0f;
		float dD = 0.5f;
		float wD = 0.6f;
		float eD = 0.5f;
		
		boolean rendered = false;
		
		float brtns = 1.0f;
		// down
		if(tile.shouldRenderFace(level, x, y - 1, z, pass, 0)) {
			brtns = tile.getBrightness(level, x, y, z) * dD;
			renderFaceInLevel(tessellator, level, tile, x, y, z, 0, brtns);
			rendered = true;
		}
		
		// up
		if(tile.shouldRenderFace(level, x, y + 1, z, pass, 1)) {
			brtns = tile.getBrightness(level, x, y + 1, z) * uD;
			renderFaceInLevel(tessellator, level, tile, x, y, z, 1, brtns);
			rendered = true;
		}

		// north
		if(tile.shouldRenderFace(level, x, y, z - 1, pass, 2)) {
			brtns = tile.getBrightness(level, x, y, z - 1) * nD;
			renderFaceInLevel(tessellator, level, tile, x, y, z, 2, brtns);
			rendered = true;
		}

		// south
		if(tile.shouldRenderFace(level, x, y, z + 1, pass, 3)) {
			brtns = tile.getBrightness(level, x, y, z + 1) * sD;
			renderFaceInLevel(tessellator, level, tile, x, y, z, 3, brtns);
			rendered = true;
		}

		// west
		if(tile.shouldRenderFace(level, x - 1, y, z, pass, 4)) {
			brtns = tile.getBrightness(level, x - 1, y, z) * wD;
			renderFaceInLevel(tessellator, level, tile, x, y, z, 4, brtns);
			rendered = true;
		}

		// east
		if(tile.shouldRenderFace(level, x + 1, y, z, pass, 5)) {
			brtns = tile.getBrightness(level, x + 1, y, z) * eD;
			renderFaceInLevel(tessellator, level, tile, x, y, z, 5, brtns);
			rendered = true;
		}
		
		return rendered;
	}
	
	private static void renderFaceInLevel(Tessellator tessellator, Level level, Tile tile, int x, int y, int z, int face, float brightness) {
		int v;
		int u = (v = tile.getTexture(face)) % 16 << 4;
		v = v / 16 << 4;
		
		float u0 = (float)u / (float)256;
		float u1 = ((float)u + 16.0f) / (float)256;
		float v0 = (float)v / (float)256;
		float v1 = ((float)v + 16.0F) / (float)256;
		
		float x0 = (float)x + tile.xx0;
		float x1 = (float)x + tile.xx1;
		float y0 = (float)y + tile.yy0;
		float y1 = (float)y + tile.yy1;
		float z0 = (float)z + tile.zz0;
		float z1 = (float)z + tile.zz1;

		float aa = 0.55f;
		float bb = 0.45f;
		
		if(face == 0) {			
			float tl = brightness;
			float tr = brightness;
			float bl = brightness;
			float br = brightness;
			
			if (Minecraft.getInstance().gameOptions.weakAO.getValue()) {
				boolean nw = level.isSolidTile(x - 1, y - 1, z - 1);
				boolean ne = level.isSolidTile(x + 1, y - 1, z - 1);
				boolean sw = level.isSolidTile(x - 1, y - 1, z + 1);
				boolean se = level.isSolidTile(x + 1, y - 1, z + 1);
				boolean n = level.isSolidTile(x, y - 1, z - 1);
				boolean s = level.isSolidTile(x, y - 1, z + 1);
				boolean w = level.isSolidTile(x - 1, y - 1, z);
				boolean e = level.isSolidTile(x + 1, y - 1, z);
				if (n && s && w && e) {
					tl = bb; 
					tr = bb; 
					bl = bb; 
					br = bb; 
				} else {
					tr = 0.9f;
					tl = 0.9f;
					br = 0.9f;
					bl = 0.9f;	
					if (nw) { tl = aa; }
					if (ne) { tr = aa; }
					if (sw) { bl = aa; }
					if (se) { br = aa; }
					if (n) { tl = aa; tr = aa; }
					if (w) { tl = aa; bl = aa; }
					if (e) { tr = aa; br = aa; }
					if (s) { bl = aa; br = aa; }
					if (w && nw && n) { tl = aa; }
					if (e && ne && n) { tr = aa; }
					if (w && sw && s) { bl = aa; }
					if (e && se && s) { br = aa; }
				}
			}
			
			tessellator.vertex(x0, y0, z1).texture(u0, v1).color(brightness, brightness, brightness).next();
			tessellator.vertex(x0, y0, z0).texture(u0, v0).color(1.0f, 0, 0).next();
			tessellator.vertex(x1, y0, z0).texture(u1, v0).color(brightness, brightness, brightness).next();
			tessellator.vertex(x1, y0, z1).texture(u1, v1).color(brightness, brightness, brightness).next();
		} else if(face == 1) { // up
			float tl = brightness;
			float tr = brightness;
			float bl = brightness;
			float br = brightness;
			
			if (Minecraft.getInstance().gameOptions.weakAO.getValue()) {
				boolean nw = level.isLightBlockerTile(x - 1, y + 1, z - 1);
				boolean ne = level.isLightBlockerTile(x + 1, y + 1, z - 1);
				boolean sw = level.isLightBlockerTile(x - 1, y + 1, z + 1);
				boolean se = level.isLightBlockerTile(x + 1, y + 1, z + 1);
				boolean n = level.isLightBlockerTile(x, y + 1, z - 1);
				boolean s = level.isLightBlockerTile(x, y + 1, z + 1);
				boolean w = level.isLightBlockerTile(x - 1, y + 1, z);
				boolean e = level.isLightBlockerTile(x + 1, y + 1, z);
				
				if (n && s && w && e) {
					tl = bb; 
					tr = bb; 
					bl = bb; 
					br = bb; 
				} else {
					tr = 0.9f;
					tl = 0.9f;
					br = 0.9f;
					bl = 0.9f;	
					if (nw) { tl = aa; }
					if (ne) { tr = aa; }
					if (sw) { bl = aa; }
					if (se) { br = aa; }
					if (n) { tl = aa; tr = aa; }
					if (w) { tl = aa; bl = aa; }
					if (e) { tr = aa; br = aa; }
					if (s) { bl = aa; br = aa; }
					if (w && nw && n) { tl = aa; }
					if (e && ne && n) { tr = aa; }
					if (w && sw && s) { bl = aa; }
					if (e && se && s) { br = aa; }
				}
			}
			
			tessellator.vertex(x1, y1, z1).texture(u1, v1).color(br, br, br).next();
			tessellator.vertex(x1, y1, z0).texture(u1, v0).color(tr, tr, tr).next();
			tessellator.vertex(x0, y1, z0).texture(u0, v0).color(tl, tl, tl).next();
			tessellator.vertex(x0, y1, z1).texture(u0, v1).color(bl, bl, bl).next();
		} else if(face == 2) {	
			float tl = brightness;
			float tr = brightness;
			float bl = brightness;
			float br = brightness;
			
			if (Minecraft.getInstance().gameOptions.weakAO.getValue()) {
				boolean nw = level.isLightBlockerTile(x + 1, y + 1, z - 1);
				boolean ne = level.isLightBlockerTile(x - 1, y + 1, z - 1);
				boolean sw = level.isLightBlockerTile(x + 1, y - 1, z - 1);
				boolean se = level.isLightBlockerTile(x - 1, y - 1, z - 1);
				boolean n = level.isLightBlockerTile(x, y + 1, z - 1);
				boolean s = level.isLightBlockerTile(x, y - 1, z - 1);
				boolean w = level.isLightBlockerTile(x + 1, y, z - 1);
				boolean e = level.isLightBlockerTile(x - 1, y, z - 1);
				
				if (n && s && w && e) {
					tl = bb; 
					tr = bb; 
					bl = bb; 
					br = bb; 
				} else {
					tr = 0.9f;
					tl = 0.9f;
					br = 0.9f;
					bl = 0.9f;	
					if (nw) { tl = aa; }
					if (ne) { tr = aa; }
					if (sw) { bl = aa; }
					if (se) { br = aa; }
					if (n) { tl = aa; tr = aa; }
					if (w) { tl = aa; bl = aa; }
					if (e) { tr = aa; br = aa; }
					if (s) { bl = aa; br = aa; }
					if (w && nw && n) { tl = aa; }
					if (e && ne && n) { tr = aa; }
					if (w && sw && s) { bl = aa; }
					if (e && se && s) { br = aa; }
				}
			}
			
			tessellator.vertex(x0, y1, z0).texture(u1, v0).color(tr, tr, tr).next();
			tessellator.vertex(x1, y1, z0).texture(u0, v0).color(tl, tl, tl).next();
			tessellator.vertex(x1, y0, z0).texture(u0, v1).color(bl, bl, bl).next();
			tessellator.vertex(x0, y0, z0).texture(u1, v1).color(br, br, br).next();
		} else if(face == 3) { // south
			float tl = brightness;
			float tr = brightness;
			float bl = brightness;
			float br = brightness;
			
			if (Minecraft.getInstance().gameOptions.weakAO.getValue()) {
				boolean nw = level.isLightBlockerTile(x - 1, y + 1, z + 1);
				boolean ne = level.isLightBlockerTile(x + 1, y + 1, z + 1);
				boolean sw = level.isLightBlockerTile(x - 1, y - 1, z + 1);
				boolean se = level.isLightBlockerTile(x + 1, y - 1, z + 1);
				boolean n = level.isLightBlockerTile(x, y + 1, z + 1);
				boolean s = level.isLightBlockerTile(x, y - 1, z + 1);
				boolean w = level.isLightBlockerTile(x - 1, y, z + 1);
				boolean e = level.isLightBlockerTile(x + 1, y, z + 1);
				
				if (n && s && w && e) {
					tl = bb; 
					tr = bb; 
					bl = bb; 
					br = bb; 
				} else {
					tr = 0.9f;
					tl = 0.9f;
					br = 0.9f;
					bl = 0.9f;	
					if (nw) { tl = aa; }
					if (ne) { tr = aa; }
					if (sw) { bl = aa; }
					if (se) { br = aa; }
					if (n) { tl = aa; tr = aa; }
					if (w) { tl = aa; bl = aa; }
					if (e) { tr = aa; br = aa; }
					if (s) { bl = aa; br = aa; }
					if (w && nw && n) { tl = aa; }
					if (e && ne && n) { tr = aa; }
					if (w && sw && s) { bl = aa; }
					if (e && se && s) { br = aa; }
				}
			}
			
			tessellator.vertex(x0, y1, z1).texture(u0, v0).color(tl, tl, tl).next();
			tessellator.vertex(x0, y0, z1).texture(u0, v1).color(bl, bl, bl).next();
			tessellator.vertex(x1, y0, z1).texture(u1, v1).color(br, br, br).next();
			tessellator.vertex(x1, y1, z1).texture(u1, v0).color(tr, tr, tr).next();
		} else if(face == 4) { // west
			float tl = brightness;
			float tr = brightness;
			float bl = brightness;
			float br = brightness;
			
			if (Minecraft.getInstance().gameOptions.weakAO.getValue()) {
				boolean nw = level.isLightBlockerTile(x - 1, y + 1, z - 1);
				boolean ne = level.isLightBlockerTile(x - 1, y + 1, z + 1);
				boolean sw = level.isLightBlockerTile(x - 1, y - 1, z - 1);
				boolean se = level.isLightBlockerTile(x - 1, y - 1, z + 1);
				boolean n = level.isLightBlockerTile(x - 1, y + 1, z);
				boolean s = level.isLightBlockerTile(x - 1, y - 1, z);
				boolean w = level.isLightBlockerTile(x - 1, y, z - 1);
				boolean e = level.isLightBlockerTile(x - 1, y, z + 1);
				
				if (n && s && w && e) {
					tl = bb; 
					tr = bb; 
					bl = bb; 
					br = bb; 
				} else {
					tr = 0.9f;
					tl = 0.9f;
					br = 0.9f;
					bl = 0.9f;	
					if (nw) { tl = aa; }
					if (ne) { tr = aa; }
					if (sw) { bl = aa; }
					if (se) { br = aa; }
					if (n) { tl = aa; tr = aa; }
					if (w) { tl = aa; bl = aa; }
					if (e) { tr = aa; br = aa; }
					if (s) { bl = aa; br = aa; }
					if (w && nw && n) { tl = aa; }
					if (e && ne && n) { tr = aa; }
					if (w && sw && s) { bl = aa; }
					if (e && se && s) { br = aa; }
				}
			}
			
			tessellator.vertex(x0, y1, z1).texture(u1, v0).color(tr, tr, tr).next();
			tessellator.vertex(x0, y1, z0).texture(u0, v0).color(tl, tl, tl).next();
			tessellator.vertex(x0, y0, z0).texture(u0, v1).color(bl, bl, bl).next();
			tessellator.vertex(x0, y0, z1).texture(u1, v1).color(br, br, br).next();
		} else if(face == 5) { // east	
			float tl = brightness;
			float tr = brightness;
			float bl = brightness;
			float br = brightness;
			
			if (Minecraft.getInstance().gameOptions.weakAO.getValue()) {
				boolean nw = level.isLightBlockerTile(x + 1, y + 1, z + 1);
				boolean ne = level.isLightBlockerTile(x + 1, y + 1, z - 1);
				boolean sw = level.isLightBlockerTile(x + 1, y - 1, z + 1);
				boolean se = level.isLightBlockerTile(x + 1, y - 1, z - 1);
				boolean n = level.isLightBlockerTile(x + 1, y + 1, z);
				boolean s = level.isLightBlockerTile(x + 1, y - 1, z);
				boolean w = level.isLightBlockerTile(x + 1, y, z + 1);
				boolean e = level.isLightBlockerTile(x + 1, y, z - 1);
			
				if (n && s && w && e) {
					tl = bb; 
					tr = bb; 
					bl = bb; 
					br = bb; 
				} else {
					tr = 0.9f;
					tl = 0.9f;
					br = 0.9f;
					bl = 0.9f;	
					if (nw) { tl = aa; }
					if (ne) { tr = aa; }
					if (sw) { bl = aa; }
					if (se) { br = aa; }
					if (n) { tl = aa; tr = aa; }
					if (w) { tl = aa; bl = aa; }
					if (e) { tr = aa; br = aa; }
					if (s) { bl = aa; br = aa; }
					if (w && nw && n) { tl = aa; }
					if (e && ne && n) { tr = aa; }
					if (w && sw && s) { bl = aa; }
					if (e && se && s) { br = aa; }
				}
			}
			
			tessellator.vertex(x1, y0, z1).texture(u0, v1).color(bl, bl, bl).next();
			tessellator.vertex(x1, y0, z0).texture(u1, v1).color(br, br, br).next();
			tessellator.vertex(x1, y1, z0).texture(u1, v0).color(tr, tr, tr).next();
			tessellator.vertex(x1, y1, z1).texture(u0, v0).color(tl, tl, tl).next();
		}
	}
	
	public static void renderInGui(MatrixStack matrices, Tessellator tessellator, Tile tile) {
		if (tile instanceof Bush) {
			int i15 = tile.getTexture(15);
			float u1;
			float u0 = (u1 = (float)((i15) % 16) / 16.0F) + 0.0624375F;
			float v1;
			float v0 = (v1 = (float)(i15 / 16) / 16.0F) + 0.0624375F;
			
			int x = 0;
			int y = 0;
			int z = 0;
			
			matrices.push();
			
			Matrix4f matrix = matrices.peek();
			
			for(int i = 0; i < 2; ++i) {
				float x1 = (float)(Math.sin((double)i * Math.PI / (double)2 + 0.7853981633974483D) * 0.5D);
				float f10 = (float)(Math.cos((double)i * Math.PI / (double)2 + 0.7853981633974483D) * 0.5D);
				float x0 = (float)x + 0.5F - x1;
				x1 += (float)x + 0.5F;
				float y0 = (float)y;
				float y1 = (float)y + 1.0F;
				float f14 = (float)z + 0.5F - f10;
				f10 += (float)z + 0.5F;
				
				tessellator.vertex(matrix, x0, y1, f14).texture(u0, v1).color(255, 255, 255).next();
				tessellator.vertex(matrix, x1, y1, f10).texture(u1, v1).color(255, 255, 255).next();
				tessellator.vertex(matrix, x1, y0, f10).texture(u1, v0).color(255, 255, 255).next();
				tessellator.vertex(matrix, x0, y0, f14).texture(u0, v0).color(255, 255, 255).next();
				
				tessellator.vertex(matrix, x1, y1, f10).texture(u0, v1).color(255, 255, 255).next();
				tessellator.vertex(matrix, x0, y1, f14).texture(u1, v1).color(255, 255, 255).next();
				tessellator.vertex(matrix, x0, y0, f14).texture(u1, v0).color(255, 255, 255).next();
				tessellator.vertex(matrix, x1, y0, f10).texture(u0, v0).color(255, 255, 255).next();
			}
			
			matrices.pop();
			
			return;
		}
		
		Matrix4f matrix = matrices.peek();
		
		float nD = 0.8f;
		float sD = 0.8f;
		float uD = 1.0f;
		float dD = 0.5f;
		float wD = 0.6f;
		float eD = 0.5f;
		
		float x0 = tile.xx0;
		float x1 = tile.xx1;
		float y0 = tile.yy0;
		float y1 = tile.yy1;
		float z0 = tile.zz0;
		float z1 = tile.zz1;
		
		float brightness = 1.0f;
		for (int face = 0; face < 6; ++face) {
			int id = tile.getTexture(face);
			int u = id % 16 << 4;
			int v = id / 16 << 4;
			
			float u0 = (float)u / (float)256;
			float u1 = ((float)u + 16.0f) / (float)256;
			float v0 = (float)v / (float)256;
			float v1 = ((float)v + 16.0F) / (float)256;
			
			if(face == 0) {
				brightness = dD;
				tessellator.vertex(matrix, x0, y0, z1).texture(u0, v1).color(brightness, brightness, brightness).next();
				tessellator.vertex(matrix, x0, y0, z0).texture(u0, v0).color(brightness, brightness, brightness).next();
				tessellator.vertex(matrix, x1, y0, z0).texture(u1, v0).color(brightness, brightness, brightness).next();
				tessellator.vertex(matrix, x1, y0, z1).texture(u1, v1).color(brightness, brightness, brightness).next();
			} else if(face == 1) {
				brightness = uD;
				tessellator.vertex(matrix, x1, y1, z1).texture(u1, v1).color(brightness, brightness, brightness).next();
				tessellator.vertex(matrix, x1, y1, z0).texture(u1, v0).color(brightness, brightness, brightness).next();
				tessellator.vertex(matrix, x0, y1, z0).texture(u0, v0).color(brightness, brightness, brightness).next();
				tessellator.vertex(matrix, x0, y1, z1).texture(u0, v1).color(brightness, brightness, brightness).next();
			} else if(face == 2) {
				brightness = nD;
				tessellator.vertex(matrix, x0, y1, z0).texture(u1, v0).color(brightness, brightness, brightness).next();
				tessellator.vertex(matrix, x1, y1, z0).texture(u0, v0).color(brightness, brightness, brightness).next();
				tessellator.vertex(matrix, x1, y0, z0).texture(u0, v1).color(brightness, brightness, brightness).next();
				tessellator.vertex(matrix, x0, y0, z0).texture(u1, v1).color(brightness, brightness, brightness).next();
			} else if(face == 3) {
				brightness = sD;
				tessellator.vertex(matrix, x0, y1, z1).texture(u0, v0).color(brightness, brightness, brightness).next();
				tessellator.vertex(matrix, x0, y0, z1).texture(u0, v1).color(brightness, brightness, brightness).next();
				tessellator.vertex(matrix, x1, y0, z1).texture(u1, v1).color(brightness, brightness, brightness).next();
				tessellator.vertex(matrix, x1, y1, z1).texture(u1, v0).color(brightness, brightness, brightness).next();
			} else if(face == 4) {
				brightness = wD;
				tessellator.vertex(matrix, x0, y1, z1).texture(u1, v0).color(brightness, brightness, brightness).next();
				tessellator.vertex(matrix, x0, y1, z0).texture(u0, v0).color(brightness, brightness, brightness).next();
				tessellator.vertex(matrix, x0, y0, z0).texture(u0, v1).color(brightness, brightness, brightness).next();
				tessellator.vertex(matrix, x0, y0, z1).texture(u1, v1).color(brightness, brightness, brightness).next();
			} else if(face == 5) {
				brightness = eD;
				tessellator.vertex(matrix, x1, y0, z1).texture(u0, v1).color(brightness, brightness, brightness).next();
				tessellator.vertex(matrix, x1, y0, z0).texture(u1, v1).color(brightness, brightness, brightness).next();
				tessellator.vertex(matrix, x1, y1, z0).texture(u1, v0).color(brightness, brightness, brightness).next();
				tessellator.vertex(matrix, x1, y1, z1).texture(u0, v0).color(brightness, brightness, brightness).next();
			}
		}
	}
}