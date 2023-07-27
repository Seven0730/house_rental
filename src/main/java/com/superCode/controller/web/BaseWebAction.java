package com.superCode.controller.web;

import com.superCode.service.ArticleService;
import com.superCode.service.BannerService;
import com.superCode.service.OrdersService;
import com.superCode.service.Housing_infoService;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.Map;

/**
 * Action 基类
 */
@Controller
public class BaseWebAction {

    /* 日志 */
    protected final Log log = LogFactory.getLog(getClass());
    @Resource
    private BannerService bannerService;
    // 注入AdminService 并getter setter
    @Resource
    private ArticleService articleService;
    @Resource
    private OrdersService ordersService;
    @Resource
    private Housing_infoService housing_infoService;

    /* 获取基本环境 */
    public Map<String, String[]> getParameters() {// 封装为Map的requestParameters
        ServletRequestAttributes attrs = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        return attrs.getRequest().getParameterMap();
    }

    public HttpServletRequest getRequest() {
        ServletRequestAttributes attrs = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        return attrs.getRequest();
    }

    public HttpSession getSession() {
        HttpSession session = null;
        try {
            session = this.getRequest().getSession();
        } catch (Exception e) {
        }
        return session;
    }


}
