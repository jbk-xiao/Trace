package com.trace.trace.util;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.EncodeHintType;
import com.google.zxing.MultiFormatWriter;
import com.google.zxing.client.j2se.MatrixToImageWriter;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.qrcode.decoder.ErrorCorrectionLevel;
import org.springframework.stereotype.Component;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
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

    public byte[] addCode(BufferedImage bim, String code){
        try{
            BufferedImage image = bim;
            BufferedImage outImage = new BufferedImage(300, 325, BufferedImage.TYPE_4BYTE_ABGR);
            Graphics2D outg = outImage.createGraphics();
            //画二维码到新的面板
            outg.drawImage(image, 0, 0, image.getWidth(), image.getHeight(), null);
            //画文字到新的面板
            outg.setColor(Color.BLACK);
            outg.setFont(new Font("微软雅黑",Font.BOLD,20)); //字体、字型、字号
            int strWidth = outg.getFontMetrics().stringWidth(code);
            outg.drawString(code, 150  - strWidth/2 , image.getHeight() + (outImage.getHeight() - image.getHeight())/2 + 10 ); //画文字
            outg.dispose();
            outImage.flush();
            image = outImage;
            image.flush();
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            baos.flush();
            ImageIO.write(image, "png", baos);
            byte[] bytes = baos.toByteArray();

            //二维码生成的路径，但是实际项目中，我们是把这生成的二维码显示到界面上的，因此下面的折行代码可以注释掉
//        ImageIO.write(image, "png", new File("src/main/resources/" + new Date().getTime() + "test.png"));
            baos.close();
            return bytes;
        }catch (Exception e){
            e.printStackTrace();
        }
        return null;
    }

    public BufferedImage getBasicQRCode(String content){
        int width=300;
        int height=300;

        HashMap hits = new HashMap();
        hits.put(EncodeHintType.CHARACTER_SET, "utf-8");//编码
        //纠错等级，纠错等级越高存储信息越少
        hits.put(EncodeHintType.ERROR_CORRECTION, ErrorCorrectionLevel.M);
        //边距
        hits.put(EncodeHintType.MARGIN, 2);

        try {
            BitMatrix bitMatrix=new MultiFormatWriter().encode(content, BarcodeFormat.QR_CODE, width, height,hits);
            BufferedImage bufferedImage = MatrixToImageWriter.toBufferedImage(bitMatrix);
            return bufferedImage;
        } catch (Exception e)
        {
            // TODO Auto-generated catch block
            e.printStackTrace();
            return null;
        }
    }
}
