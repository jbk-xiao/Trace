package com.trace.trace.controller;

import com.trace.trace.grpc.ChartsRequestByString;
import com.trace.trace.grpc.QueryResponse;
import com.trace.trace.grpc.SearchServiceGrpc;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
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
        QueryResponse response = searchServiceBlockingStub
                .getPredict(ChartsRequestByString.newBuilder().setRequest(companyName)
                        .build());
        return response.getResponse();
    }

    @RequestMapping(value = "/getNews/{company_name}", method = RequestMethod.GET)
    public String getNews(@PathVariable("company_name") String companyName) {
        QueryResponse response = searchServiceBlockingStub
                .getNews(ChartsRequestByString.newBuilder().setRequest(companyName)
                        .build());
        return response.getResponse();
    }
}
