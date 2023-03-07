package me.kalmemarq.minecraft.level.tile;

import java.util.Random;

import com.mojang.minecraft.level.Level;
import com.mojang.minecraft.level.liquid.Liquid;

public class CalmLiquidTile extends LiquidTile {
	public CalmLiquidTile(int id, String name, Liquid liquid, Settings settings) {
		super(id, name, liquid, settings);
	}
	
	@Override
	public void tick(Level level, int x, int y, int z, Random random) {
	}
}
