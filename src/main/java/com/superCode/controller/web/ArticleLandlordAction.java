package com.superCode.controller.web;

import com.superCode.controller.admin.BaseAdminAction;
import com.superCode.entity.Article;
import com.superCode.entity.Banner;
import com.superCode.entity.Landlord;
import com.superCode.service.ArticleService;
import com.superCode.service.BannerService;
import com.superCode.service.LandlordService;
import com.superCode.util.ResultUtil;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

//定义为控制器
@Controller
// 设置路径
@RequestMapping("/webarticle")
public class ArticleLandlordAction extends BaseWebAction {
    // 注入AdminService 并getter setter
    @Resource
    private ArticleService articleService;
    @Resource
    private BannerService bannerService;
    @Resource
    private LandlordService landlordService;

    // 准备添加数据
    @RequestMapping("create.action")
    @ResponseBody
    public ResultUtil create(Map<String, Object> map) {

        Map<String, Object> parameter = new HashMap<String, Object>();
        List<Banner> bannerList = bannerService.getAll(parameter);
        map.put("bannerList", bannerList);
        return ResultUtil.ok().data(map);//给前端数据
    }

    // 添加数据
    @RequestMapping("add.action")
    @ResponseBody
    public ResultUtil add(Article article) {
        article.setHits("0");
        String usersid = (String) this.getSession().getAttribute("usersid");
        article.setLandlordid(usersid);//当前登录的房东ID
        article.setAddtime(new Date());
        articleService.insert(article);
        return ResultUtil.ok();//返回成功提示
    }

    // 通过主键删除数据
    @RequestMapping("delete.action")
    @ResponseBody
    public ResultUtil delete(String id) {
        articleService.delete(id);
        return ResultUtil.ok();//返回成功提示
    }

    // 更新数据
    @RequestMapping("update.action")
    @ResponseBody
    public ResultUtil update(Article article) {
        articleService.update(article);
        return ResultUtil.ok();//返回成功提示
    }

    // 显示全部数据
    @RequestMapping("getAll.action")
    @ResponseBody
    public ResultUtil getAll(HttpServletRequest request, Map<String, Object> map) {

        Map<String, Object> parameter = new HashMap<String, Object>();
        String keyword = request.getParameter("keyword");//搜索关键词
        if (keyword != null && keyword != "") parameter.put("keyword", keyword);//搜索参数


        String usersid = (String) this.getSession().getAttribute("usersid");
        Landlord landlord = landlordService.getById(usersid);
        parameter.put("landlordid", landlord.getLandlordid());//只获取当前登录的房东

        int rowCountTotal = articleService.getRowSize(parameter);//获取总数
        int pageSize = 10;// 分页大小
        int pageNumber = 1; //设定页面参数

        if (request.getParameter("pageNumber") != null && request.getParameter("pageNumber") != "") {
            pageNumber = Integer.parseInt(request.getParameter("pageNumber"));//当前页
        }
        parameter.put("limit", pageSize);//搜索参数
        parameter.put("start", (pageNumber - 1) * pageSize);//搜索参数
        List<Article> articleList = articleService.getAll(parameter);


        //设定页面参数
        Map<String, Object> pager = new HashMap<String, Object>();
        int pageCount = 1;// 总页数
        // 计算总页数
        if (rowCountTotal % pageSize == 0) {
            pageCount = rowCountTotal / pageSize;
        } else {
            pageCount = rowCountTotal / pageSize + 1;
        }
        pager.put("pageNumber", pageNumber);//当前页
        pager.put("pageCount", pageCount);//总页数
        pager.put("rowCountTotal", rowCountTotal);//记录总条数

        map.put("keyword", keyword);
        map.put("pager", pager);
        map.put("articleList", articleList);
        return ResultUtil.ok().data(map);//给前端数据
    }

    // 按条件查询数据 (模糊查询)----备用
/*
    @RequestMapping("queryArticleByCond.action")
    @ResponseBody
    public ResultUtil queryArticleByCond(String cond, String name, Map<String, Object> map) {
        List<Article> articleList = new ArrayList<Article>();
        Article article = new Article();
        if (cond != null) {
            if ("title".equals(cond)) {
                article.setTitle(name);
                articleList = articleService.getArticleByLike(article);
            }
            if ("image".equals(cond)) {
                article.setImage(name);
                articleList = articleService.getArticleByLike(article);
            }
            if ("bannerid".equals(cond)) {
                article.setBannerid(name);
                articleList = articleService.getArticleByLike(article);
            }
            if ("istop".equals(cond)) {
                article.setIstop(name);
                articleList = articleService.getArticleByLike(article);
            }
            if ("isflv".equals(cond)) {
                article.setIsflv(name);
                articleList = articleService.getArticleByLike(article);
            }
            if ("contents".equals(cond)) {
                article.setContents(name);
                articleList = articleService.getArticleByLike(article);
            }
            if ("addtime".equals(cond)) {
                article.setAddtime(name);
                articleList = articleService.getArticleByLike(article);
            }
            if ("hits".equals(cond)) {
                article.setHits(name);
                articleList = articleService.getArticleByLike(article);
            }
        }
        map.put("articleList", articleList);
        return "admin/query";
    }
*/

    // 按主键查询数据
    @RequestMapping("getById.action")
    @ResponseBody
    public ResultUtil getById(String id, Map<String, Object> map) {

        Map<String, Object> parameter = new HashMap<String, Object>();
        Article article = articleService.getById(id);
        map.put("article", article);
       // List<Banner> bannerList = bannerService.getAll(parameter);
       // map.put("bannerList", bannerList);
        return ResultUtil.ok().data(map);//给前端数据
    }

}
