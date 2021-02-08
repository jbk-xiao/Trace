package com.trace.trace.controller;

import com.google.protobuf.ByteString;
import com.trace.trace.grpc.QueryRequest;
import com.trace.trace.grpc.SearchServiceGrpc;
import com.trace.trace.grpc.TraceResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.BufferedInputStream;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.OutputStream;

/**
 * @author jbk-xiao
 * @program trace
 * @packagename com.trace.trace.controller
 * @Description
 * @create 2021-02-01-11:04
 */
@Slf4j
@CrossOrigin("*")
@RestController
public class MediaController {

    /**
     * 从容器中获取调用GRpc stub
     */
    @Autowired
    SearchServiceGrpc.SearchServiceBlockingStub searchServiceBlockingStub;

    /**
     * 处理远程视频请求和服务器视频返回。
     *
     * @param filename request filename.
     * @param response remote httpServletResponse.
     * @param request  remote httpServletRequest.
     * @throws IOException if IOException occurs.
     */
    @RequestMapping(value = "/getVideo/{filename:.+}", method = RequestMethod.GET)
    public void getVideo(@PathVariable("filename") String filename,
                         HttpServletResponse response, HttpServletRequest request) throws IOException {
        log.info("Receive video request:" + filename);
        long start = System.currentTimeMillis();
        TraceResponse traceResponse = this.searchServiceBlockingStub
                .searchTrace(QueryRequest.newBuilder()
                        .setQuery(filename).setQueryType("video").build());
        ByteString responseBytes = traceResponse.getResponseMedia();
        byte[] data = responseBytes.toByteArray();
        assert data != null;

        String range = request.getHeader("range");
        String browser = request.getHeader("User-Agent");
        if (browser.contains("Firefox") || browser.contains("Chrome")) {
            response.setContentType("video/mp4");
            response.setContentLength(data.length);
            response.setHeader("Content-Range", range + (data.length - 1));
            response.setHeader("Accept-Ranges", "bytes");
            response.setHeader("Etag", "W/\"9767057-1323779115364\"");
            byte[] content = new byte[1024];
            BufferedInputStream is = new BufferedInputStream(new ByteArrayInputStream(data));
            OutputStream os = response.getOutputStream();
            while (is.read(content) != -1) {
                os.write(content);
            }
            is.close();
            os.close();
        } else if (browser.contains("MSIE")) {
            // IE9以上浏览器。（假的，IE暂时搞不定）
            response.setContentType("video/mp4");
            response.setContentLength(data.length);
            response.setHeader("Content-Range", range + (data.length - 1));
            response.setHeader("Accept-Ranges", "bytes");
            response.setHeader("Etag", "W/\"9767057-1323779115364\"");
            byte[] content = new byte[1024];
            BufferedInputStream is = new BufferedInputStream(new ByteArrayInputStream(data));
            OutputStream os = response.getOutputStream();
            while (is.read(content) != -1) {
                os.write(content);
            }
            is.close();
            os.close();
        } else if (browser.contains("CoreMedia")) {
            // 其他浏览器
            response.setContentType("video/mp4");
            byte[] content = new byte[1024];
            BufferedInputStream is = new BufferedInputStream(new ByteArrayInputStream(data));
            OutputStream os = response.getOutputStream();
            while (is.read(content) != -1) {
                os.write(content);
            }
            is.close();
            os.close();
        }
        long end = System.currentTimeMillis();
        log.info("Retrieval time: " + (end - start));
    }

    /**
     * 处理远程图片请求和服务器图片返回。
     *
     * @param filename request filename.
     * @param response remote httpServletResponse.
     * @param request  remote httpServletRequest.
     * @throws IOException if IOException occurs.
     */
    @RequestMapping(value = "/getPicture/{filename:.+}", method = RequestMethod.GET)
    public void getPicture(@PathVariable("filename") String filename,
                           HttpServletResponse response, HttpServletRequest request) throws IOException {
        log.info("Receive picture request: " + filename);
        long start = System.currentTimeMillis();

        TraceResponse traceResponse = this.searchServiceBlockingStub
                .searchTrace(QueryRequest.newBuilder()
                        .setQuery(filename).setQueryType("picture").build());
        ByteString responseBytes = traceResponse.getResponseMedia();
        byte[] data = responseBytes.toByteArray();
        assert data != null;
        String range = request.getHeader("range");
        response.setContentType("image/jpeg");
        response.setContentLength(data.length);
        response.setHeader("Content-Range", range + (data.length - 1));
        response.setHeader("Accept-Ranges", "bytes");
        response.setHeader("Etag", "W/\"9767057-1323779115364\"");
        byte[] content = new byte[1024];
        BufferedInputStream is = new BufferedInputStream(new ByteArrayInputStream(data));
        OutputStream os = response.getOutputStream();
        while (is.read(content) != -1) {
            os.write(content);
        }
        is.close();
        os.close();
        long end = System.currentTimeMillis();
        log.info("Retrieval time: " + (end - start));
    }

    @RequestMapping(value = "/getRecentEvent/{process_name}/{page}", method = RequestMethod.GET)
    public String getRecentEventList(@PathVariable("process_name") String processName,
                                     @PathVariable("page") String page) {
        log.info("Receive recent event request about " + processName + " in " + page);
        long start = System.currentTimeMillis();
        TraceResponse traceResponse = searchServiceBlockingStub
                .searchTrace(QueryRequest.newBuilder()
                        .setQueryType("event").setQuery(processName).setPage(page)
                        .build());
        String response = traceResponse.getResponse();
        long end = System.currentTimeMillis();
        log.info("Retrieval time: " + (end - start));
        log.info("Search result: " + response);
        return response;
    }
}
