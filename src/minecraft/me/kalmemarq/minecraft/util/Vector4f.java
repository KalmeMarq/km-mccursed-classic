package me.kalmemarq.minecraft.util;

public class Vector4f {
	private float x;
	private float y;
	private float z;
	private float w;
	
	public Vector4f(float x, float y, float z, float w) {
		this.x = x;
		this.y = y;
		this.z = z;
		this.w = w;
	}
	
	public float x() {
		return this.x;
	}
	
	public float y() {
		return this.y;
	}
	
	public float z() {
		return this.z;
	}
	
	public float w() {
		return this.w;
	}
	
	public Vector4f multiply(Matrix4f matrix) {
		float x = this.x;
		float y = this.y;
		float z = this.z;
		float w = this.w;
		this.x = matrix.m00() * x + matrix.m10() * y + matrix.m20() * z + matrix.m30() * w;
		this.y = matrix.m01() * x + matrix.m11() * y + matrix.m21() * z + matrix.m31() * w;
		this.z = matrix.m02() * x + matrix.m12() * y + matrix.m22() * z + matrix.m32() * w;
		this.w = matrix.m03() * x + matrix.m13() * y + matrix.m23() * z + matrix.m33() * w;
		return this;
	}
}
