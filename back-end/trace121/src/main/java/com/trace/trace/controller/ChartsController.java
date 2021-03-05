package com.trace.trace.controller;

import com.trace.trace.grpc.ChartsRequestByString;
import com.trace.trace.grpc.QueryResponse;
import com.trace.trace.grpc.SearchChartsServiceGrpc;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

/**
 * 用于竞品图表所需数据的返回。
 *
 * @author jbk-xiao
 * @program trace121
 * @packagename com.trace.trace.controller
 * @Description
 * @create 2021-02-23-16:47
 */
@Slf4j
@CrossOrigin("*")
@RestController
public class ChartsController {
    final SearchChartsServiceGrpc.SearchChartsServiceBlockingStub searchChartsServiceBlockingStub;

    @Autowired
    public ChartsController(SearchChartsServiceGrpc.SearchChartsServiceBlockingStub searchChartsServiceBlockingStub) {
        this.searchChartsServiceBlockingStub = searchChartsServiceBlockingStub;
    }

    /**
     * 取出前端需求的预测曲线的json内容。
     *
     * @param companyName 传入主公司名称。
     * @return 返回“预测曲线”所需内容。
     */
    @GetMapping(value = "/getPredict/{company_name}")
    public String getPredict(@PathVariable("company_name") String companyName) {
        long start = System.currentTimeMillis();
        log.info("request getPredict: {}", companyName);
        QueryResponse response = searchChartsServiceBlockingStub
                .getPredict(ChartsRequestByString.newBuilder().setChartsStrRequest(companyName)
                        .build());
        log.info("use {} ms", System.currentTimeMillis() - start);
        return response.getResponse();
    }

    /**
     * 取出企业的新闻标题和链接的url并返回给前端
     *
     * @param companyName 企业名称
     * @return json
     */
    @GetMapping(value = "/getNews/{company_name}")
    public String getNews(@PathVariable("company_name") String companyName) {
        long start = System.currentTimeMillis();
        log.info("request getNews: {}", companyName);
        QueryResponse response = searchChartsServiceBlockingStub
                .getNews(ChartsRequestByString.newBuilder().setChartsStrRequest(companyName)
                        .build());
        log.info("use {} ms", System.currentTimeMillis() - start);
        return response.getResponse();
    }

    /**
     * 利用百度指数中的关键词获得最新一段时间内的用户年龄分布指数和性别分布指数。
     *
     * @param keyword 百度指数中的关键词。
     * @return 关键词对应用户的年龄分布指数和性别分布指数。
     */
    @GetMapping(value = "/getAgeDistribution/{keyword}")
    public String getAgeOrSexDistribution(@PathVariable("keyword") String keyword) {
        long start = System.currentTimeMillis();
        log.info("request getAgeDistribution: {}", keyword);
        QueryResponse response = searchChartsServiceBlockingStub
                .getAgeDistribution(ChartsRequestByString.newBuilder().setChartsStrRequest(keyword)
                        .build());
        log.info("use {} ms", System.currentTimeMillis() - start);
        return response.getResponse();
    }

    /**
     * 利用百度指数中的关键词获得最新一段时间内的用户省份分布指数
     *
     * @param keyword 百度指数中的关键词。
     * @return 关键词对应用户的省份分布指数。
     */
    @GetMapping(value = "/getProvinceIndex/{keyword}")
    public String getProvinceIndex(@PathVariable("keyword") String keyword) {
        long start = System.currentTimeMillis();
        log.info("request getProvinceIndex: {}", keyword);
        QueryResponse response = searchChartsServiceBlockingStub
                .getProvinceIndex(ChartsRequestByString.newBuilder().setChartsStrRequest(keyword)
                        .build());
        log.info("use {} ms", System.currentTimeMillis() - start);
        return response.getResponse();
    }

    /**
     * 获取相关关键词关联检索气泡图所需数据
     * @param keyword 百度指数中的关键词
     * @return  关键词关联检索对应的json字符串
     */
    @GetMapping(value = "/getRelateSearch/{keyword}")
    public String getRelateSearch(@PathVariable("keyword") String keyword) {
        long start = System.currentTimeMillis();
        log.info("request getRelateSearch: {}", keyword);
        QueryResponse response = searchChartsServiceBlockingStub
                .getRelateSearch(ChartsRequestByString.newBuilder().setChartsStrRequest(keyword)
                        .build());
        log.info("use {} ms", System.currentTimeMillis() - start);
        return response.getResponse();
    }

    /**
     * 根据主公司sku_id获取评论分析三维图所需数据。
     * @param skuId 主公司sku_id
     * @return  评论分析三维图所需数据。
     */
    @GetMapping(value = "/get3dScore/{sku_id}")
    public String get3dScore(@PathVariable("sku_id") String skuId) {
        long start = System.currentTimeMillis();
        log.info("request get3dScore: {}", skuId);
        QueryResponse response = searchChartsServiceBlockingStub
                .get3dScore(ChartsRequestByString.newBuilder().setChartsStrRequest(skuId)
                        .build());
        log.info("use {} ms", System.currentTimeMillis() - start);
        return response.getResponse();
    }
    /**
     * 利用关键词获取该关键词的百度指数预测。
     * @param keyword 百度指数中的关键词。
     * @return 关键词的预测数据。
     */
    @GetMapping(value = "/getIndexPredict/{keyword}")
    public String getIndexPredict(@PathVariable("keyword") String keyword) {
        long start = System.currentTimeMillis();
        log.info("request getIndexPredict: {}", keyword);
        QueryResponse response = searchChartsServiceBlockingStub
                .getIndexPredict(ChartsRequestByString.newBuilder().setChartsStrRequest(keyword)
                        .build());
        log.info("use {} ms", System.currentTimeMillis() - start);
        return response.getResponse();
    }

    /**
     * 利用主公司的sku_id查询评论打分数据。
     * @param skuId 主公司sku_id
     * @return 评论打分数据。
     */
    @GetMapping(value = "/getCommentStatistic/{sku_id}")
    public String getCommentStatistic(@PathVariable("sku_id") String skuId) {
        long start = System.currentTimeMillis();
        log.info("request getCommentStatistic: {}", skuId);
        QueryResponse response = searchChartsServiceBlockingStub
                .getCommentStatistic(ChartsRequestByString.newBuilder().setChartsStrRequest(skuId)
                        .build());
        log.info("use {} ms", System.currentTimeMillis() - start);
        return response.getResponse();
    }

    @GetMapping(value = "/getEmotionAnalysis/{company_name}")
    public String getEmotionAnalysis(@PathVariable("company_name") String companyName) {
        long start = System.currentTimeMillis();
        log.info("request getEmotionAnalysis: {}", companyName);
        QueryResponse response = searchChartsServiceBlockingStub
                .getEmotionAnalysis(ChartsRequestByString.newBuilder().setChartsStrRequest(companyName)
                        .build());
        log.info("use {} ms", System.currentTimeMillis() - start);
        return response.getResponse();
    }
}
