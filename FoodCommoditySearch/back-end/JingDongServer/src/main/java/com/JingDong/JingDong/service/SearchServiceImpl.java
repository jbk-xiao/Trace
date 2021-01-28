package com.JingDong.JingDong.service;

import com.JingDong.JingDong.entity.Detail;
import com.JingDong.JingDong.entity.Query;
import com.JingDong.JingDong.grpc.QueryResponse;
import com.JingDong.JingDong.grpc.SearchServiceGrpc;
import com.JingDong.JingDong.grpc.QueryRequest;
import com.JingDong.JingDong.mapper.QueryMapper;
import com.JingDong.JingDong.mapper.DetailMapper;
import com.JingDong.JingDong.redisDao.RedisDao;
import com.JingDong.JingDong.util.createJson;
import io.grpc.stub.StreamObserver;
import lombok.extern.slf4j.Slf4j;
import org.lognet.springboot.grpc.GRpcService;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;


/**
 * 接收grpc请求并返回
 */
@GRpcService
@Slf4j
public class SearchServiceImpl extends SearchServiceGrpc.SearchServiceImplBase{
    @Autowired
    private QueryMapper queryMapper;
    @Autowired
    private DetailMapper detailMapper;
    @Autowired
    private RedisDao redisDao;

    private createJson json=new createJson();
    @Override
    public void searchQuery(QueryRequest request, StreamObserver<QueryResponse> responseObserver) {
        //获取请求类型
        String queryType = request.getQueryType();
        //获取请求内容
        String query = request.getQuery();
        String page=request.getPage();
        log.info("Receive queryType = "+queryType+", query = "+query);
        //返回结果初始化
        String jsonInfo = "";

        //分情况

        //分页检索，每次返回二十条商品
        if(queryType.equals("keyword")){
            log.info("Start searching keyword");
            String keyword = query;
            //redis方法，传入一个(query,page)，返回list
            List<String> keys = redisDao.getIDListOnPage(keyword, Integer.parseInt(page));
            log.info("redis weibo list: "+keys.toString());
            //redis方法，传入一个(query)，返回总页数
            String pageCount=""+ redisDao.getPageNumber(keyword);
            log.info("redis page count: "+pageCount);
            //mysql方法
            jsonInfo=json.toJson(pageCount,queryMapper.selectQueryBySkuIds(keys));
        }
        //获取某商品的详情信息
        else if(queryType.equals("detail")){
            log.info("Start searching detail");
            String skuId = query;
            //调用mysql方法，获取相关id的详情
            Detail detail= detailMapper.selectDetailBySkuId(skuId);
//            //调用mysql方法
//            List<Query> a_query=queryMapper.selectQueryBySkuId(skuId);
            jsonInfo=json.toJson((List) detail);
        }
        else {
            log.error("Receive the wrong message!");
        }
        log.info("result: "+jsonInfo);
        //把结果放入response
        QueryResponse response = QueryResponse.newBuilder().setResponse(jsonInfo).build();
        //放入response，传回客户端
        responseObserver.onNext(response);
        //表示此次连接结束
        responseObserver.onCompleted();
    }
}
