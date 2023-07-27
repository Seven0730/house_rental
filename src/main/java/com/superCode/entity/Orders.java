package com.superCode.entity;

import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;
import java.util.List;

public class Orders {
    private String ordersid;
    private String usersid;
    private String housing_infoid;
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date addtime;
     private String ordertime;
    private String status;
    private String memo;
    private String username;
    private String realname;

    public List getHousing_infoid_list() {
        return housing_infoid_list;
    }

    public void setHousing_infoid_list(List housing_infoid_list) {
        this.housing_infoid_list = housing_infoid_list;
    }

    private java.util.List housing_infoid_list;
    public String getHousing_infoname() {
        return housing_infoname;
    }

    public void setHousing_infoname(String housing_infoname) {
        this.housing_infoname = housing_infoname;
    }

    private String housing_infoname;

    public String getRealname() {
        return realname;
    }

    public void setRealname(String realname) {
        this.realname = realname;
    }

    public String getOrdersid() {
        return ordersid;
    }

    public void setOrdersid(String ordersid) {
        this.ordersid = ordersid;
    }

    public String getUsersid() {
        return this.usersid;
    }

    public void setUsersid(String usersid) {
        this.usersid = usersid;
    }

    public String getHousing_infoid() {
        return this.housing_infoid;
    }

    public void setHousing_infoid(String housing_infoid) {
        this.housing_infoid = housing_infoid;
    }

    public Date getAddtime() {
        return this.addtime;
    }

    public void setAddtime(Date addtime) {
        this.addtime = addtime;
    }

    public String getOrdertime() {
        return this.ordertime;
    }

    public void setOrdertime(String ordertime) {
        this.ordertime = ordertime;
    }

    public String getStatus() {
        return this.status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getMemo() {
        return this.memo;
    }

    public void setMemo(String memo) {
        this.memo = memo;
    }

    public String getUsername() {
        return this.username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

}
