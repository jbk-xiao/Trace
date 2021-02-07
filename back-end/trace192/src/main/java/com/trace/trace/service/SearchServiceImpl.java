package com.trace.trace.service;

import com.google.protobuf.ByteString;
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

    /**
     * 竞品查询模块
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
     * 溯源查询模块
     * @param request
     * @param responseObserver
     */
    @Override
    public void searchTrace(QueryRequest request, StreamObserver<TraceResponse> responseObserver) {
        //获取请求类型
        String queryType = request.getQueryType();
        //获取请求内容
        String query = request.getQuery();
        log.info("Receive queryType = " + queryType + ", query = " + query);
        //返回结果初始化
        String jsonInfo = "";

        //强加一个逻辑
        ByteString mediaData = ByteString.EMPTY;
        boolean isString = true;
        switch (queryType) {
            case "video": {
                /*  视频访问  */
                String filename = request.getQuery();
                isString = false;
                log.info("Encoding video '" + filename + "'...");
                mediaData =
                break;
            }
            case "picture": {
                //图片访问
                String filename = request.getQuery();
                isString = false;
                log.info("Encoding picture '" + filename + "'...");
                mediaData =
                break;
            }
            case "event": {
                //生产线“最近动态”列表
                String processName = request.getQuery();
                int page = Integer.parseInt(request.getPage());
                jsonInfo =
                break;
            }
            case "origin": {
                //依据溯源id查询流水线信息
                String originId = request.getQuery();
                log.info("Searching '" + originId + "' in fabric.");
                jsonInfo =
                log.info("Fabric return: " + jsonInfo);
                break;
            }
        }
        //把结果放入response
        TraceResponse response = isString
                ? TraceResponse.newBuilder().setResponse(jsonInfo).build()
                : TraceResponse.newBuilder().setResponseMedia(mediaData).build();
        //放入response，传回客户端
        responseObserver.onNext(response);
        //表示此次连接结束
        responseObserver.onCompleted();
    }


    /**
     * 电商查询模块
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

        switch (queryType) {
            case "keyword": {
                //分页检索，每次返回二十条商品
                log.info("Start searching keyword");
                String page = request.getPage();
                try {
                    jsonInfo = searchProduct.searchProducts(query,page);
                } catch (Exception e) {
                    e.printStackTrace();
                }
                break;
            }
            case "detail":
                //获取某商品的详情信息
                log.info("Start searching detail");
                String skuId = query;
                //调用mysql方法，获取相关id的详情
                jsonInfo = searchProduct.searchDetails(skuId);
                log.info("detail result: " + jsonInfo);
                break;
        }

        QueryResponse queryResponse = QueryResponse.newBuilder().setResponse(jsonInfo).build();
        //放入response，传回客户端
        responseObserver.onNext(queryResponse);
        //表示此次连接结束
        responseObserver.onCompleted();
}
