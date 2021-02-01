package com.trace.trace.controller;

import com.trace.trace.util.media.SaveUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;

/**
 * @author jbk-xiao
 * @program trace
 * @packagename com.trace.trace.controller
 * @Description save media's name from raspberry
 * @create 2021-01-31-19:42
 */
@Slf4j
@CrossOrigin("*")
@RestController
public class SaveMediaController {

    @Autowired
    SaveUtil saveUtil;

    @GetMapping("/savename/{filename}")
    public void saveMedia(@PathVariable("filename") String filename, HttpServletRequest request) {
        String host = request.getRemoteHost();
        int port = request.getRemotePort();
        log.info("receive file: " + host + ":" + port + filename);
        try {
            saveUtil.saveName(filename);
        } catch (Exception e) {
            log.error(e.toString());
        }
    }
}
