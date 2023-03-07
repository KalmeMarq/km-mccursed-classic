package me.kalmemarq.minecraft.level.tile;

import java.util.Random;

import com.mojang.minecraft.level.Level;

public class BushTile extends Tile {
	public BushTile(int id, String name, int texture, Settings settings) {
		super(id, name, texture, settings.solid(false).translucent());
	}
	
	@Override
	public void tick(Level level, int x, int y, int z, Random random) {
		int i6 = level.getTile(x, y - 1, z);
		if(!level.isLit(x, y, z) || i6 != Tiles.DIRT.getId() && i6 != Tiles.GRASS.getId()) {
			level.setTile(x, y, z, 0);
		}
	}
}
