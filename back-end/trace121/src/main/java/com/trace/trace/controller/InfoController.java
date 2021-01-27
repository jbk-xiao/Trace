package com.trace.trace.controller;

import com.trace.trace.grpc.QueryRequest;
import com.trace.trace.grpc.QueryResponse;
import com.trace.trace.grpc.SearchServiceGrpc;
import lombok.extern.slf4j.Slf4j;
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

    private SearchServiceGrpc.SearchServiceBlockingStub searchServiceBlockingStub;
    public InfoController(SearchServiceGrpc.SearchServiceBlockingStub searchServiceBlockingStub){
        this.searchServiceBlockingStub=searchServiceBlockingStub;
    }

    @RequestMapping(value = "/getCompet/{query}",method = RequestMethod.GET)
    public String getTestInfo(@PathVariable("query")String query){
        log.info("receive"+query);
        long start=System.currentTimeMillis();
        QueryResponse response=this.searchServiceBlockingStub.searchQuery(QueryRequest.newBuilder().setQuery(query).build());
        long end=System.currentTimeMillis();
        log.info("检索:"+query+"完成，用时："+(end-start));
        return response.getResponse();

    }
}
