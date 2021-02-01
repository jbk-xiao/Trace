package com.trace.trace.util.media;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.util.Base64Utils;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;

/**
 * @author jbk-xiao
 * @program trace
 * @packagename com.trace.trace.util
 * @Description
 * @create 2021-01-31-15:47
 */
@Component
public class FileUtil {

    @Value("${media.video.path}")
    private String videopath;

    @Value("${media.picture.path}")
    private String picpath;

    /**
     * @param videoname local video's basename
     * @return encode video to byte array.
     * @see com.trace.trace.util.media.FileUtil#getBytesFromFile(String)
     */
    public byte[] getBytesFromVideo(String videoname) {
        return getBytesFromFile(videopath + File.separator + videoname);
    }

    /**
     * @param picname local picture's basename
     * @return encode picture to byte array.
     * @see com.trace.trace.util.media.FileUtil#getBytesFromFile(String)
     */
    public byte[] getBytesFromPicture(String picname) {
        return getBytesFromFile(picpath + File.separator + picname);
    }

    /**
     * @param picname local picture's basename.
     * @return encode picture to UTF-8 String.
     * @see com.trace.trace.util.media.FileUtil#getBytesFromPicture(String)
     * @see org.springframework.util.Base64Utils#encodeToString(byte[])
     * @deprecated
     */
    public String getBase64FromPicture(String picname) {
        return Base64Utils.encodeToString(getBytesFromPicture(picname));
    }

    /**
     * @param filename local file's basename
     * @return encode file to byte array.
     */
    public byte[] getBytesFromFile(String filename) {
        File file = new File(filename);
        byte[] bytes = new byte[0];
        try {
            InputStream is = new FileInputStream(file);
            long length = file.length();
            if (length > Integer.MAX_VALUE) {
                return null;
            }
            bytes = new byte[(int) length];
            int offset = 0;
            int numRead;
            while ((offset < bytes.length) && ((numRead = is.read(bytes, offset, bytes.length - offset)) >= 0)) {
                offset += numRead;
            }
            if (offset < bytes.length) {
                throw new IOException("Could not completely read file " + file.getName());
            }
            is.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return bytes;
    }
}
