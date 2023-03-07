package me.kalmemarq.minecraft.bso;

import java.io.DataInput;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class BSOTypes {
	public static final BSOType BYTE = new BSOType(1) {
		@Override
		public BSOTag read(DataInput input, int additionalData) throws IOException {
			return new BSOByte(input.readByte());
		}
	};
	public static final BSOType SHORT = new BSOType(2) {
		@Override
		public BSOTag read(DataInput input, int additionalData) throws IOException {
			return new BSOShort(additionalData == 0x10 ? input.readByte() : input.readShort());
		}
	};
	public static final BSOType INT = new BSOType(3) {
		@Override
		public BSOTag read(DataInput input, int additionalData) throws IOException {
			return new BSOInt(additionalData == 0x20 ? input.readByte() : additionalData == 0x10 ? input.readShort() : input.readInt());
		}
	};
	public static final BSOType MAP = new BSOType(8) {
		@Override
		public BSOTag read(DataInput input, int additionalData) throws IOException {
			Map<String, BSOTag> map = new HashMap<String, BSOTag>();
			
			byte b;
			while ((b = input.readByte()) != 0) {
				String key = input.readUTF();
				map.put(key, byId(b & 0x0F).read(input, b & 0xF0));
			}
			
			return new BSOMap(map);
		}
	};
	public static final BSOType BYTE_ARRAY = new BSOType(10) {
		@Override
		public BSOTag read(DataInput input, int additionalData) throws IOException {
			int len = 0;
			if ((additionalData & 0x30) == 0x20) {
				len = input.readUnsignedByte();
			} else if ((additionalData & 0x30) == 0x10) {
				len = input.readUnsignedShort();
			} else {
				len = input.readInt();
			}
			
			byte[] arr = new byte[len];
			for (int i = 0; i < len; i++) {
				arr[i] = input.readByte();
			}
			
			return new BSOByteArray(arr);
		}
	};
	
	protected static BSOType byId(int id) {
		if (id == 1) {
			return BSOTypes.BYTE;
		}
		if (id == 2) {
			return BSOTypes.SHORT;
		}
		if (id == 3) {
			return BSOTypes.INT;
		}
		if (id == 8) {
			return BSOTypes.MAP;
		}
		if (id == 10) {
			return BSOTypes.BYTE_ARRAY;
		}
		
		throw new RuntimeException("Unknown bso id " + id);
	}
}
