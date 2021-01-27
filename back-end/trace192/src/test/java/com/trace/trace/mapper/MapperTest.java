package com.trace.trace.mapper;

import com.trace.trace.Trace192Application;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import javax.annotation.Resource;

/**
 * @author Zenglr
 * @program: trace
 * @packagename: com.trace.trace192.mapper
 * @Description
 * @create 2021-01-26-5:24 下午
 */
@Slf4j
@SpringBootTest(classes= Trace192Application.class)
public class MapperTest {
    @Resource
    private CompetMapper competMapper;

    @Test
    public void selectCompet(){
        String compet_regis_id = "440108400003939";
        System.out.println(competMapper.selectCompetByCompany(compet_regis_id).toString());
    }
}
