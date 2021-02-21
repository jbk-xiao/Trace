package com.trace.trace.service;

import com.trace.trace.grpc.*;
import io.grpc.stub.StreamObserver;
import lombok.extern.slf4j.Slf4j;
import org.lognet.springboot.grpc.GRpcService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


/**
 * @author Zenglr
 * @program: FoXiShengCun
 * @packagename: com.trace.trace.service
 * @Description
 * @create 2021-02-07-8:58 下午
 */
@GRpcService
@Slf4j
@Service
public class SearchServiceImpl extends SearchServiceGrpc.SearchServiceImplBase {

    @Autowired
    SearchCompet searchCompet;

    @Autowired
    SearchTrace searchTrace;

    @Autowired
    SearchProduct searchProduct;

    @Autowired
    SearchGraph searchGraph;

    @Autowired
    ManageProducts manageProducts;
    /**
     * 竞品查询模块
     *
     * @param request
     * @param responseObserver
     */
    @Override
    public void searchCompet(CompetRequest request, StreamObserver<QueryResponse> responseObserver) {
        //获取请求公司id
        String regis_id = request.getRegisId();
        log.info("Receive regis_id:" + regis_id);
        log.info("start SearchCompet --------------");
        String responseInfo = searchCompet.searchCompetBasic(regis_id);
        log.info("competPart response:" + responseInfo);
        QueryResponse response = QueryResponse.newBuilder().setResponse(responseInfo).build();
        //放入response，传回客户端
        responseObserver.onNext(response);
        //表示此次连接结束
        responseObserver.onCompleted();
    }

    /**
     * 溯源查询模块，调用searchTrace，得到trace response并传回客户端
     *
     * @param request          request
     * @param responseObserver response
     * @see SearchTrace#searchTrace(QueryRequest)
     * @see com.trace.trace.grpc.TraceResponse
     */
    @Override
    public void searchTrace(QueryRequest request, StreamObserver<TraceResponse> responseObserver) {
        TraceResponse response = searchTrace.searchTrace(request);
        responseObserver.onNext(response);
        responseObserver.onCompleted();
    }


    /**
     * 电商查询模块
     *
     * @param request
     * @param responseObserver
     */
    @Override
    public void searchQuery(QueryRequest request, StreamObserver<QueryResponse> responseObserver) {
        //获取请求类型
        String queryType = request.getQueryType();
        //获取请求内容
        String query = request.getQuery();
        log.info("Receive queryType = " + queryType + ", query = " + query);
        //返回结果初始化
        String jsonInfo = "";
        //分情况

        if ("keyword".equals(queryType)) {
            //分页检索，每次返回二十条商品
            log.info("Start searching keyword");
            String page = request.getPage();
            try {
                jsonInfo = searchProduct.searchProducts(query, page);
            } catch (Exception e) {
                e.printStackTrace();
            }
        } else if ("detail".equals(queryType)) {
            //获取某商品的详情信息
            log.info("Start searching detail");
            String skuId = query;
            //调用mysql方法，获取相关id的详情
            jsonInfo = searchProduct.searchDetails(skuId);
            log.info("detail result: " + jsonInfo);
        } else {
            log.error("Receive the wrong message!");
        }

        QueryResponse queryResponse = QueryResponse.newBuilder().setResponse(jsonInfo).build();
        //放入response，传回客户端
        responseObserver.onNext(queryResponse);
        //表示此次连接结束
        responseObserver.onCompleted();
    }

    /**
     * 知识图谱查询模块（根据品类）
     * @param request
     * @param responseObserver
     */
    @Override
    public void searchGraphByKind(GraphRequestByKind request, StreamObserver<GraphResponseByKind> responseObserver)
    {
        String kind = request.getKind();
        log.info("receive kind: " + kind);
        log.info("Start search graph...");
        String responseInfo = searchGraph.searchGraphByKind(kind);
        log.info("find info: "+ responseInfo);
        GraphResponseByKind response = GraphResponseByKind.newBuilder().setResponse(responseInfo).build();
        //放入response，传回客户端
        responseObserver.onNext(response);
        //表示此次连接结束
        responseObserver.onCompleted();
    }

    /**
     *知识图谱查询模块（根据品牌）
     * @param request
     * @param responseObserver
     */
    @Override
    public void searchGraphByBrand(GraphRequestByBrand request, StreamObserver<GraphResponseByBrand> responseObserver)
    {
        String brand = request.getBrand();
        log.info("receive brand: " + brand);
        log.info("Start search graph...");
        String responseInfo = searchGraph.searchGraphByBrand(brand);
        log.info("find info: "+ responseInfo);
        GraphResponseByBrand response = GraphResponseByBrand.newBuilder().setResponse(responseInfo).build();
        //放入response，传回客户端
        responseObserver.onNext(response);
        //表示此次连接结束
        responseObserver.onCompleted();
    }

