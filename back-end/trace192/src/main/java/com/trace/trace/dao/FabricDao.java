package com.trace.trace.dao;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import com.trace.trace.config.RedisIndexConfig;
import com.trace.trace.pojo.FabMediaInfo;
import com.trace.trace.pojo.ProcedureInfo;
import com.trace.trace.pojo.ProcessInfo;
import com.trace.trace.pojo.TraceInfo;
import com.trace.trace.pojo.TraceManagerInfo;
import com.trace.trace.util.CreateTraceCode;
import com.trace.trace.util.FabricUtil;
import com.trace.trace.util.JedisUtil;
import com.trace.trace.util.QRCodeUtil;
import lombok.extern.slf4j.Slf4j;
import org.hyperledger.fabric.gateway.Contract;
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
@Slf4j
@Component
public class FabricDao {
    final FabricUtil fabricUtil;

    final RedisIndexConfig redisIndexConfig;

    final JedisUtil jedisUtil;

    final Network network;

    final QRCodeUtil qrCodeUtil;

    final CreateTraceCode createTraceCode;

    final Gson gson = new GsonBuilder().disableHtmlEscaping().create();

    Map<String, Integer> dbMap;
    Set<String> databaseSet;

    @Value("${media.video.path}")
    String path;

    @Autowired
    public FabricDao(RedisIndexConfig redisIndexConfig, FabricUtil fabricUtil, JedisUtil jedisUtil, Network network,
                     QRCodeUtil qrCodeUtil, CreateTraceCode createTraceCode) {
        this.redisIndexConfig = redisIndexConfig;
        this.fabricUtil = fabricUtil;
        this.jedisUtil = jedisUtil;
        this.network = network;
        this.qrCodeUtil = qrCodeUtil;
        this.createTraceCode = createTraceCode;
        dbMap = redisIndexConfig.getMap();
        databaseSet = dbMap.keySet();
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
     *
     * @param originId 唯一溯源码
     * @return 溯源信息json。考虑是否通过公司名称在redis中查询公司其它信息并加入traceInfo对象
     */
    public String getInfoByOriginId(String originId) {
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
            String latestPic;
            for (ProcessInfo process : traceInfo.getProcess()) {
                processName = process.getName();
                for (ProcedureInfo procedure : process.getProcedure()) {
                    sb = new StringBuilder(processName);
                    sb.append("_").append(procedure.getName());
                    if (databaseSet.contains(sb.toString())) {
                        jedis.select(dbMap.get(sb.toString()));
                        latestPic = jedis.lindex(id, 0);
                        picture = latestPic == null ? pictureNoFound : latestPic;
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

    /**
     * 接收产品基本信息+第一个process的所有信息，利用产品基本信息生成id并使用id和基本信息在区块链中创建产品
     * 而后调用 addProcess 方法存储第一个process的信息。
     * @see #addProcess(String id, String name, String master, String location)
     * @param foodType 油辣椒酱-275g-辣椒酱(foodName-specification-category)
     * @param com 公司名称
     * @param processCount 4
     * @param name 菜籽油生产地
     * @param master 负责人名称
     * @param location 该process所在城市
     * @return traceInfo，带有qrCode字段。
     */
    public String addFirstProcess(String foodType, String com, Integer processCount, String name, String master,
                                  String location) {
        //生成溯源码
        String id = createTraceCode.getTraceCode(foodType, com);

        //将生成的溯源码id存入redis4号库，从左侧存入，因此较小索引为较新的id
        Jedis jedis = jedisUtil.getClient();
        jedis.select(4);
        jedis.lpush(id.substring(0, 13), id);
        jedis.close();

        String[] food = foodType.split("-");
        //依据生成的id和拆分过的食品基本信息调用区块链的createFood方法生成新的食品记录
        try {
            Contract contract = network.getContract(FabricInfo.TRACE_CC.value);
            contract.submitTransaction("createFood", id, food[0], food[1], food[2], processCount + "");
        } catch (Exception e) {
            log.error(e.toString());
        }
        //调用addProcess方法将首个process加入区块链
        String infoStr = addProcess(id, name, master, location);
        TraceInfo info = gson.fromJson(infoStr, TraceInfo.class);
        //依据指定名称生成二维码并补全链接
        String qrName = id + "inner";
        //此处需要修改url为前端对应url
        qrCodeUtil.addCode("http://121.46.19.26:8511/addProcess", qrName);
        info.setQrCode(FabricInfo.PICTURE_PREFIX.value + qrName + ".png");
        return gson.toJson(info);
    }

    /**
     * 接收产品某个process的信息，将其存入fabric
     * @param id
     * @param name
     * @param master
     * @param location
     * @return
     */
    public String addProcess(String id, String name, String master, String location) {
        String infoStr = "";
        try {
            Contract contract = network.getContract(FabricInfo.TRACE_CC.value);
            byte[] bytes = contract.submitTransaction("addProcess", id, name, master,
                    System.currentTimeMillis() + "", location);
            infoStr = new String(bytes);
            TraceInfo info = gson.fromJson(infoStr, TraceInfo.class);
            if (info.getProcessCount() == info.getProcess().size()) {
                qrCodeUtil.addCode("", id);
                info.setQrCode(FabricInfo.PICTURE_PREFIX.value + id + ".png");
                infoStr = gson.toJson(info);
            }
        } catch (Exception e) {
            log.error(e.toString());
        }
        return infoStr;
    }

    /**
     *
     * @param id
     * @param name
     * @param master
     * @return
     */
    public String addProcedure(String id, String name, String master) {
        String info = "";
        try {
            Contract contract = network.getContract(FabricInfo.TRACE_CC.value);
            info = new String(contract.submitTransaction("addProcedure", id, name, master,
                    System.currentTimeMillis() + ""));
        } catch (Exception e) {
            log.error(e.toString());
        }

        Jedis jedis = jedisUtil.getClient();
        int db = dbMap.get("工厂_" + name);
        jedis.select(db);
        jedis.lpush("latestCode", id);
        jedis.close();
        return info;
    }

    /**
     * 依据传入的id获取管理员界面产品列表，即TraceManagerList的列表
     * @param ids id列表
     * @return List<TraceManagerInfo>
     */
    public List<TraceManagerInfo> getManagerInfoList(String... ids) {
        StringBuilder idList = new StringBuilder("[");
        for (String id : ids) {
            idList.append(id).append(",");
        }
        int end = idList.lastIndexOf(",");
        if (end == -1) {
            idList.append("]");
        } else {
            idList.replace(end, end, "]");
        }
        String infoList = "";
        try {
            Contract contract = network.getContract(FabricInfo.TRACE_CC.value);
            infoList = new String(contract.evaluateTransaction("managerQueryInfos", idList.toString()));
        } catch (Exception e) {
            log.error(e.toString());
        }
        return gson.fromJson(infoList, new TypeToken<List<TraceManagerInfo>>() {}.getType());
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
}
