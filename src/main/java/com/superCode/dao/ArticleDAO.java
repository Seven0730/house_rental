package com.superCode.dao;

import com.superCode.entity.Article;

import java.util.List;
import java.util.Map;

public interface ArticleDAO {
    void insert(Article article);

    void update(Article article);

    void delete(String articleid);

    List<Article> getAll(Map<String, Object> params);

    int getRowSize(Map<String, Object> params);


    List<Article> getTop();

    List<Article> getFlv();

    List<Article> getByBanner(String bannerid);

    List<Article> getByCond(Article article);

    List<Article> getByLike(Article article);

    // 按主键查询表返回单一的Article实例 调用entity包article.xml里的getArticleById配置
    Article getById(String articleid);

}
