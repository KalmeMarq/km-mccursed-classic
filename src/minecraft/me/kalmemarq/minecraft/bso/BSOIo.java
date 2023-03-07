package me.kalmemarq.minecraft.bso;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.zip.GZIPInputStream;
import java.util.zip.GZIPOutputStream;

public class BSOIo {
	public static void write(File file, BSOTag tag) throws IOException {
		DataOutputStream outputStream = new DataOutputStream(new FileOutputStream(file));
		
		outputStream.writeInt(0x021815);
		outputStream.writeByte(tag.getADID());
		tag.write(outputStream);
		
		outputStream.close();
	}
	
	public static void writeCompressed(File file, BSOTag tag) throws IOException {
		DataOutputStream outputStream = new DataOutputStream(new GZIPOutputStream(new FileOutputStream(file)));
		
		outputStream.writeInt(0x021815);
		outputStream.writeByte(tag.getADID());
		tag.write(outputStream);
		
		outputStream.close();
	}
	
	public static BSOTag read(File file) throws IOException {
		DataInputStream inputStream = new DataInputStream(new FileInputStream(file));
		
		int sig = inputStream.readInt();
		
		if (sig != 0x021815) {
			inputStream.close();
			throw new RuntimeException("File is not BSO");
		}
		
		int b = inputStream.readByte();
		BSOTag tag = BSOTypes.byId(b & 0x0F).read(inputStream, b & 0xF0);
		inputStream.close();
		return tag;
	}
	
	public static BSOTag readCompressed(File file) throws IOException {
		DataInputStream inputStream = new DataInputStream(new GZIPInputStream(new FileInputStream(file)));
		
		int sig = inputStream.readInt();
		
		if (sig != 0x021815) {
			inputStream.close();
			throw new RuntimeException("File is not BSO");
		}
		
		int b = inputStream.readByte();
		BSOTag tag = BSOTypes.byId(b & 0x0F).read(inputStream, b & 0xF0);
		inputStream.close();
		return tag;
	}
}
