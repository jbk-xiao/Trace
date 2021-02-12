package com.trace.trace.service;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.trace.trace.dao.CompetRedisDao;
import com.trace.trace.entity.*;
import com.trace.trace.mapper.CompetMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

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
    private final Gson gson = new GsonBuilder().disableHtmlEscaping().create();  //防止出现字符转换

    @Autowired
    CompetMapper competMapper;
    @Autowired
    CompetRedisDao competRedisDao;

    public String searchCompetBasic(String regis_id) {
        log.info("Receive regis_id:" + regis_id);
        String skuId = competRedisDao.getSkuId(regis_id);
        String responseInfo = null;
        if(skuId == null) {
            log.info("skuId didn't find");
        }else{
            List<String> competsList = competRedisDao.getCompetRegisId(regis_id);
            List<String> skuIdList = competRedisDao.getCompetSkuId(skuId);
            List<Compet_geo> compets = competMapper.selectCompetByRegisIds(competsList);
            CompanyInfo companyInfo = competMapper.selectCompanyBasicInfo(regis_id);
            JDdetail jDdetail = competMapper.selectInfoBySkuId(skuId);
            List<JDdetail> jDdetails = competMapper.selectCompetBySkuIds(skuIdList);
            skuIdList.add(0,skuId);
            List<Comment_score> comment_scores = competMapper.selectCommentScoreBySkuIds(skuIdList);
            AllCompetinfo allinfo = new AllCompetinfo();
            allinfo.setCompanyInfo(companyInfo);
            allinfo.setCompet_geoList(compets);
            allinfo.setJdetail(jDdetail);
            allinfo.setCompet_jdetails(jDdetails);
            allinfo.setScoreList(comment_scores);
            responseInfo = gson.toJson(allinfo);
            log.info("competPart response:" + responseInfo);
        }
        return responseInfo;
    }

}
