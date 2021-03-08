package com.trace.trace.service;

import com.trace.trace.grpc.CompetRequest;
import com.trace.trace.grpc.GraphRequestByBrand;
import com.trace.trace.grpc.GraphRequestByKind;
import com.trace.trace.grpc.GraphResponseByBrand;
import com.trace.trace.grpc.GraphResponseByKind;
import com.trace.trace.grpc.QueryRequest;
import com.trace.trace.grpc.QueryResponse;
import com.trace.trace.grpc.SearchServiceGrpc;
import io.grpc.stub.StreamObserver;
import lombok.extern.slf4j.Slf4j;
import org.lognet.springboot.grpc.GRpcService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@GRpcService
@Slf4j
@Service
public class SearchServiceImpl extends SearchServiceGrpc.SearchServiceImplBase {

    private final SearchCompet searchCompet;

    private final SearchProduct searchProduct;

    private final SearchGraph searchGraph;

    @Autowired
    public SearchServiceImpl(SearchCompet searchCompet, SearchProduct searchProduct, SearchGraph searchGraph) {
        this.searchCompet = searchCompet;
        this.searchProduct = searchProduct;
        this.searchGraph = searchGraph;
    }

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
     *
     * @param request
     * @param responseObserver
     */
    @Override
    public void searchGraphByKind(GraphRequestByKind request, StreamObserver<GraphResponseByKind> responseObserver) {
        String kind = request.getKind();
        log.info("receive kind: " + kind);
        log.info("Start search graph...");
        String responseInfo = searchGraph.searchGraphByKind(kind);
        log.info("find info: " + responseInfo);
        GraphResponseByKind response = GraphResponseByKind.newBuilder().setResponse(responseInfo).build();
        //放入response，传回客户端
        responseObserver.onNext(response);
        //表示此次连接结束
        responseObserver.onCompleted();
    }

    /**
     * 知识图谱查询模块（根据品牌）
     *
     * @param request
     * @param responseObserver
     */
    @Override
    public void searchGraphByBrand(GraphRequestByBrand request, StreamObserver<GraphResponseByBrand> responseObserver) {
        String brand = request.getBrand();
        log.info("receive brand: " + brand);
        log.info("Start search graph...");
        String responseInfo = searchGraph.searchGraphByBrand(brand);
        log.info("find info: " + responseInfo);
        GraphResponseByBrand response = GraphResponseByBrand.newBuilder().setResponse(responseInfo).build();
        //放入response，传回客户端
        responseObserver.onNext(response);
        //表示此次连接结束
        responseObserver.onCompleted();
    }
}
