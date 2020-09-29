package br.unb.cic.comnet.distran.util;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

public class SerializeHelper {
	
	public static String serialize(Object object) throws IOException {
		ByteArrayOutputStream out = new ByteArrayOutputStream();
		ObjectOutputStream oos = new ObjectOutputStream(out);
		oos.writeObject(object);
		return out.toString();
	}
	
	public static <T> T unserialize(String obj) throws IOException, ClassNotFoundException {
		ByteArrayInputStream bis = new ByteArrayInputStream(obj.getBytes());
		ObjectInputStream ois = new ObjectInputStream(bis);
		return (T) ois.readObject();
	}

}
