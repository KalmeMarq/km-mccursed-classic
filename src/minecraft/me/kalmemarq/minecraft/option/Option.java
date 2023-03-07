package me.kalmemarq.minecraft.option;

import java.util.List;

import me.kalmemarq.minecraft.gui.widget.ButtonWidget;
import me.kalmemarq.minecraft.gui.widget.ButtonWidget.PressAction;

public abstract class Option<T> {
	protected final String key;
	protected final String name;
	protected final ValueChanged<T> valueChanged;
	
	protected Option(String key, String name, ValueChanged<T> valueChanged) {
		this.key = key;
		this.name = name;
		this.valueChanged = valueChanged;
	}
	
	public static Option<Boolean> ofBoolean(String key, String name, boolean value, ValueChanged<Boolean> valueChanged) {
		return new BooleanOption(key, name, value, valueChanged);
	}
	
	public static Option<Boolean> ofBoolean(String key, String name, boolean value) {
		return new BooleanOption(key, name, value, new ValueChanged<Boolean>() {
			@Override
			public void onChange(Boolean value) {	
			}
		});
	}
	
	public static <E extends NameableEnum> Option<E> ofEnum(String key, String name, E value, List<E> values, ValueChanged<E> valueChanged) {
		return new EnumOption<E>(key, name, value, values, valueChanged);
	}
	
	public static <E extends NameableEnum> Option<E> ofEnum(String key, String name, E value, List<E> values) {
		return new EnumOption<E>(key, name, value, values, new ValueChanged<E>() {	
			@Override
			public void onChange(E value) {
			}
		});
	}
	
	public String getKey() {
		return this.key;
	}
	
	public String getName() {
		return this.name;
	}
	
	abstract public T getDefaultValue();
	abstract public T getValue();
	abstract public void setValue(T value);
	abstract public void parseValue(String value);
	abstract public String encodeValue();
	
	abstract public ButtonWidget createWidget(int x, int y, int width, GameOptions options);
	
	public static class BooleanOption extends Option<Boolean> {
		private final boolean defaultValue;
		private boolean value;
		
		protected BooleanOption(String key, String name, boolean value, ValueChanged<Boolean> valueChanged) {
			super(key, name, valueChanged);
			this.defaultValue = value;
			this.value = value;
		}

		@Override
		public Boolean getDefaultValue() {
			return this.defaultValue;
		}

		@Override
		public Boolean getValue() {
			return this.value;
		}

		@Override
		public void setValue(Boolean value) {
			this.value = value;
			this.valueChanged.onChange(value);
		}

		@Override
		public void parseValue(String value) {
			this.value = "true".equals(value);
		}
		
		@Override
		public String encodeValue() {
			return this.value ? "true" : "false";
		}
		
		@Override
		public ButtonWidget createWidget(int x, int y, int width, final GameOptions options) {
			return new ButtonWidget(x, y, width, 20, this.name + ": " + (this.value ? "ON" : "OFF"), new PressAction() {
				@Override
				public void onPress(ButtonWidget button) {
					setValue(!getValue());
					button.setText(name + ": " + (value ? "ON" : "OFF"));
					options.save();
				}
			});
		}
	}
	
	public static class EnumOption<E extends NameableEnum> extends Option<E> {
		private final E defaultValue;
		private E value;
		private final List<E> values;
		
		protected EnumOption(String key, String name, E value, List<E> values, ValueChanged<E> valueChanged) {
			super(key, name, valueChanged);
			this.defaultValue = value;
			this.value = value;
			this.values = values;
		}

		@Override
		public E getDefaultValue() {
			return this.defaultValue;
		}

		@Override
		public E getValue() {
			return this.value;
		}

		@Override
		public void setValue(E value) {
			if (this.values.contains(value)) {
				this.value = value;
				this.valueChanged.onChange(value);
			}
		}
		
		public List<E> getValues() {
			return values;
		}

		@Override
		public void parseValue(String value) {
			try {
				int id = Integer.parseInt(value);
				
				for (E vl : this.values) {
					if (vl.getId() == id) {
						this.setValue(vl);
						return;
					}
				}
			} catch(Exception e) {
			}
		}
		
		@Override
		public String encodeValue() {
			return this.value.getId() + "";
		}
		
		@Override
		public ButtonWidget createWidget(int x, int y, int width, final GameOptions options) {
			return new ButtonWidget(x, y, width, 20, name + ": " + value.getName(), new PressAction() {
				@Override
				public void onPress(ButtonWidget button) {
					setValue(values.get((getValue().getId() + 1) % values.size()));
					button.setText(name + ": " + value.getName());
					options.save();
				}
			});
		}
	}
	
	public static class RangeOption<T> {
	}
	
	public interface ValueChanged<T> {
		void onChange(T value);
	}
}
