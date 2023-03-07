package me.kalmemarq.minecraft.render;

import java.nio.FloatBuffer;

import org.lwjgl.BufferUtils;
import org.lwjgl.opengl.GL11;

import com.mojang.minecraft.character.Vec3;
import com.mojang.minecraft.level.Level;
import com.mojang.minecraft.level.liquid.Liquid;
import com.mojang.minecraft.level.tile.Tile;
import com.mojang.minecraft.player.Player;

import me.kalmemarq.minecraft.util.Matrix4f;

public class RenderHelper {
	private static FloatBuffer _LIGHT_BUFFER = BufferUtils.createFloatBuffer(16);
	private static FloatBuffer _MATRIX4X4_BUFFER = BufferUtils.createFloatBuffer(16);
	
	private static int _LAST_BIND_TEXTURE_ID = -1;
	
	public static void bindTexture(String path) {
	}
	
	public static void loadProjectionMatrix(Matrix4f matrix) {
		GL11.glMatrixMode(GL11.GL_PROJECTION);
		GL11.glLoadMatrix(matrix.get(_MATRIX4X4_BUFFER, false));
	}
	
	public static void loadModelViewMatrix(Matrix4f matrix) {
		GL11.glMatrixMode(GL11.GL_MODELVIEW);
		GL11.glLoadMatrix(matrix.get(_MATRIX4X4_BUFFER, false));
	}
	
	public static void enableDepthTest() {
		GL11.glEnable(GL11.GL_DEPTH_TEST);
	}
	
	public static void disableDepthTest() {
		GL11.glDisable(GL11.GL_DEPTH_TEST);
	}
	
	public static void enableBlend() {
		GL11.glEnable(GL11.GL_BLEND);
	}
	
	public static void disableBlend() {
		GL11.glDisable(GL11.GL_BLEND);
	}
	
	public static void enableLighting() {
		GL11.glEnable(GL11.GL_LIGHTING);
	}
	
	public static void disableLighting() {
		GL11.glDisable(GL11.GL_LIGHTING);
	}
	
	public static void enableFog() {
		GL11.glEnable(GL11.GL_FOG);
	}
	
	public static void disableFog() {
		GL11.glDisable(GL11.GL_FOG);
	}
	
	public static void enableTexture() {
		GL11.glEnable(GL11.GL_TEXTURE_2D);
	}
	
	public static void disableTexture() {
		GL11.glDisable(GL11.GL_TEXTURE_2D);
	}
	
	public static void defaultBlendFunc() {
		GL11.glBlendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA);
	}
	
	public static void setColor(float r, float g, float b, float a) {
		GL11.glColor4f(r, g, b, a);
	}
	
	public static void toggleLight(boolean z1) {
		if(!z1) {
			GL11.glDisable(GL11.GL_LIGHTING);
			GL11.glDisable(GL11.GL_LIGHT0);
		} else {
			GL11.glEnable(GL11.GL_LIGHTING);
			GL11.glEnable(GL11.GL_LIGHT0);
			GL11.glEnable(GL11.GL_COLOR_MATERIAL);
			GL11.glColorMaterial(GL11.GL_FRONT_AND_BACK, GL11.GL_AMBIENT_AND_DIFFUSE);
			float f4 = 0.7F;
			float f2 = 0.3F;
			Vec3 vec33 = (new Vec3(0.0F, -1.0F, 0.5F)).normalize();
			GL11.glLight(GL11.GL_LIGHT0, GL11.GL_POSITION, getBuffer(vec33.x, vec33.y, vec33.z, 0.0F));
			GL11.glLight(GL11.GL_LIGHT0, GL11.GL_DIFFUSE, getBuffer(f2, f2, f2, 1.0F));
			GL11.glLight(GL11.GL_LIGHT0, GL11.GL_AMBIENT, getBuffer(0.0F, 0.0F, 0.0F, 1.0F));
			GL11.glLightModel(GL11.GL_LIGHT_MODEL_AMBIENT, getBuffer(f4, f4, f4, 1.0F));
		}
	}
	
    // TODO: Move variables to an enum
	public static void setupFog(Level level, Player player, float farPlane, float red, float green, float blue) {
		GL11.glFog(GL11.GL_FOG_COLOR, getBuffer(red, green, blue, 1.0F));
		GL11.glNormal3f(0.0F, -1.0F, 0.0F);
		GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
		
		Tile tile = Tile.tiles[level.getTile((int)player.x, (int)(player.y + 0.12F), (int)player.z)];
		
		if(tile != null && tile.getLiquidType() != Liquid.none) {
			Liquid liquid4 = tile.getLiquidType();
			GL11.glFogi(GL11.GL_FOG_MODE, GL11.GL_EXP);
			if(liquid4 == Liquid.water) {
				GL11.glFogf(GL11.GL_FOG_DENSITY, 0.1F);
				GL11.glLightModel(GL11.GL_LIGHT_MODEL_AMBIENT, getBuffer(0.4F, 0.4F, 0.9F, 1.0F));
			} else if(liquid4 == Liquid.lava) {
				GL11.glFogf(GL11.GL_FOG_DENSITY, 2.0F);
				GL11.glLightModel(GL11.GL_LIGHT_MODEL_AMBIENT, getBuffer(0.4F, 0.3F, 0.3F, 1.0F));
			}
		} else {
			GL11.glFogi(GL11.GL_FOG_MODE, GL11.GL_LINEAR);
			GL11.glFogf(GL11.GL_FOG_START, 0.0F);
			GL11.glFogf(GL11.GL_FOG_END, farPlane);
			GL11.glLightModel(GL11.GL_LIGHT_MODEL_AMBIENT, getBuffer(1.0F, 1.0F, 1.0F, 1.0F));
		}

		GL11.glEnable(GL11.GL_COLOR_MATERIAL);
		GL11.glColorMaterial(GL11.GL_FRONT, GL11.GL_AMBIENT);
		GL11.glEnable(GL11.GL_LIGHTING);
	}
	
	private static FloatBuffer getBuffer(float f1, float f2, float f3, float f4) {
		_LIGHT_BUFFER.clear();
		_LIGHT_BUFFER.put(f1).put(f2).put(f3).put(f4);
		_LIGHT_BUFFER.flip();
		return _LIGHT_BUFFER;
	}
	
    public static int calculateGuiScaleFactor(int guiScale, int width, int height) {
        int factor;
        for (factor = 1; factor != guiScale && factor < width && factor < height && width / (factor + 1) >= 320 && height / (factor + 1) >= 240; ++factor) {
        }
        return factor;
    }
}
