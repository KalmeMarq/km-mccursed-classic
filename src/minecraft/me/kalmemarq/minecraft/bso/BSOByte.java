package me.kalmemarq.minecraft.bso;

import java.io.DataOutput;
import java.io.IOException;

public class BSOByte extends BSOTag {
	private byte value;
	
	public BSOByte(int value) {
		this.value = (byte)value;
	}
	
	@Override
	public BSOType getType() {
		return BSOTypes.BYTE;
	}
	
	public short getValue() {
		return this.value;
	}

	@Override
	public void write(DataOutput output) throws IOException {
		output.writeByte(this.value);
	}
}
