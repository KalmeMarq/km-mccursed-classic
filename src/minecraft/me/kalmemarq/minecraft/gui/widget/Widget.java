package me.kalmemarq.minecraft.gui.widget;

import com.mojang.minecraft.Minecraft;
import com.mojang.minecraft.gui.Font;

import me.kalmemarq.minecraft.render.FontRenderer;
import me.kalmemarq.minecraft.util.MatrixStack;

public abstract class Widget {
	protected boolean visible = true;
	protected boolean enabled = true;
	protected boolean focused;

	protected int x;
	protected int y;
	protected int width;
	protected int height;
	
	protected Widget(int x, int y, int width, int height) {
		this.x = x;
		this.y = y;
		this.width = width;
		this.height = height;
	}
	
	abstract public void render(MatrixStack matrices, Minecraft mc, FontRenderer fontRenderer, int mouseX, int mouseY, float tickDelta);
	
	public void onClick() {
	}
	
	public boolean mouseClicked(int mouseX, int mouseY, int button) {
		if (this.enabled && this.visible && this.isMouseOver(mouseX, mouseY)) {
			this.onClick();
			return true;
		}
		return false;
	}
	
	public boolean keyPressed(char chr, int key) {
		return false;
	}
	
	public boolean isMouseOver(int mouseX, int mouseY) {
		return mouseX > this.x && mouseX < this.x + this.width && mouseY > this.y && mouseY < this.y + this.height;
	}
	
	public boolean isEnabled() {
		return this.enabled;
	}
	
	public boolean isVisible() {
		return this.visible;
	}
	
	public void setVisible(boolean visible) {
		this.visible = visible;
	}
	
	public void setEnabled(boolean enabled) {
		this.enabled = enabled;
	}
}
