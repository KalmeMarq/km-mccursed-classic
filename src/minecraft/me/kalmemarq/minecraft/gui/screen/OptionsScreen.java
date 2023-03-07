package me.kalmemarq.minecraft.gui.screen;

import me.kalmemarq.minecraft.gui.widget.ButtonWidget;
import me.kalmemarq.minecraft.gui.widget.ButtonWidget.PressAction;
import me.kalmemarq.minecraft.option.GameOptions;
import me.kalmemarq.minecraft.option.Option;
import me.kalmemarq.minecraft.render.DrawHelper;
import me.kalmemarq.minecraft.util.MatrixStack;

public class OptionsScreen extends Screen {
	private final Screen parent;
	private final GameOptions gameOptions;
	private final Option<?>[] options;
	
	public OptionsScreen(Screen parent, GameOptions gameOptions) {
		super("Options");
		this.parent = parent;
		this.gameOptions = gameOptions;
		this.options = new Option[] { gameOptions.music, gameOptions.sound, gameOptions.invertMouse, gameOptions.showFPS, gameOptions.renderDistance };
	}
	
	@Override
	protected void init() {
		for(int i = 0; i < this.options.length; ++i) {
			this.addWidget(this.options[i].createWidget(this.width / 2 - 100, this.height / 6 + i * 24, 200, this.gameOptions));
		}
		
		this.addWidget(new ButtonWidget(this.width / 2 - 100, this.height / 6 + 120 + 12, 200, 20, "Controls...", new PressAction() {
			@Override
			public void onPress(ButtonWidget button) {
				minecraft.setScreen(new ControlsScreen(OptionsScreen.this));
			}			
		}));
		this.addWidget(new ButtonWidget(this.width / 2 - 100, this.height / 6 + 120 + 48, 200, 20, "Done", new PressAction() {
			@Override
			public void onPress(ButtonWidget button) {
				minecraft.setScreen(parent);
			}			
		}));
	}
	
	@Override
	public void render(MatrixStack matrices, int mouseX, int mouseY, float tickDelta) {
		this.renderBackground(matrices);
		DrawHelper.drawCenteredStringWithShadow(matrices, this.fontRenderer, this.title, this.width / 2, 20, 0xFFFFFFFF);
		super.render(matrices, mouseX, mouseY, tickDelta);
	}
}
