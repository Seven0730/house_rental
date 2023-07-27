package com.superCode.dao;

import com.superCode.entity.Banner;

import java.util.List;
import java.util.Map;

public interface BannerDAO {
    void insert(Banner banner);

    void update(Banner banner);

    void delete(String bannerid);

    List<Banner> getAll(Map<String, Object> params);

    int getRowSize(Map<String, Object> params);

    List<Banner> getByCond(Banner banner);

    List<Banner> getByLike(Banner banner);

    Banner getById(String bannerid);

}
