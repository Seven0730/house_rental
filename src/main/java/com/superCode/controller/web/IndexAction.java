package com.superCode.controller.web;

import com.superCode.entity.*;
import com.superCode.service.*;
import com.superCode.util.ResultUtil;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

//定义为控制器
@Controller
// 设置路径
@RequestMapping("/web")
public class IndexAction extends BaseWebAction {


    @Resource
    private BannerService bannerService;
    // 注入AdminService 并getter setter
    @Resource
    private ArticleService articleService;

    @Resource
    private OrdersService ordersService;
   @Resource
    private CategoryService categoryService;
    @Resource
    private Housing_infoService housing_infoService;

    // 首页显示
    @RequestMapping("index.action")
    @ResponseBody
    public ResultUtil index(Map<String, Object> map) {
        return ResultUtil.ok().message("后端服务器启动成功");
    }

    //获取新闻分类
    @RequestMapping("bannerList.action")
    @ResponseBody
    public ResultUtil bannerList(Map<String, Object> map) {
        Map<String, Object> parameter = new HashMap<String, Object>();
        List<Banner> bannerList = bannerService.getAll(parameter);

        map.put("bannerList", bannerList);//分类列表





        return ResultUtil.ok().data(map);//给前端数据


    }
    //获取区域
    @RequestMapping("categoryList.action")
    @ResponseBody
    public ResultUtil categoryList(Map<String, Object> map) {
        Map<String, Object> parameter = new HashMap<String, Object>();
        List<Category> categoryList = categoryService.getAll(parameter);

        map.put("categoryList", categoryList);//区域列表





        return ResultUtil.ok().data(map);//给前端数据


    }


    // 首页显示
    @RequestMapping("web_default.action")
    @ResponseBody
    public ResultUtil web_default(Map<String, Object> map) {
        Map<String, Object> parameter = new HashMap<String, Object>();
        List<Banner> bannerList = bannerService.getAll(parameter);
        List<Banner> frontList = new ArrayList<Banner>();
        for (Banner banner : bannerList) {
            List<Article> articleList = articleService.getByBanner(banner.getBannerid());
            banner.setArticleList(articleList);
            frontList.add(banner);
        }
        map.put("frontList", frontList);//按分类获取列表,用于首页显示所有分类的条目//按分类获取列表,用于首页显示所有分类的条目

        List<Article> favList = articleService.getFlv();
        System.out.println(favList.size());

        map.put("favList", favList);//获取轮播的内容.用于首页显示
        List<Article> topList = articleService.getTop();
        System.out.println(favList.size());

        map.put("topList", topList);//获取置顶的内容,用于首页显示


        //获取首页房源信息
        Map<String, Object> parameter11 = new HashMap<String, Object>();
        parameter11.put("limit", 4);//搜索参数
        parameter11.put("start",0);//搜索参数
        parameter11.put("iscooled",  0);//审核通过的
        List<Housing_info> housing_infoList = housing_infoService.getAll(parameter11);

        map.put("housing_infoList", housing_infoList);


        return ResultUtil.ok().data(map);//给前端数据
    }


}
