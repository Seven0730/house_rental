package com.superCode.entity;

import java.util.ArrayList;
import java.util.List;

public class Banner {
    private String bannerid;
    private String bannername;
    private List<Article> articleList = new ArrayList<Article>();

    public List<Article> getArticleList() {
        return articleList;
    }

    public void setArticleList(List<Article> articleList) {
        this.articleList = articleList;
    }

    public String getBannerid() {
        return bannerid;
    }

    public void setBannerid(String bannerid) {
        this.bannerid = bannerid;
    }

    public String getBannername() {
        return this.bannername;
    }

    public void setBannername(String bannername) {
        this.bannername = bannername;
    }
}
