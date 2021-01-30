package com.trace.trace.controller;

import com.trace.trace.grpc.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;


/**
 * @author Zenglr
 * @program: trace
 * @packagename: com.trace.trace.controller
 * @Description
 * @create 2021-01-25-10:32 上午
 */
@Slf4j
@CrossOrigin("*")
@RestController
public class InfoController {

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
    public String detail(@PathVariable("skuId")String skuId){
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

    /**
     * 根据主公司id获取到公司的信息和公司竞品项目的信息
     * @param regis_id
     * @return
     */
    @RequestMapping(value = "/getCompet/{regis_id}",method = RequestMethod.GET)
    public String getCompetInfo(@PathVariable("regis_id")String regis_id){
        log.info("receive"+regis_id);
        long start=System.currentTimeMillis();
        QueryResponse response = this.searchServiceBlockingStub
                .searchQuery(QueryRequest.newBuilder()
                        .setQuery(regis_id).setQueryType("compet").build());
        long end=System.currentTimeMillis();
        log.info("search:"+regis_id+"over,use time:"+(end-start));
        return response.getResponse();

    }
}
