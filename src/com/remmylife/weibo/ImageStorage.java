package com.remmylife.weibo;

import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author William Cheung
 */
public class ImageStorage {
    
    public static byte[] getDataInBytes(ArrayList<BufferedImage> images) {
        ArrayList<StorageUnit> units = new ArrayList<StorageUnit>();
        int length = 0;
        for (BufferedImage image : images) {
            StorageUnit unit = new StorageUnit(image);
            units.add(unit);
            length += unit.size();
        }
        byte[] data = new byte[length];
        int offset = 0;
        for (StorageUnit unit : units) {
            byte[] bytes = unit.getDataInBytes();
            System.arraycopy(bytes, 0, data, offset, bytes.length);
            offset += bytes.length;
        }
        return data;
    }
    
    public static ArrayList<BufferedImage> getImages(byte[] data) {
        
        ArrayList<BufferedImage> images = new ArrayList<BufferedImage>();
        int offset = 0;
        while (true) {
            try {
                byte[] data1 = new byte[4];
                System.arraycopy(data, offset, data1, 0, 4);
                offset += 4;
                int length = Utils.bytes2int(data1);
                byte[] data2 = new byte[length];
                System.arraycopy(data, offset, data2, 0, length);
                images.add(Utils.convertBytes2Image(data2));
                offset += length;
                if (offset == data.length)
                    break;
            } catch (Exception ex) {
                Logger.getLogger(ImageStorage.class.getName()).log(Level.SEVERE, null, ex);
                return null;
            }
        }
        
        return images;
    }
}

class StorageUnit {
    private int length;
    private byte[] data1 = null;
    private byte[] data2 = null;
        
    public StorageUnit(BufferedImage image) {
            data2 = Utils.getImageBytes(image);
            this.length = data2.length;
            data1 = Utils.int2bytes(length);        
    }
        
    public byte[] getDataInBytes() {
            byte[] data = new byte[data1.length + data2.length];
            System.arraycopy(data1, 0, data, 0, data1.length);
            System.arraycopy(data2, 0, data, data1.length, data2.length);
            return data;
    }
    
    public int size() {
        return data1.length + data2.length;
    }
}
