package com.trace.trace.service;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.trace.trace.dao.SearchChartsMongoDao;
import com.trace.trace.entity.AgeOrSexDistributionData;
import com.trace.trace.entity.ProvinceIndexData;
import com.trace.trace.mapper.ChartsMapper;
import com.trace.trace.pojo.AgeOrSexDistribution;
import com.trace.trace.pojo.ProvinceIndex;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * @author jbk-xiao
 * @program trace192
 * @packagename com.trace.trace.service
 * @Description
 * @create 2021-02-23-17:05
 */
@Slf4j
@Component
public class SearchCharts {
    final SearchChartsMongoDao searchChartsMongoDao;
    final ChartsMapper chartsMapper;
    final Gson gson = new GsonBuilder().disableHtmlEscaping().create();
    @Autowired
    public SearchCharts(SearchChartsMongoDao searchChartsMongoDao, ChartsMapper chartsMapper) {
        this.searchChartsMongoDao = searchChartsMongoDao;
        this.chartsMapper = chartsMapper;
    }

    public String getPredictData(String companyName) {
        return searchChartsMongoDao.getPredictData(companyName);
    }

    public String getNewsData(String companyName) {
        return searchChartsMongoDao.getNewsData(companyName);
    }

    public String getAgeDistribution(String keyword) {
        long start = System.currentTimeMillis();
        //从mysql中查找年龄分布
        AgeOrSexDistributionData[] ageDistributionData = chartsMapper.selectAgeDistributionData(keyword);
        log.info("mysql selecting ageDistributionData {} uses: {}ms", keyword, System.currentTimeMillis() - start);
        //TODO 是否增加一个列表在redis，表示寻找最新的period？
        AgeOrSexDistribution ageDistribution = new AgeOrSexDistribution(keyword, ageDistributionData[0].getPeriod());
        ageDistribution.setAgeOrSexDistributionData(ageDistributionData);
        //从mysql中查找性别分布
        start = System.currentTimeMillis();
        AgeOrSexDistributionData[] sexDistributionData = chartsMapper.selectSexDistributionData(keyword);
        log.info("mysql selecting sexDistributionData {} uses: {}ms", keyword, System.currentTimeMillis() - start);
        AgeOrSexDistribution sexDistribution = new AgeOrSexDistribution(keyword, sexDistributionData[0].getPeriod());
        sexDistribution.setAgeOrSexDistributionData(sexDistributionData);
        return "{\"age_distribution\":" + gson.toJson(ageDistribution) + ","
               + "\"sex_distribution\":" + gson.toJson(sexDistribution) + "}";
    }

    public String getProvinceIndex(String keyword) {
        long start = System.currentTimeMillis();
        ProvinceIndexData[] provinceIndexData = chartsMapper.selectProvinceIndexData(keyword);
        log.info("mysql selecting {} uses: {}ms", keyword, System.currentTimeMillis() - start);
        ProvinceIndex provinceIndex = new ProvinceIndex(keyword, provinceIndexData[0].getPeriod());
        provinceIndex.setProvinceIndexData(provinceIndexData);
        return gson.toJson(provinceIndex);
    }
}
