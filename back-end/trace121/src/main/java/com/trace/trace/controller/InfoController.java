package com.trace.trace.controller;

import com.trace.trace.grpc.CompetRequest;
import com.trace.trace.grpc.GraphRequestByBrand;
import com.trace.trace.grpc.GraphRequestByKind;
import com.trace.trace.grpc.GraphResponseByBrand;
import com.trace.trace.grpc.GraphResponseByKind;
import com.trace.trace.grpc.QueryRequest;
import com.trace.trace.grpc.QueryResponse;
import com.trace.trace.grpc.SearchServiceGrpc;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@CrossOrigin("*")
@RestController
public class InfoController {

    /**
     * 从容器中获取调用GRpc stub
     */
    private final SearchServiceGrpc.SearchServiceBlockingStub searchServiceBlockingStub;

    @Autowired
    public InfoController(SearchServiceGrpc.SearchServiceBlockingStub searchServiceBlockingStub) {
        this.searchServiceBlockingStub = searchServiceBlockingStub;
    }

    /**
     * 依据检索词展示出对应的电商
     *
     * @param query 检索词
     * @param page  页码
     * @return      检索结果
     */
    @GetMapping("/getCommodity/{query}/{page}")
    public String query(@PathVariable("query") String query, @PathVariable("page") String page) {
        log.info("Receive commodity request : " + query + " page=" + page);
        if (query.isEmpty() || "undefined".equals(query) || "NaN".equals(page)) {
            log.warn("Receive NaN page or empty query.");
            return "[]";
        }
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
    public String detail(@PathVariable("skuId") String skuId) {
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
     *
     * @param regisId 主公司id
     * @return 公司信息可公司竞品项目信息。
     */
    @GetMapping(value = "/getCompet/{regis_id}")
    public String getCompetInfo(@PathVariable("regis_id") String regisId) {
        log.info("receive" + regisId);
        long start = System.currentTimeMillis();
        QueryResponse response = this.searchServiceBlockingStub
                .searchCompet(CompetRequest.newBuilder().setRegisId(regisId).build());
        long end = System.currentTimeMillis();
        log.info("search: " + regisId + " over, use time: " + (end - start));
        return response.getResponse();
    }

    @GetMapping(value = "/getGraphByKind/{kind}")
    public String getGraphByKind(@PathVariable("kind") String kind) {
        log.info("receive" + kind);
        long start = System.currentTimeMillis();
        GraphResponseByKind response = this.searchServiceBlockingStub
                .searchGraphByKind(GraphRequestByKind.newBuilder().setKind(kind).build());
        long end = System.currentTimeMillis();
        log.info("search: " + kind + " over, use time: " + (end - start));
        return response.getResponse();
    }

    @GetMapping(value = "/getGraphByBrand/{brand}")
    public String getGraphByBrand(@PathVariable("brand") String brand) {
        log.info("receive" + brand);
        long start = System.currentTimeMillis();
        GraphResponseByBrand response = this.searchServiceBlockingStub
                .searchGraphByBrand(GraphRequestByBrand.newBuilder().setBrand(brand).build());
        long end = System.currentTimeMillis();
        log.info("search: " + brand + " over, use time: " + (end - start));
        return response.getResponse();
    }

}
