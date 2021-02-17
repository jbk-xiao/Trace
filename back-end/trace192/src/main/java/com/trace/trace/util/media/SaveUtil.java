package com.trace.trace.util.media;

import com.trace.trace.config.RedisIndexConfig;
import com.trace.trace.dao.FabricDao;
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

    @Value("${media.picture.path}")
    private String picPath;

    @Autowired
    JedisUtil jedisUtil;

    @Autowired
    FabricDao fabricDao;

    @Autowired
    RedisIndexConfig redisIndexConfig;

    /**
     * 保存文件路径
     *
     * @param fullname remote full basename
     * @throws UnexpectedException if the file is neither video nor pic, of if the name is already in redis.
     * @throws FileNotFoundException if the file can't be found in local path.
     */
    public void saveName(String fullname) throws UnexpectedException, FileNotFoundException {
        /*检查文件类型*/
        String filetype = fullname.split("\\.")[1];
        String savepath;
        if ("jpg".equalsIgnoreCase(filetype)) {
            savepath = picPath + File.separator + fullname;
        } else {
            throw new UnexpectedException("Unexpect filetype: " + filetype);
        }
        /*检查文件状态*/
        File file = new File(savepath);
        if ( !file.exists() ) {
            throw new FileNotFoundException("No such file found in: " + savepath);
        }
        String step = fullname.split("-")[0];
        int database = redisIndexConfig.getMap().get(step);
        Jedis jedis = jedisUtil.getClient();
        jedis.select(database);
        String traceCode = jedis.get("latestCode");
        if (jedis.lrange(traceCode, 0, -1).contains(fullname)) {
            throw new UnexpectedException('"' + fullname + "\" already exits in redis.");
        } else {
            jedis.rpush(traceCode, fullname);
        }
        jedis.close();
        /* 获取md5码 */
        String md5code = null;
        try {
            md5code = DigestUtils.md5DigestAsHex(new FileInputStream(file));
        } catch (IOException e) {
            e.printStackTrace();
        }
        log.info("save \"" + fullname + "\" to redis, md5 is: " + md5code);
        /* 调用区块链中存储文件信息的逻辑 */
        fabricDao.saveMedia(filetype, fullname, md5code);
        log.info("save '" + fullname + "' to fabric.");
    }
}
