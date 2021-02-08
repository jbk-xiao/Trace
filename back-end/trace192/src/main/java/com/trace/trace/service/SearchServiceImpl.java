package com.trace.trace.service;

import com.trace.trace.grpc.CompetRequest;
import com.trace.trace.grpc.QueryRequest;
import com.trace.trace.grpc.QueryResponse;
import com.trace.trace.grpc.SearchServiceGrpc;
import com.trace.trace.grpc.TraceResponse;
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
}
