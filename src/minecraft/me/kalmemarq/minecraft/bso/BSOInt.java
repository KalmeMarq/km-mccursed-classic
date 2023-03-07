package me.kalmemarq.minecraft.bso;

import java.io.DataOutput;
import java.io.IOException;

public class BSOInt extends BSOTag {
	private int value;
	
	public BSOInt(int value) {
		this.value = value;
	}
	
	@Override
	public BSOType getType() {
		return BSOTypes.INT;
	}
	
	public int getValue() {
		return this.value;
	}

	@Override
	public void write(DataOutput output) throws IOException {
		int range = checkRange();
		if (range == 2) {
			output.writeByte((byte)this.value);
		} else if (range == 1) {
			output.writeShort((short)this.value);
		} else {
			output.writeInt(this.value);
		}
	}
	
	@Override
	public int getAdditionalData() {
		return 0x10 * checkRange();
	}
	
	protected int checkRange() {
		if (this.value >= Byte.MIN_VALUE && this.value <= Byte.MAX_VALUE) {
			return 2;
		}
		if (this.value >= Short.MIN_VALUE && this.value <= Short.MAX_VALUE) {
			return 1;
		}
		return 0;
	}
}
