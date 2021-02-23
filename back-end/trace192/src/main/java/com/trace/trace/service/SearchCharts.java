package com.trace.trace.service;

import com.google.gson.Gson;
import com.trace.trace.dao.SearchChartsMongoDao;
import com.trace.trace.entity.AgeDistributionData;
import com.trace.trace.entity.ProvinceIndexData;
import com.trace.trace.mapper.ChartsMapper;
import com.trace.trace.pojo.AgeDistribution;
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
        AgeDistributionData[] ageDistributionData = chartsMapper.selectAgeDistributionData(keyword);
        log.info("mysql selecting {} uses: {}ms", keyword, System.currentTimeMillis() - start);
        //TODO 是否增加一个列表在redis，表示寻找最新的period？
        AgeDistribution ageDistribution = new AgeDistribution(keyword);
        ageDistribution.setAgeDistributionData(ageDistributionData);
        return "{\"age_distribution\":" + new Gson().toJson(ageDistribution) + "}";
    }

    public String getProvinceIndex(String keyword) {
        long start = System.currentTimeMillis();
        ProvinceIndexData[] provinceIndexData = chartsMapper.selectProvinceIndexData(keyword);
        log.info("mysql selecting {} uses: {}ms", keyword, System.currentTimeMillis() - start);
        ProvinceIndex provinceIndex = new ProvinceIndex(keyword);
        provinceIndex.setProvinceIndexData(provinceIndexData);
        return new Gson().toJson(provinceIndex);
    }
}
