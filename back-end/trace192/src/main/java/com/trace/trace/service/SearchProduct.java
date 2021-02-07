package com.trace.trace.service;

import com.google.gson.Gson;
import com.google.protobuf.ByteString;
import com.trace.trace.dao.FabricDao;
import com.trace.trace.dao.RedisDao;
import com.trace.trace.dao.ProcessEventDao;
import com.trace.trace.entity.AllCompetinfo;
import com.trace.trace.entity.CompanyInfo;
import com.trace.trace.entity.Compet_geo;
import com.trace.trace.entity.Detail;
import com.trace.trace.entity.JDdetail;
import com.trace.trace.grpc.QueryRequest;
import com.trace.trace.grpc.QueryResponse;
import com.trace.trace.grpc.SearchServiceGrpc;
import com.trace.trace.mapper.CompetMapper;
import com.trace.trace.mapper.DetailMapper;
import com.trace.trace.mapper.QueryMapper;
import com.trace.trace.util.media.FileUtil;
import com.trace.trace.util.createJson;
import io.grpc.stub.StreamObserver;
import lombok.extern.slf4j.Slf4j;
import org.lognet.springboot.grpc.GRpcService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author Zenglr
 * @program: trace
 * @packagename: com.trace.trace.service
 * @Description 电商模块
 * @create 2021-01-26-5:18 下午
 */
@Slf4j
@Component
public class SearchProduct{

    @Autowired
    private QueryMapper queryMapper;

    @Autowired
    private DetailMapper detailMapper;

    @Autowired
    private RedisDao redisDao;

    private final createJson json = new createJson();

    public String searchProducts(String query,String page) {
        //分页检索，每次返回二十条商品
        log.info("Start searching keyword");
        //redis方法，传入一个(query,page)，返回list
        List<String> keys = redisDao.getIDListOnPage(query, Integer.parseInt(page));
        log.info("redis weibo list: " + keys.toString());
        //redis方法，传入一个(query)，返回总页数
        String pageCount = "" + redisDao.getPageNumber(query);
        log.info("redis page count: " + pageCount);
        //mysql方法
        String jsonInfo = "";
        try {
            jsonInfo = json.toJson(pageCount, queryMapper.selectQueryBySkuIds(keys));
        } catch (Exception e) {
            e.printStackTrace();
        }
        return jsonInfo;
    }

    public String searchDetails(String skuId) {
        //获取某商品的详情信息
        log.info("Start searching detail");
        //调用mysql方法，获取相关id的详情
        Detail detail = detailMapper.selectDetailBySkuId(skuId);
        String jsonInfo = json.toJson((List) detail);
        return jsonInfo;
    }

}
