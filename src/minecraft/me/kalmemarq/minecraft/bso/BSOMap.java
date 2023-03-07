package me.kalmemarq.minecraft.bso;

import java.io.DataOutput;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class BSOMap extends BSOTag {
	private Map<String, BSOTag> entries;
	
	public BSOMap() {
		this.entries = new HashMap<String, BSOTag>();
	}
	
	public BSOMap(Map<String, BSOTag> map) {
		this.entries = map;
	}
	
	public void put(String key, BSOTag tag) {
		this.entries.put(key, tag);
	}
	
	public void putInt(String key, int value) {
		this.entries.put(key, new BSOInt(value));
	}
	
	public void putByteArray(String key, byte[] value) {
		this.entries.put(key, new BSOByteArray(value));
	}
	
	public int getInt(String key) {
		if (this.entries.containsKey(key) && this.entries.get(key).getType() == BSOTypes.INT) {
			return ((BSOInt)this.entries.get(key)).getValue();
		}
		return 0; 
	}
	
	public byte[] getByteArray(String key) {
		if (this.entries.containsKey(key) && this.entries.get(key).getType() == BSOTypes.BYTE_ARRAY) {
			return ((BSOByteArray)this.entries.get(key)).getByteArray();
		}
		return new byte[] {};
	}
	
	@Override
	public BSOType getType() {
		return BSOTypes.MAP;
	}

	@Override
	public void write(DataOutput output) throws IOException {
		for (Map.Entry<String, BSOTag> entry : this.entries.entrySet()) {
			output.writeByte(entry.getValue().getADID());
			output.writeUTF(entry.getKey());
			entry.getValue().write(output);
		}
		
		output.writeByte(0);
	}
}
