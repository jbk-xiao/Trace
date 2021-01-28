package com.JingDong.JingDong.controller;


import com.JingDong.JingDong.grpc.QueryRequest;
import com.JingDong.JingDong.grpc.QueryResponse;
import com.JingDong.JingDong.grpc.SearchServiceGrpc;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * web层：用于接收ajax请求
 */
@Slf4j
@CrossOrigin("*")
@RestController
public class WeiboController {
    //从容器中获取调用GRpc stub
    @Autowired
    SearchServiceGrpc.SearchServiceBlockingStub searchServiceBlockingStub;

    @GetMapping("/getCommodity/{query}/{page}")
    public String query(@PathVariable("query")String query, @PathVariable("page")String page){
        log.info("Receive commodity request : "+query+" page="+page);
        long start = System.currentTimeMillis();
        QueryResponse response = this.searchServiceBlockingStub
                .searchQuery(QueryRequest.newBuilder()
                .setQuery(query).setQueryType("keyword").setPage(page).build());
        long end = System.currentTimeMillis();
        log.info("Search result : "+response.getResponse());
        log.info("Retrieval time: "+(end-start));
        return response.getResponse();
    }

    @GetMapping("/getDetail/{skuId}")
    public String relationship(@PathVariable("skuId")String skuId){
        log.info("Receive detail request : "+skuId);
        long start = System.currentTimeMillis();
        QueryResponse response = this.searchServiceBlockingStub
                .searchQuery(QueryRequest.newBuilder()
                        .setQuery(skuId).setQueryType("detail").build());
        long end = System.currentTimeMillis();
        log.info("Search result : "+response.getResponse());
        log.info("Retrieval time: "+(end-start));
        return response.getResponse();
    }
}
