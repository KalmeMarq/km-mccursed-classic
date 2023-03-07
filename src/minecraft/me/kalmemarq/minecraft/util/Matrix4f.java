package me.kalmemarq.minecraft.util;

import java.nio.FloatBuffer;

public class Matrix4f {
	private float m00;
	private float m01;
	private float m02;
	private float m03;
	private float m10;
	private float m11;
	private float m12;
	private float m13;
	private float m20;
	private float m21;
	private float m22;
	private float m23;
	private float m30;
	private float m31;
	private float m32;
	private float m33;
	
	public Matrix4f() {
	}
	
	public Matrix4f(Matrix4f other) {
		this.m00 = other.m00;
		this.m01 = other.m01;
		this.m02 = other.m02;
		this.m03 = other.m03;
		this.m10 = other.m10;
		this.m11 = other.m11;
		this.m12 = other.m12;
		this.m13 = other.m13;
		this.m20 = other.m20;
		this.m21 = other.m21;
		this.m22 = other.m22;
		this.m23 = other.m23;
		this.m30 = other.m30;
		this.m31 = other.m31;
		this.m32 = other.m32;
		this.m33 = other.m33;
	}
	
	public float m00() {
		return this.m00;
	}
	
	public float m01() {
		return this.m01;
	}
	
	public float m02() {
		return this.m02;
	}
	
	public float m03() {
		return this.m03;
	}
	
	public float m10() {
		return this.m10;
	}
	
	public float m11() {
		return this.m11;
	}
	
	public float m12() {
		return this.m12;
	}
	
	public float m13() {
		return this.m13;
	}
	
	public float m20() {
		return this.m20;
	}
	
	public float m21() {
		return this.m21;
	}
	
	public float m22() {
		return this.m22;
	}
	
	public float m23() {
		return this.m23;
	}
	
	public float m30() {
		return this.m30;
	}
	
	public float m31() {
		return this.m31;
	}
	
	public float m32() {
		return this.m32;
	}
	
	public float m33() {
		return this.m33;
	}

	public Matrix4f identify() {
		this.m00 = 1.0f;
		this.m01 = 0.0f;
		this.m02 = 0.0f;
		this.m03 = 0.0f;
		this.m10 = 0.0f;
		this.m11 = 1.0f;
		this.m12 = 0.0f;
		this.m13 = 0.0f;
		this.m20 = 0.0f;
		this.m21 = 0.0f;
		this.m22 = 1.0f;
		this.m23 = 0.0f;
		this.m30 = 0.0f;
		this.m31 = 0.0f;
		this.m32 = 0.0f;
		this.m33 = 1.0f;
		return this;
	}
	
	public Matrix4f setOrtho(float left, float right, float bottom, float top, float zNear, float zFar) {
		this.identify();
		this.m00 = 2.0f / (right - left);
		this.m11 = 2.0f / (top - bottom);
		this.m22 = 2.0f / (zNear - zFar);
		this.m30 = (right + left) / (left - right);
		this.m31 = (top + bottom) / (bottom - top);
		this.m32 = (zFar + zNear) / (zNear - zFar);
		return this;
	}
	
	public Matrix4f setPerspective(float fov, float aspect, float zNear, float zFar) {
		this.identify();
		float h = (float) Math.tan(fov * 0.5f);
		float rm00 = 1.0f / (h * aspect);
		float rm11 = 1.0f / h;
		float rm22 = (zFar + zNear) / (zNear - zFar);
		float rm32 =  (zFar + zFar) * zNear / (zNear - zFar);

		float nm20 = m20 * rm22 - m30;
		float nm21 = m21 * rm22 - m31;
		float nm22 = m22 * rm22 - m32;
		float nm23 = m23 * rm22 - m33;
		
		this.m00 *= rm00;
		this.m01 *= rm00;
		this.m02 *= rm00;
		this.m03 *= rm00;
		
		this.m10 *= rm11;
		this.m11 *= rm11;
		this.m12 *= rm11;
		this.m13 *= rm11;
		
		this.m30 = m20 * rm32;
		this.m31 = m21 * rm32;
		this.m32 = m22 * rm32;
		this.m33 = m23 * rm32;
		
		this.m20 = nm20;
		this.m21 = nm21;
		this.m22 = nm22;
		this.m23 = nm23;
		
		return this;
	}
	
