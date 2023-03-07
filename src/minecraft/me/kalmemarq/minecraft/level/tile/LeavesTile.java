package me.kalmemarq.minecraft.level.tile;

public class LeavesTile extends Tile {
	private final boolean renderAdjacentFaces;
	
	public LeavesTile(int id, String name, int texture, boolean renderAdjacentFaces, Settings settings) {
		super(id, name, texture, settings);
		this.renderAdjacentFaces = renderAdjacentFaces;
	}
}
