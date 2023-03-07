package me.kalmemarq.minecraft.util;

public interface Registry<T> {
	public String getId(T value);
	public T get(String id);
	public void set(String id, T value);
}
