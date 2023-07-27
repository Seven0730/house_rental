package com.superCode.dao;

import com.superCode.entity.Admin;

import java.util.List;
import java.util.Map;

public interface AdminDAO {

    void insert(Admin admin);

    void update(Admin admin);

    void delete(String adminid);

    List<Admin> getAll(Map<String, Object> params);

    int getRowSize(Map<String, Object> params);

    List<Admin> getByCond(Admin admin);

    List<Admin> getByLike(Admin admin);

    Admin getById(String adminid);


}
