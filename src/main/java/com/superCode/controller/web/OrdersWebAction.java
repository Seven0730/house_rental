package com.superCode.controller.web;

import com.superCode.entity.Orders;
import com.superCode.entity.Housing_info;
import com.superCode.service.OrdersService;
import com.superCode.service.LandlordService;
import com.superCode.service.Housing_infoService;
import com.superCode.util.ResultUtil;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.io.UnsupportedEncodingException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

//定义为控制器
@Controller
// 设置路径
@RequestMapping("/web")
public class OrdersWebAction extends BaseWebAction {


    @Resource
    private LandlordService landlordService;
    // 注入AdminService 并getter setter
    @Resource
    private OrdersService ordersService;

    @Resource
    private Housing_infoService housing_infoService;
    // 注入AdminService 并getter setter


    /*-----------------------------------------用户*/
    // 准备提交申请
    @RequestMapping("preOrders.action")
    @ResponseBody
    public ResultUtil preOrders(String id, Map<String, Object> map) {

        Housing_info housing_info = housing_infoService.getById(id);
        map.put("housing_info", housing_info);
        return ResultUtil.ok().data(map);//给前端数据
    }

    // 提交申请
    @RequestMapping("addOrders.action")
    @ResponseBody
    public ResultUtil addOrders() {

        String usersid = (String) this.getSession().getAttribute("usersid");
        Orders orders = new Orders();
        orders.setAddtime(new Date());
        orders.setHousing_infoid(this.getRequest().getParameter("housing_infoid"));
        orders.setMemo(this.getRequest().getParameter("memo"));
        orders.setStatus("待审核");
        orders.setUsersid(usersid);

         
        orders.setOrdertime(this.getRequest().getParameter("ordertime"));
        ordersService.insert(orders);
        return ResultUtil.ok();//返回成功提示
    }


    // 我的申请
    @RequestMapping("myOrders.action")
    @ResponseBody
    public ResultUtil myOrders(Map<String, Object> map) {

        String usersid = (String) this.getSession().getAttribute("usersid");
        Orders orders = new Orders();
        orders.setUsersid(usersid);
        List<Orders> ordersList = ordersService.getByCond(orders);
        map.put("ordersList", ordersList);
        return ResultUtil.ok().data(map);//给前端数据
    }


    // 取消预约看房
    @RequestMapping("cancel.action")
    @ResponseBody
    public ResultUtil cancel(String id, Map<String, Object> map) {

        Orders orders = ordersService.getById(id);
        orders.setStatus("取消");
        ordersService.update(orders);
        return ResultUtil.ok();//返回成功提示
    }



    /*-----------------------------------------房东*/
    // 按房东ID显示数据
    @RequestMapping("getLandlordOrders.action")
    @ResponseBody
    public ResultUtil getLandlordOrders(HttpServletRequest request, Map<String, Object> map) throws UnsupportedEncodingException {


        Map<String, Object> parameter = new HashMap<String, Object>();
        String keyword = request.getParameter("keyword");//搜索关键词
        if (keyword != null && keyword != "") parameter.put("keyword", keyword);//搜索参数
        String usersid = (String) getRequest().getSession().getAttribute("usersid");
        //获取房东包含的所有房源
        Map<String, Object> parameter11 = new HashMap<String, Object>();
        parameter11.put("landlordid", usersid);//只显示当前房东的
        List<Housing_info> housing_infoList = housing_infoService.getAll(parameter11);


        //只显示当前房东的房源 涉及的预约看房
        ArrayList housing_infoid_list111111 = new ArrayList<String>();
        for (int i = 0; i < housing_infoList.size(); i++) {
            Housing_info housing_info = (Housing_info) housing_infoList.get(i);//转为MAP
            int id = housing_info.getHousing_infoid();//获取  ID
            housing_infoid_list111111.add(String.valueOf(id));
        }
        parameter.put("housing_infoid_list", housing_infoid_list111111);


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


    // 完成预约看房
    @RequestMapping("over.action")
    @ResponseBody
    public ResultUtil over(String id, String status, Map<String, Object> map) throws UnsupportedEncodingException {

        Orders orders = ordersService.getById(id);
        //status = new String(status.getBytes("iso-8859-1"), "utf-8");//编码转换,防止乱码
        orders.setStatus(status);
        ordersService.update(orders);
        return ResultUtil.ok();//返回成功提示
    }

}
