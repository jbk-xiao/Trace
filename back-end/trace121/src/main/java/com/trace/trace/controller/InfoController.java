package com.trace.trace.controller;

import com.trace.trace.grpc.CompetRequest;
import com.trace.trace.grpc.CompetResponse;
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

    /**
     * 根据主公司id获取到公司的信息和公司竞品项目的信息
     * @param regis_id
     * @return
     */
    @RequestMapping(value = "/getCompet/{regis_id}",method = RequestMethod.GET)
    public String getCompetInfo(@PathVariable("regis_id")String regis_id){
        log.info("receive"+regis_id);
        long start=System.currentTimeMillis();
        CompetResponse response=this.searchServiceBlockingStub.searchCompet(CompetRequest.newBuilder().setRegisId(regis_id).build());
        long end=System.currentTimeMillis();
        log.info("search:"+regis_id+"over,time："+(end-start));
        return response.getResponse();

    }
}
