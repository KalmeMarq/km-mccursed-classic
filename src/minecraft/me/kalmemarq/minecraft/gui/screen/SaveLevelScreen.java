package me.kalmemarq.minecraft.gui.screen;

import me.kalmemarq.minecraft.gui.widget.ButtonWidget;

public class SaveLevelScreen extends LoadLevelScreen {
	public SaveLevelScreen(Screen parent) {
		super(parent);
		this.title = "Save level";
	}
	
	@Override
	protected void setLevels(String[] levels) {
		for(int i = 0; i < 5; ++i) {
			((ButtonWidget)this.children.get(i)).setText(levels[i]);
			this.children.get(i).setVisible(true);
		}
	}

	@Override
	protected void loadLevel(int index) {
//		this.minecraft.setScreen(new NameLevelScreen(this, ((ButtonWidget)this.children.get(index)).setMessage, index));
	}
}
