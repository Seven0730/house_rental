package com.superCode.controller.web;

import com.superCode.entity.Housing_info;
import com.superCode.entity.Landlord;
import com.superCode.service.Housing_infoService;
import com.superCode.service.OrdersService;
import com.superCode.service.LandlordService;
import com.superCode.util.ResultUtil;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.text.SimpleDateFormat;
import java.util.*;

//定义为控制器
@Controller
// 设置路径
@RequestMapping("/web")
public class Housing_infoWebAction extends BaseWebAction {
    // 注入AdminService 并getter setter
    @Resource
    private Housing_infoService housing_infoService;
    @Resource
    private OrdersService ordersService;

    @Resource
    private LandlordService landlordService;


    // 显示全部数据//审核过的
    @RequestMapping("housing_info.action")
    @ResponseBody
    public ResultUtil housing_info(HttpServletRequest request, Map<String, Object> map) {

        Map<String, Object> parameter = new HashMap<String, Object>();
        String keyword = request.getParameter("keyword");//搜索关键词
        if (keyword != null && keyword != "") parameter.put("keyword", keyword);//搜索参数
        String categoryid = request.getParameter("categoryid");//搜索关键词
        if (categoryid != null && categoryid != "") parameter.put("categoryid", categoryid);//搜索参数
        String landlordid = request.getParameter("landlordid");//搜索关键词
        if (landlordid != null && landlordid != "") parameter.put("landlordid", landlordid);//搜索参数

        parameter.put("iscooled", 0);//审核成功的
        parameter.put("status", 0);//上线成功的

        int rowCountTotal = housing_infoService.getRowSize(parameter);//获取总数
        int pageSize = 10;// 分页大小
        int pageNumber = 1; //设定页面参数

        if (request.getParameter("pageNumber") != null && request.getParameter("pageNumber") != "") {
            pageNumber = Integer.parseInt(request.getParameter("pageNumber"));//当前页
        }
        parameter.put("limit", pageSize);//搜索参数
        parameter.put("start", (pageNumber - 1) * pageSize);//搜索参数


        List<Housing_info> housing_infoList = housing_infoService.getAll(parameter);


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


        List<Housing_info> a_list = new ArrayList();

        for (Housing_info housing_info : housing_infoList) {
            int ordernumber = 0;
            try {
                ordernumber = housing_info.getCountrequire() - ordersService.getOrdersNumber(housing_info.getHousing_infoid());
            } catch (Exception e) {
                e.printStackTrace();
            }
            if (!(ordernumber > 0)) ordernumber = 0;
            housing_info.setCountrequire(ordernumber);
            a_list.add(housing_info);
        }
        map.put("housing_infoList", a_list);
        return ResultUtil.ok().data(map);//给前端数据
    }


    // 按主键查询数据
    @RequestMapping("housing_infoDetail.action")
    @ResponseBody
    public ResultUtil housing_infoDetail(String id, Map<String, Object> map) {

        Map<String, Object> parameter = new HashMap<String, Object>();
        Housing_info housing_info = housing_infoService.getById(id);
        map.put("housing_info", housing_info);
        ///List<Landlord> landlordList = landlordService.getAll(parameter);
        ///map.put("landlordList", landlordList);
        return ResultUtil.ok().data(map);//给前端数据
    }


//房东管理-----------------------------------


    // 状态更新
    @RequestMapping("status.action")
    @ResponseBody
    public ResultUtil status(String id, int status, Map<String, Object> map) {

        Housing_info housing_info = housing_infoService.getById(id);
        housing_info.setStatus(status);
        housing_infoService.update(housing_info);
        return ResultUtil.ok();//返回成功提示
    }


    // 准备添加数据
    @RequestMapping("create.action")
    @ResponseBody
    public ResultUtil create(Map<String, Object> map) {

        Map<String, Object> parameter = new HashMap<String, Object>();
        //List<Landlord> landlordList = landlordService.getAll(parameter);
        // map.put("landlordList", landlordList);
        return ResultUtil.ok().data(map);//给前端数据
    }

    // 添加数据
    @RequestMapping("add.action")
    @ResponseBody
    public ResultUtil add(Housing_info housing_info) {
        String usersid = (String) this.getSession().getAttribute("usersid");
        housing_info.setLandlordid(usersid);//当前登录的房东ID
        housing_info.setIscooled(1);//默认未审核
        housing_info.setStatus(0);
        Date currentTime = new Date();
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
        String date = formatter.format(currentTime);
        housing_info.setCreatedate(date);
        housing_infoService.insert(housing_info);
        return ResultUtil.ok();//返回成功提示
    }

    // 通过主键删除数据
    @RequestMapping("delete.action")
    @ResponseBody
    public ResultUtil delete(String id) {
        housing_infoService.delete(id);
        return ResultUtil.ok();//返回成功提示
    }

    // 更新数据
    @RequestMapping("update.action")
    @ResponseBody
    public ResultUtil update(Housing_info housing_info) {
        housing_info.setIscooled(1);//默认未审核
        housing_infoService.update(housing_info);
        return ResultUtil.ok();//返回成功提示
    }


    // 只获取当前登录的房东
    @RequestMapping("getList.action")
    @ResponseBody
    public ResultUtil getList(HttpServletRequest request, Map<String, Object> map) {

        Map<String, Object> parameter = new HashMap<String, Object>();
        String keyword = request.getParameter("keyword");//搜索关键词
        if (keyword != null && keyword != "") parameter.put("keyword", keyword);//搜索参数

        String usersid = (String) this.getSession().getAttribute("usersid");
        Landlord landlord = landlordService.getById(usersid);
        parameter.put("landlordid", landlord.getLandlordid());//只获取当前登录的房东

        int rowCountTotal = housing_infoService.getRowSize(parameter);//获取总数
        int pageSize = 10;// 分页大小
        int pageNumber = 1; //设定页面参数

        if (request.getParameter("pageNumber") != null && request.getParameter("pageNumber") != "") {
            pageNumber = Integer.parseInt(request.getParameter("pageNumber"));//当前页
        }
        parameter.put("limit", pageSize);//搜索参数
        parameter.put("start", (pageNumber - 1) * pageSize);//搜索参数


        List<Housing_info> housing_infoList = housing_infoService.getAll(parameter);


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


        List<Housing_info> a_list = new ArrayList();

        for (Housing_info housing_info : housing_infoList) {
            int ordernumber = 0;
            try {
                ordernumber = housing_info.getCountrequire() - ordersService.getOrdersNumber(housing_info.getHousing_infoid());
            } catch (Exception e) {
                e.printStackTrace();
            }
            if (!(ordernumber > 0)) ordernumber = 0;
            housing_info.setCountrequire(ordernumber);
            a_list.add(housing_info);
        }
        map.put("housing_infoList", a_list);
        return ResultUtil.ok().data(map);//给前端数据
    }


    // 按主键查询数据
    @RequestMapping("getById.action")
    @ResponseBody
    public ResultUtil getById(String id, Map<String, Object> map) {

        Map<String, Object> parameter = new HashMap<String, Object>();
        Housing_info housing_info = housing_infoService.getById(id);
        map.put("housing_info", housing_info);
        ///List<Landlord> landlordList = landlordService.getAll(parameter);
        ///map.put("landlordList", landlordList);
        return ResultUtil.ok().data(map);//给前端数据
    }


}
