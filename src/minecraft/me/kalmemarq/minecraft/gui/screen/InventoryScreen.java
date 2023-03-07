package me.kalmemarq.minecraft.gui.screen;

import com.mojang.minecraft.User;
import com.mojang.minecraft.level.tile.Tile;
import com.mojang.minecraft.player.Inventory;

import me.kalmemarq.minecraft.render.DrawHelper;
import me.kalmemarq.minecraft.util.MatrixStack;

public class InventoryScreen extends Screen {
	public InventoryScreen() {
		super("Select block");
		this.passEvents = true;
	}
	
	@Override
	public void render(MatrixStack matrices, int mouseX, int mouseY, float tickDelta) {
		DrawHelper.fillVGradient(matrices, this.width / 2 - 120, 30, this.width / 2 + 120, 180, 0, -1878719232, -1070583712);
		DrawHelper.drawCenteredStringWithShadow(matrices, this.fontRenderer, "Select block", this.width / 2, 40, 0xFFFFFFFF);
	}
	
	@Override
	public void mouseClicked(int mouseX, int mouseY, int button) {
		if(button == 0) {
			Inventory inventory = this.minecraft.player.inventory;
			int tile = this.getTileAtSlot(mouseX, mouseY);
			if(tile >= 0) {
				inventory.setTile((Tile)User.creativeTiles.get(tile));
			}

			this.minecraft.setScreen(null);
		}
	}
	
	private int getTileAtSlot(int i1, int i2) {
		for(int i3 = 0; i3 < User.creativeTiles.size(); ++i3) {
			int i4 = this.width / 2 + i3 % 8 * 24 - 96 - 3;
			int i5 = this.height / 2 + i3 / 8 * 24 - 48 + 3;
			if(i1 >= i4 && i1 <= i4 + 24 && i2 >= i5 - 12 && i2 <= i5 + 12) {
				return i3;
			}
		}

		return -1;
	}
}