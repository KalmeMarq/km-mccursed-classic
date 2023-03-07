package me.kalmemarq.minecraft.level.tile;

public class LogTile extends Tile {
	public LogTile(int id, String name, Settings settings) {
		super(id, name, 20, settings);
	}

	@Override
	public int getTextureForFace(int face) {
		return face == 1 ? 21 : (face == 0 ? 21 : 20);
	}
}
