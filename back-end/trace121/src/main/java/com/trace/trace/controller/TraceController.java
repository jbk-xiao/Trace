package com.trace.trace.controller;

import com.google.gson.Gson;
import com.trace.trace.grpc.QueryRequest;
import com.trace.trace.grpc.SearchServiceGrpc;
import com.trace.trace.grpc.TraceResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
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
     * {"main_process":"{
     * "id":"16119701634150000",
     * "process":[
     * {"name":"菜籽油生产地","master":"张三","enter":true,"time":1611970163415},
     * {"name":"菜籽油生产地","master":"张三","enter":false,"time":1611970595415},
     * {"name":"工厂","master":"李四","enter":true,"time":1611970595415},
     * {"name":"工厂","master":"李四","enter":false,"time":1611971027415},
     * {"name":"分销商","master":"王五","enter":true,"time":1611971027415},
     * {"name":"分销商","master":"王五","enter":false,"time":1611971459415},
     * {"name":"经销商超市","master":"麻六","enter":true,"time":1611971459415},
     * {"name":"经销商超市","master":"麻六","enter":false,"time":1611971891415}
     * ]
     * }",
     * "industry_process":"{
     * "id":"16119701634150000",
     * "name":"工厂",
     * "master":"李四",
     * "procedure":[
     * {"name":"烧制玻璃瓶","master":"李四一","start":true,"time":1611974483415},
     * {"name":"烧制玻璃瓶","master":"李四一","start":false,"time":1611974915415},
     * {"name":"炒制","master":"李四二","start":true,"time":1611974915415},
     * {"name":"炒制","master":"李四二","start":false,"time":1611975347415},
     * {"name":"罐装","master":"李四三","start":true,"time":1611975347415},
     * {"name":"罐装","master":"李四三","start":false,"time":1611975779415},
     * {"name":"机器拧盖","master":"李重四","start":true,"time":1611975779415},
     * {"name":"机器拧盖","master":"李重四","start":false,"time":1611976211415}
     * ]
     * }"}
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

    @RequestMapping(value = "/addProcess", method = RequestMethod.POST)
    public String addProcess(@RequestParam(value = "id") String id, @RequestParam(value = "name") String name,
                    @RequestParam(value = "master") String master, @RequestParam(value = "location") String location) {
        log.info("Add process {}: {}, {}, {}.", id, name, master, location);
        long start = System.currentTimeMillis();
        HashMap<String, String> params = new HashMap<>();
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
        HashMap<String, String> params = new HashMap<>();
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
}
