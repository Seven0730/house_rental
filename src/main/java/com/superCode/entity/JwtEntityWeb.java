package com.superCode.entity;


/**
 * 保存 JWT 对应存储的数据   后台使用
 */
public class JwtEntityWeb {
    // 保存用户 ID
    private String usersid;

    private Users users;//普通用户
    private Landlord landlord;//房东用户
    private int role;//权限普通用户  房东用户2


    // 保存 JWT 创建时间戳
    private Long time;


    public Long getTime() {
        return time;
    }

    public void setTime(Long time) {
        this.time = time;
    }


    public String getUsersid() {
        return usersid;
    }

    public void setUsersid(String usersid) {
        this.usersid = usersid;
    }




    public Users getUsers() {
        return users;
    }

    public void setUsers(Users users) {
        this.users = users;
    }

    public Landlord getLandlord() {
        return landlord;
    }

    public void setLandlord(Landlord landlord) {
        this.landlord = landlord;
    }

    public int getRole() {
        return role;
    }

    public void setRole(int role) {
        this.role = role;
    }
}
