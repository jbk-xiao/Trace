package com.trace.trace.service;

import com.trace.trace.grpc.ChartsRequestByString;
import com.trace.trace.grpc.QueryResponse;
import com.trace.trace.grpc.SearchChartsServiceGrpc;
import io.grpc.stub.StreamObserver;
import lombok.extern.slf4j.Slf4j;
import org.lognet.springboot.grpc.GRpcService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@GRpcService
@Slf4j
@Service
public class SearchChartsServiceImpl extends SearchChartsServiceGrpc.SearchChartsServiceImplBase {

    private final SearchCharts searchCharts;

    @Autowired
    public SearchChartsServiceImpl(SearchCharts searchCharts) {
        this.searchCharts = searchCharts;
    }

    /**
     * 取出前端需求的预测曲线的json内容。
     *
     * @param request          包含公司名称的grpc请求。
     * @param responseObserver StreamObserver<QueryResponse>
     */
    @Override
    public void getPredict(ChartsRequestByString request, StreamObserver<QueryResponse> responseObserver) {
        String companyName = request.getChartsStrRequest();
        log.info("getPredict: {}", companyName);
        String predictData = searchCharts.getPredictData(companyName);
        log.info("getPredict response: {}", predictData);
        QueryResponse response = QueryResponse.newBuilder().setResponse(predictData).build();
        responseObserver.onNext(response);
        responseObserver.onCompleted();
    }

    /**
     * 取出企业的新闻标题和链接的url并返回。
     *
     * @param request          包含公司名称的grpc请求。
     * @param responseObserver StreamObserver<QueryResponse>
     */
    @Override
    public void getNews(ChartsRequestByString request, StreamObserver<QueryResponse> responseObserver) {
        String companyName = request.getChartsStrRequest();
        log.info("getNews: {}", companyName);
        String newsData = searchCharts.getNewsData(companyName);
        log.info("getNews response: {}chars", newsData.length());
        QueryResponse response = QueryResponse.newBuilder().setResponse(newsData).build();
        responseObserver.onNext(response);
        responseObserver.onCompleted();
    }

    /**
     * 取出年龄和性别分布指数
     *
     * @param request          关键词
     * @param responseObserver StreamObserver<QueryResponse>
     */
    @Override
    public void getAgeDistribution(ChartsRequestByString request, StreamObserver<QueryResponse> responseObserver) {
        String keyword = request.getChartsStrRequest();
        log.info("getAgeDistribution: {}", keyword);
        String ageDistributionData = searchCharts.getAgeDistribution(keyword);
        log.info("getAgeDistribution: {}", ageDistributionData);
        QueryResponse response = QueryResponse.newBuilder().setResponse(ageDistributionData).build();
        responseObserver.onNext(response);
        responseObserver.onCompleted();
    }

    /**
     * 取出省份分布指数
     *
     * @param request          关键词
     * @param responseObserver StreamObserver
     */
    @Override
    public void getProvinceIndex(ChartsRequestByString request, StreamObserver<QueryResponse> responseObserver) {
        String keyword = request.getChartsStrRequest();
        log.info("getProvinceIndex: {}", keyword);
        String provinceIndex = searchCharts.getProvinceIndex(keyword);
        log.info("getProvinceIndex: {}", provinceIndex);
        QueryResponse response = QueryResponse.newBuilder().setResponse(provinceIndex).build();
        responseObserver.onNext(response);
        responseObserver.onCompleted();
    }

    /**
     * 获取相关关键词关联检索气泡图所需数据
     * @param request           带有关键词的ChartsRequestByString
     * @param responseObserver  StreamObserver
     */
    @Override
    public void getRelateSearch(ChartsRequestByString request, StreamObserver<QueryResponse> responseObserver) {
        String keyword = request.getChartsStrRequest();
        log.info("getRelateSearch: {}", keyword);
        String relateSearch = searchCharts.getRelateSearch(keyword);
        log.info("getRelateSearch: {}", relateSearch);
        QueryResponse response = QueryResponse.newBuilder().setResponse(relateSearch).build();
        responseObserver.onNext(response);
        responseObserver.onCompleted();
    }

    /**
     * 根据主公司sku_id获取评论分析三维图所需数据。
     * @param request 含主公司sku_id的ChartsRequestByString
     * @param responseObserver StreamObserver
     */
    @Override
    public void get3dScore(ChartsRequestByString request, StreamObserver<QueryResponse> responseObserver) {
        String skuId = request.getChartsStrRequest();
        log.info("get3dScore: {}", skuId);
        String s3dScore = searchCharts.get3dScore(skuId);
        log.info("get3dScore: {}", s3dScore);
        QueryResponse response = QueryResponse.newBuilder().setResponse(s3dScore).build();
        responseObserver.onNext(response);
        responseObserver.onCompleted();
    }

    /**
     * 获取关键词的百度指数预测结果。
     *
     * @param request          关键词
     * @param responseObserver StreamObserver
     */
    @Override
    public void getIndexPredict(ChartsRequestByString request, StreamObserver<QueryResponse> responseObserver) {
        String keyword = request.getChartsStrRequest();
        log.info("getIndexPredict: {}", keyword);
        String indexPredict = searchCharts.getIndexPredict(keyword);
        log.info("getIndexPredict: {}", indexPredict);
        QueryResponse response = QueryResponse.newBuilder().setResponse(indexPredict).build();
        responseObserver.onNext(response);
        responseObserver.onCompleted();
    }
    /**
     * 利用主公司的sku_id查询评论打分数据。
     * @param request           含主公司sku_id的ChartsRequestByString
     * @param responseObserver  StreamObserver
     */
    @Override
    public void getCommentStatistic(ChartsRequestByString request, StreamObserver<QueryResponse> responseObserver) {
        String skuId = request.getChartsStrRequest();
        log.info("getCommentStatistic: {}", skuId);
        String commentStatistic = searchCharts.getCommentStatistic(skuId);
        log.info("getCommentStatistic: {}", commentStatistic);
        QueryResponse response = QueryResponse.newBuilder().setResponse(commentStatistic).build();
        responseObserver.onNext(response);
        responseObserver.onCompleted();
    }

    /**
     * 利用主公司简称（老干妈、泰奇）查询评论情感分析数据
     * @param request           含主公司简称的ChartsRequestByString
     * @param responseObserver  StreamObserver
     */
    @Override
    public void getEmotionAnalysis(ChartsRequestByString request, StreamObserver<QueryResponse> responseObserver) {
        String companyName = request.getChartsStrRequest();
        log.info("getEmotionAnalysis: {}", companyName);
        String emotionAnalysis = searchCharts.getEmotionAnalysis(companyName);
        log.info("getEmotionAnalysis: {}", emotionAnalysis);
        QueryResponse response = QueryResponse.newBuilder().setResponse(emotionAnalysis).build();
        responseObserver.onNext(response);
        responseObserver.onCompleted();
    }
}
