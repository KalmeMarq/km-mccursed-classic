package me.kalmemarq.minecraft.option;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.lwjgl.input.Keyboard;
import org.lwjgl.opengl.Display;

import com.mojang.minecraft.Minecraft;

public class GameOptions {
	public final Option<Boolean> music = Option.ofBoolean("music", "Music", true);
	public final Option<Boolean> sound = Option.ofBoolean("sound", "Sound", true);
	public final Option<Boolean> invertMouse = Option.ofBoolean("invertMouse", "Invert Mouse", false);
	public final Option<Boolean> showFPS = Option.ofBoolean("showFPS", "Show FPS", false);
	public final Option<RenderDistance> renderDistance = Option.ofEnum("renderDistance", "Render Distance", RenderDistance.FAR, Arrays.asList(RenderDistance.values()));
	
	public final Option<Boolean> moreDebugInfo = Option.ofBoolean("moreDebugInfo", "More Debug Info", false);
	public final Option<Boolean> weakAO = Option.ofBoolean("weakAO", "Weak AO", false, new Option.ValueChanged<Boolean>() {
		@Override
		public void onChange(Boolean value) {
			Minecraft.getInstance().levelRenderer.setAllChunksDirty();
		}
	});
	public final Option<Boolean> vsync = Option.ofBoolean("vsync", "Vsync", false, new Option.ValueChanged<Boolean>() {
		@Override
		public void onChange(Boolean value) {
			Display.setVSyncEnabled(value);
		}
	});
	public final Option<CrosshairRender> crosshairRender = Option.ofEnum("crosshairRender", "Crosshair", CrosshairRender.CLASSIC, Arrays.asList(CrosshairRender.values()));
	public final Option<CloudsRender> cloudsRender = Option.ofEnum("cloudsRender", "Clouds", CloudsRender.FLAT, Arrays.asList(CloudsRender.values()));
	// TODO: FOV,GuiScale
	
	public final KeyBinding forwardKey = new KeyBinding("forward", "Forward", Keyboard.KEY_W);
	public final KeyBinding leftKey = new KeyBinding("left", "Left", Keyboard.KEY_A);
	public final KeyBinding backKey = new KeyBinding("back", "Back", Keyboard.KEY_S);
	public final KeyBinding rightKey = new KeyBinding("right", "Right", Keyboard.KEY_D);
	public final KeyBinding jumpKey = new KeyBinding("jump", "Jump", Keyboard.KEY_SPACE);
	public final KeyBinding buildKey = new KeyBinding("build", "Build", Keyboard.KEY_B);
	public final KeyBinding chatKey = new KeyBinding("chat", "Chat", Keyboard.KEY_T);
	public final KeyBinding toggleFogKey = new KeyBinding("toggleFog", "Toggle fog", Keyboard.KEY_F);
	public final KeyBinding saveKey = new KeyBinding("saveLocation", "Save location", Keyboard.KEY_RETURN);
	public final KeyBinding loadKey = new KeyBinding("loadLocation", "Load location", Keyboard.KEY_R);
	public final KeyBinding[] allKeys = new KeyBinding[]{this.forwardKey, this.leftKey, this.backKey, this.rightKey, this.jumpKey, this.buildKey, this.chatKey, this.toggleFogKey, this.saveKey, this.loadKey};
	
	public int guiScale = 4;
	
	private final File optionsFile;
	private final Map<String, String> unknownOptions;
	
	public GameOptions(File gameDir) {
		this.optionsFile = new File(gameDir, "options.txt");
		this.unknownOptions = new HashMap<String, String>();
	}
	
	protected void accept(Visitor visitor) {
		visitor.visitOption(this.music);
		visitor.visitOption(this.sound);
		visitor.visitOption(this.invertMouse);
		visitor.visitOption(this.showFPS);
		visitor.visitOption(this.renderDistance);
		visitor.visitOption(this.moreDebugInfo);
		visitor.visitOption(this.weakAO);
		visitor.visitOption(this.vsync);
		visitor.visitOption(this.crosshairRender);
		visitor.visitOption(this.cloudsRender);
		
		for (KeyBinding binding : allKeys) {
			visitor.visitKeyBinding("key_" + binding.getOptionName(), binding);
		}
	}
	