    /**
     * 产品列表查询
     * @param request
     * @param responseObserver
     */
    @Override
    public void searchProducts(ProductsRequest request, StreamObserver<ProductsResponse> responseObserver)
    {
        String key = request.getKey();
        log.info("receive key: " + key);
        log.info("Start search products...");
        String responseInfo = manageProducts.SearchProducts(key);
        log.info("find info: "+ responseInfo);
        ProductsResponse response = ProductsResponse.newBuilder().setResponse(responseInfo).build();
        //放入response，传回客户端
        responseObserver.onNext(response);
        //表示此次连接结束
        responseObserver.onCompleted();
    }

    /**
     * 添加产品
     * @param request
     * @param responseObserver
     */
    @Override
    public void addProduct(AddProductRequest request, StreamObserver<AddProductResponse> responseObserver)
    {
        String key = request.getKey();
        String field = request.getField();
        String value = request.getValue();
        log.info("receive: " + key + "-" + field + "-" + value);
        log.info("Start adding products...");
        String responseInfo = manageProducts.AddProduct(key, field, value);
        log.info("find info: "+ responseInfo);
        AddProductResponse response = AddProductResponse.newBuilder().setResponse(responseInfo).build();
        //放入response，传回客户端
        responseObserver.onNext(response);
        //表示此次连接结束
        responseObserver.onCompleted();
    }

    /**
     * 删除产品
     * @param request
     * @param responseObserver
     */
    @Override
    public void deleteProduct(DeleteProductRequest request, StreamObserver<DeleteProductResponse> responseObserver)
    {
        String key = request.getKey();
        String field = request.getField();
        log.info("receive: " + key + "-" + field);
        log.info("Start deleting products...");
        String responseInfo = manageProducts.DeleteProduct(key, field);
        log.info("find info: "+ responseInfo);
        DeleteProductResponse response = DeleteProductResponse.newBuilder().setResponse(responseInfo).build();
        //放入response，传回客户端
        responseObserver.onNext(response);
        //表示此次连接结束
        responseObserver.onCompleted();
    }

    /**
     * 管理界面获取到所有的溯源列表
     * @param request
     * @param responseObserver
     */
    @Override
    public void searchAllTraceByName(AllTraceRequest request, StreamObserver<QueryResponse> responseObserver)
    {
        String product_name = request.getProductName();
        String regis_id = request.getRegisId();
        String page = request.getPage();
        log.info("receive regis_id: " + regis_id + ",product_name: " + product_name + ",page: " + page);
        String responseInfo = searchTrace.searchAllTraceByName(product_name,regis_id,page);
        log.info("searchAllTraceByName response: " + responseInfo);
        QueryResponse response = QueryResponse.newBuilder().setResponse(responseInfo).build();
        //放入response，传回客户端
        responseObserver.onNext(response);
        //表示此次连接结束
        responseObserver.onCompleted();
    }

    /**
     * 获取到所有商品名称列表
     * @param request
     * @param responseObserver
     */
//    @Override
//    public void getAllProductNameList(ProductsRequest request, StreamObserver<QueryResponse> responseObserver){
//        String regis_id = request.getKey();
//        log.info("receive regis_id: " + regis_id);
//        String responseInfo = manageProducts.getAllProductNameList(regis_id);
//        log.info("getAllProductNameList response: " + responseInfo);
//        QueryResponse response = QueryResponse.newBuilder().setResponse(responseInfo).build();
//        //放入response，传回客户端
//        responseObserver.onNext(response);
//        //表示此次连接结束
//        responseObserver.onCompleted();
//    }

    /**
     * 返回第一次流程填写所需的信息
     * @param request
     * @param responseObserver
     */
    @Override
    public void getFirstProcessInfo(ProductsRequest request, StreamObserver<QueryResponse> responseObserver){
        String regis_id = request.getKey();
        log.info("receive regis_id: " + regis_id);
        String responseInfo = searchTrace.getFirstProcessInfo(regis_id);
        log.info("getFirstProcessInfo response: " + responseInfo);
        QueryResponse response = QueryResponse.newBuilder().setResponse(responseInfo).build();
        //放入response，传回客户端
        responseObserver.onNext(response);
        //表示此次连接结束
        responseObserver.onCompleted();
    }
}
