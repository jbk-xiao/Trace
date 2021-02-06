package com.trace.trace.service;

import com.google.gson.Gson;
import com.google.protobuf.ByteString;
import com.trace.trace.dao.FabricDao;
import com.trace.trace.dao.RedisDao;
import com.trace.trace.dao.ProcessEventDao;
import com.trace.trace.entity.AllCompetinfo;
import com.trace.trace.entity.CompanyInfo;
import com.trace.trace.entity.Compet_geo;
import com.trace.trace.entity.Detail;
import com.trace.trace.entity.JDdetail;
import com.trace.trace.grpc.QueryRequest;
import com.trace.trace.grpc.QueryResponse;
import com.trace.trace.grpc.SearchServiceGrpc;
import com.trace.trace.mapper.CompetMapper;
import com.trace.trace.mapper.DetailMapper;
import com.trace.trace.mapper.QueryMapper;
import com.trace.trace.util.media.FileUtil;
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
public class InfoServiceImpl extends SearchServiceGrpc.SearchServiceImplBase {
    private final Gson gson = new Gson();

    @Autowired
    private CompetMapper competMapper;

    @Autowired
    private QueryMapper queryMapper;

    @Autowired
    private DetailMapper detailMapper;

    @Autowired
    private RedisDao redisDao;

    @Autowired
    private ProcessEventDao processEventDao;

    @Autowired
    private FabricDao fabricDao;

    @Autowired
    private FileUtil fileUtil;

    private final createJson json = new createJson();

    @Override
    public void searchQuery(QueryRequest request, StreamObserver<QueryResponse> responseObserver) {
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

        //分情况

        switch (queryType) {
            case "keyword": {
                //分页检索，每次返回二十条商品
                log.info("Start searching keyword");
                String page = request.getPage();
                //redis方法，传入一个(query,page)，返回list
                List<String> keys = redisDao.getIDListOnPage(query, Integer.parseInt(page));
                log.info("redis weibo list: " + keys.toString());
                //redis方法，传入一个(query)，返回总页数
                String pageCount = "" + redisDao.getPageNumber(query);
                log.info("redis page count: " + pageCount);
                //mysql方法
                jsonInfo = "";
                try {
                    jsonInfo = json.toJson(pageCount, queryMapper.selectQueryBySkuIds(keys));
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
                Detail detail = detailMapper.selectDetailBySkuId(skuId);
                jsonInfo = json.toJson((List) detail);
                log.info("result: " + jsonInfo);
                break;
            case "compet":
                //竞品模块
                String regis_id = request.getQuery();
                log.info("Receive regis_id = " + regis_id);
                List<Compet_geo> compets = competMapper.selectCompetByCompany(regis_id);
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
                break;
            case "video": {
                /*  视频访问  */
                String filename = request.getQuery();
                isString = false;
                log.info("Encoding video '" + filename + "'...");
                mediaData = ByteString.copyFrom(fileUtil.getBytesFromVideo(filename));
                break;
            }
            case "picture": {
                //图片访问
                String filename = request.getQuery();
                isString = false;
                log.info("Encoding picture '" + filename + "'...");
                mediaData = ByteString.copyFrom(fileUtil.getBytesFromPicture(filename));
                break;
            }
            case "event": {
                //生产线“最近动态”列表
                String processName = request.getQuery();
                int page = Integer.parseInt(request.getPage());
                List<Long> timeList = processEventDao.getEventTitleListOnPage(processName, page);
                jsonInfo = gson.toJson(timeList);
                break;
            }
            case "origin": {
                //依据溯源id查询流水线信息
                String originId = request.getQuery();
                log.info("Searching '" + originId + "' in fabric.");
                jsonInfo = fabricDao.getInfoByOriginId(originId);
                log.info("Fabric return: " + jsonInfo);
                break;
            }
            default: {
                log.error("Receive the wrong message!");
                break;
            }
        }
        //把结果放入response
        QueryResponse response = isString
                ? QueryResponse.newBuilder().setResponse(jsonInfo).build()
                : QueryResponse.newBuilder().setResponseMedia(mediaData).build();
        //放入response，传回客户端
        responseObserver.onNext(response);
        //表示此次连接结束
        responseObserver.onCompleted();
    }

}
