package me.kalmemarq.minecraft.level.tile;

import com.mojang.minecraft.level.Level;
import com.mojang.minecraft.level.liquid.Liquid;

public class FallingTile extends Tile {
	public FallingTile(int id, String name, int texture, Settings settings) {
		super(id, name, texture, settings);
	}
	
	@Override
	public void onBlockAdded(Level level, int x, int y, int z) {
		this.tryToFall(level, x, y, z);
	}
	
	@Override
	public void onNeighborChanged(Level level, int x, int y, int z, int unknown) {
		this.tryToFall(level, x, y, z);
	}
	
	private void tryToFall(Level level, int x, int y, int z) {
		int i11 = x;
		int i5 = y;
		int i6 = z;

		while(true) {
			int i9 = i5 - 1;
			int i7;
			Liquid liquid12;
			if(!((i7 = level.getTile(i11, i9, i6)) == 0 ? true : ((liquid12 = Tiles.byId(i7).getLiquidType()) == Liquid.water ? true : liquid12 == Liquid.lava)) || i5 <= 0) {
				if(i5 != y) {
					level.swap(x, y, z, i11, i5, i6);
				}

				return;
			}

			--i5;
		}
	}
}
