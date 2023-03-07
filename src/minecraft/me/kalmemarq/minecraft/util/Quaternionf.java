package me.kalmemarq.minecraft.util;

public class Quaternionf {
	private float x;
	private float y;
	private float z;
	private float w;
	
	public Quaternionf() {
		this.w = 1.0f;
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
	
	public Quaternionf rotateX(float angle) {
		float sin = MathHelper.sin(angle * 0.5f);
		float cos = MathHelper.cos(angle * 0.5f);
		this.x = w * sin + x * cos;
		this.y = y * cos + z * sin;
		this.z = z * cos - y * sin;
		this.w = w * cos - x * sin;
		return this;
	}
	
	public Quaternionf rotateY(float angle) {
		float sin = MathHelper.sin(angle * 0.5f);
		float cos = MathHelper.cos(angle * 0.5f);
		this.x = x * cos - z * sin;
		this.y = w * sin + y * cos;
		this.z = x * sin + z * cos;
		this.w = w * cos - y * sin;
		return this;
	}
	
	public Quaternionf rotateZ(float angle) {
		float sin = MathHelper.sin(angle * 0.5f);
		float cos = MathHelper.cos(angle * 0.5f);
		this.x = x * cos + y * sin;
		this.y = y * cos - x * sin;
		this.z = w * sin + z * cos;
		this.w = w * cos - z * sin;
		return this;
	}
	
	public static Quaternionf rotationNegX(float angle) {
		return new Quaternionf().rotateX(-angle);
	}
	
	public static Quaternionf rotationNegY(float angle) {
		return new Quaternionf().rotateY(-angle);
	}
	
	public static Quaternionf rotationNegZ(float angle) {
		return new Quaternionf().rotateZ(-angle);
	}
	
	public static Quaternionf rotationPosX(float angle) {
		return new Quaternionf().rotateX(angle);
	}
	
	public static Quaternionf rotationPosY(float angle) {
		return new Quaternionf().rotateY(angle);
	}
	
	public static Quaternionf rotationPosZ(float angle) {
		return new Quaternionf().rotateZ(angle);
	}
	
	public static Quaternionf rotationNegXDegrees(float angle) {
		return new Quaternionf().rotateX(-angle * (float)(Math.PI / 180));
	}
	
	public static Quaternionf rotationNegYDegrees(float angle) {
		return new Quaternionf().rotateY(-angle * (float)(Math.PI / 180));
	}
	
	public static Quaternionf rotationNegZDegrees(float angle) {
		return new Quaternionf().rotateZ(-angle * (float)(Math.PI / 180));
	}
	
	public static Quaternionf rotationPosXDegrees(float angle) {
		return new Quaternionf().rotateX(angle * (float)(Math.PI / 180));
	}
	
	public static Quaternionf rotationPosYDegrees(float angle) {
		return new Quaternionf().rotateY(angle * (float)(Math.PI / 180));
	}
	
	public static Quaternionf rotationPosZDegrees(float angle) {
		return new Quaternionf().rotateZ(angle * (float)(Math.PI / 180));
	}
	
	public Quaternionf normalize() {
		float norm = MathHelper.invSqrt(Math.fma(x, x, Math.fma(y, y, Math.fma(z, z, w * w))));
		this.x = x * norm;
		this.y = y * norm;
		this.z = z * norm;
		this.w = w * norm;
		return this;
	}
}
