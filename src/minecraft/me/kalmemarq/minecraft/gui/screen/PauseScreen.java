package me.kalmemarq.minecraft.gui.screen;

import me.kalmemarq.minecraft.gui.widget.ButtonWidget;
import me.kalmemarq.minecraft.gui.widget.ButtonWidget.PressAction;
import me.kalmemarq.minecraft.render.DrawHelper;
import me.kalmemarq.minecraft.util.MatrixStack;

public class PauseScreen extends Screen {
	public PauseScreen() {
		super("Game Menu");
	}

	@Override
	protected void init() {
		int y = this.height / 4;
		
		this.addWidget(new ButtonWidget(this.width / 2 - 100, y, 200, 20, "Options...", new PressAction() {
			@Override
			public void onPress(ButtonWidget button) {
				minecraft.setScreen(new OptionsScreen(PauseScreen.this, minecraft.gameOptions));
			}
		}));
		
		y += 24;
	
		this.addWidget(new ButtonWidget(this.width / 2 - 100, y, 200, 20, "Extra Options...", new PressAction() {
			@Override
			public void onPress(ButtonWidget button) {
				minecraft.setScreen(new ExtraOptionsScreen(PauseScreen.this, minecraft.gameOptions));
			}
		}));
		
		y += 24;
	
		ButtonWidget genButton = this.addWidget(new ButtonWidget(this.width / 2 - 100, y, 200, 20, "Generate new level...", new PressAction() {
			@Override
			public void onPress(ButtonWidget button) {
				minecraft.setScreen(new NewLevelScreen(PauseScreen.this));
			}
		}));
		
		y += 24;
		
		ButtonWidget saveButton = this.addWidget(new ButtonWidget(this.width / 2 - 100, y, 200, 20, "Save level..", new PressAction() {
			@Override
			public void onPress(ButtonWidget button) {
				minecraft.setScreen(new SaveLevelScreen(PauseScreen.this));
			}
		}));

		y += 24;
		
		ButtonWidget loadButton = this.addWidget(new ButtonWidget(this.width / 2 - 100, y, 200, 20, "Load level..", new PressAction() {
			@Override
			public void onPress(ButtonWidget button) {
				minecraft.setScreen(new LoadLevelScreen(PauseScreen.this));
			}
		}));

		y += 24;
		
		this.addWidget(new ButtonWidget(this.width / 2 - 100, y, 200, 20, "Back to game", new PressAction() {
			@Override
			public void onPress(ButtonWidget button) {
				minecraft.setScreen(null);
				minecraft.grabMouse();
			}
		}));
		
		if (this.minecraft.user == null) {
			saveButton.setEnabled(false);
			loadButton.setEnabled(false);
		}
		
		if (this.minecraft.connectionManager != null) {
			genButton.setEnabled(false);
			saveButton.setEnabled(false);
			loadButton.setEnabled(false);
		}
	}

	@Override
	public void render(MatrixStack matrices, int mouseX, int mouseY, float tickDelta) {
		this.renderBackground(matrices);
		DrawHelper.drawCenteredStringWithShadow(matrices, this.fontRenderer, this.title, this.width / 2, 40, 0xFFFFFFFF);
		super.render(matrices, mouseX, mouseY, tickDelta);
	}
}
