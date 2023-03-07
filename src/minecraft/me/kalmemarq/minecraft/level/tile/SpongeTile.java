package me.kalmemarq.minecraft.level.tile;

import com.mojang.minecraft.level.Level;

public class SpongeTile extends Tile {
	public SpongeTile(int id, String name, Settings settings) {
		super(id, name, 48, settings);
	}
	
	@Override
	public void onTileAdded(Level level, int x, int y, int z) {
		for(int i7 = x - 2; i7 <= x + 2; ++i7) {
			for(int i5 = y - 2; i5 <= y + 2; ++i5) {
				for(int i6 = z - 2; i6 <= z + 2; ++i6) {
					if(level.isWater(i7, i5, i6)) {
						level.setTileNoNeighborChange(i7, i5, i6, 0);
					}
				}
			}
		}
	}
	
	@Override
	public void onTileRemoved(Level level, int x, int y, int z) {
		for(int i7 = x - 2; i7 <= x + 2; ++i7) {
			for(int i5 = y - 2; i5 <= y + 2; ++i5) {
				for(int i6 = z - 2; i6 <= z + 2; ++i6) {
					level.updateNeighborsAt(i7, i5, i6, level.getTile(i7, i5, i6));
				}
			}
		}
	}
}
