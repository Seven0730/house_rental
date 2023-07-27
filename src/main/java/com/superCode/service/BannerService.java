package com.superCode.service;

import com.superCode.dao.BannerDAO;
import com.superCode.entity.Banner;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;

@Service("banner")

public class BannerService {
    @Resource
    private BannerDAO bannerDao;

    // 插入数据 调用entity包banner.xml里的insertBanner配置
    public void insert(Banner banner) {
        bannerDao.insert(banner);
    }

    // 更新数据 调用entity包banner.xml里的updateBanner配置
    public void update(Banner banner) {
        bannerDao.update(banner);
    }

    // 删除数据 调用entity包banner.xml里的deleteBanner配置
    public void delete(String bannerid) {
        bannerDao.delete(bannerid);
    }

    // 查询全部数据 调用entity包banner.xml里的getAllBanner配置
    public List<Banner> getAll(Map<String, Object> params) {
        return bannerDao.getAll(params);
    }

    // 查询总行数 调用entity包Banner.xml里的配置
    public int getRowSize(Map<String, Object> params) {
        return bannerDao.getRowSize(params);
    }


    // 按照Admin类里面的值精确查询 调用entity包banner.xml里的getBannerByCond配置
    public List<Banner> getByCond(Banner banner) {
        return bannerDao.getByCond(banner);
    }

    // 按照Banner类里面的值模糊查询 调用entity包banner.xml里的getBannerByLike配置
    public List<Banner> getByLike(Banner banner) {
        return bannerDao.getByLike(banner);
    }

    // 按主键查询表返回单一的Banner实例 调用entity包banner.xml里的getBannerById配置
    public Banner getById(String bannerid) {
        return bannerDao.getById(bannerid);
    }

}
