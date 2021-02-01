package com.trace.trace.util.media;

import com.trace.trace.util.JedisUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.util.DigestUtils;
import redis.clients.jedis.Jedis;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.rmi.UnexpectedException;

/**
 * @author jbk-xiao
 * @program trace
 * @packagename com.trace.trace.util.media
 * @Description
 * @create 2021-01-31-20:20
 */
@Slf4j
@Component
public class SaveUtil {

    @Value("${media.video.database}")
    private int videoBase;

    @Value("${media.picture.database}")
    private int picBase;

    @Value("${media.video.path}")
    private String videoPath;

    @Value("${media.picture.path}")
    private String picPath;

    @Autowired
    JedisUtil jedisUtil;

    /**
     * 保存文件路径
     *
     * @param fullname remote full basename
     * @throws UnexpectedException if the file is neither video nor pic, of if the name is already in redis.
     * @throws FileNotFoundException if the file can't be found in local path.
     */
    public void saveName(String fullname) throws UnexpectedException, FileNotFoundException {

//        String[] splitname = fullname.split("\\.");
//        String filename = splitname[0];
        String filetype = fullname.split("\\.")[1];
        int database;
        String savepath;
        if ("mp4".equalsIgnoreCase(filetype) || "mkv".equalsIgnoreCase(filetype)) {
            database = videoBase;
            savepath = videoPath + File.separator + fullname;
        } else if ("jpg".equalsIgnoreCase(filetype)) {
            database = picBase;
            savepath = picPath + File.separator + fullname;
        } else {
            throw new UnexpectedException("Unexpect filetype: " + filetype);
        }

        File file = new File(savepath);
        if ( !file.exists() ) {
            throw new FileNotFoundException("No such file found in: " + savepath);
        }
        Jedis jedis = jedisUtil.getClient();
        jedis.select(database);
        if (jedis.lrange("product", 0, -1).contains(fullname)) {
            throw new UnexpectedException('"' + fullname + "\" already exits in redis.");
        } else {
            jedis.rpush("product", fullname);
        }
        jedis.close();
        String md5code = null;
        try {
            md5code = DigestUtils.md5DigestAsHex(new FileInputStream(file));
        } catch (IOException e) {
            e.printStackTrace();
        }
        log.info("save \"" + savepath + "\" to redis, md5 is: " + md5code);
        /*
        缺区块链访问逻辑，即调用addMedia（方法）将文件类型（整型1为图片，2为视频）、文件名称（String）、md5code（String）、checkTime（当前时间的long）传入。
         */
    }
}
