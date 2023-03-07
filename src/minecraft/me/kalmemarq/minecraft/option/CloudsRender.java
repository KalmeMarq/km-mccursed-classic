package me.kalmemarq.minecraft.option;

import java.util.HashMap;
import java.util.Map;

public enum CloudsRender implements NameableEnum {
	FLAT(0, "Flat"),
	OFF(1, "OFF");
	
	private static final Map<Integer, CloudsRender> BY_ID = new HashMap<Integer, CloudsRender>();
	private final int id;
	private final String name;
	
	private CloudsRender(int id, String name) {
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
		case FLAT:
			return "flat";
		case OFF:
			return "off";
		default:
			throw new IncompatibleClassChangeError();
		}
	}
	
	static {
		for (CloudsRender value : CloudsRender.values()) {
			BY_ID.put(value.getId(), value);
		}
	}
}

