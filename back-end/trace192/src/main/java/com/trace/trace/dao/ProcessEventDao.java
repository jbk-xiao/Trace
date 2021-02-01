package com.trace.trace.dao;

import com.trace.trace.util.JedisUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import redis.clients.jedis.Jedis;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

/**
 * @author jbk-xiao
 * @program trace
 * @packagename com.trace.trace.dao.redisDao
 * @Description
 * @create 2021-02-01-15:06
 */
@Slf4j
@Component
public class ProcessEventDao {
    @Autowired
    JedisUtil jedisUtil;

    @Value("${media.picture.database}")
    private int database;

    private static final int RECORD_PER_PAGE = 15;

    /**
     * Return a list of time when the videos are created.
     * @param processName event in which process.
     * @param page in which page, 15 records per page.
     * @return a list of Long Integer. (1970.1.1)
     */
    public List<Long> getEventTitleListOnPage(String processName, int page) {
        Jedis jedis = jedisUtil.getClient();
        jedis.select(database);
        List<String> list = jedis.lrange(processName, RECORD_PER_PAGE * (page - 1), RECORD_PER_PAGE * page - 1);
        List<Long> result = new ArrayList<>();
        for (String filename: list) {
            result.add(filenameToDate(filename));
        }
        return result;
    }

    /**
     * Receive a filename and find the date.
     * @param filename picture name.
     * @return current-1970.1.1
     */
    private long filenameToDate(String filename) {
        Calendar date = Calendar.getInstance();
        String timeString = filename.split("-")[1];
        int year = Integer.parseInt(timeString.substring(0, 4));
        int month = Integer.parseInt(timeString.substring(4, 6)) - 1;
        int day = Integer.parseInt(timeString.substring(6, 8));
        int hour = Integer.parseInt(timeString.substring(8, 10));
        int minute = Integer.parseInt(timeString.substring(10, 12));
        int second = Integer.parseInt(timeString.substring(12, 14));
        date.set(year, month, day, hour, minute, second);
        log.info("Get date: " + new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(date.getTime()) + " in " + timeString);
        return date.getTimeInMillis();
    }
}