	public Matrix4f translate(float x, float y, float z) {
		m30 += m00 * x + m10 * y + m20 * z;
		m31 += m01 * x + m11 * y + m21 * z;
		m32 += m02 * x + m12 * y + m22 * z;
		m33 += m03 * x + m13 * y + m23 * z;
		return this;
	}
	
	public Matrix4f rotate(float angle, float x, float y, float z) {
		if (y == 0.0f && z == 0.0f && MathHelper.absEqualsOne(x)) {
			return this.rotateX(angle, x);
		} else if (z == 0.0f && x == 0.0f && MathHelper.absEqualsOne(y)) {
			return this.rotateY(angle, y);
		} else if (x == 0.0f && y == 0.0f && MathHelper.absEqualsOne(z)) {
			return this.rotateZ(angle, z);
		}
		return this._rotate(angle, x, y, z);
	}
	
	public Matrix4f rotate(Quaternionf quaternion) {
		float w2 = quaternion.w() * quaternion.w();
		float x2 = quaternion.x() * quaternion.x();
        float y2 = quaternion.y() * quaternion.y();
        float z2 = quaternion.z() * quaternion.z();
        float zw = quaternion.z() * quaternion.w();
        float dzw = zw + zw;
        float xy = quaternion.x() * quaternion.y();
        float dxy = xy + xy;
        float xz = quaternion.x() * quaternion.z();
        float dxz = xz + xz;
        float yw = quaternion.y() * quaternion.w();
        float dyw = yw + yw;
        float yz = quaternion.y() * quaternion.z();
        float dyz = yz + yz;
        float xw = quaternion.x() * quaternion.w();
        float dxw = xw + xw;
        float rm00 = w2 + x2 - z2 - y2;
        float rm01 = dxy + dzw;
        float rm02 = dxz - dyw;
        float rm10 = -dzw + dxy;
        float rm11 = y2 - z2 + w2 - x2;
        float rm12 = dyz + dxw;
        float rm20 = dyw + dxz;
        float rm21 = dyz - dxw;
        float rm22 = z2 - y2 - x2 + w2;
        float nm00 = m00() * rm00 + m10() * rm01 + m20() * rm02;
        float nm01 = m01() * rm00 + m11() * rm01 + m21() * rm02;
        float nm02 = m02() * rm00 + m12() * rm01 + m22() * rm02;
        float nm03 = m03() * rm00 + m13() * rm01 + m23() * rm02;
        float nm10 = m00() * rm10 + m10() * rm11 + m20() * rm12;
        float nm11 = m01() * rm10 + m11() * rm11 + m21() * rm12;
        float nm12 = m02() * rm10 + m12() * rm11 + m22() * rm12;
        float nm13 = m03() * rm10 + m13() * rm11 + m23() * rm12;
		
        this.m20 = m00 * rm20 + m10 * rm21 + m20 * rm22;
        this.m21 = m01 * rm20 + m11 * rm21 + m21 * rm22;
        this.m22 = m02 * rm20 + m12 * rm21 + m22 * rm22;
        this.m23 = m03 * rm20 + m13 * rm21 + m23 * rm22;
        this.m00 = nm00;
        this.m01 = nm01;
        this.m02 = nm02;
        this.m03 = nm03;
        this.m10 = nm10;
        this.m11 = nm11;
        this.m12 = nm12;
        this.m13 = nm13;        
		return this;
	}
	
	public Matrix4f rotateX(float angle, float x) {
		float sin = MathHelper.sin(angle);
		float cos = MathHelper.cos(angle);
		
		float lm10 = m10;
		float lm11 = m11;
		float lm12 = m12;
		float lm13 = m13;
		float lm20 = m20;
		float lm21 = m21;
		float lm22 = m22;
		float lm23 = m23;
		
		this.m20 = Math.fma(lm10, -sin, lm20 * cos);
		this.m21 = Math.fma(lm11, -sin, lm21 * cos);
		this.m22 = Math.fma(lm12, -sin, lm22 * cos);
		this.m23 = Math.fma(lm13, -sin, lm23 * cos);
		this.m10 = Math.fma(lm10, cos, lm20 * sin);
		this.m11 = Math.fma(lm11, cos, lm21 * sin);
		this.m12 = Math.fma(lm12, cos, lm22 * sin);
		this.m13 = Math.fma(lm13, cos, lm23 * sin);
		
		return this;
	}
	
