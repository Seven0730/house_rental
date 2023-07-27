package com.superCode.interceptor;

import com.alibaba.fastjson.JSONObject;
import com.superCode.entity.JwtEntityWeb;
import com.superCode.entity.Landlord;
import com.superCode.service.LandlordService;
import com.superCode.util.JwtUtil;
import com.superCode.util.ResultUtil;
import io.jsonwebtoken.Claims;
import org.apache.commons.lang.StringUtils;
import org.apache.http.HttpStatus;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Date;

/**
 * 登录拦截器,房东用户 未登录的跳转到登录页
 *
 * @author superman
 */
public class WebLoginInterceptor_2 implements HandlerInterceptor {

    @Override
    public void afterCompletion(HttpServletRequest arg0,
                                HttpServletResponse arg1, Object arg2, Exception arg3)
            throws Exception {
        // TODO Auto-generated method stub

    }

    @Override
    public void postHandle(HttpServletRequest arg0, HttpServletResponse arg1,
                           Object arg2, ModelAndView arg3) throws Exception {
        // TODO Auto-generated method stub

    }

    /**
     * 访问 controller 前被调用
     */
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response,
                             Object arg2) throws Exception {
        // TODO Auto-generated method stub
        String requestURI = request.getRequestURI();
        //config.springmvc中配置需要登录才可以访问的页面地址

        String token = request.getHeader("token");
        if (StringUtils.isBlank(token)) {
            token = request.getParameter("token");
        }
        // 验证 token 是否过期（根据时间戳比较）
        if (JwtUtil.checkToken(token)) {

            token = refreshToken(token, response, request);// 刷新 token(过期时间小于 5 分钟，会重新创建 token，否则 使用原 token)

            return true;
        }
        // 认证失败，返回数据，并返回 401 状态码
        returnJsonData(response);
        System.out.println("链接" + requestURI + "进入拦截器！");
        return false;
    }


    /**
     * 返回 json 格式的数据
     */
    public void returnJsonData(HttpServletResponse response) {
        PrintWriter pw = null;
        response.setCharacterEncoding("UTF-8");
        response.setContentType("application/json; charset=utf-8");
        try {
            pw = response.getWriter();
            // 返回 code 为 401，表示 token 失效。
            pw.print(com.alibaba.fastjson.JSONObject.toJSONString(ResultUtil.error().message("token 登录失效或过期").code(HttpStatus.SC_UNAUTHORIZED)));
        } catch (IOException e) {

            System.out.println(e.getMessage());
            throw new RuntimeException(e);
        }
    }


    @Resource
    private LandlordService landlordService;

    /**
     * 刷新 token
     *
     * @param token 旧 token 值
     * @return 新 token
     */
    public String refreshToken(String token, HttpServletResponse response, HttpServletRequest request) {
        // 获取 token 中的数据
        Claims claims = JwtUtil.getTokenBody(token);
        // 获取 token 过期时间戳
        Long expire = claims.getExpiration().getTime();

        // 获取当前时间戳
        Long currentTime = new Date().getTime();


        //从token中获取自定义的用户信息

        JwtEntityWeb jwtEntityWeb = new JwtEntityWeb();

        Object map = claims.get("data");
        try {

            com.alibaba.fastjson.JSONObject jsonObject = (com.alibaba.fastjson.JSONObject) com.alibaba.fastjson.JSONObject.toJSON(map);//先转为JSONOBJECT
            jwtEntityWeb = (JwtEntityWeb) JSONObject.toJavaObject(jsonObject, JwtEntityWeb.class);//再转为jwtmodel
            if (jwtEntityWeb.getRole() == 2) {

                //重新设定session
                HttpSession session = request.getSession(); //request是HttpServletRequest类型的


                Landlord landlord = landlordService.getById(jwtEntityWeb.getUsersid());

                session.setAttribute("usersid", landlord.getLandlordid());
                session.setAttribute("landlord", landlord);
                session.setAttribute("role", 2);

            }
        } catch (Exception e) {
            e.printStackTrace();
        }


        // 若当前 token 过期时间 小于 5 分钟，则 更新 token
        if ((expire - currentTime) / 1000 < 300) {
            // 获取 token 数据
            // 重新生成 token
            token = JwtUtil.getJwtToken(jwtEntityWeb, JwtUtil.DEFAULT_EXPIRE);


            response.setHeader("token", token);
        }
        return token;
    }

}
