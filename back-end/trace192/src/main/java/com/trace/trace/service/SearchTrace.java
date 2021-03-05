package com.trace.trace.service;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import com.google.protobuf.ByteString;
import com.trace.trace.dao.FabricDao;
import com.trace.trace.dao.ProductRedisDao;
import com.trace.trace.dao.TraceRedisDao;
import com.trace.trace.entity.CompanyInfo;
import com.trace.trace.mapper.CompetMapper;
import com.trace.trace.pojo.TraceManagerInfo;
import com.trace.trace.util.CreateJson;
import com.trace.trace.util.FileUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;

/**
 * @author Zenglr
 * @program: FoXiShengCun
 * @packagename: com.trace.trace.service
 * @Description 溯源模块
 * @create 2021-02-07-7:57 下午
 */
@Service
@Slf4j
public class SearchTrace {

    private final Gson gson = new GsonBuilder().disableHtmlEscaping().create();  //防止出现字符转换

    private final FabricDao fabricDao;

    private final FileUtil fileUtil;

    private final ProductRedisDao productRedisDao;

    private final CompetMapper competMapper;

    private final TraceRedisDao traceRedisDao;

    private final CreateJson json = new CreateJson();

    @Autowired
    public SearchTrace(FabricDao fabricDao, FileUtil fileUtil, ProductRedisDao productRedisDao,
                       CompetMapper competMapper, TraceRedisDao traceRedisDao) {
        this.fabricDao = fabricDao;
        this.fileUtil = fileUtil;
        this.productRedisDao = productRedisDao;
        this.competMapper = competMapper;
        this.traceRedisDao = traceRedisDao;
    }

    /**
     * 获取到第一次流程输入时所需的公司基本信息以及商品列表
     *
     * @param regis_id
     * @return
     */
    public String getFirstProcessInfo(String regis_id) {
        List<String> productNameList = productRedisDao.getAllProductName(regis_id);
        CompanyInfo companyInfo = competMapper.selectCompanyBasicInfo(regis_id);
        StringBuilder sb = new StringBuilder();
        sb.append(gson.toJson(companyInfo));
        sb.deleteCharAt(sb.length() - 1);
        sb.append(",\"productNameList\":[");
        for (String productName : productNameList) {
            sb.append("{\"name\":\"");
            sb.append(productName);
            sb.append("\"},");
        }
        sb.deleteCharAt(sb.length() - 1);
        sb.append("]}");
        return sb.toString();
    }

    /**
     * 根据公司名、商品名、页码获取到管理员界面的溯源信息列表
     *
     * @param product_name 商品名称
     * @param company_name 公司名称
     * @param page         页码
     * @return 溯源信息列表
     * @see TraceManagerInfo
     */
    public String searchAllTraceByName(String product_name, String company_name, String page) {
        String code = traceRedisDao.getProductCode(product_name, company_name);
        List<String> traceCodeList = traceRedisDao.getAllTraceCode(code, Integer.parseInt(page));
        String pageCount = "" + traceRedisDao.getPageNumber(code);
        List<TraceManagerInfo> allTraceInfo = fabricDao.getManagerInfoList(traceCodeList);
        String foodName = product_name.split("-")[0];
        String specification = product_name.split("-")[1];
        String category = product_name.split("-")[2];
        for (TraceManagerInfo traceInfo : allTraceInfo) {
            traceInfo.setFoodName(foodName);
            traceInfo.setSpecification(specification);
            traceInfo.setCategory(category);
        }
        return json.toJson(pageCount, allTraceInfo);
    }

    /**
     * 根据图片名称返回图片字节码
     *
     * @param picName 图片名称
     * @return ByteString，可转化为byte[]
     */
    public ByteString searchPicture(String picName) {
        return ByteString.copyFrom(fileUtil.getBytesFromPicture(picName));
    }

    /**
     * 根据视频文件名称返回视频字节码
     *
     * @param videoName 视频文件名称
     * @return ByteString，可转化为byte[]
     */
    public ByteString searchVideo(String videoName) {
        return ByteString.copyFrom(fileUtil.getBytesFromVideo(videoName));
    }

    /**
     * 根据溯源id查询溯源信息
     *
     * @param originId 唯一溯源id
     * @return 溯源信息json字符串，见示例数据。
     * @see com.trace.trace.pojo.TraceInfo
     */
    public String getOrigin(String originId) {
        return fabricDao.getInfoByOriginId(originId);
    }

    /**
     * 接收带有产品基本信息的json字符串，提取出结构化的信息之后添加到区块链中。
     *
     * @param processInfo 带有产品基本信息的json字符串
     * @return 带有二维码链接(qrCode)的json字符串
     */
    public String addFirstProcess(String processInfo) {
        HashMap<String, String> map = gson.fromJson(processInfo, new TypeToken<HashMap<String, String>>() {
        }.getType());
        return fabricDao.addFirstProcess(map.get("foodType"), map.get("com"), Integer.parseInt(map.get("processCount")),
                map.get("name"), map.get("master"), map.get("location"));
    }

    public String addProcess(String processInfo) {
        long start = System.currentTimeMillis();
        HashMap<String, String> map = gson.fromJson(processInfo, new TypeToken<HashMap<String, String>>() {
        }.getType());
        String jsonInfo = fabricDao.addProcess(map.get("id"), map.get("name"), map.get("master"), map.get("location"));
        log.info("192 add process takes {} ms", System.currentTimeMillis() - start);
        return jsonInfo;
    }

    public String addProcedure(String procedureInfo) {
        long start = System.currentTimeMillis();
        HashMap<String, String> map = gson.fromJson(procedureInfo, new TypeToken<HashMap<String, String>>() {
        }.getType());
        String jsonInfo = fabricDao.addProcedure(map.get("id"), map.get("name"), map.get("master"));
        log.info("192 add procedure takes {} ms", System.currentTimeMillis() - start);
        return jsonInfo;
    }

}
