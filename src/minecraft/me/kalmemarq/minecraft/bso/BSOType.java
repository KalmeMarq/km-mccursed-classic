package me.kalmemarq.minecraft.bso;

import java.io.DataInput;
import java.io.IOException;

public abstract class BSOType {
	private final int id;
	
	public BSOType(int id) {
		this.id = id;
	}
	
	public int getId() {
		return this.id;
	}
 	
	abstract public BSOTag read(DataInput input, int additionalData) throws IOException;
}
