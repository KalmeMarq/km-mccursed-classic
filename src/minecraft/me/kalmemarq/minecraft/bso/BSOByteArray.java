package me.kalmemarq.minecraft.bso;

import java.io.DataOutput;
import java.io.IOException;

public class BSOByteArray extends BSOTag {
	private byte[] array;
	
	public BSOByteArray(byte[] array) {
		this.array = array;
	}
	
	@Override
	public BSOType getType() {
		return BSOTypes.BYTE_ARRAY;
	}
	
	public byte[] getByteArray() {
		return this.array;
	}

	@Override
	public void write(DataOutput output) throws IOException {
		int range = checkRange();
		if (range == 2) {
			output.writeByte((byte)this.array.length);
		} else if (range == 1) {
			output.writeShort((short)this.array.length);
		} else {
			output.writeInt(this.array.length);
		}
		
		output.write(this.array);
	}
	
	@Override
	public int getAdditionalData() {
		return 0x10 * checkRange();
	}
	
	protected int checkRange() {
		if (array.length <= 0xFF) {
			return 2;
		}
		if (array.length <= 0xFFFF) {
			return 1;
		}
		return 0;
	}
}
