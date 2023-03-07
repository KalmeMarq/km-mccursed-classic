package me.kalmemarq.minecraft.option;

public class KeyBinding {
	private final String optionName;
	private final String name;
	private final int defaultKey;
	private int key;
	
	public KeyBinding(String optionName, String name, int key) {
		this.optionName = optionName;
		this.name = name;
		this.defaultKey = key;
		this.key = key;
	}
	
	public int getKey() {
		return key;
	}
	
	public String getName() {
		return name;
	}
	
	public void setKey(int key) {
		this.key = key;
	}
	
	public int getDefaultKey() {
		return defaultKey;
	}
	
	public void resetToDefault() {
		this.key = this.defaultKey;
	}
	
	protected String getOptionName() {
		return optionName;
	}
}
