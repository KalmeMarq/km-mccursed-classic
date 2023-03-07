package me.kalmemarq.minecraft.level.tile;

import com.mojang.minecraft.level.Level;

public class AirTile extends Tile {
	public AirTile(int id, String name) {
		super(id, name, 0, new Settings().solid(false).translucent());
	}
	
	@Override
	public boolean shouldRenderFace(Level level, int x, int y, int z, int pass, int face) {
		return false;
	}
}
