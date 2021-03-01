package com.trace.trace;

import com.google.gson.Gson;
import com.trace.trace.entity.AllCompetinfo;
import com.trace.trace.entity.CompanyInfo;
import com.trace.trace.entity.Compet_geo;
import com.trace.trace.entity.JDdetail;
import com.trace.trace.mapper.CompetMapper;
import com.trace.trace.service.SearchGraph;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import javax.annotation.Resource;
import java.util.List;

@SpringBootTest
class Trace192ApplicationTests {
    @Autowired
    SearchGraph searchGraph;
    @Test
    void contextLoads() {
        System.out.println(searchGraph.searchGraphByBrand("湾仔码头"));
    }

}
