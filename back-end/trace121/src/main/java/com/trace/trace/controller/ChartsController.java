package com.trace.trace.controller;

import com.trace.trace.grpc.ChartsRequestByString;
import com.trace.trace.grpc.QueryResponse;
import com.trace.trace.grpc.SearchServiceGrpc;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

/**
 * 用于所有图表所需数据的返回。
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
    final SearchServiceGrpc.SearchServiceBlockingStub searchServiceBlockingStub;

    public ChartsController(SearchServiceGrpc.SearchServiceBlockingStub searchServiceBlockingStub) {
        this.searchServiceBlockingStub = searchServiceBlockingStub;
    }

    /**
     * 取出前端需求的预测曲线的json内容。
     * @param companyName 传入主公司名称。
     * @return 返回“预测曲线”所需内容。
     */
    @RequestMapping(value = "/getPredict/{company_name}", method = RequestMethod.GET)
    public String getPredict(@PathVariable("company_name") String companyName) {
        long start = System.currentTimeMillis();
        log.info("request getPredict: {}", companyName);
        QueryResponse response = searchServiceBlockingStub
                .getPredict(ChartsRequestByString.newBuilder().setRequest(companyName)
                        .build());
        log.info("use {} ms", System.currentTimeMillis() - start);
        return response.getResponse();
    }

    /**
     * 取出企业的新闻标题和链接的url并返回给前端
     * @param companyName 企业名称
     * @return json
     */
    @RequestMapping(value = "/getNews/{company_name}", method = RequestMethod.GET)
    public String getNews(@PathVariable("company_name") String companyName) {
        long start = System.currentTimeMillis();
        log.info("request getNews: {}", companyName);
        QueryResponse response = searchServiceBlockingStub
                .getNews(ChartsRequestByString.newBuilder().setRequest(companyName)
                        .build());
        log.info("use {} ms", System.currentTimeMillis() - start);
        return response.getResponse();
    }

    @RequestMapping(value = "/getAgeDistribution/{keyword}", method = RequestMethod.GET)
    public String getAgeDistribution(@PathVariable("keyword") String keyword) {
        long start = System.currentTimeMillis();
        log.info("request getAgeDistribution: {}", keyword);
        QueryResponse response = searchServiceBlockingStub
                .getAgeDistribution(ChartsRequestByString.newBuilder().setRequest(keyword)
                        .build());
        log.info("use {} ms", System.currentTimeMillis() - start);
        return response.getResponse();
    }

    @RequestMapping(value = "/getProvinceIndex/{keyword}", method = RequestMethod.GET)
    public String getProvinceIndex(@PathVariable("keyword") String keyword) {
        long start = System.currentTimeMillis();
        log.info("request getProvinceIndex: {}", keyword);
        QueryResponse response = searchServiceBlockingStub
                .getProvinceIndex(ChartsRequestByString.newBuilder().setRequest(keyword)
                        .build());
        log.info("use {} ms", System.currentTimeMillis() - start);
        return response.getResponse();
    }
}
