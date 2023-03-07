package me.kalmemarq.minecraft.render;

import java.util.Iterator;
import java.util.TreeSet;

import org.lwjgl.opengl.GL11;

import com.mojang.minecraft.Minecraft;
import com.mojang.minecraft.character.Vec3;
import com.mojang.minecraft.level.Level;
import com.mojang.minecraft.level.liquid.Liquid;
import com.mojang.minecraft.level.tile.Tile;
import com.mojang.minecraft.player.Player;
import com.mojang.minecraft.renderer.Chunk;
import com.mojang.minecraft.renderer.DirtyChunkSorter;
import com.mojang.minecraft.renderer.Frustum;
import com.mojang.minecraft.renderer.LevelRenderer;
import com.mojang.minecraft.renderer.Textures;

import me.kalmemarq.minecraft.util.Matrix4f;
import me.kalmemarq.minecraft.util.MatrixStack;

public class GameRenderer {
	private Minecraft mc;
	private float fogColorRed = 0.5F;
	private float fogColorGreen = 0.8F;
	private float fogColorBlue = 1.0F;
	
	public GameRenderer(Minecraft mc) {
		this.mc = mc;
	}
	
	public void setupFogColor() {
		Level level = this.mc.level;
		Player player = this.mc.player;
		
		FogColor fogColor = FogColor.DEFAULT;
		
		Tile tile = Tile.tiles[level.getTile((int)player.x, (int)(player.y + 0.12F), (int)player.z)];
		
		if(tile != null && tile.getLiquidType() != Liquid.none) {
			Liquid liquid = tile.getLiquidType();
			if (liquid == Liquid.water) {
				fogColor = FogColor.WATER;
			} else if (liquid == Liquid.lava) {
				fogColor = FogColor.LAVA;
			}
		}
		
		if (fogColor == FogColor.DEFAULT) {
			float in = 1.0F / (float)(4 - this.mc.gameOptions.renderDistance.getValue().getId());
			float mul = (float)Math.pow(in, 0.25d);
			
			this.fogColorRed = 0.6F * (1.0F - mul) + mul;
			this.fogColorGreen = 0.8F * (1.0F - mul) + mul;
			this.fogColorBlue = 1.0F * (1.0F - mul) + mul;	
		} else {
			this.fogColorRed = fogColor.r;
			this.fogColorGreen = fogColor.g;
			this.fogColorBlue = fogColor.b;
		}
	}
	
	MatrixStack matrices = new MatrixStack().identity();
	
	public void render(int mouseX, int mouseY, float tickDelta) {
		GL11.glViewport(0, 0, this.mc.width, this.mc.height);
		
		if (this.mc.level != null && this.mc.player != null) {
			this.renderLevel(tickDelta);	
		}
		
		GL11.glClear(GL11.GL_DEPTH_BUFFER_BIT);
		
		RenderHelper.defaultBlendFunc();
		
		int scaledWidth = this.mc.width / this.mc.guiScaleFactor;
		int scaledHeight = this.mc.height / this.mc.guiScaleFactor;
		
		RenderHelper.loadProjectionMatrix(new Matrix4f().setOrtho(0.0f, scaledWidth, scaledHeight, 0.0f, 100.0f, 300.0f));
		
		matrices.push();
		matrices.translate(0.0f, 0.0f, -200.0f);
		RenderHelper.loadModelViewMatrix(matrices.peek());
		matrices.pop();
		
		if (this.mc.level != null) {
			this.mc.hud.render(matrices, mouseX, mouseY, tickDelta);
		}
		
		if (this.mc.screen != null) {
			this.mc.screen.render(matrices, mouseX, mouseY, tickDelta);
		}
	}
	
