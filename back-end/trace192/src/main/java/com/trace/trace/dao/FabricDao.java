package com.trace.trace.dao;

import com.google.gson.Gson;
import com.trace.trace.pojo.FabMediaInfo;
import com.trace.trace.util.FabricUtil;
import org.hyperledger.fabric.gateway.Contract;
import org.hyperledger.fabric.gateway.Gateway;
import org.hyperledger.fabric.gateway.Network;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.util.DigestUtils;

import java.io.File;
import java.io.FileInputStream;

/**
 * @author jbk-xiao
 * @program trace
 * @packagename com.trace.trace.dao
 * @Description
 * @create 2021-02-01-10:53
 */
@Component
public class FabricDao {

    @Autowired
    FabricUtil fabricUtil;

    @Value("${media.video.path}")
    String path;

    private enum FabricInfo {
        //
        CHANNEL_NAME("mychannel"),
        MEDIA_CC("fabmedia"),
        TRACE_CC("fabtrace"),
        INDUSTRY_CC("fabindustry");

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
        Gateway gateway = fabricUtil.getGateway();
        Network network = gateway.getNetwork(FabricInfo.CHANNEL_NAME.value);
        Contract contract = network.getContract(FabricInfo.MEDIA_CC.value);

        String type = "jpg".equals(filetype) ? 1 + "" : 2 + "";
        String checkTime = System.currentTimeMillis() + "";

        try {
            contract.submitTransaction("addMedia", type, filename, md5code, checkTime);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            gateway.close();
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
            Gateway gateway = fabricUtil.getGateway();
            Network network = gateway.getNetwork(FabricInfo.CHANNEL_NAME.value);
            Contract contract = network.getContract(FabricInfo.MEDIA_CC.value);

            String result = new String(contract.evaluateTransaction("queryMedia", filename));

            FabMediaInfo mediaInfo = gson.fromJson(result, FabMediaInfo.class);
            gateway.close();
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
//        String result = "{";
        StringBuffer sb = new StringBuffer("{");
//        try (Gateway gateway = fabricUtil.getGateway()) {
        try {
            Network network = fabricUtil.getNetwork();
//            Network network = gateway.getNetwork(FabricInfo.CHANNEL_NAME.value);
            Contract contract = network.getContract(FabricInfo.TRACE_CC.value);
            String mainProcess = new String(contract.evaluateTransaction("queryInfoByID", originId));
//            result = result + "\"main_process\":\"" + mainProcess + "\"";
            sb.append("\"main_process\":\"");
            sb.append(mainProcess);
            sb.append('"');
            contract = network.getContract(FabricInfo.INDUSTRY_CC.value);
            String industryProcess = new String(contract.evaluateTransaction("queryInfoByID", originId));
//            result = result + ",\"industry_process\":\"" + industryProcess + "\"";
            sb.append(",\"industry_process\":\"");
            sb.append(industryProcess);
            sb.append('"');
        } catch (Exception e) {
            e.printStackTrace();
        }
//        result = result + "}";
        sb.append('}');
//        return result;
        return sb.toString();
    }
}
