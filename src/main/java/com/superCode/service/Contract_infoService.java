package com.superCode.service;

import com.superCode.dao.Contract_infoDAO;
import com.superCode.entity.Contract_info;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;

@Service("contract_info")
public class Contract_infoService {

    @Resource
    private Contract_infoDAO contract_infoDao;

    // 插入数据 调用dAO里的insertContract_info配置
    public void insert(Contract_info contract_info) {
        contract_infoDao.insert(contract_info);
    }

    // 更新数据 调用dAO里的updateContract_info配置
    public void update(Contract_info contract_info) {
        contract_infoDao.update(contract_info);
    }

    // 删除数据 调用dAO里的deleteContract_info配置
    public void delete(String contract_infoid) {
        contract_infoDao.delete(contract_infoid);
    }

    // 查询全部数据 调用dAO里的getAllContract_info配置
    public List<Contract_info> getAll(Map<String, Object> params) {
        return contract_infoDao.getAll(params);
    }

    // 查询总行数 调用dAO里的配置
    public int getRowSize(Map<String, Object> params) {
        return contract_infoDao.getRowSize(params);
    }


    // 按主键查询表返回单一的Contract_info实例 调用dAO里的getContract_infoById配置
    public Contract_info getById(String contract_infoid) {
        return contract_infoDao.getById(contract_infoid);
    }


}
