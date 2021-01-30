package com.trace.trace;

import com.google.gson.Gson;
import com.trace.trace.entity.Allinfo;
import com.trace.trace.entity.CompanyInfo;
import com.trace.trace.entity.Compet_geo;
import com.trace.trace.entity.JDdetail;
import com.trace.trace.mapper.CompetMapper;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import javax.annotation.Resource;
import java.util.List;

@SpringBootTest
class Trace192ApplicationTests {
    @Resource
    private CompetMapper competMapper;
    private Gson gson = new Gson();

    @Test
    void contextLoads() {
        String regis_id = "520102000400793";
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
        System.out.println(all_info);
    }

}
