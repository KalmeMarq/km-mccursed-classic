package me.kalmemarq.minecraft.option;

import java.util.HashMap;
import java.util.Map;

public enum RenderDistance implements NameableEnum {
	FAR(0, "Far"),
	NORMAL(1, "Normal"),
	SHORT(2, "Short"),
	TINY(3, "Tiny");
	
	private static final Map<Integer, RenderDistance> BY_ID = new HashMap<Integer, RenderDistance>();
	private final int id;
	private final String name;
	
	private RenderDistance(int id, String name) {
		this.id = id;
		this.name = name;
	}
	
	@Override
	public int getId() {
		return this.id;
	}
	
	@Override
	public String getName() {
		return this.name;
	}
	
	@Override
	public String toString() {
		switch (this) {
		case FAR:
			return "far";
		case NORMAL:
			return "normal";
		case SHORT:
			return "short";
		case TINY:
			return "tiny";
		default:
			throw new IncompatibleClassChangeError();
		}
	}
	
	public static RenderDistance byId(int id) {
		return BY_ID.get(Math.abs(id % BY_ID.size()));
	}
	
	static {
		for (RenderDistance value : RenderDistance.values()) {
			BY_ID.put(value.getId(), value);
		}
	}
}
