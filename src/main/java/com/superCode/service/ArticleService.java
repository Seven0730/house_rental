package com.superCode.service;

import com.superCode.dao.ArticleDAO;
import com.superCode.entity.Article;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;

@Service("article")

public class ArticleService {
    @Resource
    private ArticleDAO articleDao;

    // 插入数据 调用entity包article.xml里的insertArticle配置
    public void insert(Article article) {
        articleDao.insert(article);
    }

    // 更新数据 调用entity包article.xml里的updateArticle配置
    public void update(Article article) {
        articleDao.update(article);
    }

    // 删除数据 调用entity包article.xml里的deleteArticle配置
    public void delete(String articleid) {
        articleDao.delete(articleid);
    }

    // 查询全部数据 调用entity包article.xml里的getAllArticle配置
    public List<Article> getAll(Map<String, Object> params) {
        return articleDao.getAll(params);
    }

    // 查询总行数 调用entity包Article.xml里的配置
    public int getRowSize(Map<String, Object> params) {
        return articleDao.getRowSize(params);
    }


    public List<Article> getTop() {
        return articleDao.getTop();
    }

    public List<Article> getFlv() {
        return articleDao.getFlv();
    }

    public List<Article> getByBanner(String bannerid) {
        return articleDao.getByBanner(bannerid);
    }

    // 按照Admin类里面的值精确查询 调用entity包article.xml里的getArticleByCond配置
    public List<Article> getByCond(Article article) {
        return articleDao.getByCond(article);
    }

    // 按照Article类里面的值模糊查询 调用entity包article.xml里的getArticleByLike配置
    public List<Article> getByLike(Article article) {
        return articleDao.getByLike(article);
    }


    // 按主键查询表返回单一的Article实例 调用entity包article.xml里的getArticleById配置
    public Article getById(String articleid) {
        return articleDao.getById(articleid);
    }


}
