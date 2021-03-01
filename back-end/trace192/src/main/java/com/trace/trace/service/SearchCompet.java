package com.trace.trace.service;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.trace.trace.dao.CompetMongoDao;
import com.trace.trace.dao.CompetRedisDao;
import com.trace.trace.entity.AllCompetinfo;
import com.trace.trace.entity.Comment_score;
import com.trace.trace.entity.CompanyInfo;
import com.trace.trace.entity.Compet_geo;
import com.trace.trace.entity.JDdetail;
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
    final CompetMapper competMapper;
    final CompetRedisDao competRedisDao;
    final CompetMongoDao competMongoDao;
    private final Gson gson = new GsonBuilder().disableHtmlEscaping().create();  //防止出现字符转换

    @Autowired
    public SearchCompet(CompetMapper competMapper, CompetRedisDao competRedisDao, CompetMongoDao competMongoDao) {
        this.competMapper = competMapper;
        this.competRedisDao = competRedisDao;
        this.competMongoDao = competMongoDao;
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
            String comment_statistic = competMongoDao.getCommentStatistic(skuId);
            AllCompetinfo allinfo = new AllCompetinfo();
            allinfo.setCompanyInfo(companyInfo);
            allinfo.setCompet_geoList(compets);
            allinfo.setJdetail(jDdetail);
            allinfo.setCompet_jdetails(jDdetails);
            allinfo.setScoreList(comment_scores);
            sb.append(gson.toJson(allinfo));
            sb.deleteCharAt(sb.length() - 1);
            sb.append(",");
            sb.append(comment_statistic);
            log.info("competPart response");
        }
        return sb.toString();
    }

}
