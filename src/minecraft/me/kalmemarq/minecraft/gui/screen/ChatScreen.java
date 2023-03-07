package me.kalmemarq.minecraft.gui.screen;

import org.lwjgl.input.Keyboard;

import com.mojang.minecraft.net.ConnectionManager;
import com.mojang.minecraft.net.Packet;

import me.kalmemarq.minecraft.render.DrawHelper;
import me.kalmemarq.minecraft.util.MatrixStack;

public class ChatScreen extends Screen {
	private String message = "";
	private int ticks = 0;
	
	public ChatScreen() {
		super("");
	}
	
	@Override
	public void tick() {
		this.ticks++;
	}
	
	@Override
	protected void init() {
		Keyboard.enableRepeatEvents(true);
	}
	
	@Override
	public void onRemoved() {
		Keyboard.enableRepeatEvents(false);
	}
	
	@Override
	public void render(MatrixStack matrices, int mouseX, int mouseY, float tickDelta) {
		DrawHelper.fill(matrices, 2, this.height - 14, this.width - 2, this.height - 2, 0, Integer.MIN_VALUE);
		DrawHelper.drawStringWithShadow(matrices, this.fontRenderer, "> " + this.message + (this.ticks / 6 % 2 == 0 ? "_" : ""), 4, this.height - 12, 14737632);
	}
	
	@Override
	public void keyPressed(char chr, int key) {
		if(key == 1) {
			this.minecraft.setScreen(null);
		} else if(key == 28) {
			ConnectionManager connectionManager10000 = this.minecraft.connectionManager;
			String string4 = this.message.trim();
			ConnectionManager connectionManager3 = connectionManager10000;
			if((string4 = string4.trim()).length() > 0) {
				connectionManager3.connection.sendPacket(Packet.CHAT_MESSAGE, new Object[]{-1, string4});
			}

			this.minecraft.setScreen(null);
		} else {
			if(key == 14 && this.message.length() > 0) {
				this.message = this.message.substring(0, this.message.length() - 1);
			}

			if("abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789 ,.:-_\'*!\\\"#%/()=+?[]{}<>@|$;".indexOf(chr) >= 0 && this.message.length() < 64 - (this.minecraft.user.name.length() + 2)) {
				this.message = this.message + chr;
			}
		}
	}
	
	@Override
	public void mouseClicked(int mouseX, int mouseY, int button) {
		if(button == 0 && this.minecraft.hud.hoveredUsername != null) {
			if(this.message.length() > 0 && !this.message.endsWith(" ")) {
				this.message = this.message + " ";
			}

			this.message = this.message + this.minecraft.hud.hoveredUsername;
			mouseX = 64 - (this.minecraft.user.name.length() + 2);
			if(this.message.length() > mouseX) {
				this.message = this.message.substring(0, mouseX);
			}
		}
	}
}
