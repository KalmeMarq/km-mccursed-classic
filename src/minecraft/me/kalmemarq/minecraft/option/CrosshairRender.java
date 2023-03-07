package me.kalmemarq.minecraft.option;

import java.util.HashMap;
import java.util.Map;

public enum CrosshairRender implements NameableEnum {
	CLASSIC(0, "Classic"),
	INVERTED(1, "Inverted");
	
	private static final Map<Integer, CrosshairRender> BY_ID = new HashMap<Integer, CrosshairRender>();
	private final int id;
	private final String name;
	
	private CrosshairRender(int id, String name) {
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
		case CLASSIC:
			return "classic";
		case INVERTED:
			return "inverted";
		default:
			throw new IncompatibleClassChangeError();
		}
	}
	
	static {
		for (CrosshairRender value : CrosshairRender.values()) {
			BY_ID.put(value.getId(), value);
		}
	}
}
