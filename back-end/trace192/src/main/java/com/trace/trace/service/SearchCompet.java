package com.trace.trace.service;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.trace.trace.dao.CompetRedisDao;
import com.trace.trace.entity.AllCompetinfo;
import com.trace.trace.entity.Comment_score;
import com.trace.trace.entity.CompanyInfo;
import com.trace.trace.entity.Compet_geo;
import com.trace.trace.entity.JDdetail;
import com.trace.trace.mapper.CompetMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service
public class SearchCompet {
    private final CompetMapper competMapper;
    private final CompetRedisDao competRedisDao;
    private final Gson gson = new GsonBuilder().disableHtmlEscaping().create();  //防止出现字符转换

    @Autowired
    public SearchCompet(CompetMapper competMapper, CompetRedisDao competRedisDao) {
        this.competMapper = competMapper;
        this.competRedisDao = competRedisDao;
    }

    public String searchCompetBasic(String regis_id) {
        log.info("Receive regis_id:" + regis_id);
        String skuId = competRedisDao.getSkuId(regis_id);
        StringBuilder sb = new StringBuilder();
        if (skuId == null) {
            log.info("skuId didn't find");
        } else {
            List<String> competsList = competRedisDao.getCompetRegisId(regis_id);
            List<String> skuIdList = competRedisDao.getCompetSkuId(skuId);
            List<Compet_geo> compets = competMapper.selectCompetByRegisIds(competsList);
            CompanyInfo companyInfo = competMapper.selectCompanyBasicInfo(regis_id);
            JDdetail jDdetail = competMapper.selectInfoBySkuId(skuId);
            List<JDdetail> jDdetails = competMapper.selectCompetBySkuIds(skuIdList);
            skuIdList.add(0, skuId);
            List<Comment_score> comment_scores = competMapper.selectCommentScoreBySkuIds(skuIdList);
            AllCompetinfo allinfo = new AllCompetinfo();
            allinfo.setCompanyInfo(companyInfo);
            allinfo.setCompet_geoList(compets);
            allinfo.setJdetail(jDdetail);
            allinfo.setCompet_jdetails(jDdetails);
            allinfo.setScoreList(comment_scores);
            sb.append(gson.toJson(allinfo));
            sb.deleteCharAt(sb.length() - 1);
            sb.append("}");
            log.info("competPart response");
        }
        return sb.toString();
    }

}