	public void renderLevel(float tickDelta) {
		this.setupFogColor();
		
		GL11.glClearColor(this.fogColorRed, this.fogColorGreen, this.fogColorBlue, 0.0F);
		GL11.glClear(GL11.GL_COLOR_BUFFER_BIT | GL11.GL_DEPTH_BUFFER_BIT);
		
		this.updateHitResult(tickDelta);
		
		float farPlane = (float)(512 >> (this.mc.gameOptions.renderDistance.getValue().getId() << 1));
		float fov = 70.0f;
		
		RenderHelper.loadProjectionMatrix(new Matrix4f().setPerspective((float)(fov * 0.01745329238474369f), (float)this.mc.width / (float)this.mc.height, 0.05F, farPlane));
		RenderHelper.loadModelViewMatrix(new Matrix4f().identify());
	
		Player player = this.mc.player;
		Level level = this.mc.level;
		GL11.glTranslatef(0.0F, 0.0F, -0.3F);
		GL11.glRotatef(player.xRotO + (player.xRot - player.xRotO) * tickDelta, 1.0F, 0.0F, 0.0F);
		GL11.glRotatef(player.yRotO + (player.yRot - player.yRotO) * tickDelta, 0.0F, 1.0F, 0.0F);
		float f83 = player.xo + (player.x - player.xo) * tickDelta;
		float f61 = player.yo + (player.y - player.yo) * tickDelta;
		float f69 = player.zo + (player.z - player.zo) * tickDelta;
		GL11.glTranslatef(-f83, -f61, -f69);
		GL11.glEnable(GL11.GL_CULL_FACE);
		Frustum frustum67 = Frustum.getFrustum();
		Frustum frustum72 = frustum67;
		
		LevelRenderer levelRenderer = this.mc.levelRenderer;

		for(int i = 0; i < levelRenderer.f.length; ++i) {
			levelRenderer.f[i].isInFrustum(frustum72);
		}

		TreeSet<Chunk> treeSet81 = new TreeSet<Chunk>(new DirtyChunkSorter(player));
		treeSet81.addAll(levelRenderer.dirtyChunks);
		
		Iterator<Chunk> iterator85 = treeSet81.iterator();

		int i82 = 4;
		while(iterator85.hasNext()) {
			Chunk chunk86;
			(chunk86 = (Chunk)iterator85.next()).rebuild();
			levelRenderer.dirtyChunks.remove(chunk86);
			--i82;
			if(i82 == 0) {
				break;
			}
		}

		boolean z47 = level.isSolid(player.x, player.y, player.z, 0.1F);
		
		RenderHelper.setupFog(level, player, farPlane, fogColorRed, fogColorGreen, fogColorBlue);
		RenderHelper.enableFog();
		levelRenderer.render(player, 0);

		if(z47) {
			int i48 = (int)player.x;
			int i56 = (int)player.y;
			int i66 = (int)player.z;

			for(int i79 = i48 - 1; i79 <= i48 + 1; ++i79) {
				for(i82 = i56 - 1; i82 <= i56 + 1; ++i82) {
					for(int i87 = i66 - 1; i87 <= i66 + 1; ++i87) {
						levelRenderer.render(i79, i82, i87);
					}
				}
			}
		}

		RenderHelper.toggleLight(true);
		levelRenderer.renderEntities(frustum67, tickDelta);
		RenderHelper.toggleLight(false);
		
		RenderHelper.setupFog(level, player, farPlane, fogColorRed, fogColorGreen, fogColorBlue);
		
		this.mc.particleEngine.render(player, tickDelta);
		
		GL11.glCallList(levelRenderer.surroundLists);
		RenderHelper.disableLighting();
		
		RenderHelper.setupFog(level, player, farPlane, fogColorRed, fogColorGreen, fogColorBlue);
		levelRenderer.renderClouds(tickDelta);
		RenderHelper.setupFog(level, player, farPlane, fogColorRed, fogColorGreen, fogColorBlue);
		
		GL11.glCallList(levelRenderer.surroundLists + 1);
		RenderHelper.enableBlend();
		GL11.glColorMask(false, false, false, false);
		int i48 = levelRenderer.render(player, 1);
		GL11.glColorMask(true, true, true, true);
		
		if (i48 > 0) {
			RenderHelper.enableTexture();
			Textures.bindTexture(levelRenderer.textures.getTextureId("/terrain.png"));
			GL11.glCallLists(levelRenderer.d);
			RenderHelper.disableTexture();
		}

		GL11.glDepthMask(true);
		RenderHelper.disableBlend();
		RenderHelper.disableLighting();
		RenderHelper.disableFog();
		RenderHelper.disableTexture();
		
		RenderHelper.enableLighting();
		
		if(this.mc.hitResult != null) {
			RenderHelper.disableLighting();
			GL11.glDisable(GL11.GL_ALPHA_TEST);
			levelRenderer.renderHit(player, this.mc.hitResult, this.mc.editMode, player.inventory.getSelected());
			LevelRenderer.renderHitOutline(this.mc.hitResult, this.mc.editMode);
			GL11.glEnable(GL11.GL_ALPHA_TEST);
			RenderHelper.enableLighting();
		}
		
		RenderHelper.disableBlend();
		RenderHelper.disableLighting();
		RenderHelper.disableFog();
		RenderHelper.disableTexture();
		
		if(this.mc.hitResult != null) {
			GL11.glDepthFunc(GL11.GL_LESS);
			GL11.glDisable(GL11.GL_ALPHA_TEST);
			levelRenderer.renderHit(player, this.mc.hitResult, this.mc.editMode, player.inventory.getSelected());
			LevelRenderer.renderHitOutline(this.mc.hitResult, this.mc.editMode);
			GL11.glEnable(GL11.GL_ALPHA_TEST);
			GL11.glDepthFunc(GL11.GL_LEQUAL);
		}
	}
	
	public void updateHitResult(float tickDelta) {
		Player player = this.mc.player;
		
		float xRot = player.xRotO + (player.xRot - player.xRotO) * tickDelta;
		float yRot = player.yRotO + (player.yRot - player.yRotO) * tickDelta;
		
		float x = player.xo + (player.x - player.xo) * tickDelta;
		float y = player.yo + (player.y - player.yo) * tickDelta;
		float z = player.zo + (player.z - player.zo) * tickDelta;
		
		Vec3 start = new Vec3(x, y, z);
		
		float ff69 = (float)Math.cos((double)(-yRot) * Math.PI / 180.0D + Math.PI);
		float f77 = (float)Math.sin((double)(-yRot) * Math.PI / 180.0D + Math.PI);
		
		float ff80 = (float)Math.cos((double)(-xRot) * Math.PI / 180.0D);
		float ff17 = (float)Math.sin((double)(-xRot) * Math.PI / 180.0D);

		f77 *= ff80;
		ff69 *= ff80;
		
		float xx = f77 * 5.0f;
		float yy = ff17 * 5.0f;
		float zz = ff69 * 5.0f;
		
		Vec3 end = new Vec3(start.x + xx, start.y + yy, start.z + zz);
		
		this.mc.hitResult = this.mc.level.clip(start, end);
	}
	
	public enum FogColor {
		DEFAULT(0.6f, 0.8f, 1.0f),
		WATER(0.02f, 0.02f, 0.2f),
		LAVA(0.6f, 0.1f, 0.0f);
		
		public final float r;
		public final float g;
		public final float b;
		
		FogColor(float r, float g, float b) {
			this.r = r;
			this.g = g;
			this.b = b;
		}
	}
}
