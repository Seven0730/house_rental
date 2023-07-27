package com.superCode.entity;


/**
 * 保存 JWT 对应存储的数据   后台使用
 */
public class JwtEntityAdmin {
    // 保存用户 ID
    private String adminid;

    //角色
    private String username;

    // 保存用户名
    private String realname;

    // 保存 JWT 创建时间戳
    private Long time;


    public Long getTime() {
        return time;
    }

    public void setTime(Long time) {
        this.time = time;
    }


    public String getAdminid() {
        return adminid;
    }

    public void setAdminid(String adminid) {
        this.adminid = adminid;
    }


    public String getRealname() {
        return realname;
    }

    public void setRealname(String realname) {
        this.realname = realname;
    }


    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }
}
