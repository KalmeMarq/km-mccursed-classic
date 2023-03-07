package me.kalmemarq.minecraft.level;

import java.util.Arrays;

import com.mojang.minecraft.level.liquid.Liquid;

import me.kalmemarq.minecraft.level.tile.Tile;
import me.kalmemarq.minecraft.level.tile.Tiles;

public class Level {
	private int width;
	private int height;
	private int depth;
	private Properties properties;
	private byte[] blocks;
	private transient int[] heightMap;
	
	public Level() {
	}
	
	public void loadData(int width, int height, int depth, byte[] blocks, Properties properties) {
		this.width = width;
		this.height = height;
		this.depth = depth;
		this.blocks = blocks;
		this.properties = properties;
	}
	
	public Properties getProperties() {
		return this.properties;
	}
	
	public Tile getTileAt(int x, int y, int z) {
		if (this.isInLevelBounds(x, y, z)) {
			return Tiles.byId(this.blocks[(y * this.height + z) * this.width + x] & 255);
		}
		return Tiles.AIR;
	}
	
	public boolean isLightBlocker(int x, int y, int z) {
		return this.getTileAt(x, y, z).isLightBlocker();
	}
	
	public boolean isWater(int x, int y, int z) {
		Tile tile = this.getTileAt(z, y, z);
		if (tile == Tiles.AIR) {
			return false;
		}
		return tile.getLiquidType() == Liquid.water;
	}
	
	private boolean isInLevelBounds(int x, int y, int z) {
		return x >= 0 && y >= 0 && z >= 0 && x < this.width && y < this.depth && z < this.height;
	}
	
	public int getHighestTileAt(int x, int z) {
		int d;
		for(d = this.depth; (this.getTileAt(x, d - 1, z) == Tiles.AIR || this.getTileAt(x, d - 1, z).getLiquidType() != Liquid.none) && d > 0; --d) {
		}

		return d;
	}
	
	public boolean isInShadow(int x, int y, int z) {
		if (this.isInLevelBounds(x, y, z)) {
			return y < this.heightMap[x + z * this.width];
		}
		return false;
	}
	
	public float getBrightnessAt(int x, int y, int z) {
		return this.isInShadow(x, y, z) ? 0.6f : 1.0f;
	}
	
	public float getGroundLevel() {
		return (float)(this.depth / 2 - 2);
	}

	public float getWaterLevel() {
		return (float)(this.depth / 2);
	}

	public byte[] copyBlocks() {
		return Arrays.copyOf(this.blocks, this.blocks.length);
	}
	
	public static class Properties {
		private String name = "-";
		private String creator = "unknown";
		private long createdTime = 0L;
		private int spawnX;
		private int spawnY;
		private int spawnZ;
		private float spawnRotation;
		
		public void setCreatedTime(long createdTime) {
			this.createdTime = createdTime;
		}
		
		public void setCreator(String creator) {
			this.creator = creator;
		}
		
		public void setName(String name) {
			this.name = name;
		}
		
		public String getCreator() {
			return this.creator;
		}
		
		public long getCreatedTime() {
			return this.createdTime;
		}
		
		public String getName() {
			return this.name;
		}
		
		public void setSpawnPosition(int x, int y, int z) {
			this.spawnX = x;
			this.spawnY = y;
			this.spawnZ = z;
		}
		
		public void setSpawnRotation(float rotation) {
			this.spawnRotation = rotation;
		}
		
		public float getSpawnRotation() {
			return this.spawnRotation;
		}
		
		public int getSpawnX() {
			return this.spawnX;
		}
		
		public int getSpawnY() {
			return this.spawnY;
		}
		
		public int getSpawnZ() {
			return this.spawnZ;
		}
	}
}
