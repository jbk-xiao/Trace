package com.trace.trace.controller;

import com.google.gson.Gson;
import com.trace.trace.grpc.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;

/**
 * @author jbk-xiao
 * @program trace121
 * @packagename com.trace.trace.controller
 * @Description
 * @create 2021-02-17-14:08
 */
@Slf4j
@CrossOrigin("*")
@RestController
public class TraceController {

    final SearchServiceGrpc.SearchServiceBlockingStub searchServiceBlockingStub;

    @Autowired
    public TraceController(SearchServiceGrpc.SearchServiceBlockingStub searchServiceBlockingStub) {
        this.searchServiceBlockingStub = searchServiceBlockingStub;
    }

    /**
     * 根据产品溯源码返回溯源信息
     *
     * @param originId 唯一溯源码
     * @return 溯源信息json字符串，如：
     *
     */
    @RequestMapping(value = "/getOrigin/{origin_id}", method = RequestMethod.GET)
    public String getOriginInfo(@PathVariable("origin_id") String originId) {
        log.info("Receive origin request: " + originId);
        long start = System.currentTimeMillis();
        TraceResponse traceResponse = this.searchServiceBlockingStub
                .searchTrace(QueryRequest.newBuilder()
                        .setQuery(originId).setQueryType("origin").build());
        long end = System.currentTimeMillis();
        log.info("Request origin '" + originId + "' over, taking " + (end - start));
        return traceResponse.getResponse();
    }

    /**
     *
     * @param foodType 油辣椒酱-275g-辣椒酱(foodName-specification-category)
     * @param com company name
     * @param processCount 用于判断是否是最后一步
     * @param name process name
     * @param master process负责人
     * @param location process所在城市
     * @return TraceInfo.json带有qrCode字段存储二维码url
     */
    @RequestMapping(value = "/addFirstProcess", method = RequestMethod.POST)
    public String addFirstProcess(@RequestParam(value = "foodType") String foodType,
                @RequestParam(value = "com") String com, @RequestParam(value = "processCount") Integer processCount,
                @RequestParam(value = "name") String name, @RequestParam(value = "master") String master,
                @RequestParam(value = "location") String location) {
        log.info("Add first process {}, {}, {}, {}, {}.", foodType, processCount, name, master, location);
        long start = System.currentTimeMillis();
        HashMap<String, String> params = new HashMap<>(6);
        params.put("foodType", foodType);
        params.put("com", com);
        params.put("processCount", processCount + "");
        params.put("name", name);
        params.put("master", master);
        params.put("location", location);
        TraceResponse traceResponse = this.searchServiceBlockingStub
                .searchTrace(QueryRequest.newBuilder()
                        .setQuery(new Gson().toJson(params)).setQueryType("addFirstProcess").build());
        long end = System.currentTimeMillis();
        log.info("add first process over, taking " + (end - start));
        return traceResponse.getResponse();
    }

    @RequestMapping(value = "/addProcess", method = RequestMethod.POST)
    public String addProcess(@RequestParam(value = "id") String id,
                @RequestParam(value = "name") String name, @RequestParam(value = "master") String master,
                @RequestParam(value = "location") String location) {
        log.info("Add process {}: {}, {}, {}.", id, name, master, location);
        long start = System.currentTimeMillis();
        HashMap<String, String> params = new HashMap<>(7);
        params.put("id", id);
        params.put("name", name);
        params.put("master", master);
        params.put("location", location);
        TraceResponse traceResponse = this.searchServiceBlockingStub
                .searchTrace(QueryRequest.newBuilder()
                        .setQuery(new Gson().toJson(params)).setQueryType("addProcess").build());
        long end = System.currentTimeMillis();
        log.info("add process over, taking " + (end - start));
        return traceResponse.getResponse();
    }

    @RequestMapping(value = "/addProcedure", method = RequestMethod.POST)
    public String addProcedure(@RequestParam(value = "id") String id, @RequestParam(value = "name") String name,
                    @RequestParam(value = "master") String master) {
        log.info("Add procedure {}: {}, {}.", id, name, master);
        long start = System.currentTimeMillis();
        HashMap<String, String> params = new HashMap<>(3);
        params.put("id", id);
        params.put("name", name);
        params.put("master", master);
        TraceResponse traceResponse = this.searchServiceBlockingStub
                .searchTrace(QueryRequest.newBuilder()
                        .setQuery(new Gson().toJson(params)).setQueryType("addProcedure").build());
        long end = System.currentTimeMillis();
        log.info("add process over, taking " + (end - start));
        return traceResponse.getResponse();
    }

    @RequestMapping(value = "/getAllTraceInfo/{regis_id}/{product_name}/{page}", method = RequestMethod.GET)
    public String getAllTraceInfo(@PathVariable("product_name") String product_name,@PathVariable("regis_id") String regis_id,@PathVariable("page") String page){
        log.info("Receive product_name request: "+product_name);
        long start = System.currentTimeMillis();
        QueryResponse queryResponse = this.searchServiceBlockingStub
                .searchAllTraceByName(AllTraceRequest.newBuilder()
                        .setProductName(product_name).setRegisId(regis_id).setPage(page).build());
        long end = System.currentTimeMillis();
        log.info("Request product_name request '" + product_name + "' over, taking " + (end - start));
        return queryResponse.getResponse();
    }
}
