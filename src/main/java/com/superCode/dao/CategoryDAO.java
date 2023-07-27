package com.superCode.dao;

import com.superCode.entity.Category;

import java.util.List;
import java.util.Map;

public interface CategoryDAO {
    void insert(Category category);

    void update(Category category);

    void delete(String categoryid);

    List<Category> getAll(Map<String, Object> params);

    int getRowSize(Map<String, Object> params);

    List<Category> getByCond(Category category);

    List<Category> getByLike(Category category);

    Category getById(String categoryid);

}
