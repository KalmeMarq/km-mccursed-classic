package me.kalmemarq.minecraft.bso;

import java.io.DataOutput;
import java.io.IOException;

public class BSOShort extends BSOTag {
	private short value;
	
	public BSOShort(int value) {
		this.value = (short)value;
	}
	
	@Override
	public BSOType getType() {
		return BSOTypes.SHORT;
	}
	
	public short getValue() {
		return this.value;
	}

	@Override
	public void write(DataOutput output) throws IOException {
		if (checkRange() == 1) {
			output.writeByte((byte)this.value);
		} else {
			output.writeShort(this.value);
		}
	}
	
	@Override
	public int getAdditionalData() {
		return 0x10 * checkRange();
	}
	
	protected int checkRange() {
		if (this.value >= Byte.MIN_VALUE && this.value <= Byte.MAX_VALUE) {
			return 1;
		}
		return 0;
	}
}
