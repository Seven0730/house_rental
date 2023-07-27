package com.superCode.controller.admin;

import com.superCode.entity.Orders;
import com.superCode.entity.Landlord;
import com.superCode.entity.Users;
import com.superCode.service.OrdersService;
import com.superCode.service.LandlordService;
import com.superCode.service.UsersService;
import com.superCode.util.ResultUtil;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.io.UnsupportedEncodingException;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

//定义为控制器
@Controller
// 设置路径
@RequestMapping("/admin/orders")
public class OrdersAction extends BaseAdminAction {
    // 注入AdminService
    @Resource
    private OrdersService ordersService;
    @Resource
    private UsersService usersService;
    @Resource
    private LandlordService landlordService;

    // 准备添加数据
   /* @RequestMapping("create.action")
@ResponseBody
    public ResultUtil   createOrders(Map<String, Object> map) {
        List<Users> usersList = usersService.getAllUsers();
        map.put("usersList", usersList);
        List<Landlord> landlordList = landlordService.getAllLandlord();
        map.put("landlordList", landlordList);
        return ResultUtil.ok().data(map);//给前端数据
    }

    // 添加数据
    @RequestMapping("add.action")
@ResponseBody
    public ResultUtil   addOrders(Orders orders) {
        orders.setOrdersid(VeDate.getStringDatex());
        ordersService.insertOrders(orders);
        return ResultUtil.ok();//返回成功提示
    }
*/
    // 通过主键删除数据
    @RequestMapping("delete.action")
    @ResponseBody
    public ResultUtil deleteOrders(String id) {
        ordersService.delete(id);
        return ResultUtil.ok();//返回成功提示
    }

    // 更新数据
 /*   @RequestMapping("update.action")
@ResponseBody
    public ResultUtil   update(Orders orders) {
        ordersService.update(orders);
        return ResultUtil.ok();//返回成功提示
    }*/

    // 显示全部数据
    @RequestMapping("getAll.action")
    @ResponseBody
    public ResultUtil getAll(HttpServletRequest request, Map<String, Object> map) throws UnsupportedEncodingException {
        Map<String, Object> parameter = new HashMap<String, Object>();
        String keyword = request.getParameter("keyword");//搜索关键词
        if (keyword != null && keyword != "") parameter.put("keyword", keyword);//搜索参数
        int rowCountTotal = ordersService.getRowSize(parameter);//获取总数
        int pageSize = 10;// 分页大小
        int pageNumber = 1; //设定页面参数

        if (request.getParameter("pageNumber") != null && request.getParameter("pageNumber") != "") {
            pageNumber = Integer.parseInt(request.getParameter("pageNumber"));//当前页
        }
        parameter.put("limit", pageSize);//搜索参数
        parameter.put("start", (pageNumber - 1) * pageSize);//搜索参数
        List<Orders> ordersList = ordersService.getAll(parameter);


        //设定页面参数
        Map<String, Object> pager = new HashMap<String, Object>();
        int pageCount = 1;// 总页数
        // 计算总页数
        if (rowCountTotal % pageSize == 0) {
            pageCount = rowCountTotal / pageSize;
        } else {
            pageCount = rowCountTotal / pageSize + 1;
        }
        pager.put("pageNumber", pageNumber);//当前页
        pager.put("pageCount", pageCount);//总页数
        pager.put("rowCountTotal", rowCountTotal);//记录总条数

        map.put("keyword", keyword);
        map.put("pager", pager);

        map.put("ordersList", ordersList);
        return ResultUtil.ok().data(map);//给前端数据
    }

