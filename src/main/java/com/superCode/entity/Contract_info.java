package com.superCode.entity;

import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;
import java.util.List;

public class Contract_info {
    private String contract_infoid;
    private String usersid;
    private String housing_infoid;
    private String addtime;
    private String number;
    private String contract_info_files;
    private String memo;
    private String username;
    private String realname;

    public List getHousing_infoid_list() {
        return housing_infoid_list;
    }

    public void setHousing_infoid_list(List housing_infoid_list) {
        this.housing_infoid_list = housing_infoid_list;
    }

    private List housing_infoid_list;

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

    public String getContract_infoid() {
        return contract_infoid;
    }

    public void setContract_infoid(String contract_infoid) {
        this.contract_infoid = contract_infoid;
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

    public String getAddtime() {
        return this.addtime;
    }

    public void setAddtime(String addtime) {
        this.addtime = addtime;
    }


    public String getContract_info_files() {
        return this.contract_info_files;
    }

    public void setContract_info_files(String contract_info_files) {
        this.contract_info_files = contract_info_files;
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

    public void setNumber(String number) {
        this.number = number;
    }

    public String getNumber() {
        return number;
    }
}
