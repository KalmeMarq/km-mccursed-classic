package me.kalmemarq.minecraft.level.tile;

import java.util.Random;

import com.mojang.minecraft.level.Level;
import com.mojang.minecraft.level.liquid.Liquid;

public class Tile {
	private static final Random RANDOM = new Random();
	
	private boolean blocksLight = true;
	private boolean isSolid = true;
	private SoundType soundType;
	private float particleGravity;
	
	private final int id;
	private final String name;
	private int texture;
	
	public Tile(int id, String name, int texture, Settings settings) {
		this.id = id;
		this.name = name;
		this.texture = texture;
		this.blocksLight = settings.blocksLight;
		this.isSolid = settings.isSolid;
		this.soundType = settings.soundType;
		this.particleGravity = settings.particleGravity;
	}
	
	public int getId() {
		return this.id;
	}
	
	public String getName() {
		return this.name;
	}
	
	public void tick(Level level, int x, int y, int z, Random random) {
	}
	
	public void onNeighborChanged(Level level, int x, int y, int z, int unknown) {
	}

	public void onBlockAdded(Level level, int x, int y, int z) {
	}

	public int getTickDelay() {
		return 0;
	}

	public void onTileAdded(Level level, int x, int y, int z) {
	}

	public void onTileRemoved(Level level, int x, int y, int z) {
	}
	
	public int getTextureForFace(int face) {
		return 0;
	}
	
	public boolean isSolid() {
		return this.isSolid;
	}
	
	public boolean isLightBlocker() {
		return this.blocksLight;
	}
	
	public Liquid getLiquidType() {
		return Liquid.none;
	}
	
	public boolean shouldRenderFace(Level level, int x, int y, int z, int pass, int face) {
		return pass == 1 ? false : !level.isSolidTile(z, y, z);
	}
	
	public static class Settings {
		private boolean blocksLight = true;
		private boolean isSolid = true;
		private SoundType soundType = SoundType.NONE;
		private float particleGravity;
		
		public Settings sound(SoundType soundType) {
			this.soundType = soundType;
			return this;
		}
		
		public Settings translucent() {
			this.blocksLight = false;
			return this;
		}
		
		public Settings solid(boolean solid) {
			this.isSolid = solid;
			return this;
		}
		
		public Settings gravity(float a, float b) {
			this.particleGravity = b;
			return this;
		}
	}
	
	public static enum SoundType {
		NONE("-", 0.0F, 0.0F),
		GRASS("grass", 0.6F, 1.0F),
		CLOTH("grass", 0.7F, 1.2F),
		GRAVEL("gravel", 1.0F, 1.0F),
		STONE("stone", 1.0F, 1.0F),
		METAL("stone", 1.0F, 2.0F),
		WOOD("wood", 1.0F, 1.0F);

		public final String name;
		private final float volume;
		private final float pitch;

		private SoundType(String string3, float f4, float f5) {
			this.name = string3;
			this.volume = f4;
			this.pitch = f5;
		}

		public float getVolume() {
			return this.volume / (RANDOM.nextFloat() * 0.4F + 1.0F) * 0.5F;
		}

		public float getPitch() {
			return this.pitch / (RANDOM.nextFloat() * 0.2F + 0.9F);
		}
	}
}
