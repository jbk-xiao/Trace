package com.trace.trace.service;

import com.google.gson.Gson;
import com.trace.trace.entity.AllCompetinfo;
import com.trace.trace.entity.CompanyInfo;
import com.trace.trace.entity.Compet_geo;
import com.trace.trace.entity.JDdetail;
import com.trace.trace.grpc.CompetRequest;
import com.trace.trace.grpc.QueryRequest;
import com.trace.trace.grpc.QueryResponse;
import com.trace.trace.grpc.SearchServiceGrpc;
import com.trace.trace.mapper.CompetMapper;
import io.grpc.stub.StreamObserver;
import lombok.extern.slf4j.Slf4j;
import org.lognet.springboot.grpc.GRpcService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author Zenglr
 * @program: FoXiShengCun
 * @packagename: com.trace.trace.service
 * @Description 竞品模块
 * @create 2021-02-07-7:42 下午
 */
@Slf4j
@Component
public class SearchCompet {
    private final Gson gson = new Gson();

    @Autowired
    private CompetMapper competMapper;

    public String searchCompetBasic(String regis_id) {
        log.info("Receive regis_id:" + regis_id);
        List<Compet_geo> compets = competMapper.selectCompetByCompany(regis_id);
        CompanyInfo companyInfo = competMapper.selectCompanyBasicInfo(regis_id);
        List<JDdetail> jDdetails = competMapper.selectCompetDetails(regis_id);
        JDdetail jDdetail = competMapper.selectMainDetail(regis_id);
        AllCompetinfo allinfo = new AllCompetinfo();
        allinfo.setCompanyInfo(companyInfo);
        allinfo.setCompet_geoList(compets);
        allinfo.setJdetail(jDdetail);
        allinfo.setCompet_jdetails(jDdetails);
        String responseInfo = gson.toJson(allinfo);
        log.info("competPart response:" + responseInfo);
        return responseInfo;
    }

}
