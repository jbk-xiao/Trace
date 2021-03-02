package com.trace.trace.controller;

import com.trace.trace.grpc.AddProductRequest;
import com.trace.trace.grpc.AddProductResponse;
import com.trace.trace.grpc.DeleteProductRequest;
import com.trace.trace.grpc.DeleteProductResponse;
import com.trace.trace.grpc.ManagerServiceGrpc;
import com.trace.trace.grpc.ProductsRequest;
import com.trace.trace.grpc.ProductsResponse;
import com.trace.trace.grpc.SearchServiceGrpc;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author jbk-xiao
 * @program trace121
 * @packagename com.trace.trace.controller
 * @Description
 * @create 2021-03-02-9:30
 */
@Slf4j
@CrossOrigin("*")
@RestController
public class ManagerController {
    /**
     * 从容器中获取调用GRpc stub
     */
    final ManagerServiceGrpc.ManagerServiceBlockingStub managerServiceBlockingStub;

    @Autowired
    public ManagerController(ManagerServiceGrpc.ManagerServiceBlockingStub managerServiceBlockingStub) {
        this.managerServiceBlockingStub = managerServiceBlockingStub;
    }
    @GetMapping(value = "/getProducts/{regis_id}")
    public String getProducts(@PathVariable("regis_id") String regisId) {
        log.info("Receive product request : " + regisId);
        long start = System.currentTimeMillis();
        ProductsResponse response = this.managerServiceBlockingStub
                .searchProducts(ProductsRequest.newBuilder()
                        .setKey(regisId).build());
        long end = System.currentTimeMillis();
        log.info("Search result: " + response.getResponse());
        log.info("Retrieval time: " + (end - start));
        return response.getResponse();
    }

    @GetMapping(value = "/addProduct/{regis_id}/{product_name}/{code}")
    public String addProduct(@PathVariable("regis_id") String regisId,
                             @PathVariable("product_name") String productName,
                             @PathVariable("code") String code) {
        log.info("Receive product request : " + regisId + "-" + productName + "-" + code);
        long start = System.currentTimeMillis();
        AddProductResponse response = this.managerServiceBlockingStub
                .addProduct(AddProductRequest.newBuilder()
                        .setKey(regisId).setField(productName).setValue(code).build());
        long end = System.currentTimeMillis();
        log.info("Add result: " + response.getResponse());
        log.info("Retrieval time: " + (end - start));
        return response.getResponse();
    }

    @GetMapping(value = "/deleteProduct/{regis_id}/{product_name}")
    public String deleteProduct(@PathVariable("regis_id") String regisId,
                                @PathVariable("product_name") String productName) {
        log.info("Receive product request : " + regisId + "-" + productName);
        long start = System.currentTimeMillis();
        DeleteProductResponse response = this.managerServiceBlockingStub
                .deleteProduct(DeleteProductRequest.newBuilder()
                        .setKey(regisId).setField(productName).build());
        long end = System.currentTimeMillis();
        log.info("Delete result: " + response.getResponse());
        log.info("Retrieval time: " + (end - start));
        return response.getResponse();
    }
}
