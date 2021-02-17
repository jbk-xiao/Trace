package com.trace.trace.dao;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

/**
 * @author jbk-xiao
 * @program trace
 * @packagename com.trace.trace.dao
 * @Description
 * @create 2021-02-03-17:30
 */
@SpringBootTest("FabricDao.java")
class FabricDaoTest {

    @Autowired
    FabricDao fabricDao;

    @Test
    public void isModifiedTest() {
        boolean result = fabricDao.isModified("05-20210202212618.mkv");
        Assertions.assertTrue(result);
    }

    @Test
    public void addProcess() {
        String result = fabricDao.addProcess("123456", "", "", "");
        System.out.println(result);
    }


    @Test
    public void addProcedure() {
        String result = fabricDao.addProcedure("123456", "", "");
        System.out.println(result);
    }
}