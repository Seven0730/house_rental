package com.superCode.entity;

import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;

public class Users {
    private String usersid;
    private String username;
    private String password;
    private String realname;
    private String sex;
    private String createdate;
    private String contact;
    private String image;
    private String college;
    private String major;
    private String address;
    private String iscooled;
    private String qq;
    private String idcard;
    private String email;
    private String contract_info0;
    private String contract_info1;
    private String contract_info2;
    private String contract_info3;
    private String contract_info4;
    private String contract_info5;
    private String contract_info6;


    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date regdate;

    public String getUsersid() {
        return usersid;
    }

    public void setUsersid(String usersid) {
        this.usersid = usersid;
    }

    public String getUsername() {
        return this.username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return this.password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getRealname() {
        return this.realname;
    }

    public void setRealname(String realname) {
        this.realname = realname;
    }

    public String getSex() {
        return this.sex;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }

    public String getCreatedate() {
        return this.createdate;
    }

    public void setCreatedate(String createdate) {
        this.createdate = createdate;
    }

    public String getContact() {
        return this.contact;
    }

    public void setContact(String contact) {
        this.contact = contact;
    }

    public String getImage() {
        return this.image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public Date getRegdate() {
        return this.regdate;
    }

    public void setRegdate(Date regdate) {
        this.regdate = regdate;
    }

    public String getCollege() {
        return college;
    }

    public void setCollege(String college) {
        this.college = college;
    }

    public String getMajor() {
        return major;
    }

    public void setMajor(String major) {
        this.major = major;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getIscooled() {
        return iscooled;
    }

    public void setIscooled(String iscooled) {
        this.iscooled = iscooled;
    }

    public String getQq() {
        return qq;
    }

    public void setQq(String qq) {
        this.qq = qq;
    }

    public String getIdcard() {
        return idcard;
    }

    public void setIdcard(String idcard) {
        this.idcard = idcard;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getContract_info0() {
        return contract_info0;
    }

    public void setContract_info0(String contract_info0) {
        this.contract_info0 = contract_info0;
    }

    public String getContract_info1() {
        return contract_info1;
    }

    public void setContract_info1(String contract_info1) {
        this.contract_info1 = contract_info1;
    }

    public String getContract_info2() {
        return contract_info2;
    }

    public void setContract_info2(String contract_info2) {
        this.contract_info2 = contract_info2;
    }

    public String getContract_info3() {
        return contract_info3;
    }

    public void setContract_info3(String contract_info3) {
        this.contract_info3 = contract_info3;
    }

    public String getContract_info4() {
        return contract_info4;
    }

    public void setContract_info4(String contract_info4) {
        this.contract_info4 = contract_info4;
    }

    public String getContract_info5() {
        return contract_info5;
    }

    public void setContract_info5(String contract_info5) {
        this.contract_info5 = contract_info5;
    }

    public String getContract_info6() {
        return contract_info6;
    }

    public void setContract_info6(String contract_info6) {
        this.contract_info6 = contract_info6;
    }
}
