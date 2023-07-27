package com.superCode.service;

import com.superCode.dao.OrdersDAO;
import com.superCode.entity.Orders;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;

@Service("orders")

public class OrdersService {
    @Resource
    private OrdersDAO ordersDao;

    // 插入数据 调用entity包orders.xml里的insertOrders配置
    public void insert(Orders orders) {
        ordersDao.insert(orders);
    }

    // 更新数据 调用entity包orders.xml里的updateOrders配置
    public void update(Orders orders) {
        ordersDao.update(orders);
    }

    // 删除数据 调用entity包orders.xml里的deleteOrders配置
    public void delete(String ordersid) {
        ordersDao.delete(ordersid);
    }

    // 查询全部数据 调用entity包orders.xml里的getAllOrders配置
    public List<Orders> getAll(Map<String, Object> params) {
        return ordersDao.getAll(params);
    }

    // 查询总行数 调用entity包Orders.xml里的配置
    public int getRowSize(Map<String, Object> params) {
        return ordersDao.getRowSize(params);
    }


    // 按照Admin类里面的值精确查询 调用entity包orders.xml里的getOrdersByCond配置
    public List<Orders> getByCond(Orders orders) {
        return ordersDao.getByCond(orders);
    }

    // 按照Orders类里面的值模糊查询 调用entity包orders.xml里的getOrdersByLike配置
    public List<Orders> getByLike(Orders orders) {
        return ordersDao.getByLike(orders);
    }

    // 按主键查询表返回单一的Orders实例 调用entity包orders.xml里的getOrdersById配置
    public Orders getById(String ordersid) {
        return ordersDao.getById(ordersid);
    }


    // 统计分析
    public List orderMoneyTotal(Map<String, Object> params) {
        return ordersDao.orderMoneyTotal( params);
    }
    // 统计分析
    public List getcontract_infonumber(Map<String, Object> params) {
        return ordersDao.getcontract_infonumber( params);
    }


    /* <!--获取房源预约看房审核成功的可预约看房人数-->*/
    public  int getOrdersNumber(int housing_infoid)  {
        return ordersDao.getOrdersNumber( housing_infoid);
    }

}
