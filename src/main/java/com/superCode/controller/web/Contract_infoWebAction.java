package com.superCode.controller.web;

import com.superCode.entity.Housing_info;
import com.superCode.entity.Contract_info;
import com.superCode.entity.Landlord;
import com.superCode.entity.Users;
import com.superCode.service.Housing_infoService;
import com.superCode.service.Contract_infoService;
import com.superCode.service.LandlordService;
import com.superCode.service.UsersService;
import com.superCode.util.ResultUtil;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

//定义为控制器
@Controller
// 设置路径
@RequestMapping("/web")
public class Contract_infoWebAction extends BaseWebAction {
    // 注入AdminService 并getter setter
    @Resource
    private Contract_infoService contract_infoService;
    @Resource
    private Housing_infoService housing_infoService;

    @Resource
    private LandlordService landlordService;

    @Resource
    private UsersService usersService;


    // 用户查看自己的
    @RequestMapping("contract_info.action")
    @ResponseBody
    public ResultUtil  contract_info(HttpServletRequest request, Map<String, Object> map) {

        Map<String, Object> parameter = new HashMap<String, Object>();
       /* String contract_infokeyword = request.getParameter("contract_infokeyword");//搜索关键词
        if (contract_infokeyword != null && contract_infokeyword != "") parameter.put("keyword", contract_infokeyword);//搜索参数
*/


        String usersid = (String) this.getSession().getAttribute("usersid");
        parameter.put("usersid", usersid);
        int rowCountTotal = contract_infoService.getRowSize(parameter);//获取总数
        int pageSize = 10;// 分页大小
        int pageNumber = 1; //设定页面参数

        if (request.getParameter("pageNumber") != null && request.getParameter("pageNumber") != "") {
            pageNumber = Integer.parseInt(request.getParameter("pageNumber"));//当前页
        }
        parameter.put("limit", pageSize);//搜索参数
        parameter.put("start", (pageNumber - 1) * pageSize);//搜索参数


        List<Contract_info> contract_infoList = contract_infoService.getAll(parameter);


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

        //map.put("contract_infokeyword", contract_infokeyword);
        map.put("pager", pager);


        map.put("contract_infoList", contract_infoList);
        return ResultUtil.ok().data(map);//给前端数据
    }

/*

    // 按主键查询数据
    @RequestMapping("contract_infoDetail.action")
    @ResponseBody
    public ResultUtil  contract_infoDetail(String id, Map<String, Object> map) {

        Map<String, Object> parameter = new HashMap<String, Object>();
        Contract_info contract_info = contract_infoService.getById(id);
        map.put("contract_info", contract_info);
        ///List<Landlord> landlordList = landlordService.getAll(parameter);
        ///map.put("landlordList", landlordList);
        return "contract_infoDetail";//跳转到WebContent目录下对应的JSP页面
    }

*/

//房东管理-----------------------------------


    // 准备添加数据
    @RequestMapping("createContract_info.action")
    @ResponseBody
    public ResultUtil  create(Map<String, Object> map) {

        Map<String, Object> parameter = new HashMap<String, Object>();
        List<Users> usersList = usersService.getAll(parameter);
        map.put("usersList", usersList);
        List<Housing_info> housing_infoList = housing_infoService.getAll(parameter);
        map.put("housing_infoList", housing_infoList);
        return ResultUtil.ok().data(map);//给前端数据
    }

    // 添加数据
    @RequestMapping("addContract_info.action")
    @ResponseBody
    public ResultUtil  add(Contract_info contract_info) {
        String usersid = (String) this.getSession().getAttribute("usersid");

         
        contract_infoService.insert(contract_info);
        return ResultUtil.ok();//返回成功提示
    }

    // 通过主键删除数据
    @RequestMapping("deleteContract_info.action")
    @ResponseBody
    public ResultUtil  delete(String id) {
        contract_infoService.delete(id);
        return ResultUtil.ok();//返回成功提示
    }

    // 更新数据
    @RequestMapping("updateContract_info.action")
    @ResponseBody
    public ResultUtil  update(Contract_info contract_info) {
         
        contract_infoService.update(contract_info);
        return ResultUtil.ok();//返回成功提示
    }


    // 只获取当前登录的房东
    @RequestMapping("getListContract_info.action")
    @ResponseBody
    public ResultUtil  getList(HttpServletRequest request, Map<String, Object> map) {

        Map<String, Object> parameter = new HashMap<String, Object>();
        String keyword = request.getParameter("keyword");//搜索关键词
        if (keyword != null && keyword != "") parameter.put("keyword", keyword);//搜索参数

        String usersid = (String) this.getSession().getAttribute("usersid");
        Landlord landlord = landlordService.getById(usersid);
        parameter.put("landlordid", landlord.getLandlordid());//只获取当前登录的房东

        int rowCountTotal = contract_infoService.getRowSize(parameter);//获取总数
        int pageSize = 10;// 分页大小
        int pageNumber = 1; //设定页面参数

        if (request.getParameter("pageNumber") != null && request.getParameter("pageNumber") != "") {
            pageNumber = Integer.parseInt(request.getParameter("pageNumber"));//当前页
        }
        parameter.put("limit", pageSize);//搜索参数
        parameter.put("start", (pageNumber - 1) * pageSize);//搜索参数


        List<Contract_info> contract_infoList = contract_infoService.getAll(parameter);


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


        map.put("contract_infoList", contract_infoList);
        return ResultUtil.ok().data(map);//给前端数据
    }


    // 按主键查询数据
    @RequestMapping("getByIdContract_info.action")
    @ResponseBody
    public ResultUtil  getById(String id, Map<String, Object> map) {

        Map<String, Object> parameter = new HashMap<String, Object>();
        Contract_info contract_info = contract_infoService.getById(id);
        map.put("contract_info", contract_info);
        List<Users> usersList = usersService.getAll(parameter);
        map.put("usersList", usersList);
        List<Housing_info> housing_infoList = housing_infoService.getAll(parameter);
        map.put("housing_infoList", housing_infoList);
        return ResultUtil.ok().data(map);//给前端数据
    }


}
