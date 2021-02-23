package com.trace.trace.service;

import com.google.gson.Gson;
import com.trace.trace.dao.RedisDao;
import com.trace.trace.entity.Detail;
import com.trace.trace.mapper.DetailMapper;
import com.trace.trace.mapper.QueryMapper;
import com.trace.trace.util.createJson;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

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

    private final QueryMapper queryMapper;

    private final DetailMapper detailMapper;

    private final RedisDao redisDao;

    private final createJson json = new createJson();

    @Autowired
    public SearchProduct(QueryMapper queryMapper, DetailMapper detailMapper, RedisDao redisDao) {
        this.queryMapper = queryMapper;
        this.detailMapper = detailMapper;
        this.redisDao = redisDao;
    }

    public String searchProducts(String query, String page) {
        //分页检索，每次返回二十条商品
        log.info("Start searching keyword");
        //redis方法，传入一个(query,page)，返回list，其中list的首位是总页数
        List<String> keys = redisDao.getIDListOnPage(query, Integer.parseInt(page));
        log.info("redis skuId list: " + keys.toString());
        //获取总页数
        String pageCount = keys.get(0);
        keys.remove(0);
        log.info("redis page count: " + pageCount);
        //mysql方法
        String jsonInfo = "";
        try {
            jsonInfo = json.toJson(pageCount, queryMapper.selectQueryBySkuIds(keys));
        } catch (Exception e) {
            log.error("" + e);
        }
        return jsonInfo;
    }

    public String searchDetails(String skuId) {
        //获取某商品的详情信息
        log.info("Start searching detail");
        //调用mysql方法，获取相关id的详情
        Detail detail = detailMapper.selectDetailBySkuId(skuId);
        return new Gson().toJson(detail);
    }

}
