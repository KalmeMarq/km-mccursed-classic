package me.kalmemarq.minecraft.render;

import java.nio.ByteBuffer;

import org.lwjgl.BufferUtils;
import org.lwjgl.opengl.GL11;

import me.kalmemarq.minecraft.util.Matrix4f;
import me.kalmemarq.minecraft.util.Vector4f;

public class Tessellator {
	private static final Tessellator INSTANCE = new Tessellator(0x70000);
	
	private VertexFormat vertexFormat;
	private DrawMode drawMode;
	private VertexFormat.Attribute currentAttribute;
	private int currentAttributeId;
	private int attributeOffset = 0;
	private int vertexCount = 0;
	
	private ByteBuffer buffer;
	private boolean building;
	
	private Tessellator(int initialCapacity) {
		this.buffer = BufferUtils.createByteBuffer(initialCapacity);
	}
	
	public static Tessellator getInstance() {
		return INSTANCE;
	}
	
	private void putByte(int index, byte value) {
		this.buffer.put(this.attributeOffset + index, value);
	}
	
	private void putFloat(int index, float value) {
		this.buffer.putFloat(this.attributeOffset + index, value);
	}
	
	public Tessellator vertex(Matrix4f matrix, double x, double y, double z) {
		Vector4f vec = matrix.transform(new Vector4f((float)x, (float)y, (float)z, 1.0f));
		return this.vertex(vec.x(), vec.y(), vec.z());
	}
	
	public Tessellator vertexUV(double x, double y, double z, float u, float v) {
		vertex(x, y, z);
		texture(u, v);
		return this;
	}
	
	public Tessellator vertex(double x, double y, double z) {
		if (this.currentAttribute != VertexFormat.Attribute.POSITION) {
			return this;
		}
		this.putFloat(0, (float)x);
		this.putFloat(4, (float)y);
		this.putFloat(8, (float)z);
		this.nextAttribute();
		return this;
	}
	
	public Tessellator texture(float u, float v) {
		if (this.currentAttribute != VertexFormat.Attribute.TEXTURE) {
			return this;
		}
		this.putFloat(0, u);
		this.putFloat(4, v);
		this.nextAttribute();
		return this;
	}
	
	public Tessellator color(float red, float green, float blue, float alpha) {
		return color((int)(red * 255), (int)(green * 255), (int)(blue * 255), (int)(alpha * 255));
	}
	
	public Tessellator color(float red, float green, float blue) {
		return color((int)(red * 255), (int)(green * 255), (int)(blue * 255), 255);
	}

	public Tessellator color(int red, int green, int blue) {
		return color(red, green, blue, 255);
	}
	
	public Tessellator color(int red, int green, int blue, int alpha) {
		if (this.currentAttribute != VertexFormat.Attribute.COLOR) {
			return this;
		}
		this.putByte(0, (byte)red);
		this.putByte(1, (byte)green);
		this.putByte(2, (byte)blue);
		this.putByte(3, (byte)alpha);
		this.nextAttribute();
		return this;
	}
	
	public Tessellator normal(double x, double y, double z) {
		if (this.currentAttribute != VertexFormat.Attribute.NORMAL) {
			return this;
		}
		this.putFloat(0, (float)x);
		this.putFloat(4, (float)y);
		this.putFloat(8, (float)z);
		this.nextAttribute();
		return this;
	}
	
	private void nextAttribute() {
		this.attributeOffset += this.currentAttribute.size;
		this.currentAttributeId = (this.currentAttributeId + 1) % this.vertexFormat.attributes.length;
		this.currentAttribute = this.vertexFormat.attributes[this.currentAttributeId];
	}
	
	public void next() {
		if (this.currentAttributeId != 0) {
			throw new IllegalStateException("Some vertex attributes have not been filled");
		}
		++this.vertexCount;
	}
	
	private void reset() {
		this.building = false;
		this.vertexCount = 0;
		this.attributeOffset = 0;
		this.drawMode = null;
		this.vertexFormat = null;
		this.currentAttribute = null;
		this.currentAttributeId = 0;
	}
	
	public void begin(DrawMode mode, VertexFormat format) {
		if (this.building) {
			throw new IllegalStateException("Already building!");
		}
		this.building = true;
		this.drawMode = mode;
		this.vertexFormat = format;
		this.currentAttributeId = 0;
		this.currentAttribute = format.attributes[0];
		this.buffer.rewind();
	}
	
	public void draw() {
		if (!this.building) {
			throw new IllegalStateException("Not building!");
		}
		this.vertexFormat.setup(this.buffer);		
		GL11.glDrawArrays(this.drawMode.glType, 0, this.vertexCount);
		this.vertexFormat.clear();

		this.reset();
		this.buffer.clear();
	}
	
	public BuiltBuffer end() {
		BuiltBuffer b = new BuiltBuffer();
		byte[] bb = new byte[attributeOffset];
		this.buffer.get(0, bb);
		b.buffer = BufferUtils.createByteBuffer(bb.length);
		b.buffer.put(bb);
		b.vertexCount = vertexCount;
		b.drawMode = drawMode;
		b.vertexFormat = vertexFormat;
		this.reset();
		this.buffer.clear();
		return b;
	}
	
	public class BuiltBuffer {
		public ByteBuffer buffer;
		public VertexFormat vertexFormat;
		public DrawMode drawMode;
		public int vertexCount;
		
		public void draw() {
			this.vertexFormat.setup(this.buffer);		
			GL11.glDrawArrays(this.drawMode.glType, 0, this.vertexCount);
			this.vertexFormat.clear();
		}
	}
}
