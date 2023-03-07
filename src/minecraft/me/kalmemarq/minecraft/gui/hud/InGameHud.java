package me.kalmemarq.minecraft.gui.hud;

import java.util.ArrayList;
import java.util.List;

import org.lwjgl.opengl.GL11;

import com.mojang.minecraft.Minecraft;
import com.mojang.minecraft.level.tile.Tile;
import com.mojang.minecraft.player.Player;
import com.mojang.minecraft.renderer.Textures;

import me.kalmemarq.minecraft.gui.screen.ChatScreen;
import me.kalmemarq.minecraft.option.CrosshairRender;
import me.kalmemarq.minecraft.render.DrawHelper;
import me.kalmemarq.minecraft.render.DrawMode;
import me.kalmemarq.minecraft.render.FontRenderer;
import me.kalmemarq.minecraft.render.RenderHelper;
import me.kalmemarq.minecraft.render.Tessellator;
import me.kalmemarq.minecraft.render.TileRenderer;
import me.kalmemarq.minecraft.render.VertexFormat;
import me.kalmemarq.minecraft.util.MatrixStack;
import me.kalmemarq.minecraft.util.Quaternionf;

public class InGameHud {
	private Minecraft minecraft;
	private int scaledWidth;
	private int scaledHeight;
	public String hoveredUsername = null;
	private int ticks;
	private float xR = 30.0f;
	private float yR = 225.0f;
	public boolean doThing;

	private List<ChatLine> messages = new ArrayList<ChatLine>();
	
	public InGameHud(Minecraft mc) {
		this.minecraft = mc;
	}
	
	public void tick() {
		for(int i = 0; i < this.messages.size(); ++i) {
			++(this.messages.get(i)).counter;
		}
		
		if (doThing) {
			++ticks;
			
			xR = 30.0f + ticks;
			yR = 225.0f + ticks;	
		}
	}
	
	public void render(MatrixStack matrices, int mouseX, int mouseY, float tickDelta) {
		this.scaledWidth = this.minecraft.width / this.minecraft.guiScaleFactor;
		this.scaledHeight = this.minecraft.height / this.minecraft.guiScaleFactor;
		
		RenderHelper.enableTexture();
		GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
		this.renderHotbar(matrices, tickDelta);
		RenderHelper.disableTexture();
		
		this.renderDebugInfo(matrices);

		this.renderCrosshair(matrices);
		
		byte b18 = 10;
		boolean z19 = false;
		if(this.minecraft.screen instanceof ChatScreen) {
			b18 = 20;
			z19 = true;
		}

		for(int i10 = 0; i10 < this.messages.size() && i10 < b18; ++i10) {
			if(((ChatLine)this.messages.get(i10)).counter < 200 || z19) {
				this.minecraft.fontRenderer.drawStringWithShadow(matrices, ((ChatLine)this.messages.get(i10)).message, 2, this.scaledHeight - 8 - i10 * 9 - 20, 0xFFFFFF);
			}
		}
	}
	
	private void renderCrosshair(MatrixStack matrices) {
		GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
		RenderHelper.enableBlend();
		
		int cx = this.scaledWidth / 2;
		int cy = this.scaledHeight / 2;
		
		if (this.minecraft.gameOptions.crosshairRender.getValue() == CrosshairRender.INVERTED) {
			GL11.glBlendFunc(GL11.GL_ONE_MINUS_DST_COLOR, GL11.GL_ONE_MINUS_SRC_COLOR);
		

			DrawHelper.fill(matrices, cx, cy - 4, cx + 1, cy, 0, 0xFFFFFFFF);
			DrawHelper.fill(matrices, cx, cy + 1, cx + 1, cy + 5, 0, 0xFFFFFFFF);
			DrawHelper.fill(matrices, cx - 4, cy, cx, cy + 1, 0, 0xFFFFFFFF);
			DrawHelper.fill(matrices, cx, cy, cx + 5, cy + 1, 0, 0xFFFFFFFF);
		} else {
			DrawHelper.fill(matrices, cx, cy - 4, cx + 1, cy + 5, 0, 0xFFFFFFFF);
			DrawHelper.fill(matrices, cx - 4, cy, cx + 5, cy + 1, 0, 0xFFFFFFFF);
		}
		
		RenderHelper.disableBlend();
	}
	
