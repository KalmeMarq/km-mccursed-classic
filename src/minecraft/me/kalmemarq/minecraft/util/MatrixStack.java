package me.kalmemarq.minecraft.util;

import java.util.ArrayDeque;
import java.util.Deque;

public class MatrixStack {
	private final Deque<Matrix4f> stack = createInitialStack();
	
	public void push() {
		Matrix4f last = stack.getLast();
		this.stack.addLast(new Matrix4f(last));
	}
	
	public void pop() {
		this.stack.removeLast();
	}
	
	public Matrix4f peek() {
		return this.stack.getLast();
	}
	
	public MatrixStack identity() {
		this.stack.getLast().identify();
		return this;
	}
	
	public void scale(float x, float y, float z) {
		this.stack.getLast().scale(x, y, z);
	}
	
	public void translate(float x, float y, float z) {
		this.stack.getLast().translate(x, y, z);
	}
	
	public void multiply(Quaternionf quaternion) {
		this.stack.getLast().rotate(quaternion);
	}
	
	public void rotate(float angle, float x, float y, float z) {
		this.stack.getLast().rotate(angle, x, y, z);
	}
	
	public void rotateDegrees(float angle, float x, float y, float z) {
		this.stack.getLast().rotate((float)(angle * Math.PI / 180.0f), x, y, z);
	}
	
	private static Deque<Matrix4f> createInitialStack() {
		Deque<Matrix4f> stack = new ArrayDeque<Matrix4f>();
		stack.add(new Matrix4f());
		return stack;
	}
}