	public Matrix4f rotateY(float angle, float y) {
		float sin = MathHelper.sin(angle);
		float cos = MathHelper.cos(angle);
		
        float nm00 = Math.fma(m00(), cos, m20() * -sin);
        float nm01 = Math.fma(m01(), cos, m21() * -sin);
        float nm02 = Math.fma(m02(), cos, m22() * -sin);
        float nm03 = Math.fma(m03(), cos, m23() * -sin);
		
        this.m20 = Math.fma(m00, sin, m20 * cos);
        this.m21 = Math.fma(m01, sin, m21 * cos);
        this.m22 = Math.fma(m02, sin, m22 * cos);
        this.m23 = Math.fma(m03, sin, m23 * cos);
        
        this.m00 = nm00;
        this.m01 = nm01;
        this.m02 = nm02;
        this.m03 = nm03;
        
		return this;
	}
	
	public Matrix4f rotateZ(float angle, float z) {
		float sin = MathHelper.sin(angle);
		float cos = MathHelper.cos(angle);
		
		float nm00 = Math.fma(m00(), cos, m10() * sin);
        float nm01 = Math.fma(m01(), cos, m11() * sin);
        float nm02 = Math.fma(m02(), cos, m12() * sin);
        float nm03 = Math.fma(m03(), cos, m13() * sin);
        
        this.m10 = Math.fma(m00(), -sin, m10() * cos);
        this.m11 = Math.fma(m01(), -sin, m11() * cos);
        this.m12 = Math.fma(m02(), -sin, m12() * cos);
        this.m13 = Math.fma(m03(), -sin, m13() * cos);
        
        this.m00 = nm00;
        this.m01 = nm01;
        this.m02 = nm02;
        this.m03 = nm03;
        
		return this;
	}
	
	private Matrix4f _rotate(float angle, float x, float y, float z) {
		float sin = MathHelper.sin(angle);
		float cos = MathHelper.cos(angle);
	    float C = 1.0f - cos;
	    float xy = x * y;
	    float xz = x * z;
	    float yz = y * z;
	    
	    this.m00 = (cos + x * x * C);
	    this.m10 = (xy * C - z * sin);
	    this.m20 = (xz * C + y * sin);
	    this.m01 = (xy * C + z * sin);
	    this.m11 = (cos + y * y * C);
	    this.m21 = (yz * C - x * sin);
	    this.m02 = (xz * C - y * sin);
	    this.m12 = (yz * C + x * sin);
	    this.m22 = (cos + z * z * C);
	    
		return this;
	}
	
	public Matrix4f scale(float x, float y, float z) {
		m00 *= x;
		m01 *= x;
		m02 *= x;
		m03 *= x;
		m10 *= y;
		m11 *= y;
		m12 *= y;
		m13 *= y;
		m20 *= z;
		m21 *= z;
		m22 *= z;
		m23 *= z;
		return this;
	}
	
	public Vector4f transform(Vector4f vec) {
		return vec.multiply(this);
	}
	
	public FloatBuffer get(FloatBuffer buffer, boolean column) {
		if (column) {
			buffer.position(0);
			buffer.put(0, m00);
			buffer.put(1, m10);
			buffer.put(2, m20);
			buffer.put(3, m30);
			buffer.put(4, m01);
			buffer.put(5, m11);
			buffer.put(6, m21);
			buffer.put(7, m31);
			buffer.put(8, m02);
			buffer.put(9, m12);
			buffer.put(10, m22);
			buffer.put(11, m32);
			buffer.put(12, m03);
			buffer.put(13, m13);
			buffer.put(14, m23);
			buffer.put(15, m33);
		} else {
			buffer.position(0);
			buffer.put(0, m00);
			buffer.put(1, m01);
			buffer.put(2, m02);
			buffer.put(3, m03);
			buffer.put(4, m10);
			buffer.put(5, m11);
			buffer.put(6, m12);
			buffer.put(7, m13);
			buffer.put(8, m20);
			buffer.put(9, m21);
			buffer.put(10, m22);
			buffer.put(11, m23);
			buffer.put(12, m30);
			buffer.put(13, m31);
			buffer.put(14, m32);
			buffer.put(15, m33);
		}
		return buffer;
	}
}
