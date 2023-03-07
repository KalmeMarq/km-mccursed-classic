package me.kalmemarq.minecraft.render;

import java.nio.ByteBuffer;

import org.lwjgl.opengl.GL11;

public class VertexFormat {
	public static final VertexFormat POSITION = new VertexFormat(Attribute.POSITION);
	public static final VertexFormat POSITION_TEXTURE = new VertexFormat(Attribute.POSITION, Attribute.TEXTURE);
	public static final VertexFormat POSITION_COLOR = new VertexFormat(Attribute.POSITION, Attribute.COLOR);
	public static final VertexFormat POSITION_TEXTURE_COLOR = new VertexFormat(Attribute.POSITION, Attribute.TEXTURE, Attribute.COLOR);
	public static final VertexFormat POSITION_TEXTURE_COLOR_NORMAL = new VertexFormat(Attribute.POSITION, Attribute.TEXTURE, Attribute.COLOR);
	
	public final Attribute[] attributes;
	public final int stride;
	
	public VertexFormat(Attribute... attributes) {
		this.attributes = attributes;
		int srtd = 0;
		
		for (int i = 0; i < attributes.length; ++i) {
			srtd += attributes[i].size;
		}
		
		this.stride = srtd;
	}
	
	public void setup(ByteBuffer buffer) {
		int offset = 0;
		
		for (int i = 0; i < this.attributes.length; ++i) {
			buffer.position(offset);
			this.attributes[i].setup(this.stride, buffer);
			offset += this.attributes[i].size;
		}
	}
	
	public void clear() {
		for (int i = 0; i < this.attributes.length; ++i) {
			this.attributes[i].clear();
		}
	}
	
	public static enum Attribute {
		POSITION(ComponentType.FLOAT, 3, new Setup() {
			@Override
			public void setup(int components, int type, int stride, ByteBuffer buffer) {
				GL11.glVertexPointer(components, type, stride, buffer);
				GL11.glEnableClientState(GL11.GL_VERTEX_ARRAY);
			}
		}, new Clear() {
			@Override
			public void clear() {
				GL11.glDisableClientState(GL11.GL_VERTEX_ARRAY);
			}
		}),
		TEXTURE(ComponentType.FLOAT, 2, new Setup() {
			@Override
			public void setup(int components, int type, int stride, ByteBuffer buffer) {
				GL11.glTexCoordPointer(components, type, stride, buffer);
				GL11.glEnableClientState(GL11.GL_TEXTURE_COORD_ARRAY);
			}
		}, new Clear() {
			@Override
			public void clear() {
				GL11.glDisableClientState(GL11.GL_TEXTURE_COORD_ARRAY);
			}
		}),
		COLOR(ComponentType.UBYTE, 4, new Setup() {
			@Override
			public void setup(int components, int type, int stride, ByteBuffer buffer) {
				GL11.glColorPointer(components, type, stride, buffer);
				GL11.glEnableClientState(GL11.GL_COLOR_ARRAY);
			}
		}, new Clear() {
			@Override
			public void clear() {
				GL11.glDisableClientState(GL11.GL_COLOR_ARRAY);
			}
		}),
		NORMAL(ComponentType.FLOAT, 3, new Setup() {
			@Override
			public void setup(int components, int type, int stride, ByteBuffer buffer) {
				GL11.glNormalPointer(type, stride, buffer);
				GL11.glEnableClientState(GL11.GL_NORMAL_ARRAY);
			}
		}, new Clear() {
			@Override
			public void clear() {
				GL11.glDisableClientState(GL11.GL_NORMAL_ARRAY);
			}
		});
		
		public final int size;
		public final int components;
		public final ComponentType type;
		private final Setup setup;
		private final Clear clear;
		
		Attribute(ComponentType type, int components, Setup setup, Clear clear) {
			this.type = type;
			this.components = components;
			this.size = type.byteSize * components;
			this.setup = setup;
			this.clear = clear;
		}
		
		public void setup(int stride, ByteBuffer buffer) {
			this.setup.setup(this.components, this.type.glType, stride, buffer);
		}
		
		public void clear() {
			this.clear.clear();
		}
	}
	
	public interface Setup {
		void setup(int components, int type, int stride, ByteBuffer buffer);
	}
	
	public interface Clear {
		void clear();
	}
	
	public static enum ComponentType {
		UBYTE(GL11.GL_UNSIGNED_BYTE, Byte.BYTES),
		INT(GL11.GL_INT, Integer.BYTES),
		FLOAT(GL11.GL_FLOAT, Float.BYTES);
		
		public final int byteSize;
		public final int glType;
		
		private ComponentType(int glType, int byteSize) {
			this.glType = glType;
			this.byteSize = byteSize;
		}
	}
}
