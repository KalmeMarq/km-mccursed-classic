package me.kalmemarq.minecraft.gui.screen;

import me.kalmemarq.minecraft.gui.widget.ButtonWidget;
import me.kalmemarq.minecraft.gui.widget.ButtonWidget.PressAction;
import me.kalmemarq.minecraft.util.MatrixStack;

public class ControlsScreen extends Screen {
	private final Screen parent;
	private int selectedKey = -1;
	
	public ControlsScreen(Screen parent) {
		super("Controls");
		this.parent = parent;
	}
	
	@Override
	protected void init() {
		for(int i = 0; i < this.minecraft.gameOptions.allKeys.length; ++i) {
			final int ii = i;
			this.addWidget(new ButtonWidget(this.width / 2 - 155 + i % 2 * 160, this.height / 6 + 24 * (i >> 1), 150, 20, this.minecraft.gameOptions.getKeyBinding(i), new PressAction() {
				@Override
				public void onPress(ButtonWidget button) {
					selectedKey = ii;
					button.setText("> " + minecraft.gameOptions.getKeyBinding(ii) + " <");
				}
			}));
		}
		
		this.addWidget(new ButtonWidget(this.width / 2 - 100, this.height / 6 + 168 - 24, 200, 20, "Reset Keys", new PressAction() {
			@Override
			public void onPress(ButtonWidget button) {
				resetKeys();
			}
		}));
		
		this.addWidget(new ButtonWidget(this.width / 2 - 100, this.height / 6 + 168, 200, 20, "Done", new PressAction() {
			@Override
			public void onPress(ButtonWidget button) {
				minecraft.setScreen(parent);
			}
		}));
	}
	
	@Override
	public void render(MatrixStack matrices, int mouseX, int mouseY, float tickDelta) {
		this.renderBackground(matrices);
		super.render(matrices, mouseX, mouseY, tickDelta);
	}
	
	protected void resetKeys() {
		for (int i = 0; i < this.minecraft.gameOptions.allKeys.length; ++i) {
			this.minecraft.gameOptions.allKeys[i].resetToDefault();
			((ButtonWidget)this.children.get(i)).setText(this.minecraft.gameOptions.getKeyBinding(i));
		}
		this.minecraft.gameOptions.save();
	}
	
	@Override
	public void mouseClicked(int mouseX, int mouseY, int button) {
		for (int i = 0; i < this.minecraft.gameOptions.allKeys.length; ++i) {
			((ButtonWidget)this.children.get(i)).setText(this.minecraft.gameOptions.getKeyBinding(i));
		}
		this.selectedKey = -1;
		
		super.mouseClicked(mouseX, mouseY, button);
	}
	
	@Override
	public void keyPressed(char chr, int key) {
		if(this.selectedKey >= 0) {
			this.minecraft.gameOptions.setKeyBinding(this.selectedKey, key);
			this.minecraft.gameOptions.save();
			((ButtonWidget)this.children.get(this.selectedKey)).setText(this.minecraft.gameOptions.getKeyBinding(this.selectedKey));
			this.selectedKey = -1;
		} else {
			super.keyPressed(chr, key);
		}
	}
}
