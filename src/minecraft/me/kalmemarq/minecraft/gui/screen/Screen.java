package me.kalmemarq.minecraft.gui.screen;

import java.util.ArrayList;
import java.util.List;

import org.lwjgl.input.Keyboard;
import org.lwjgl.input.Mouse;

import com.mojang.minecraft.Minecraft;
import com.mojang.minecraft.gui.Font;
import com.mojang.minecraft.renderer.Textures;

import me.kalmemarq.minecraft.gui.widget.Widget;
import me.kalmemarq.minecraft.render.DrawHelper;
import me.kalmemarq.minecraft.render.DrawMode;
import me.kalmemarq.minecraft.render.FontRenderer;
import me.kalmemarq.minecraft.render.RenderHelper;
import me.kalmemarq.minecraft.render.Tessellator;
import me.kalmemarq.minecraft.render.VertexFormat;
import me.kalmemarq.minecraft.util.MatrixStack;

public abstract class Screen {
	protected List<Widget> children = new ArrayList<Widget>();
	protected Minecraft minecraft;
	protected FontRenderer fontRenderer;
	protected int width;
	protected int height;
	protected String title;
	public boolean passEvents = false;
	
	protected Screen(String title) {
		this.title = title;
	}
	
	public void init(Minecraft mc, int width, int height) {
		this.minecraft = mc;
		this.fontRenderer = mc.fontRenderer;
		this.width = width;
		this.height = height;
		this.children.clear();
		this.init();
	}
	
	public void resize(int width, int height) {
		this.width = width;
		this.height = height;
		this.children.clear();
		this.init();
	}
	
	protected void init() {
	}
	
	public void onRemoved() {
	}
	
	public boolean canCloseOnEsc() {
		return true;
	}
	
	public <T extends Widget> T addWidget(T widget) {
		this.children.add(widget);
		return widget;
	}
	
	public void render(MatrixStack matrices, int mouseX, int mouseY, float tickDelta) {
		for (Widget widget : this.children) {
			widget.render(matrices, minecraft, fontRenderer, mouseX, mouseY, tickDelta);
		}
	}
	
	public void tick() {
	}
	
	protected void renderBackground(MatrixStack matrices) {
		DrawHelper.fillVGradient(matrices, 0, 0, this.width, this.height, 0, 1610941696, -1607454624);
	}
	
	protected void renderDirtBackground(MatrixStack matrices, float offsetY) {
		RenderHelper.setColor(0.25f, 0.25f, 0.25f, 1.0f);
		Textures.bindTexture(this.minecraft.textures.getTextureId("/dirt.png"));
		DrawHelper.drawTexture(matrices, 0, 0, 0, width, height, 0, this.height + (int)offsetY, this.width, this.height, 32, 32);
		RenderHelper.setColor(1.0f, 1.0f, 1.0f, 1.0f);
	}

    public void mouseMoved(int mouseX, int mouseY) {
    }
	
	public void mouseClicked(int mouseX, int mouseY, int button) {
		for (Widget widget : this.children) {
			if (widget.mouseClicked(mouseX, mouseY, button)) {
				return;
			}
		}
	}

    public void mouseReleased(int mouseX, int mouseY, int button) {
    }
	
	public void keyPressed(char chr, int key) {
		if (key == Keyboard.KEY_ESCAPE && this.canCloseOnEsc()) {
			this.minecraft.setScreen(null);
		}
	}

    public void keyReleased(int key) {
    }

    public void mouseDragged(int mouseX, int mouseY, int button, int deltaX, int deltaY) {
    }
	
	public final void updateEvents() {
		while(Mouse.next()) {
			this.updateMouseEvents();
		}

		while(Keyboard.next()) {
			this.updateKeyboardEvents();
		}
	}

	public final void updateMouseEvents() {
		if(Mouse.getEventButtonState()) {
			int i1 = Mouse.getEventX() * this.width / this.minecraft.width;
			int i2 = this.height - Mouse.getEventY() * this.height / this.minecraft.height - 1;
			this.mouseClicked(i1, i2, Mouse.getEventButton());
		}
	}

	public final void updateKeyboardEvents() {
		if (Keyboard.getEventKeyState()) {
			this.keyPressed(Keyboard.getEventCharacter(), Keyboard.getEventKey());
		}
	}
}
