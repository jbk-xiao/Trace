package com.trace.trace.service;

import com.trace.trace.grpc.AddProductRequest;
import com.trace.trace.grpc.AddProductResponse;
import com.trace.trace.grpc.DeleteProductRequest;
import com.trace.trace.grpc.DeleteProductResponse;
import com.trace.trace.grpc.ManagerServiceGrpc;
import com.trace.trace.grpc.ProductsRequest;
import com.trace.trace.grpc.ProductsResponse;
import io.grpc.stub.StreamObserver;
import lombok.extern.slf4j.Slf4j;
import org.lognet.springboot.grpc.GRpcService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@GRpcService
@Slf4j
@Service
public class ManagerServiceImpl extends ManagerServiceGrpc.ManagerServiceImplBase {

    private final ManageProducts manageProducts;

    @Autowired
    public ManagerServiceImpl(ManageProducts manageProducts) {
        this.manageProducts = manageProducts;
    }

    /**
     * 产品列表查询
     *
     * @param request          带有公司regisId的请求
     * @param responseObserver StreamObserver
     */
    @Override
    public void searchProducts(ProductsRequest request, StreamObserver<ProductsResponse> responseObserver) {
        String regisId = request.getKey();
        log.info("receive regisId: " + regisId);
        log.info("Start search products...");
        String responseInfo = manageProducts.searchProducts(regisId);
        log.info("find info: " + responseInfo);
        ProductsResponse response = ProductsResponse.newBuilder().setResponse(responseInfo).build();
        //放入response，传回客户端
        responseObserver.onNext(response);
        //表示此次连接结束
        responseObserver.onCompleted();
    }

    /**
     * 添加产品
     *
     * @param request
     * @param responseObserver
     */
    @Override
    public void addProduct(AddProductRequest request, StreamObserver<AddProductResponse> responseObserver) {
        String key = request.getKey();
        String field = request.getField();
        String value = request.getValue();
        log.info("receive: " + key + "-" + field + "-" + value);
        log.info("Start adding products...");
        String responseInfo = manageProducts.addProduct(key, field, value);
        log.info("find info: " + responseInfo);
        AddProductResponse response = AddProductResponse.newBuilder().setResponse(responseInfo).build();
        //放入response，传回客户端
        responseObserver.onNext(response);
        //表示此次连接结束
        responseObserver.onCompleted();
    }

    /**
     * 删除产品
     *
     * @param request          包含公司regisID与产品名称的请求。
     * @param responseObserver StreamObserver
     */
    @Override
    public void deleteProduct(DeleteProductRequest request, StreamObserver<DeleteProductResponse> responseObserver) {
        String regisId = request.getKey();
        String productName = request.getField();
        log.info("receive: " + regisId + "-" + productName);
        log.info("Start deleting products...");
        String responseInfo = manageProducts.deleteProduct(regisId, productName);
        log.info("find info: " + responseInfo);
        DeleteProductResponse response = DeleteProductResponse.newBuilder().setResponse(responseInfo).build();
        //放入response，传回客户端
        responseObserver.onNext(response);
        //表示此次连接结束
        responseObserver.onCompleted();
    }
}
