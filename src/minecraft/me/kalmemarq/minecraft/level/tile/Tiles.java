package me.kalmemarq.minecraft.level.tile;

import java.util.HashMap;
import java.util.Map;

import com.mojang.minecraft.level.liquid.Liquid;

import me.kalmemarq.minecraft.level.tile.Tile.SoundType;

public class Tiles {
	public static final Map<Integer, Tile> ID_TO_TILE = new HashMap<Integer, Tile>();
	
	public static final Tile AIR = register(new AirTile(0, "Air"));
	public static final Tile ROCK = register(new Tile(1, "Rock", 1, new Tile.Settings().sound(SoundType.STONE).gravity(1.0f, 1.0f)));
	public static final Tile GRASS = register(new Tile(2, "Grass", 2, new Tile.Settings().sound(SoundType.GRASS).gravity(0.9f, 1.0f)));
	public static final Tile DIRT = register(new DirtTile(3, "Dirt", new Tile.Settings().sound(SoundType.GRASS).gravity(0.8f, 1.0f)));
	public static final Tile WOOD = register(new Tile(4, "Wood", 16, new Tile.Settings().sound(SoundType.WOOD).gravity(1.0f, 1.0f)));
	public static final Tile STONE_BRICK = register(new Tile(5, "Stone Brick", 4, new Tile.Settings().sound(Tile.SoundType.WOOD).gravity(1.0F, 1.0F)));
	public static final Tile BUSH = register(new BushTile(6, "Bush", 15, new Tile.Settings().gravity(0.7F, 1.0F)));
	public static final Tile UNBREAKABLE = register(new Tile(7, "Unbreakable", 17, new Tile.Settings().sound(Tile.SoundType.STONE).gravity(1.0F, 1.0F)));
	public static final Tile water = register(new LiquidTile(8, "Water", Liquid.water, new Tile.Settings().gravity(1.0F, 1.0F)));
	public static final Tile calmWater = register(new CalmLiquidTile(9, "Calm Water", Liquid.water, new Tile.Settings().gravity(1.0F, 1.0F)));
	public static final Tile lava = register(new LiquidTile(10, "Lava", Liquid.lava, new Tile.Settings().gravity(1.0F, 1.0F)));
	public static final Tile calmLava = register(new CalmLiquidTile(11, "Calm Lava", Liquid.lava, new Tile.Settings().gravity(1.0F, 1.0F)));
	public static final Tile SAND = register(new FallingTile(12, "Sand", 18, new Tile.Settings().sound(Tile.SoundType.GRAVEL).gravity(0.8F, 1.0F)));
	public static final Tile GRAVEL = register(new FallingTile(13, "Gravel", 19, new Tile.Settings().sound(Tile.SoundType.GRAVEL).gravity(0.8F, 1.0F)));
	public static final Tile GOLD_ORE = register(new Tile(14, "Gold Ore", 32, new Tile.Settings().sound(Tile.SoundType.STONE).gravity(1.0F, 1.0F)));
	public static final Tile IRON_ORE = register(new Tile(15, "Iron Ore", 33, new Tile.Settings().sound(Tile.SoundType.STONE).gravity(1.0F, 1.0F)));
	public static final Tile COAL_ORE = register(new Tile(16, "Coal Ore", 34, new Tile.Settings().sound(Tile.SoundType.STONE).gravity(1.0F, 1.0F)));
	public static final Tile LOG = register(new LogTile(17, "Log", new Tile.Settings().sound(Tile.SoundType.WOOD).gravity(1.0F, 1.0F)));
	public static final Tile LEAVES = register(new LeavesTile(18, "Leaves", 22, true, new Tile.Settings().sound(Tile.SoundType.GRASS).gravity(1.0F, 0.4F).solid(false)));
	public static final Tile SPONGE = register(new SpongeTile(19, "Sponge", new Tile.Settings().sound(Tile.SoundType.CLOTH).gravity(1.0F, 0.9F)));
	public static final Tile GLASS = register(new GlassTile(20, "Glass", 49, false, new Tile.Settings().sound(Tile.SoundType.METAL).gravity(1.0F, 1.0F)));
	public static final Tile RED_CLOTH = register(new Tile(21, "Red Cloth", 64, new Tile.Settings().sound(Tile.SoundType.CLOTH).gravity(1.0F, 1.0F)));
	public static final Tile ORANGE_CLOTH = register(new Tile(22, "Orange Cloth", 65, new Tile.Settings().sound(Tile.SoundType.CLOTH).gravity(1.0F, 1.0F)));
	public static final Tile YELLOW_CLOTH = register(new Tile(23, "Yellow Cloth", 66, new Tile.Settings().sound(Tile.SoundType.CLOTH).gravity(1.0F, 1.0F)));
	public static final Tile CHARTREUSE_CLOTH = register(new Tile(24, "Chartreuse Cloth", 67, new Tile.Settings().sound(Tile.SoundType.CLOTH).gravity(1.0F, 1.0F)));
	public static final Tile GREEN_CLOTH = register(new Tile(25, "Green Cloth", 68, new Tile.Settings().sound(Tile.SoundType.CLOTH).gravity(1.0F, 1.0F)));
	public static final Tile SPRING_GREEN_CLOTH = register(new Tile(26, "Spring Green Cloth", 69, new Tile.Settings().sound(Tile.SoundType.CLOTH).gravity(1.0F, 1.0F)));
	public static final Tile CYAN_CLOTH = register(new Tile(27, "Cyan Cloth", 70, new Tile.Settings().sound(Tile.SoundType.CLOTH).gravity(1.0F, 1.0F)));
	public static final Tile CAPRI_CLOTH = register(new Tile(28, "Capri Cloth", 71, new Tile.Settings().sound(Tile.SoundType.CLOTH).gravity(1.0F, 1.0F)));
	public static final Tile ULTRAMARINE_CLOTH = register(new Tile(29, "Ultramarine Cloth", 72, new Tile.Settings().sound(Tile.SoundType.CLOTH).gravity(1.0F, 1.0F)));
	public static final Tile VIOLET_CLOTH = register(new Tile(30, "Violet Cloth",  73, new Tile.Settings().sound(Tile.SoundType.CLOTH).gravity(1.0F, 1.0F)));
	public static final Tile PURPLE_CLOTH = register(new Tile(31, "Purple Cloth", 74, new Tile.Settings().sound(Tile.SoundType.CLOTH).gravity(1.0F, 1.0F)));
	public static final Tile MAGENTA_CLOTH = register(new Tile(32, "Magenta Cloth", 75, new Tile.Settings().sound(Tile.SoundType.CLOTH).gravity(1.0F, 1.0F)));
	public static final Tile ROSE_CLOTH = register(new Tile(33, "Rose Cloth", 76, new Tile.Settings().sound(Tile.SoundType.CLOTH).gravity(1.0F, 1.0F)));
	public static final Tile DARK_GRAY_CLOTH = register(new Tile(34, "Dark Gray Cloth", 77, new Tile.Settings().sound(Tile.SoundType.CLOTH).gravity(1.0F, 1.0F)));
	public static final Tile GRAY_CLOTH = register(new Tile(35, "Gray Cloth", 78, new Tile.Settings().sound(Tile.SoundType.CLOTH).gravity(1.0F, 1.0F)));
	public static final Tile WHITE_CLOTH = register(new Tile(36, "White Cloth", 79, new Tile.Settings().sound(Tile.SoundType.CLOTH).gravity(1.0F, 1.0F)));
	public static final Tile YELLOW_FLOWER = register(new BushTile(37, "Yellow Flower", 13, new Tile.Settings().sound(Tile.SoundType.NONE).gravity(0.7F, 1.0F)));
	public static final Tile RED_FLOWER = register(new BushTile(38, "Red Flower", 12, new Tile.Settings().gravity(0.7F, 1.0F)));
	public static final Tile BROWN_MUSHROOM = register(new BushTile(39, "Brown Mushroom", 29, new Tile.Settings().gravity(0.7F, 1.0F)));
	public static final Tile RED_MUSHROOM = register(new BushTile(40, "Red Mushroom", 28, new Tile.Settings().gravity(0.7F, 1.0F)));
	public static final Tile GOLD_BLOCK = register(new Tile(41, "Gold Block", 40, new Tile.Settings().sound(Tile.SoundType.METAL).gravity(0.7F, 1.0F)));

	public static Tile byId(int id) {
		return ID_TO_TILE.getOrDefault(id, Tiles.AIR);
	}
	
	public static Tile register(Tile tile) {
		ID_TO_TILE.put(tile.getId(), tile);
		return tile;
	}
}
