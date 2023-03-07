package me.kalmemarq.minecraft.gui.screen;

import me.kalmemarq.minecraft.render.DrawHelper;
import me.kalmemarq.minecraft.util.MatrixStack;

public class ErrorScreen extends Screen {
	private final String description;
	
	public ErrorScreen(String title, String description) {
		super(title);
		this.description = description;
	}
	
	@Override
	public void render(MatrixStack matrices, int mouseX, int mouseY, float tickDelta) {
		DrawHelper.fillVGradient(matrices, 0, 0, this.width, this.height, 0, -12574688, -11530224);
		DrawHelper.drawCenteredStringWithShadow(matrices, this.fontRenderer, this.title, this.width / 2, 90, 0xFFFFFF);
		DrawHelper.drawCenteredStringWithShadow(matrices, this.fontRenderer, this.description, this.width / 2, 110, 0xFFFFFF);
		super.render(matrices, mouseX, mouseY, tickDelta);
	}
	
	@Override
	public void keyPressed(char chr, int key) {
	}
}
