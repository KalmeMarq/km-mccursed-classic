package me.kalmemarq.minecraft.bso;

import java.io.DataOutput;
import java.io.IOException;

public abstract class BSOTag {
	abstract public BSOType getType();
	
	public int getTypeId() {
		return this.getType().getId();
	}
	
	public byte getADID() {
		return (byte)(this.getTypeId() + this.getAdditionalData());
	}
	
	abstract public void write(DataOutput output) throws IOException;
	
	public int getAdditionalData() {
		return 0;
	}
}
