package me.kalmemarq.minecraft.level.tile;

import com.mojang.minecraft.level.liquid.Liquid;

public class LiquidTile extends Tile {
	private final Liquid liquid;
	
	public LiquidTile(int id, String name, Liquid liquid, Settings settings) {
		super(id, name, 0, settings);
		this.liquid = liquid;
	}
	
	@Override
	public Liquid getLiquidType() {
		return this.liquid;
	}
}
