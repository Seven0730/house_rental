package com.superCode.controller.admin;

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
import java.text.SimpleDateFormat;
import java.util.*;

//定义为控制器
@Controller
// 设置路径
@RequestMapping("/admin/housing_info")
public class Housing_infoAction extends BaseAdminAction {
    // 注入AdminService 并getter setter
    @Resource
    private Housing_infoService housing_infoService;
    @Resource
    private OrdersService ordersService;


    // 状态更新
    @RequestMapping("status.action")
    @ResponseBody
    public ResultUtil status(String id, int status, Map<String, Object> map) {

        Housing_info housing_info = housing_infoService.getById(id);
        housing_info.setStatus(status);
        housing_infoService.update(housing_info);
        return ResultUtil.ok();//返回成功提示
    }


    // 审核更新
    @RequestMapping("iscooled.action")
    @ResponseBody
    public ResultUtil iscooled(String id, int iscooled, Map<String, Object> map) {

        Housing_info housing_info = housing_infoService.getById(id);
        housing_info.setIscooled(iscooled);
        housing_infoService.update(housing_info);
        return ResultUtil.ok();//返回成功提示
    }

    @Resource
    private LandlordService landlordService;

    // 准备添加数据
    @RequestMapping("create.action")
    @ResponseBody
    public ResultUtil create(Map<String, Object> map) {
        Map<String, Object> parameter = new HashMap<String, Object>();

        List<Landlord> landlordList = landlordService.getAll(parameter);
        map.put("landlordList", landlordList);



        //List<Landlord> landlordList = landlordService.getAll(parameter);
        // map.put("landlordList", landlordList);
        return ResultUtil.ok().data(map);//给前端数据
    }

    // 添加数据
    @RequestMapping("add.action")
    @ResponseBody
    public ResultUtil add(Housing_info housing_info) {
        housing_info.setIscooled(0);//管理员添加默认直接上线
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
        housing_infoService.update(housing_info);
        return ResultUtil.ok();//返回成功提示
    }

    // 显示全部数据
    @RequestMapping("getAll.action")
    @ResponseBody
    public ResultUtil getAll(HttpServletRequest request, Map<String, Object> map) {
        Map<String, Object> parameter = new HashMap<String, Object>();
        String keyword = request.getParameter("keyword");//搜索关键词
        if (keyword != null && keyword != "") parameter.put("keyword", keyword);//搜索参数


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

    // 按条件查询数据 (模糊查询)
 /*
    @RequestMapping("queryHousing_infoByCond.action")
    @ResponseBody
    public ResultUtil queryHousing_infoByCond(String cond, String name, Map<String, Object> map) {
        List<Housing_info> housing_infoList = new ArrayList<Housing_info>();
        Housing_info housing_info = new Housing_info();
        if (cond != null) {
            if ("username".equals(cond)) {
                housing_info.setUsername(name);
                housing_infoList = housing_infoService.getHousing_infoByLike(housing_info);
            }
            if ("password".equals(cond)) {
                housing_info.setPassword(name);
                housing_infoList = housing_infoService.getHousing_infoByLike(housing_info);
            }
            if ("housing_infoname".equals(cond)) {
                housing_info.setHousing_infoname(name);
                housing_infoList = housing_infoService.getHousing_infoByLike(housing_info);
            }
            if ("slogan".equals(cond)) {
                housing_info.setSlogan(name);
                housing_infoList = housing_infoService.getHousing_infoByLike(housing_info);
            }
            if ("createdate".equals(cond)) {
                housing_info.setCreatedate(name);
                housing_infoList = housing_infoService.getHousing_infoByLike(housing_info);
            }
            if ("contact".equals(cond)) {
                housing_info.setContact(name);
                housing_infoList = housing_infoService.getHousing_infoByLike(housing_info);
            }
            if ("landlordid".equals(cond)) {
                housing_info.setLandlordid(name);
                housing_infoList = housing_infoService.getHousing_infoByLike(housing_info);
            }
            if ("contents".equals(cond)) {
                housing_info.setContents(name);
                housing_infoList = housing_infoService.getHousing_infoByLike(housing_info);
            }
        }
        map.put("housing_infoList", housing_infoList);
        return "admin/query";
    }
*/

    // 按主键查询数据
    @RequestMapping("getById.action")
    @ResponseBody
    public ResultUtil getById(String id, Map<String, Object> map) {
        Map<String, Object> parameter = new HashMap<String, Object>();
        Housing_info housing_info = housing_infoService.getById(id);
        map.put("housing_info", housing_info);
        List<Landlord> landlordList = landlordService.getAll(parameter);
         map.put("landlordList", landlordList);
        return ResultUtil.ok().data(map);//给前端数据
    }


}
