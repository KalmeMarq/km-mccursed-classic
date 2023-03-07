package me.kalmemarq.minecraft.util;

public class MathHelper {
    private static final float[] SINE_TABLE = createSinTable();
    
    public static float invSqrt(float value) {
    	 return 1.0f / (float)Math.sqrt(value);
    }
    
    public static boolean absEqualsOne(float r) {
		return (Float.floatToRawIntBits(r) & 0x7FFFFFFF) == 0x3F800000;
    }
    
    public static float sin(float value) {
        return SINE_TABLE[(int)(value * 10430.378f) & 0xFFFF];
    }

    public static float cos(float value) {
        return SINE_TABLE[(int)(value * 10430.378f + 16384.0f) & 0xFFFF];
    }

    private static float[] createSinTable() {
    	float[] arr = new float[65536];
    	for (int i = 0; i < arr.length; ++i) {
            arr[i] = (float)Math.sin((double)i * Math.PI * 2.0 / 65536.0);
        }
    	return arr;
    }
}
