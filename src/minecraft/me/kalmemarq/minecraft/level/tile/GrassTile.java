package me.kalmemarq.minecraft.level.tile;

import java.util.Random;

import com.mojang.minecraft.level.Level;

public class GrassTile extends Tile {
	public GrassTile(int id, String name, Settings settings) {
		super(id, name, 3, settings);
	}
	
	public final int getTexture(int i1) {
		return i1 == 1 ? 0 : (i1 == 0 ? 2 : 3);
	}

	public final void tick(Level level, int x, int y, int z, Random random) {
		if(random.nextInt(4) == 0) {
			if(!level.isLit(x, y + 1, z)) {
				level.setTile(x, y, z, Tiles.DIRT.getId());
			} else {
				for(int i9 = 0; i9 < 4; ++i9) {
					int i6 = x + random.nextInt(3) - 1;
					int i7 = y + random.nextInt(5) - 3;
					int i8 = z + random.nextInt(3) - 1;
					if(level.getTile(i6, i7, i8) == Tiles.DIRT.getId() && level.isLit(i6, i7 + 1, i8)) {
						level.setTile(i6, i7, i8, Tiles.GRASS.getId());
					}
				}
			}
		}
	}
}
