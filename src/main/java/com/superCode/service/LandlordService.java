package com.superCode.service;



import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;
import com.superCode.dao.*;
import com.superCode.entity.*;


@Service("landlord")
public class LandlordService {
    @Resource
    private LandlordDAO landlordDao;

    // 插入数据 调用dAO里的insertLandlord配置
    public void insert(Landlord landlord) {
        landlordDao.insert( landlord);
    }

    // 更新数据 调用dAO里的updateLandlord配置
    public void update(Landlord landlord) {
        landlordDao.update( landlord);
    }

    // 删除数据 调用dAO里的deleteLandlord配置
    public void delete(String landlordid) {
        landlordDao.delete(  landlordid);
    }

    // 查询全部数据 调用dAO里的getAllLandlord配置
    public List<Landlord> getAll(Map<String, Object> params) {
        return landlordDao.getAll(  params);
    }

    // 查询总行数 调用dAO里的配置
    public int getRowSize(Map<String, Object> params) {
        return landlordDao.getRowSize(  params);
    }


    // 按照Admin类里面的值精确查询 调用dAO里的getLandlordByCond配置
    public List<Landlord> getByCond(Landlord landlord) {
        return landlordDao.getByCond( landlord);
    }

    // 按照Landlord类里面的值模糊查询 调用dAO里的getLandlordByLike配置
    public List<Landlord> getByLike(Landlord landlord) {
        return landlordDao.getByLike(  landlord);
    }

    // 按主键查询表返回单一的Landlord实例 调用dAO里的getLandlordById配置
    public Landlord getById(String landlordid) {
        return landlordDao.getById(  landlordid);
    }


}
