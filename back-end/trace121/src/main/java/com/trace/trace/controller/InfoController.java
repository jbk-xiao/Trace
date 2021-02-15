package com.trace.trace.controller;

import com.google.protobuf.ByteString;
import com.trace.trace.grpc.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.BufferedInputStream;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.OutputStream;


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

    /**
     *从容器中获取调用GRpc stub
     */
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
        log.info("Search result: " + response.getResponse());
        log.info("Retrieval time: " + (end - start));
        return response.getResponse();
    }

    @GetMapping("/getDetail/{skuId}")
    public String detail(@PathVariable("skuId")String skuId){
        log.info("Receive detail request: " + skuId);
        long start = System.currentTimeMillis();
        QueryResponse response = this.searchServiceBlockingStub
                .searchQuery(QueryRequest.newBuilder()
                        .setQuery(skuId).setQueryType("detail").build());
        long end = System.currentTimeMillis();
        log.info("Search result : " + response.getResponse());
        log.info("Retrieval time: " + (end - start));
        return response.getResponse();
    }

    /**
     * 根据主公司id获取到公司的信息和公司竞品项目的信息
     * @param regis_id
     * @return
     */
    @RequestMapping(value = "/getCompet/{regis_id}", method = RequestMethod.GET)
    public String getCompetInfo(@PathVariable("regis_id")String regis_id){
        log.info("receive"+regis_id);
        long start = System.currentTimeMillis();
        QueryResponse response = this.searchServiceBlockingStub
                .searchCompet(CompetRequest.newBuilder().setRegisId(regis_id).build());
        long end = System.currentTimeMillis();
        log.info("search: " + regis_id + " over, use time: " + (end - start));
        return response.getResponse();

    }

    /**
     * 根据产品溯源码返回溯源信息
     * @param originId 唯一溯源码
     * @return 溯源信息json字符串，如：
     * {"main_process":"{
     *   "id":"16119701634150000",
     *   "process":[
     *     {"name":"菜籽油生产地","master":"张三","enter":true,"time":1611970163415},
     *     {"name":"菜籽油生产地","master":"张三","enter":false,"time":1611970595415},
     *     {"name":"工厂","master":"李四","enter":true,"time":1611970595415},
     *     {"name":"工厂","master":"李四","enter":false,"time":1611971027415},
     *     {"name":"分销商","master":"王五","enter":true,"time":1611971027415},
     *     {"name":"分销商","master":"王五","enter":false,"time":1611971459415},
     *     {"name":"经销商超市","master":"麻六","enter":true,"time":1611971459415},
     *     {"name":"经销商超市","master":"麻六","enter":false,"time":1611971891415}
     *   ]
     * }",
     * "industry_process":"{
     *   "id":"16119701634150000",
     *   "name":"工厂",
     *   "master":"李四",
     *   "procedure":[
     *     {"name":"烧制玻璃瓶","master":"李四一","start":true,"time":1611974483415},
     *     {"name":"烧制玻璃瓶","master":"李四一","start":false,"time":1611974915415},
     *     {"name":"炒制","master":"李四二","start":true,"time":1611974915415},
     *     {"name":"炒制","master":"李四二","start":false,"time":1611975347415},
     *     {"name":"罐装","master":"李四三","start":true,"time":1611975347415},
     *     {"name":"罐装","master":"李四三","start":false,"time":1611975779415},
     *     {"name":"机器拧盖","master":"李重四","start":true,"time":1611975779415},
     *     {"name":"机器拧盖","master":"李重四","start":false,"time":1611976211415}
     *   ]
     * }"}
     */
    @RequestMapping(value = "/getOrigin/{origin_id}", method = RequestMethod.GET)
    public String getOriginInfo(@PathVariable("origin_id") String originId) {
        log.info("Receive origin request: " + originId);
        long start = System.currentTimeMillis();
        TraceResponse response = this.searchServiceBlockingStub
                .searchTrace(QueryRequest.newBuilder()
                        .setQuery(originId).setQueryType("origin").build());
        long end = System.currentTimeMillis();
        log.info("Request origin '" + originId +"' over, taking " + (end - start));
        return response.getResponse();
    }

    @RequestMapping(value = "/getGraphByKind/{kind}", method = RequestMethod.GET)
    public String getGraphByKind(@PathVariable("kind")String kind){
        log.info("receive" + kind);
        long start = System.currentTimeMillis();
        GraphResponseByKind response = this.searchServiceBlockingStub
                .searchGraphByKind(GraphRequestByKind.newBuilder().setKind(kind).build());
        long end = System.currentTimeMillis();
        log.info("search: " + kind + " over, use time: " + (end - start));
        return response.getResponse();
    }

    @RequestMapping(value = "/getGraphByBrand/{brand}", method = RequestMethod.GET)
    public String getGraphByBrand(@PathVariable("brand")String brand){
        log.info("receive" + brand);
        long start = System.currentTimeMillis();
        GraphResponseByBrand response = this.searchServiceBlockingStub
                .searchGraphByBrand(GraphRequestByBrand.newBuilder().setBrand(brand).build());
        long end = System.currentTimeMillis();
        log.info("search: " + brand + " over, use time: " + (end - start));
        return response.getResponse();
    }

}
