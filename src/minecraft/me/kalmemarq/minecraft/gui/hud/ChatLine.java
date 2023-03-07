package me.kalmemarq.minecraft.gui.hud;

public final class ChatLine {
	public String message;
	public int counter;

	public ChatLine(String string1) {
		this.message = string1;
		this.counter = 0;
	}
}