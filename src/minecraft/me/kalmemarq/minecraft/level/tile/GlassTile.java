package me.kalmemarq.minecraft.level.tile;

public class GlassTile extends Tile {
	private final boolean renderAdjacentFaces;
	
	public GlassTile(int id, String name, int texture, boolean renderAdjacentFaces, Settings settings) {
		super(id, name, texture, settings.translucent().solid(false));
		this.renderAdjacentFaces = renderAdjacentFaces;
	}
}
