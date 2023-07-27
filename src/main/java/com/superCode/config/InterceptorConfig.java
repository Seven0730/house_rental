package com.superCode.config;

import com.superCode.interceptor.AdminLoginInterceptor;
import com.superCode.interceptor.WebLoginInterceptor;
import com.superCode.interceptor.WebLoginInterceptor_2;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.PathMatchConfigurer;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;


//拦截器

@Configuration
public class InterceptorConfig implements WebMvcConfigurer {

    //注入项目的名称
    @Value("${server.servlet.context-path}")
    private String context_path;


    /*此操作是将拦截器注册为bean也交给spring管理,这样拦截器内部注入其他的bean对象也就可以被spring识别了
     * 否则HandlerInterceptor LoginInterceptor中无法调用其他service*/
    @Bean
    AdminLoginInterceptor adminLoginInterceptor() {
        return new AdminLoginInterceptor();
    }
    @Bean
    WebLoginInterceptor webLoginInterceptor() {
        return new WebLoginInterceptor();
    }
    @Bean
    WebLoginInterceptor_2 webLoginInterceptor_2() {
        return new WebLoginInterceptor_2();
    }

    @Override
    public void addInterceptors(InterceptorRegistry registry) {

        //System.out.println(context_path);


        //M do后辍
        //WEB action后辍


        //先将静态资源排除
        //后台访问拦截器
        registry.addInterceptor(adminLoginInterceptor())
                .excludePathPatterns(
                        "/assets/**"
                        , "/upfiles/**"
                        , "/admin/login/login.action"
                        , "/admin/login/exit.action"
                        , "/admin/users/getById.action"
                )//排除的连接
                .addPathPatterns("/admin/**")//未登录所有连接不可以访问

        ;


        //前台访问拦截器  普通 用户
        registry.addInterceptor(webLoginInterceptor())

                .addPathPatterns(

                        "/web/contract_info.action",


                        "/web/preOrders.action",
                        "/web/addOrders.action",
                        "/web/myOrders.action",
                        "/web/cancel.action",
                        "/web/prePwd.action",
                        "/web/editpwd.action",
                        "/web/preInfo.action",
                        "/web/editinfo.action"

                )//未登录所有连接不可以访问

        ;
        //前台访问拦截器  房东用户
        registry.addInterceptor(webLoginInterceptor_2())

                .addPathPatterns(
                        "/web/getList.action",//信息列表 需要登录访问的连接-->
                        "/web/add.action",//信息发布 需要登录访问的连接-->
                        "/webarticle/add.action",//信息发布 需要登录访问的连接-->
                        "/webarticle/getAll.action",//信息发布 需要登录访问的连接-->



                        "/web/createContract_info.action",//需要登录访问的连接-->
                        "/web/addContract_info.action",//需要登录访问的连接-->
                        "/web/deleteContract_info.action",//需要登录访问的连接-->
                        "/web/updateContract_info.action",//需要登录访问的连接-->
                        "/web/getListContract_info.action",//需要登录访问的连接-->


                        "/web/getLandlordOrders.action",
                        "/web/over.action",
                        "/web/prePwd_2.action",
                        "/web/editpwd_2.action",
                        "/web/preInfo_2.action",
                        "/web/editinfo_2.action"
                )//未登录所有连接不可以访问

        ;

    }

    /**
     * -设置url后缀模式匹配规则
     * -该设置匹配所有的后缀，使用.do或.do都可以
     */
    @Override
    public void configurePathMatch(PathMatchConfigurer configurer) {
        configurer.setUseSuffixPatternMatch(true)    //设置是否是后缀模式匹配,即:/test.*
                .setUseTrailingSlashMatch(true);    //设置是否自动后缀路径模式匹配,即：/test/
    }
}
