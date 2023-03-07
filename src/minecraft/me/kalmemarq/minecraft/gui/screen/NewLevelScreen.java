package me.kalmemarq.minecraft.gui.screen;

import me.kalmemarq.minecraft.gui.widget.ButtonWidget;
import me.kalmemarq.minecraft.gui.widget.ButtonWidget.PressAction;
import me.kalmemarq.minecraft.render.DrawHelper;
import me.kalmemarq.minecraft.util.MatrixStack;

public class NewLevelScreen extends Screen {
	private static final String[] SIZES = new String[] { "Small", "Normal", "Huge" }; 
	
	private final Screen parent;
	
	public NewLevelScreen(Screen parent) {
		super("Generate new level");
		this.parent = parent;
	}
	
	@Override
	protected void init() {
		for (int i = 0; i < SIZES.length; ++i) {
			final int ii = i;
			this.addWidget(new ButtonWidget(this.width / 2 - 100, this.height / 3 + (i * 32), 200, 20, SIZES[i], new PressAction() {
				@Override
				public void onPress(ButtonWidget button) {
					minecraft.generateLevel(ii);
					minecraft.setScreen((Screen)null);
					minecraft.grabMouse();
				}
			}));
		}
		
		this.addWidget(new ButtonWidget(this.width / 2 - 100, this.height / 3 + 96, 200, 20, "Cancel", new PressAction() {	
			@Override
			public void onPress(ButtonWidget button) {
				minecraft.setScreen(parent);
			}
		}));
	}
	
	@Override
	public void render(MatrixStack matrices, int mouseX, int mouseY, float tickDelta) {
		this.renderBackground(matrices);
		DrawHelper.drawCenteredStringWithShadow(matrices, this.fontRenderer, this.title, this.width / 2, 40, 0xFFFFFFFF);
		super.render(matrices, mouseX, mouseY, tickDelta);
	}
}
