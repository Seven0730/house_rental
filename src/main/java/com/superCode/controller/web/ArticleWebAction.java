package com.superCode.controller.web;

import com.superCode.entity.Article;
import com.superCode.service.ArticleService;
import com.superCode.util.ResultUtil;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

//定义为控制器
@Controller
// 设置路径
@RequestMapping("/web")
public class ArticleWebAction extends BaseWebAction {


    // 注入AdminService 并getter setter
    @Resource
    private ArticleService articleService;


    // 新闻公告
    @RequestMapping("article.action")
    @ResponseBody
    public ResultUtil article(HttpServletRequest request, Map<String, Object> map) throws UnsupportedEncodingException {

        Map<String, Object> parameter = new HashMap<String, Object>();
        String bannerid = request.getParameter("bannerid");//搜索关键词
        String articlekeyword = request.getParameter("keyword");//搜索关键词

        if (bannerid != null && bannerid != "") parameter.put("bannerid", bannerid);//搜索参数


        if (articlekeyword != null && articlekeyword != "") parameter.put("keyword", articlekeyword);//搜索参数


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

        map.put("articlekeyword", articlekeyword);
        map.put("bannerid", bannerid);
        map.put("pager", pager);
        map.put("articleList", articleList);
        return ResultUtil.ok().data(map);//给前端数据
    }

    // 阅读公告
    @RequestMapping("articleDetail.action")
    @ResponseBody
    public ResultUtil articleDetail(String id, Map<String, Object> map) {

        Article article = articleService.getById(id);
        article.setHits("" + (Integer.parseInt(article.getHits()) + 1));
        articleService.update(article);
        map.put("article", article);
        return ResultUtil.ok().data(map);//给前端数据
    }


}
