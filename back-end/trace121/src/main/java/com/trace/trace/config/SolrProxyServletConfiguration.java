package com.trace.trace.config;

import org.mitre.dsmiley.httpproxy.ProxyServlet;
import org.springframework.boot.web.servlet.ServletRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.servlet.Servlet;

@Configuration
public class SolrProxyServletConfiguration {
    @Bean
    public ServletRegistrationBean<Servlet> servletRegistrationBean(){
        ProxyServlet proxyServlet = new ProxyServlet();
        ServletRegistrationBean<Servlet> servletRegistrationBean = new ServletRegistrationBean<>(
                proxyServlet, "/monitor/*");
        servletRegistrationBean.setName("monitor");
        servletRegistrationBean.addInitParameter("targetUri", "http://222.200.184.74:1111");
        servletRegistrationBean.addInitParameter(ProxyServlet.P_LOG, "ture");
        return servletRegistrationBean;
    }
}
