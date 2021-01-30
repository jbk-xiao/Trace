package com.trace.trace.service;

import com.google.gson.Gson;
import com.trace.trace.entity.Allinfo;
import com.trace.trace.entity.CompanyInfo;
import com.trace.trace.entity.Compet_geo;
import com.trace.trace.entity.JDdetail;
import com.trace.trace.grpc.CompetRequest;
import com.trace.trace.grpc.CompetResponse;
import com.trace.trace.grpc.SearchServiceGrpc;
import com.trace.trace.mapper.CompetMapper;
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

    /**
     * 竞品模块
     * @param request
     * @param responseObserver
     */
    @Override
    public void searchCompet(CompetRequest request, StreamObserver<CompetResponse> responseObserver) {
        String regis_id = request.getRegisId();
        log.info("Receive regis_id = "+regis_id);
        List<Compet_geo> compets= competMapper.selectCompetByCompany(regis_id);
        CompanyInfo companyInfo = competMapper.selectCompanyBasicInfo(regis_id);
        List<JDdetail> jDdetails = competMapper.selectCompetDetails(regis_id);
        JDdetail jDdetail = competMapper.selectMainDetail(regis_id);
        Allinfo allinfo = new Allinfo();
        allinfo.setCompanyInfo(companyInfo);
        allinfo.setCompet_geoList(compets);
        allinfo.setJdetail(jDdetail);
        allinfo.setCompet_jdetails(jDdetails);
        String all_info=gson.toJson(allinfo);
        log.info(all_info);
        //把结果放入response
        CompetResponse response = CompetResponse.newBuilder().setResponse(all_info).build();
        //放入response，传回客户端
        responseObserver.onNext(response);
        //表示此次连接结束
        responseObserver.onCompleted();
    }
}
