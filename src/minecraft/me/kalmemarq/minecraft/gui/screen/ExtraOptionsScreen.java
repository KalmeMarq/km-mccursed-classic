package me.kalmemarq.minecraft.gui.screen;

import me.kalmemarq.minecraft.gui.widget.ButtonWidget;
import me.kalmemarq.minecraft.gui.widget.ButtonWidget.PressAction;
import me.kalmemarq.minecraft.option.GameOptions;
import me.kalmemarq.minecraft.option.Option;
import me.kalmemarq.minecraft.render.DrawHelper;
import me.kalmemarq.minecraft.util.MatrixStack;

public class ExtraOptionsScreen extends Screen {
	private final Screen parent;
	private final GameOptions gameOptions;
	private final Option<?>[] options;
	
	protected ExtraOptionsScreen(Screen parent, GameOptions gameOptions) {
		super("Extra Options");
		this.parent = parent;
		this.gameOptions = gameOptions;
		this.options = new Option[] { gameOptions.moreDebugInfo, gameOptions.weakAO, gameOptions.vsync, gameOptions.crosshairRender, gameOptions.cloudsRender };
	}
	
	@Override
	protected void init() {	
		for (int i = 0; i < this.options.length; ++i) {
			this.addWidget(this.options[i].createWidget(this.width / 2 - 155 + (i % 2) * 160, this.height / 6 + (i / 2) * 24, 155, this.gameOptions));
		}
		
		this.addWidget(new ButtonWidget(this.width / 2 - 100, this.height / 6 + 120 + 24, 200, 20, "Texture Packs...", new PressAction() {
			@Override
			public void onPress(ButtonWidget button) {
				minecraft.setScreen(new TexturePackScreen(ExtraOptionsScreen.this));
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
