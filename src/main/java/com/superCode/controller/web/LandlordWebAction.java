package com.superCode.controller.web;

import com.superCode.entity.Housing_info;
import com.superCode.entity.Landlord;
import com.superCode.service.OrdersService;
import com.superCode.service.Housing_infoService;
import com.superCode.service.LandlordService;
import com.superCode.util.ResultUtil;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

//定义为控制器
@Controller
// 设置路径
@RequestMapping("/web")
public class LandlordWebAction extends BaseWebAction {


    @Resource
    private LandlordService landlordService;
    // 注入AdminService 并getter setter

    @Resource
    private Housing_infoService housing_infoService;
    // 注入AdminService 并getter setter
    @Resource
    private OrdersService ordersService;
    // 注入AdminService 并getter setter


    // 全部房东
    @RequestMapping("landlord.action")
    @ResponseBody
    public ResultUtil landlord(HttpServletRequest request, Map<String, Object> map) {

        Map<String, Object> parameter = new HashMap<String, Object>();

        String keyword = request.getParameter("keyword");//搜索关键词
        if (keyword != null && keyword != "") parameter.put("keyword", keyword);//搜索参数

        String categoryid = request.getParameter("categoryid");//按区域搜索
        if (categoryid != null && categoryid != "") parameter.put("categoryid", categoryid);//搜索参数


        int rowCountTotal = landlordService.getRowSize(parameter);//获取总数
        int pageSize = 10;// 分页大小
        int pageNumber = 1; //设定页面参数

        if (request.getParameter("pageNumber") != null && request.getParameter("pageNumber") != "") {
            pageNumber = Integer.parseInt(request.getParameter("pageNumber"));//当前页
        }
        parameter.put("limit", pageSize);//搜索参数
        parameter.put("start", (pageNumber - 1) * pageSize);//搜索参数
        List<Landlord> landlordList = landlordService.getAll(parameter);


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
        map.put("categoryid", categoryid);
        map.put("pager", pager);
        map.put("landlordList", landlordList);
        return ResultUtil.ok().data(map);//给前端数据
    }

    // 房东详细
    @RequestMapping("landlordDetail.action")
    @ResponseBody
    public ResultUtil landlordDetail(HttpServletRequest request, String id, Map<String, Object> map) {

        Landlord landlord = landlordService.getById(id);
        map.put("landlord", landlord);


        //房东----------------------------------------包含的房源
        Map<String, Object> parameter = new HashMap<String, Object>();
        String keyword = request.getParameter("keyword");//搜索关键词
        if (keyword != null && keyword != "") parameter.put("keyword", keyword);//搜索参数


        parameter.put("iscooled", 0);//审核通过的
        parameter.put("landlordid", id);//只显示当前房东的
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


    /////房东登录后的操作-----------------------------


    // 准备修改密码
    @RequestMapping("prePwd_2.action")
    @ResponseBody
    public ResultUtil prePwd_2() {

        return ResultUtil.ok();//返回成功提示
    }

    // 修改密码
    @RequestMapping("editpwd_2.action")
    @ResponseBody
    public ResultUtil editpwd_2(Map<String, Object> map) {




        String usersid = (String) this.getSession().getAttribute("usersid");
        String password = this.getRequest().getParameter("password");
        String repassword = this.getRequest().getParameter("repassword");


        Landlord landlord = landlordService.getById(usersid);
        if (password.equals(landlord.getPassword())) {
            landlord.setPassword(repassword);
            landlordService.update(landlord);
            return ResultUtil.ok().message("修改成功 ");

        } else {
            return ResultUtil.error().message("旧密码错误");//给前端数据

        }
    }


    // 准备修改信息
    @RequestMapping("preInfo_2.action")
    @ResponseBody
    public ResultUtil preInfo_2(String id, Map<String, Object> map) {

        Landlord landlord = landlordService.getById(id);
        map.put("landlord", landlord);
        return ResultUtil.ok().data(map);//给前端数据



    }

    // 修改信息
    @RequestMapping("editinfo_2.action")
    @ResponseBody
    public ResultUtil editinfo_2(Map<String, Object> map) {


        String usersid = (String) this.getSession().getAttribute("usersid");
        String title = this.getRequest().getParameter("title");
        String slogan = this.getRequest().getParameter("slogan");
        String createdate = this.getRequest().getParameter("createdate");
        String contact = this.getRequest().getParameter("contact");
        String image = this.getRequest().getParameter("image");
        String contents = this.getRequest().getParameter("contents");

        Landlord landlord = landlordService.getById(usersid);
        landlord.setTitle(title);
        landlord.setSlogan(slogan);
        landlord.setCreatedate(createdate);
        landlord.setContact(contact);
        landlord.setImage(image);
        landlord.setContents(contents);

        landlordService.update(landlord);
        return ResultUtil.ok().message("修改成功");


    }


}
