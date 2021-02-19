package com.trace.trace.controller;

import com.trace.trace.grpc.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

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
     * 从容器中获取调用GRpc stub
     */
    @Autowired
    SearchServiceGrpc.SearchServiceBlockingStub searchServiceBlockingStub;


    @GetMapping("/getCommodity/{query}/{page}")
    public String query(@PathVariable("query") String query, @PathVariable("page") String page) {
        log.info("Receive commodity request : " + query + " page=" + page);
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
     * @param regis_id
     * @return
     */
    @RequestMapping(value = "/getCompet/{regis_id}", method = RequestMethod.GET)
    public String getCompetInfo(@PathVariable("regis_id") String regis_id) {
        log.info("receive" + regis_id);
        long start = System.currentTimeMillis();
        QueryResponse response = this.searchServiceBlockingStub
                .searchCompet(CompetRequest.newBuilder().setRegisId(regis_id).build());
        long end = System.currentTimeMillis();
        log.info("search: " + regis_id + " over, use time: " + (end - start));
        return response.getResponse();
    }

    @RequestMapping(value = "/getGraphByKind/{kind}", method = RequestMethod.GET)
    public String getGraphByKind(@PathVariable("kind") String kind) {
        log.info("receive" + kind);
        long start = System.currentTimeMillis();
        GraphResponseByKind response = this.searchServiceBlockingStub
                .searchGraphByKind(GraphRequestByKind.newBuilder().setKind(kind).build());
        long end = System.currentTimeMillis();
        log.info("search: " + kind + " over, use time: " + (end - start));
        return response.getResponse();
    }

    @RequestMapping(value = "/getGraphByBrand/{brand}", method = RequestMethod.GET)
    public String getGraphByBrand(@PathVariable("brand") String brand) {
        log.info("receive" + brand);
        long start = System.currentTimeMillis();
        GraphResponseByBrand response = this.searchServiceBlockingStub
                .searchGraphByBrand(GraphRequestByBrand.newBuilder().setBrand(brand).build());
        long end = System.currentTimeMillis();
        log.info("search: " + brand + " over, use time: " + (end - start));
        return response.getResponse();
    }

    @RequestMapping(value = "/getProducts/{key}", method = RequestMethod.GET)
    public String getProducts(@PathVariable("key") String key) {
        log.info("Receive product request : " + key);
        long start = System.currentTimeMillis();
        ProductsResponse response = this.searchServiceBlockingStub
                .searchProducts(ProductsRequest.newBuilder()
                        .setKey(key).build());
        long end = System.currentTimeMillis();
        log.info("Search result: " + response.getResponse());
        log.info("Retrieval time: " + (end - start));
        return response.getResponse();
    }

    @RequestMapping(value = "/addProduct/{key}/{field}/{value}", method = RequestMethod.GET)
    public String addProduct(@PathVariable("key") String key, @PathVariable("field") String field, @PathVariable("value") String value) {
        log.info("Receive product request : " + key + "-" + field + "-" + value);
        long start = System.currentTimeMillis();
        AddProductResponse response = this.searchServiceBlockingStub
                .addProduct(AddProductRequest.newBuilder()
                        .setKey(key).setField(field).setValue(value).build());
        long end = System.currentTimeMillis();
        log.info("Add result: " + response.getResponse());
        log.info("Retrieval time: " + (end - start));
        return response.getResponse();
    }

    @RequestMapping(value = "/addProduct/{key}/{field}", method = RequestMethod.GET)
    public String deleteProduct(@PathVariable("key") String key, @PathVariable("field") String field) {
        log.info("Receive product request : " + key + "-" + field);
        long start = System.currentTimeMillis();
        DeleteProductResponse response = this.searchServiceBlockingStub
                .deleteProduct(DeleteProductRequest.newBuilder()
                        .setKey(key).setField(field).build());
        long end = System.currentTimeMillis();
        log.info("Delete result: " + response.getResponse());
        log.info("Retrieval time: " + (end - start));
        return response.getResponse();
    }
}