	public final String getKeyBinding(int i) {
		return this.allKeys[i].getName() + ": " + Keyboard.getKeyName(this.allKeys[i].getKey());
	}
	
	public final void setKeyBinding(int i, int key) {
		this.allKeys[i].setKey(key);
	}
	
	public void load() {
		if (!this.optionsFile.exists()) return;

		try {
			BufferedReader reader = new BufferedReader(new FileReader(this.optionsFile));
		
			this.unknownOptions.clear();
			
			final Map<String, String> options = new HashMap<String, String>();
			
			String line;
			while ((line = reader.readLine()) != null) {
				String[] entry = line.split(":");
				if (entry.length == 2) {
					options.put(entry[0], entry[1]);
				}
			}
			
			final List<String> unsetOptions = new ArrayList<String>();
			
			this.accept(new Visitor() {
				@Override
				public void visitOption(Option<?> option) {
					loadOption(options, unsetOptions, option);
				}
				
				@Override
				public void visitKeyBinding(String key, KeyBinding binding) {
					loadKeyBinding(options, unsetOptions, binding);
				}
			});
			
			for (String key : options.keySet()) {
				if (!this.tryLoadUnknownOption(key, options.get(key))) {
					this.unknownOptions.put(key, options.get(key));
				}
			}
			
			if (unsetOptions.size() > 0) {
				System.out.print("Some options weren't loaded (not found in the options.txt): ");
				int i = 0;
				for (String option : unsetOptions) {
					System.out.print(option + (i + 1 < unsetOptions.size() ? "," : ""));
					++i;
				}	
			}
			
			reader.close();
		} catch (IOException e) {
			System.out.println("Failed to load options");
			e.printStackTrace();
		}
	}
	
	protected void loadOption(Map<String, String> options, List<String> unsetOptions, Option<?> option) {
		if (options.containsKey(option.getKey())) {
			option.parseValue(options.get(option.getKey()));
			options.remove(option.getKey());
			return;
		}
		
		unsetOptions.add(option.getKey());
	}
	
	protected void loadKeyBinding(Map<String, String> options, List<String> unsetOptions, KeyBinding keybinding) {
		String key = "key_" + keybinding.getOptionName();
		if (options.containsKey(key)) {
			try {
				int keyCode = Integer.parseInt(options.get(key));
				keybinding.setKey(keyCode);
				return;
			} catch(Exception e) {				
			}
		}
		
		unsetOptions.add("key_" + keybinding.getOptionName());
	}
	
	public void save() {
		try {
			final PrintWriter printWriter = new PrintWriter(new FileWriter(this.optionsFile));

			this.accept(new Visitor() {
				@Override
				public void visitOption(Option<?> option) {
					writeOption(printWriter, option);
				}
				
				@Override
				public void visitKeyBinding(String key, KeyBinding binding) {
					printWriter.print(key);
					printWriter.print(":");
					printWriter.println(binding.getKey());
				}
			});
			
			for (Map.Entry<String, String> entry : this.unknownOptions.entrySet()) {
				printWriter.print(entry.getKey() + ":" + entry.getValue());
			}
			
			printWriter.close();
		} catch (IOException e) {
			System.out.println("Failed to save options");
			e.printStackTrace();
		}
	}
	
	protected void writeOption(PrintWriter writer, Option<?> option) {
		writer.print(option.getKey());
		writer.print(":");
		writer.println(option.encodeValue());
	}
	
	protected boolean tryLoadUnknownOption(String key, String value) {
		return false;
	}
	
	private interface Visitor {
		void visitOption(Option<?> option);
		void visitKeyBinding(String key, KeyBinding binding);
	}
}
