package com.trace.trace.dao;

import com.google.gson.Gson;
import com.trace.trace.config.RedisIndexConfig;
import com.trace.trace.pojo.FabMediaInfo;
import com.trace.trace.pojo.ProcedureInfo;
import com.trace.trace.pojo.ProcessInfo;
import com.trace.trace.pojo.TraceInfo;
import com.trace.trace.util.FabricUtil;
import com.trace.trace.util.JedisUtil;
import org.hyperledger.fabric.gateway.Contract;
import org.hyperledger.fabric.gateway.Gateway;
import org.hyperledger.fabric.gateway.Network;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.util.DigestUtils;
import redis.clients.jedis.Jedis;

import java.io.File;
import java.io.FileInputStream;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * @author jbk-xiao
 * @program trace
 * @packagename com.trace.trace.dao
 * @Description
 * @create 2021-02-01-10:53
 */
@Component
public class FabricDao {
    final FabricUtil fabricUtil;

    final RedisIndexConfig redisIndexConfig;

    final JedisUtil jedisUtil;

    final Network network;

    Map<String, Integer> dbMap;
    Set<String> databaseSet;

    @Value("${media.video.path}")
    String path;

    @Autowired
    public FabricDao(RedisIndexConfig redisIndexConfig, FabricUtil fabricUtil, JedisUtil jedisUtil, Network network) {
        this.redisIndexConfig = redisIndexConfig;
        this.fabricUtil = fabricUtil;
        this.jedisUtil = jedisUtil;
        this.network = network;
        dbMap = redisIndexConfig.getMap();
        databaseSet = dbMap.keySet();
    }

    private enum FabricInfo {
        //
        MEDIA_CC("fabmedia"),
        TRACE_CC("fabtrace"),
        PICTURE_PREFIX("http://121.46.19.26:8511/getPicture/"),
        PICTURE_NO_FOUND("picture_no_found.jpg");

        final String value;

        FabricInfo(final String value) {
            this.value = value;
        }
    }

    /**
     * save a media record to fabric.
     *
     * @param filetype mp4, mkv, jpg
     * @param filename full basename with filetype.
     * @param md5code  md5code
     */
    public void saveMedia(String filetype, String filename, String md5code) {
        Contract contract = network.getContract(FabricInfo.MEDIA_CC.value);

        String type = "jpg".equals(filetype) ? 1 + "" : 2 + "";
        String checkTime = System.currentTimeMillis() + "";

        try {
            contract.submitTransaction("addMedia", type, filename, md5code, checkTime);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Check if the file had been modified since it stored.
     *
     * @param filename full basename.
     * @return boolean
     */
    public boolean isModified(String filename) {
        Gson gson = new Gson();
        try {
            String currentCode = DigestUtils.md5DigestAsHex(new FileInputStream(path + File.separator + filename));
            Contract contract = network.getContract(FabricInfo.MEDIA_CC.value);

            String result = new String(contract.evaluateTransaction("queryMedia", filename));

            FabMediaInfo mediaInfo = gson.fromJson(result, FabMediaInfo.class);
            return currentCode.equals(mediaInfo.getMd5code());

        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    /**
     * 查询fabric，返回溯源信息，包括大阶段和在工厂内的阶段。
     * @param originId 唯一溯源码
     * @return 溯源信息json
     */
    public String getInfoByOriginId(String originId) {
        Gson gson = new Gson();
        Jedis jedis = jedisUtil.getClient();
        String traceInfoStr = "{}";
        String processName;
        String picturePrefix = FabricInfo.PICTURE_PREFIX.value;
        String pictureNoFound = FabricInfo.PICTURE_NO_FOUND.value;
        StringBuilder sb;
        try {
            Contract contract = network.getContract(FabricInfo.TRACE_CC.value);
            traceInfoStr = new String(contract.evaluateTransaction("queryInfoByID", originId));
            TraceInfo traceInfo = gson.fromJson(traceInfoStr, TraceInfo.class);
            String id = traceInfo.getId();
            String picture = pictureNoFound;
            List<String> list;
            for (ProcessInfo process: traceInfo.getProcess()) {
                processName = process.getName();
                for (ProcedureInfo procedure: process.getProcedure()) {
                    sb = new StringBuilder(processName);
                    sb.append("_").append(procedure.getName());
                    if (databaseSet.contains(sb.toString())) {
                        jedis.select(dbMap.get(sb.toString()));
                        list = jedis.lrange(id, 0, 0);
                        picture = list.isEmpty() ? pictureNoFound : list.get(0);
                        procedure.setPicture(picturePrefix + picture);
                    }
                }
                process.setPicture(picturePrefix + picture);
            }
            traceInfoStr = gson.toJson(traceInfo);
        } catch (Exception e) {
            e.printStackTrace();
        }
        jedis.close();
        return "[" + traceInfoStr + "]";
    }
}
