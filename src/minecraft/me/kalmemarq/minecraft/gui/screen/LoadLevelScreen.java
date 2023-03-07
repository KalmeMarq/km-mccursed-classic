package me.kalmemarq.minecraft.gui.screen;

import me.kalmemarq.minecraft.gui.widget.ButtonWidget;
import me.kalmemarq.minecraft.gui.widget.ButtonWidget.PressAction;
import me.kalmemarq.minecraft.render.DrawHelper;
import me.kalmemarq.minecraft.util.MatrixStack;

public class LoadLevelScreen extends Screen{
	private final Screen parent;
	private boolean loaded;
	private boolean finished;
	private String status = "";
	
	public LoadLevelScreen(Screen parent) {
		super("Load level");
		this.parent = parent;
	}
	
	@Override
	protected void init() {
		(new Thread(new LevelFetcher())).start();
		
		for(int i = 0; i < 5; ++i) {
			final int ii = i;
			this.addWidget(new ButtonWidget(this.width / 2 - 100, this.height / 4 + i * 24, 200, 20, "---", new PressAction() {
				@Override
				public void onPress(ButtonWidget button) {
					loadLevel(ii);
				}
			})).setVisible(false);
		}
		
		this.addWidget(new ButtonWidget(this.width / 2 - 100, this.height / 4 + 144, 200, 20, "Cancel", new PressAction() {	
			@Override
			public void onPress(ButtonWidget button) {
				if (finished || loaded) minecraft.setScreen(parent);
			}
		}));
	}
	
	protected void setLevels(String[] levels) {
		for(int i = 0; i < 5; ++i) {
			this.children.get(i).setEnabled(!levels[i].equals("-"));
			((ButtonWidget)this.children.get(i)).setText(levels[i]);
			this.children.get(i).setVisible(true);
		}
	}
	
	protected void loadLevel(int index) {
		this.minecraft.loadLevel(this.minecraft.user.name, index);
		this.minecraft.setScreen(null);
		this.minecraft.grabMouse();
	}
	
	@Override
	public void render(MatrixStack matrices, int mouseX, int mouseY, float tickDelta) {
		this.renderBackground(matrices);
		DrawHelper.drawCenteredStringWithShadow(matrices, this.fontRenderer, this.title, this.width / 2, 40, 0xFFFFFFFF);
		if(!this.loaded) {
			DrawHelper.drawCenteredStringWithShadow(matrices, this.fontRenderer, this.status, this.width / 2, this.height / 2 - 4, 0xFFFFFF);
		}
		super.render(matrices, mouseX, mouseY, tickDelta);
	}
	
	private class LevelFetcher implements Runnable {
		@Override
		public void run() {
			LoadLevelScreen.this.status = "Failed to load levels";
			LoadLevelScreen.this.finished = true;
		}
	}
}
