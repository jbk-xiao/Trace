package com.trace.trace.controller;

import com.trace.trace.util.SaveUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;

@Slf4j
@CrossOrigin("*")
@RestController
public class SaveMediaController {

    private final SaveUtil saveUtil;

    @Autowired
    public SaveMediaController(SaveUtil saveUtil) {
        this.saveUtil = saveUtil;
    }

    @GetMapping("/saveName/{filename}")
    public void saveMedia(@PathVariable("filename") String filename, HttpServletRequest request) {
        long start = System.currentTimeMillis();
        String host = request.getRemoteHost();
        int port = request.getRemotePort();
        log.info("receive file: " + host + ":" + port + filename);
        try {
            saveUtil.saveName(filename);
        } catch (Exception e) {
            log.error(e.toString());
        }
        long end = System.currentTimeMillis();
        log.info("Retrieval time: " + (end - start) + "ms");
    }
}
