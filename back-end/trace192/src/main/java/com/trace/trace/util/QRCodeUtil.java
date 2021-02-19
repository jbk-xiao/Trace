package com.trace.trace.util;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.EncodeHintType;
import com.google.zxing.MultiFormatWriter;
import com.google.zxing.client.j2se.MatrixToImageWriter;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.qrcode.decoder.ErrorCorrectionLevel;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.util.HashMap;

/**
 * @author Zenglr
 * @program: FoXiShengCun
 * @packagename: com.trace.trace.util
 * @Description
 * @create 2021-02-17-5:20 下午
 */
@Component
public class QRCodeUtil {

    @Value("${media.picture.path}")
    private String picPath;

    /**
     * 生成带有溯源码的二维码图片
     * @param content
     * @param code
     * @return
     */
    public byte[] addCode(String content, String code) {
        try {
            BufferedImage image = getBasicQRCode(content);
            BufferedImage outImage = new BufferedImage(300, 325, BufferedImage.TYPE_4BYTE_ABGR);
            Graphics2D outg = outImage.createGraphics();
            //画二维码到新的面板
            assert image != null;
            outg.drawImage(image, 0, 0, image.getWidth(), image.getHeight(), null);
            //画文字到新的面板
            outg.setColor(Color.BLACK);
            //字体、字型、字号
            outg.setFont(new Font("微软雅黑", Font.BOLD, 20));
            int strWidth = outg.getFontMetrics().stringWidth(code);
            outg.drawString(code, 150 - strWidth / 2,
                    image.getHeight() + (outImage.getHeight() - image.getHeight()) / 2 + 10); //画文字
            outg.dispose();
            outImage.flush();
            image = outImage;
            image.flush();
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            baos.flush();
            ImageIO.write(image, "png", baos);
            byte[] bytes = baos.toByteArray();

            // 二维码生成的路径，但是实际项目中，我们是把这生成的二维码显示到界面上的，因此下面的这行代码可以注释掉
            ImageIO.write(image, "png", new File(picPath + File.separator + code + ".png"));
            baos.close();
            return bytes;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 生成二维码图片
     * @param content
     * @return
     */
    private BufferedImage getBasicQRCode(String content) {
        int width = 300;
        int height = 300;

        HashMap<EncodeHintType, Object> hits = new HashMap<>();
        hits.put(EncodeHintType.CHARACTER_SET, "utf-8");//编码
        //纠错等级，纠错等级越高存储信息越少
        hits.put(EncodeHintType.ERROR_CORRECTION, ErrorCorrectionLevel.M);
        //边距
        hits.put(EncodeHintType.MARGIN, 2);

        try {
            BitMatrix bitMatrix = new MultiFormatWriter().encode(content, BarcodeFormat.QR_CODE, width, height, hits);
            return MatrixToImageWriter.toBufferedImage(bitMatrix);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
}
