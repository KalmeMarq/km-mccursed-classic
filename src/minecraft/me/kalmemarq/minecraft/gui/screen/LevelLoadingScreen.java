package me.kalmemarq.minecraft.gui.screen;

import org.lwjgl.opengl.GL11;

import com.mojang.minecraft.renderer.Textures;

import me.kalmemarq.minecraft.render.DrawHelper;
import me.kalmemarq.minecraft.render.DrawMode;
import me.kalmemarq.minecraft.render.RenderHelper;
import me.kalmemarq.minecraft.render.Tessellator;
import me.kalmemarq.minecraft.render.VertexFormat;
import me.kalmemarq.minecraft.util.MatrixStack;

public class LevelLoadingScreen extends Screen {
	private String title = "";
	private String text = "";
	private int progress;

	protected LevelLoadingScreen() {
		super("");
	}
	
	@Override
	public void render(MatrixStack matrices, int mouseX, int mouseY, float tickDelta) {
		this.renderDirtBackground(matrices, 0);
		
		Tessellator tessellator = Tessellator.getInstance();
		
		if(this.progress >= 0) {
			int i5 = this.width / 2 - 50;
			int i6 = this.height / 2 + 16;
			GL11.glDisable(GL11.GL_TEXTURE_2D);
			tessellator.begin(DrawMode.QUADS, VertexFormat.POSITION_COLOR);
			tessellator.vertex((float)i5, (float)i6, 0.0F).color(128, 128, 128).next();
			tessellator.vertex((float)i5, (float)(i6 + 2), 0.0F).color(128, 128, 128).next();
			tessellator.vertex((float)(i5 + 100), (float)(i6 + 2), 0.0F).color(128, 128, 128).next();
			tessellator.vertex((float)(i5 + 100), (float)i6, 0.0F).color(128, 128, 128).next();
			
			tessellator.vertex((float)i5, (float)i6, 0.0F).color(128, 255, 128).next();
			tessellator.vertex((float)i5, (float)(i6 + 2), 0.0F).color(128, 255, 128).next();
			tessellator.vertex((float)(i5 + progress), (float)(i6 + 2), 0.0F).color(128, 255, 128).next();
			tessellator.vertex((float)(i5 + progress), (float)i6, 0.0F).color(128, 255, 128).next();
			tessellator.draw();
			GL11.glEnable(GL11.GL_TEXTURE_2D);
		}
		
		DrawHelper.drawCenteredStringWithShadow(matrices, fontRenderer, this.text, this.width / 2, this.height / 2 - 4 - 16, 0xFFFFFFFF);
		DrawHelper.drawCenteredStringWithShadow(matrices, fontRenderer,this.title, this.width / 2, this.height / 2 - 4 + 8, 0xFFFFFFFF);
		
		super.render(matrices, mouseX, mouseY, tickDelta);
	}
}