    // 按条件查询数据 (模糊查询)----备用
 /*
    @RequestMapping("queryOrdersByCond.action")
@ResponseBody
    public ResultUtil   queryOrdersByCond(String cond, String name, Map<String, Object> map) {
        List<Orders> ordersList = new ArrayList<Orders>();
        Orders orders = new ();
        if (cond != null) {
            if ("usersid".equals(cond)) {
                orders.setUsersid(name);
                ordersList = ordersService.getOrdersByLike(orders);
            }
            if ("housing_infoid".equals(cond)) {
                orders.setHousing_infoid(name);
                ordersList = ordersService.getOrdersByLike(orders);
            }
            if ("addtime".equals(cond)) {
                orders.setAddtime(name);
                ordersList = ordersService.getOrdersByLike(orders);
            }
            if ("status".equals(cond)) {
                orders.setStatus(name);
                ordersList = ordersService.getOrdersByLike(orders);
            }
            if ("memo".equals(cond)) {
                orders.setMemo(name);
                ordersList = ordersService.getOrdersByLike(orders);
            }
        }
        map.put("ordersList", ordersList);
        return "admin/orders/query";
    }
*/
    // 完成预约看房
    @RequestMapping("over.action")
    @ResponseBody
    public ResultUtil over(String id, String status, Map<String, Object> map) throws UnsupportedEncodingException {

        Orders orders = ordersService.getById(id);
        // status = new String(status.getBytes("iso-8859-1"), "utf-8");//编码转换,防止乱码
        orders.setStatus(status);
        ordersService.update(orders);
        return ResultUtil.ok();//返回成功提示
    }

    // 按主键查询数据
    @RequestMapping("getById.action")
    @ResponseBody
    public ResultUtil getOrdersById(String id, Map<String, Object> map) {
        Map<String, Object> parameter = new HashMap<String, Object>();
        Orders orders = ordersService.getById(id);
        map.put("orders", orders);
        List<Users> usersList = usersService.getAll(parameter);
        map.put("usersList", usersList);
        List<Landlord> landlordList = landlordService.getAll(parameter);
        map.put("landlordList", landlordList);
        return ResultUtil.ok().data(map);//给前端数据
    }


    // 预约看房人数分析
    @RequestMapping("/orderMoneyTotal.action")
    @ResponseBody
    public ResultUtil orderMoneyTotal(HttpServletRequest request, Map<String, Object> map) {
        String createdate1 = request.getParameter("createdate1");//搜索开始日期
        String createdate2 = request.getParameter("createdate2");//搜索结束 日期
        Map<String, Object> parameter = new HashMap<String, Object>();
        if (createdate1 != null && createdate1 != "") {
            parameter.put("createdate1", createdate1);//搜索参数

        }
        if (createdate2 != null && createdate2 != "") {
            parameter.put("createdate2", createdate2);//搜索参数

        }
        List countList = ordersService.orderMoneyTotal(parameter);


        ArrayList dateList = new ArrayList();// 日期列表
        ArrayList moneyList = new ArrayList();// 数据列表

        for (int i = 0; i < countList.size(); i++) {
            Map map11 = (Map) countList.get(i);//转为MAP
            String date = (String) map11.get("addtime");//获取  日期
            Long total = (Long) map11.get("total");//获取  金额
            //double total =Double.valueOf( map.get("total").toString());//获取  数量
            //  int total =Integer.parseInt(map.get("total").toString());//获取  数量


            DecimalFormat df = new DecimalFormat("######0.00");


            df.format(total);


            dateList.add(date);
            moneyList.add(total);


        }


        map.put("dateList", dateList);
        map.put("moneyList", moneyList);
        map.put("createdate1", createdate1);
        map.put("createdate2", createdate2);

        return ResultUtil.ok().data(map);//给前端数据

    }  
	
	// 区域房源分析

    @RequestMapping("/getcontract_infonumber.action")
    @ResponseBody
    public ResultUtil getcontract_infonumber(HttpServletRequest request, Map<String, Object> map) {

        String housing_infoid = request.getParameter("housing_infoid");//搜索结束 日期
        Map<String, Object> parameter = new HashMap<String, Object>();
        if (housing_infoid != null && housing_infoid != "") {
            parameter.put("housing_infoid", housing_infoid);//搜索参数

        }

        List countList = ordersService.getcontract_infonumber(parameter);


        map.put("countList", countList);
        map.put("housing_infoid", housing_infoid);
        return ResultUtil.ok().data(map);//给前端数据

    }
}
