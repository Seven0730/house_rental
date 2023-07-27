package com.superCode.entity;

import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;

public class Article {
    private String articleid;
    private String title;
    private String image;
    private String bannerid;
    private String istop;
    private String isflv;
    private String contents;
    private String landlordtitle;
    private String landlordid;


    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date addtime;

    private String hits;
    private String bannername;

    public String getArticleid() {
        return articleid;
    }

    public void setArticleid(String articleid) {
        this.articleid = articleid;
    }

    public String getTitle() {
        return this.title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getImage() {
        return this.image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getBannerid() {
        return this.bannerid;
    }

    public void setBannerid(String bannerid) {
        this.bannerid = bannerid;
    }

    public String getIstop() {
        return this.istop;
    }

    public void setIstop(String istop) {
        this.istop = istop;
    }

    public String getIsflv() {
        return this.isflv;
    }

    public void setIsflv(String isflv) {
        this.isflv = isflv;
    }

    public String getContents() {
        return this.contents;
    }

    public void setContents(String contents) {
        this.contents = contents;
    }

    public Date getAddtime() {
        return this.addtime;
    }

    public void setAddtime(Date addtime) {
        this.addtime = addtime;
    }

    public String getHits() {
        return this.hits;
    }

    public void setHits(String hits) {
        this.hits = hits;
    }

    public String getBannername() {
        return bannername;
    }

    public void setBannername(String bannername) {
        this.bannername = bannername;
    }

    public String getLandlordtitle() {
        return landlordtitle;
    }

    public void setLandlordtitle(String landlordtitle) {
        this.landlordtitle = landlordtitle;
    }

    public String getLandlordid() {
        return landlordid;
    }

    public void setLandlordid(String landlordid) {
        this.landlordid = landlordid;
    }
}
