package com.superCode.dao;

import com.superCode.entity.Users;

import java.util.List;
import java.util.Map;

public interface UsersDAO {
    void insert(Users users);

    void update(Users users);

    void delete(String usersid);

    List<Users> getAll(Map<String, Object> params);

    int getRowSize(Map<String, Object> params);

    List<Users> getByCond(Users users);

    List<Users> getByLike(Users users);

    Users getById(String usersid);

}
