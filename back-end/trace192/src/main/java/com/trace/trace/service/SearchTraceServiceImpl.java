package com.trace.trace.service;

import com.google.protobuf.ByteString;
import com.trace.trace.grpc.AllTraceRequest;
import com.trace.trace.grpc.ProductsRequest;
import com.trace.trace.grpc.QueryResponse;
import com.trace.trace.grpc.SearchTraceServiceGrpc;
import com.trace.trace.grpc.TraceBytesResponse;
import com.trace.trace.grpc.TraceRequestByString;
import com.trace.trace.grpc.TraceStringResponse;
import io.grpc.stub.StreamObserver;
import lombok.extern.slf4j.Slf4j;
import org.lognet.springboot.grpc.GRpcService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@GRpcService
@Slf4j
@Service
public class SearchTraceServiceImpl extends SearchTraceServiceGrpc.SearchTraceServiceImplBase {
    private final SearchTrace searchTrace;

    @Autowired
    public SearchTraceServiceImpl(SearchTrace searchTrace) {
        this.searchTrace = searchTrace;
    }

    /**
     * 依据图片名称返回图片字节数据
     *
     * @param request          带有图片名称的TraceRequestByString实例
     * @param responseObserver StreamObserver
     */
    @Override
    public void searchPicture(TraceRequestByString request, StreamObserver<TraceBytesResponse> responseObserver) {
        String picName = request.getTraceStrRequest();
        log.info("SearchPicture: {}", picName);
        ByteString picData = searchTrace.searchPicture(picName);
        log.info("SearchPicture responses: {}bytes", picData.size());
        TraceBytesResponse response = TraceBytesResponse.newBuilder().setTraceBytesResponse(picData).build();
        responseObserver.onNext(response);
        responseObserver.onCompleted();
    }

    /**
     * 依据视频名称返回视频字节数据
     *
     * @param request          带有视频名称的TraceRequestByString实例
     * @param responseObserver StreamObserver<TraceBytesResponse>
     */
    @Override
    public void searchVideo(TraceRequestByString request, StreamObserver<TraceBytesResponse> responseObserver) {
        String videoName = request.getTraceStrRequest();
        log.info("SearchVideo: {}", videoName);
        ByteString videoData = searchTrace.searchVideo(videoName);
        log.info("SearchVideo responses: {}bytes", videoData.size());
        TraceBytesResponse response = TraceBytesResponse.newBuilder().setTraceBytesResponse(videoData).build();
        responseObserver.onNext(response);
        responseObserver.onCompleted();
    }

    /**
     * 依据唯一溯源码返回对应商品的溯源信息。
     *
     * @param request          含有唯一溯源码的TraceRequestByString实例
     * @param responseObserver StreamObserver<TraceStringResponse>
     */
    @Override
    public void getOrigin(TraceRequestByString request, StreamObserver<TraceStringResponse> responseObserver) {
        String originId = request.getTraceStrRequest();
        log.info("GetOrigin: {}", originId);
        String originInfo = searchTrace.getOrigin(originId);
        log.info("GetOrigin responses: {}chars", originInfo.length());
        TraceStringResponse response = TraceStringResponse.newBuilder().setTraceStrResponse(originInfo).build();
        responseObserver.onNext(response);
        responseObserver.onCompleted();
    }

    /**
     * 依据所提供的基本信息，添加对应产品的第一个process
     *
     * @param request          带有新建产品基本信息的TraceRequestByString实例。
     * @param responseObserver
     */
    @Override
    public void addFirstProcess(TraceRequestByString request, StreamObserver<TraceStringResponse> responseObserver) {
        String processInfo = request.getTraceStrRequest();
        log.info("AddFirstProcess: {}", processInfo);
        String responseInfo = searchTrace.addFirstProcess(processInfo);
        log.info("AddFirstProcess responses: {}", responseInfo);
        TraceStringResponse response = TraceStringResponse.newBuilder().setTraceStrResponse(responseInfo).build();
        responseObserver.onNext(response);
        responseObserver.onCompleted();
    }

    /**
     * 依据所提供的唯一溯源id和process基本信息，添加对应产品的一个process
     *
     * @param request
     * @param responseObserver
     */
    @Override
    public void addProcess(TraceRequestByString request, StreamObserver<TraceStringResponse> responseObserver) {
        String processInfo = request.getTraceStrRequest();
        log.info("AddProcess: {}", processInfo);
        String responseInfo = searchTrace.addProcess(processInfo);
        log.info("AddProcess responses: {}", responseInfo);
        TraceStringResponse response = TraceStringResponse.newBuilder().setTraceStrResponse(responseInfo).build();
        responseObserver.onNext(response);
        responseObserver.onCompleted();
    }

    @Override
    public void addProcedure(TraceRequestByString request, StreamObserver<TraceStringResponse> responseObserver) {
        String procedureInfo = request.getTraceStrRequest();
        log.info("AddProcedure: {}", procedureInfo);
        String responseInfo = searchTrace.addProcedure(procedureInfo);
        log.info("AddProcedure responses: {}", responseInfo);
        TraceStringResponse response = TraceStringResponse.newBuilder().setTraceStrResponse(responseInfo).build();
        responseObserver.onNext(response);
        responseObserver.onCompleted();
    }

    /**
     * 管理界面获取到所有的溯源列表
     *
     * @param request
     * @param responseObserver
     */
    @Override
    public void searchAllTraceByName(AllTraceRequest request, StreamObserver<QueryResponse> responseObserver) {
        String product_name = request.getProductName();
        String regis_id = request.getRegisId();
        String page = request.getPage();
        log.info("receive regis_id: " + regis_id + ",product_name: " + product_name + ",page: " + page);
        String responseInfo = searchTrace.searchAllTraceByName(product_name, regis_id, page);
        log.info("searchAllTraceByName response: " + responseInfo);
        QueryResponse response = QueryResponse.newBuilder().setResponse(responseInfo).build();
        //放入response，传回客户端
        responseObserver.onNext(response);
        //表示此次连接结束
        responseObserver.onCompleted();
    }

    /**
     * 返回第一次流程填写所需的信息
     *
     * @param request
     * @param responseObserver
     */
    @Override
    public void getFirstProcessInfo(ProductsRequest request, StreamObserver<QueryResponse> responseObserver) {
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
