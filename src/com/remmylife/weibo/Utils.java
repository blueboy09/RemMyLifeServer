/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.remmylife.weibo;

import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import javax.imageio.ImageIO;

/**
 *
 * @author William Cheung
 */
public class Utils {
    /*
     * Covert String in format "yyyy/MM/dd" to Date
     */
    public static Date str2date(String str) {
        int year, month, day;
        try {
            year = Integer.parseInt("" + 
                    str.charAt(0) + str.charAt(1) + str.charAt(2) + str.charAt(3)
                    );
            month = Integer.parseInt("" + str.charAt(5) + str.charAt(6));
            day = Integer.parseInt("" + str.charAt(8) + str.charAt(9));
        } catch (Exception e) {
            return null;
        }
        Calendar calendar = Calendar.getInstance();
        calendar.set(year, month, day);
        return calendar.getTime();
    }
    
    public static String date2str(Date date) {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd");
        return dateFormat.format(date);
    }
    
    public static void writeBytesToFile(byte[] data, String pathname) throws IOException {
    	File file = new File(pathname);
    	if (!file.exists()) {
            file.createNewFile();
    	}
    	if (data != null) {
            FileOutputStream fileOutputStream = new FileOutputStream(file);
            fileOutputStream.write(data);
    	} else {
            file.delete();
            file.createNewFile();
	}
    }
    
    public static byte[] readBytesFromFile(String pathname) throws IOException {
        File file = new File(pathname);
        if (!file.exists())
            return null;
        
    	byte[] buffer = new byte[1000000]; // Max size : 1MB 
    	FileInputStream fileInputStream = new FileInputStream(file);
    	int size = fileInputStream.read(buffer);
    	byte[] data = null;
    	if (size > 0) {
            data = new byte[size];
            System.arraycopy(buffer, 0, data, 0, size);
    	}
        return data;
    }
    
    public static void deleteFile(String pathname) {
    	File file = new File(pathname);
    	if (file.exists()) {
    		file.delete();
    	}
    }
    
    public static byte[] getImageBytes(BufferedImage bufferedImage) {
        ByteArrayOutputStream bos = new ByteArrayOutputStream();
        try {
        	ImageIO.write(bufferedImage, "jpg", bos);
        } catch(IOException ioe) {
            //ioe.printStackTrace();
        	return null;
        }
        byte[] imagesInBytes = bos.toByteArray();
        return imagesInBytes;
    }
    
    public static BufferedImage convertBytes2Image(byte[] bytes) {
        ByteArrayInputStream in = new ByteArrayInputStream(bytes);
        BufferedImage image = null;
        try {
            image = ImageIO.read(in);
        } catch(IOException ioe) {
            //ioe.printStackTrace();
        }
        return image;
    }
    
    public static int getImageSize(BufferedImage image) {
        byte[] bytes = getImageBytes(image);
        int size = 0;
        if (image != null && bytes != null)
            size = bytes.length;
        return size;
    }
    
    public static byte[] int2bytes(int i) {
    	byte[] bytes = new byte[4];
    	bytes[0] = (byte)(i & 0xff);
    	i >>= 8;
    	bytes[1] = (byte)(i & 0xff);
    	i >>= 8;
    	bytes[2] = (byte)(i & 0xff);
    	i >>= 8;
    	bytes[3] = (byte)(i & 0xff);
    	return bytes;
    }
    
    public static int bytes2int(byte[] bytes) throws Exception {
    	if (bytes.length != 4) {
    		throw new Exception("The number of bytes in type 'int' must be 4");
    	}
    	int i = (int) bytes[0];
        i &= 0xff;
    	i |= ((int)bytes[1]) << 8;
        i &= 0xffff;
    	i |= ((int)bytes[2]) << 16;
        i &= 0xffffff;
    	i |= ((int)bytes[3]) << 24;
    	return i;
    }
}