	private void renderDebugInfo(MatrixStack matrices) {
		FontRenderer fontRenderer = this.getFontRenderer();
		
		int y = 2;
		DrawHelper.drawStringWithShadow(matrices, fontRenderer, "0.0.23a_01", 2, y, 0xFFFFFF);
		
		y += 10;
		
		if(this.minecraft.gameOptions.showFPS.getValue()) {
			DrawHelper.drawStringWithShadow(matrices, fontRenderer, this.minecraft.fpsString, 2, y, 0xFFFFFF);
			y += 10;
		}
		
		if (this.minecraft.player != null && this.minecraft.gameOptions.moreDebugInfo.getValue()) {
			DrawHelper.drawStringWithShadow(matrices, fontRenderer, String.format("XYZ: %f / %f / %f", this.minecraft.player.x, this.minecraft.player.y, this.minecraft.player.z), 2, y, 0xFFFFFFFF);			
			y += 10;
			DrawHelper.drawStringWithShadow(matrices, fontRenderer, String.format("yRot: %f / %f", this.minecraft.player.yRot, this.minecraft.player.yRotO), 2, y, 0xFFFFFFFF);
			y += 10;
			y += 10;
			DrawHelper.drawStringWithShadow(matrices, fontRenderer, String.format("Level Creator: %s", this.minecraft.level.creator), 2, y, 0xFFFFFFFF);
			y += 10;
			DrawHelper.drawStringWithShadow(matrices, fontRenderer, String.format("Level Name: %s", this.minecraft.level.name), 2, y, 0xFFFFFFFF);
			y += 10;
			DrawHelper.drawStringWithShadow(matrices, fontRenderer, String.format("RX: %f RY: %f", xR, yR), 2, y, 0xFFFFFFFF);
			y += 10;
			y += 10;
			
			if (this.minecraft.hitResult != null) {
				DrawHelper.drawStringWithShadow(matrices, fontRenderer, String.format("Tile: %s", this.minecraft.level.getTile(this.minecraft.hitResult.x, this.minecraft.hitResult.y, this.minecraft.hitResult.z)), 2, y, 0xFFFFFFFF);
			}
		}
		
		y = 2;
		
		if (this.minecraft.gameOptions.moreDebugInfo.getValue()) {
			DrawHelper.drawLeftStringWithShadow(matrices, fontRenderer, String.format("Java: %s", System.getProperty("java.version")), this.scaledWidth - 2, y, 0xFFFFFFFF);
			y += 10;
	        
			long maxMem = Runtime.getRuntime().maxMemory();
	        long totalMem = Runtime.getRuntime().totalMemory();
	        long freeMem = Runtime.getRuntime().freeMemory();
	        long availMem = totalMem - freeMem;    

			DrawHelper.drawLeftStringWithShadow(matrices, fontRenderer, String.format("Mem: % 2d%% %03d/%03dMB", availMem * 100L / maxMem, toMiB(availMem), toMiB(maxMem)), this.scaledWidth - 2, y, 0xFFFFFFFF);
			y += 10;
			
			DrawHelper.drawLeftStringWithShadow(matrices, fontRenderer, String.format("Allocated: % 2d%% %03dMB", totalMem * 100L / maxMem, toMiB(totalMem)), this.scaledWidth - 2, y, 0xFFFFFFFF);
			y += 20;
			
			DrawHelper.drawLeftStringWithShadow(matrices, fontRenderer, String.format("Display: %dx%d", this.minecraft.width, this.minecraft.height), this.scaledWidth - 2, y, 0xFFFFFFFF);
		}
	}
	
    private static long toMiB(long bytes) {
        return bytes / 1024L / 1024L;
    }
	
	private FontRenderer getFontRenderer() {
		return this.minecraft.fontRenderer;
	}

	
	private void renderHotbar(MatrixStack matrices, float tickDelta) {
		Player player = this.minecraft.player;
		if (player != null) {
			RenderHelper.enableBlend();
			Textures.bindTexture(this.minecraft.textures.getTextureId("/gui.png"));
			DrawHelper.drawTexture(matrices, this.scaledWidth / 2 - 91, this.scaledHeight - 22, -90, 182, 22, 0, 0, 182, 22, 256, 256);
			DrawHelper.drawTexture(matrices, this.scaledWidth / 2 - 91 - 1 + player.inventory.selectedSlot * 20, this.scaledHeight - 22 - 1, -90, 24, 22, 0, 22, 24, 22, 256, 256);	
			RenderHelper.disableBlend();
			

			RenderHelper.enableTexture();
			Textures.bindTexture(this.minecraft.textures.getTextureId("/terrain.png"));
			
			int i9;
			for(int i8 = 0; i8 < player.inventory.slots.length; ++i8) {
				if((i9 = player.inventory.slots[i8]) > 0) {
					matrices.push();
					
					matrices.translate((float)(this.scaledWidth / 2 - 90 + i8 * 20) + 18, (float)(this.scaledHeight - 16) + 5, -50.0F);
					matrices.scale(1.0f, -1.0f, 1.0f);
					matrices.scale(16.0f, 16.0f, 16.0f);
					matrices.translate(-0.5f, -0.5f, -0.5f);
					matrices.multiply(Quaternionf.rotationPosXDegrees(30));
					matrices.multiply(Quaternionf.rotationPosYDegrees(-128));
					matrices.scale(0.745f, 0.745f, 0.745f);

					Tessellator tessellator = Tessellator.getInstance();
					
					tessellator.begin(DrawMode.QUADS, VertexFormat.POSITION_TEXTURE_COLOR);
					TileRenderer.renderInGui(matrices, tessellator, Tile.tiles[i9]);
					tessellator.draw();
					
					matrices.pop();
				}
			}
			
			RenderHelper.disableTexture();
		}
	}
	
	public void addChatMessage(String string1) {
		this.messages.add(0, new ChatLine(string1));

		while(this.messages.size() > 50) {
			this.messages.remove(this.messages.size() - 1);
		}
	}
}