package me.kalmemarq.minecraft.gui.screen;

import me.kalmemarq.minecraft.gui.widget.ButtonWidget;
import me.kalmemarq.minecraft.gui.widget.ButtonWidget.PressAction;
import me.kalmemarq.minecraft.util.MatrixStack;

public class TexturePackScreen extends Screen {
	private final Screen parent;
	private float down;
	
	public TexturePackScreen(Screen parent) {
		super("Texture Packs");
		this.parent = parent;
	}

	@Override
	protected void init() {
		this.addWidget(new ButtonWidget(this.width / 2 - 100, this.height / 6 + 120 + 48, 200, 20, "Done", new PressAction() {
			@Override
			public void onPress(ButtonWidget button) {
				minecraft.setScreen(parent);
			}			
		}));
	}
	
	@Override
	public void render(MatrixStack matrices, int mouseX, int mouseY, float tickDelta) {
		down += tickDelta * 0.1;
		this.renderDirtBackground(matrices, down);
		super.render(matrices, mouseX, mouseY, tickDelta);
	}
}
