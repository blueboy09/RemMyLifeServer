package com.remmylife.service;

import java.io.*;

public class Utils {
	public static Object convertToObject(byte[] bytes) {                                                       
		ByteArrayInputStream byteArrayInputStream = new ByteArrayInputStream(bytes);
		ObjectInputStream objectInputStream;
		Object object = null;
		try {
			objectInputStream = new ObjectInputStream(byteArrayInputStream); 
			object = objectInputStream.readObject();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return object;
	}
	
	public static byte[] convertToByteArray(Object object) {
		ObjectOutputStream objectOutputStream = null;
		ByteArrayOutputStream byteStream = new ByteArrayOutputStream(4096);
		try {
			objectOutputStream = new ObjectOutputStream(new BufferedOutputStream(byteStream));
			objectOutputStream.flush();
			objectOutputStream.writeObject(object);
			objectOutputStream.flush();
			objectOutputStream.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		byte[] bytes = byteStream.toByteArray();
		return bytes;
	}
}
