package com.trace.trace.service;

import com.google.gson.Gson;
import com.trace.trace.dao.RedisDao;
import com.trace.trace.entity.*;
import com.trace.trace.grpc.*;
import com.trace.trace.mapper.CompetMapper;
import com.trace.trace.mapper.DetailMapper;
import com.trace.trace.mapper.QueryMapper;
import com.trace.trace.util.createJson;
import io.grpc.stub.StreamObserver;
import lombok.extern.slf4j.Slf4j;
import org.lognet.springboot.grpc.GRpcService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author Zenglr
 * @program: trace
 * @packagename: com.trace.trace.service
 * @Description
 * @create 2021-01-26-5:18 下午
 */
@GRpcService
@Slf4j
@Service
public class InfoServiceImpl extends SearchServiceGrpc.SearchServiceImplBase{
    @Autowired
    private CompetMapper competMapper;
    private Gson gson = new Gson();

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
        log.info("Receive queryType = "+queryType+", query = "+query);
        //返回结果初始化
        String jsonInfo = "";

        //分情况

        //分页检索，每次返回二十条商品
        if(queryType.equals("keyword")){
            log.info("Start searching keyword");
            String keyword = query;
            String page = request.getPage();
            //redis方法，传入一个(query,page)，返回list
            List<String> keys = redisDao.getIDListOnPage(keyword, Integer.parseInt(page));
            log.info("redis weibo list: "+keys.toString());
            //redis方法，传入一个(query)，返回总页数
            String pageCount = ""+ redisDao.getPageNumber(keyword);
            log.info("redis page count: "+pageCount);
            //mysql方法
            jsonInfo = json.toJson(pageCount,queryMapper.selectQueryBySkuIds(keys));
            log.info("result: "+jsonInfo);
        }
        //获取某商品的详情信息
        else if(queryType.equals("detail")){
            log.info("Start searching detail");
            String skuId = query;
            //调用mysql方法，获取相关id的详情
            Detail detail= detailMapper.selectDetailBySkuId(skuId);
//            //调用mysql方法
//            List<Query> a_query=queryMapper.selectQueryBySkuId(skuId);
            jsonInfo = json.toJson((List) detail);
            log.info("result: "+jsonInfo);
        }
//        竞品模块
        else if(queryType.equals("compet")){
            String regis_id = request.getQuery();
            log.info("Receive regis_id = "+regis_id);
            List<Compet_geo> compets= competMapper.selectCompetByCompany(regis_id);
            CompanyInfo companyInfo = competMapper.selectCompanyBasicInfo(regis_id);
            List<JDdetail> jDdetails = competMapper.selectCompetDetails(regis_id);
            JDdetail jDdetail = competMapper.selectMainDetail(regis_id);
            AllCompetinfo allinfo = new AllCompetinfo();
            allinfo.setCompanyInfo(companyInfo);
            allinfo.setCompet_geoList(compets);
            allinfo.setJdetail(jDdetail);
            allinfo.setCompet_jdetails(jDdetails);
            jsonInfo = gson.toJson(allinfo);
            log.info(jsonInfo);
        }
        else {
            log.error("Receive the wrong message!");
        }
        //把结果放入response
        QueryResponse response = QueryResponse.newBuilder().setResponse(jsonInfo).build();
        //放入response，传回客户端
        responseObserver.onNext(response);
        //表示此次连接结束
        responseObserver.onCompleted();
    }


//    /**
//     * 竞品模块
//     * @param request
//     * @param responseObserver
//     */
//    @Override
//    public void searchCompet(CompetRequest request, StreamObserver<CompetResponse> responseObserver) {
//        String regis_id = request.getRegisId();
//        log.info("Receive regis_id = "+regis_id);
//        List<Compet_geo> compets= competMapper.selectCompetByCompany(regis_id);
//        CompanyInfo companyInfo = competMapper.selectCompanyBasicInfo(regis_id);
//        List<JDdetail> jDdetails = competMapper.selectCompetDetails(regis_id);
//        JDdetail jDdetail = competMapper.selectMainDetail(regis_id);
//        AllCompetinfo allinfo = new AllCompetinfo();
//        allinfo.setCompanyInfo(companyInfo);
//        allinfo.setCompet_geoList(compets);
//        allinfo.setJdetail(jDdetail);
//        allinfo.setCompet_jdetails(jDdetails);
//        String all_info=gson.toJson(allinfo);
//        log.info(all_info);
//        //把结果放入response
//        CompetResponse response = CompetResponse.newBuilder().setResponse(all_info).build();
//        //放入response，传回客户端
//        responseObserver.onNext(response);
//        //表示此次连接结束
//        responseObserver.onCompleted();
//    }
}
