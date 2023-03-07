package me.kalmemarq.minecraft.gui.widget;

import com.mojang.minecraft.Minecraft;
import com.mojang.minecraft.gui.Font;
import com.mojang.minecraft.renderer.Textures;

import me.kalmemarq.minecraft.render.DrawHelper;
import me.kalmemarq.minecraft.render.FontRenderer;
import me.kalmemarq.minecraft.render.RenderHelper;
import me.kalmemarq.minecraft.util.MatrixStack;

public class ButtonWidget extends Widget {
	private String text;
	private PressAction pressAction;
	
	public ButtonWidget(int x, int y, int width, int height, String text, PressAction onPress) {
		super(x, y, width, height);
		this.text = text;
		this.pressAction = onPress;
	}
	
	@Override
	public void render(MatrixStack matrices, Minecraft mc, FontRenderer fontRenderer, int mouseX, int mouseY, float tickDelta) {
		if (this.visible) {
			int v = 1;
			int textColor = 14737632;
			if (!this.enabled) {
				v = 0;
				textColor = -6250336;
			} else if (this.isMouseOver(mouseX, mouseY)) {
				v = 2;
				textColor = 16777120;
			}
			
			RenderHelper.enableBlend();
			RenderHelper.defaultBlendFunc();
			RenderHelper.setColor(1.0f, 1.0f, 1.0f, 1.0f);
			
			Textures.bindTexture(mc.textures.getTextureId("/gui.png"));
			
			DrawHelper.drawTexture(matrices, this.x, this.y, this.width / 2, 20, 0, 46 + (v * 20));
			DrawHelper.drawTexture(matrices, this.x + this.width / 2, this.y, this.width / 2, 20, 200 - this.width / 2, 46 + (v * 20));
		
			DrawHelper.drawCenteredStringWithShadow(matrices, fontRenderer, this.text, this.x + this.width / 2, this.y + (this.height - 8) / 2, textColor);
		
			RenderHelper.disableBlend();
		}
	}
	
	public void setText(String text) {
		this.text = text;
	}
	
	public String getText() {
		return this.text;
	}
	
	@Override
	public void onClick() {
		this.pressAction.onPress(this);
	}
	
	public interface PressAction {
		void onPress(ButtonWidget button);
	}
}
