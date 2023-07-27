package com.superCode.util;

import org.apache.http.HttpStatus;
import org.springframework.stereotype.Component;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

//SpringMVC 3 ，通过Filter的方式实现。
//对应在web.xml中要加代码
//解决vue访问跨域问题
@Component
public class CORSFilter implements Filter {

    public void doFilter(ServletRequest req, ServletResponse res, FilterChain chain) throws IOException, ServletException {

        try {
            HttpServletResponse response = (HttpServletResponse) res;
            HttpServletRequest request = (HttpServletRequest) req;
            response.setHeader("Access-Control-Allow-Origin", "*");
            response.setHeader("Access-Control-Allow-Methods", "*");
            response.setHeader("Access-Control-Max-Age", "3600");
            response.setHeader("Access-Control-Allow-Headers", "*");
            response.setHeader("content-type", "*");


            //必须加些段 否则 无法上传文件
            if ("OPTIONS".equals(request.getMethod())) {
                response.setStatus(HttpStatus.SC_NO_CONTENT);
                System.out.println("OPTIONS处理直接返回");
                return;
            }
            chain.doFilter(req, res);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void init(FilterConfig filterConfig) {
    }

    public void destroy() {
    }
}