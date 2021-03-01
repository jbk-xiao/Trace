package com.trace.trace.controller;

import com.google.gson.Gson;
import com.trace.trace.grpc.AllTraceRequest;
import com.trace.trace.grpc.ProductsRequest;
import com.trace.trace.grpc.QueryResponse;
import com.trace.trace.grpc.SearchTraceServiceGrpc;
import com.trace.trace.grpc.TraceRequestByString;
import com.trace.trace.grpc.TraceStringResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
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

    final SearchTraceServiceGrpc.SearchTraceServiceBlockingStub searchTraceServiceBlockingStub;

    @Autowired
    public TraceController(SearchTraceServiceGrpc.SearchTraceServiceBlockingStub searchTraceServiceBlockingStub) {
        this.searchTraceServiceBlockingStub = searchTraceServiceBlockingStub;
    }

    /**
     * 根据产品溯源码返回溯源信息
     *
     * @param originId 唯一溯源码
     * @return 溯源信息json字符串，如：
     */
    @GetMapping(value = "/getOrigin/{origin_id}")
    public String getOriginInfo(@PathVariable("origin_id") String originId) {
        log.info("Receive origin request: " + originId);
        long start = System.currentTimeMillis();
        TraceStringResponse response = this.searchTraceServiceBlockingStub
                .getOrigin(TraceRequestByString.newBuilder()
                        .setTraceStrRequest(originId).build());
        long end = System.currentTimeMillis();
        log.info("Request origin '" + originId + "' over, taking " + (end - start));
        return response.getTraceStrResponse();
    }

    /**
     * 添加第一个process，即，创建一条新的溯源记录。
     *
     * @param foodType     油辣椒酱-275g-辣椒酱(foodName-specification-category)
     * @param com          公司的regisId
     * @param processCount 用于判断是否是最后一步
     * @param name         process名称
     * @param master       process负责人
     * @param location     process所在城市
     * @return TraceInfo.json带有qrCode字段存储二维码url
     */
    @PostMapping(value = "/addFirstProcess")
    public String addFirstProcess(@RequestParam(value = "foodType") String foodType,
                                  @RequestParam(value = "com") String com,
                                  @RequestParam(value = "processCount") Integer processCount,
                                  @RequestParam(value = "name") String name,
                                  @RequestParam(value = "master") String master,
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
        TraceStringResponse response = this.searchTraceServiceBlockingStub
                .addFirstProcess(TraceRequestByString.newBuilder()
                        .setTraceStrRequest(new Gson().toJson(params)).build());
        long end = System.currentTimeMillis();
        log.info("add first process over, taking " + (end - start));
        return response.getTraceStrResponse();
    }

    /**
     * 添加id对应批次产品的一条流动记录。
     *
     * @param id       唯一溯源id
     * @param name     流程名称
     * @param master   流程负责人名称
     * @param location 流程所在城市
     * @return id对应的已有溯源信息，添加完最后一个流程后会返回用于查询所有溯源信息的二维码。
     */
    @PostMapping(value = "/addProcess")
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
        TraceStringResponse response = this.searchTraceServiceBlockingStub
                .addProcess(TraceRequestByString.newBuilder()
                        .setTraceStrRequest(new Gson().toJson(params)).build());
        long end = System.currentTimeMillis();
        log.info("add process over, taking " + (end - start));
        return response.getTraceStrResponse();
    }

    /**
     * 添加一道工序信息。
     *
     * @param id     唯一溯源id
     * @param name   工序名称
     * @param master 工序负责人
     * @return id对应的已有溯源信息。
     */
    @PostMapping(value = "/addProcedure")
    public String addProcedure(@RequestParam(value = "id") String id, @RequestParam(value = "name") String name,
                               @RequestParam(value = "master") String master) {
        log.info("Add procedure {}: {}, {}.", id, name, master);
        long start = System.currentTimeMillis();
        HashMap<String, String> params = new HashMap<>(3);
        params.put("id", id);
        params.put("name", name);
        params.put("master", master);
        TraceStringResponse response = this.searchTraceServiceBlockingStub
                .addProcedure(TraceRequestByString.newBuilder()
                        .setTraceStrRequest(new Gson().toJson(params)).build());
        long end = System.currentTimeMillis();
        log.info("add process over, taking " + (end - start));
        return response.getTraceStrResponse();
    }

    /**
     * 根据regis_id、商品名称、页码来获取溯源列表
     *
     * @param productName 商品名称
     * @param regisId     商家的唯一regisId
     * @param page        页码
     * @return 10条溯源最新信息。
     */
    @GetMapping(value = "/getAllTraceInfo/{regis_id}/{product_name}/{page}")
    public String getAllTraceInfo(@PathVariable("product_name") String productName,
                                  @PathVariable("regis_id") String regisId, @PathVariable("page") String page) {
        log.info("Receive product_name request: " + productName + ",regis_id: " + regisId + ",page: " + page);
        long start = System.currentTimeMillis();
        QueryResponse queryResponse = this.searchTraceServiceBlockingStub
                .searchAllTraceByName(AllTraceRequest.newBuilder()
                        .setProductName(productName).setRegisId(regisId).setPage(page).build());
        long end = System.currentTimeMillis();
        log.info("Request product_name request '" + productName + "' over, taking " + (end - start));
        return queryResponse.getResponse();
    }

    /**
     * 获取到第一次流程输入的公司基本信息以及商品选项列表
     *
     * @param regisId   商家的唯一regisId
     * @return          第一次流程输入的公司基本信息以及商品选项列表
     */
    @GetMapping(value = "/getFirstProcessInfo/{regis_id}")
    public String getFirstProcessInfo(@PathVariable("regis_id") String regisId) {
        log.info("Receive regis_id request: " + regisId);
        long start = System.currentTimeMillis();
        QueryResponse queryResponse = this.searchTraceServiceBlockingStub
                .getFirstProcessInfo(ProductsRequest.newBuilder().setKey(regisId).build());
        long end = System.currentTimeMillis();
        log.info("Request regis_id request '" + regisId + "' to getAllProductNameList over, taking " + (end - start));
        return queryResponse.getResponse();
    }
}
