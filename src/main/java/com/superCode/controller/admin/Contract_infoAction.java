package com.superCode.controller.admin;

import com.superCode.entity.Contract_info;
import com.superCode.service.Contract_infoService;
import com.superCode.util.ResultUtil;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

//定义为控制器
@Controller
// 设置路径
@RequestMapping("/admin/contract_info")
public class Contract_infoAction extends BaseAdminAction {
    // 注入AdminService 并getter setter
    @Resource
    private Contract_infoService contract_infoService;

    /* @Resource
     private Contract_infoService contract_infoService;


     // 准备添加数据
     @RequestMapping("create.action")
     @ResponseBody
    public ResultUtil  create(Map<String, Object> map) {
         Map<String, Object> parameter = new HashMap<String, Object>();
         //List<Landlord> landlordList = landlordService.getAll(parameter);
         // map.put("landlordList", landlordList);
         return "admin/contract_info/add";//跳转到WebContent目录下对应的JSP页面
     }

     // 添加数据
     @RequestMapping("add.action")
     @ResponseBody
    public ResultUtil  add(Contract_info contract_info) {
         contract_info.setIscooled(0);//管理员添加默认直接上线
         contract_info.setTerm(0);
         Date currentTime = new Date();
         SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
         String date = formatter.format(currentTime);
         contract_info.setCreatedate(date);
         contract_infoService.insert(contract_info);
         return "redirect:/admin/contract_info/getAll.action";//跳转到action
     }

     // 通过主键删除数据
     @RequestMapping("delete.action")
     @ResponseBody
    public ResultUtil  delete(String id) {
         contract_infoService.delete(id);
         return "redirect:/admin/contract_info/getAll.action";//跳转到action
     }

     // 更新数据
     @RequestMapping("update.action")
     @ResponseBody
    public ResultUtil  update(Contract_info contract_info) {
         contract_infoService.update(contract_info);
         return "redirect:/admin/contract_info/getAll.action";//跳转到action
     }
 */
    // 显示全部数据
    @RequestMapping("getAll.action")
    @ResponseBody
    public ResultUtil getAll(HttpServletRequest request, Map<String, Object> map) {
        Map<String, Object> parameter = new HashMap<String, Object>();
        String keyword = request.getParameter("keyword");//搜索关键词
        if (keyword != null && keyword != "") parameter.put("keyword", keyword);//搜索参数


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

    // 按条件查询数据 (模糊查询)
 /*
    @RequestMapping("queryContract_infoByCond.action")
    @ResponseBody
    public ResultUtil  queryContract_infoByCond(String cond, String name, Map<String, Object> map) {
        List<Contract_info> contract_infoList = new ArrayList<Contract_info>();
        Contract_info contract_info = new Contract_info();
        if (cond != null) {
            if ("username".equals(cond)) {
                contract_info.setUsername(name);
                contract_infoList = contract_infoService.getContract_infoByLike(contract_info);
            }
            if ("password".equals(cond)) {
                contract_info.setPassword(name);
                contract_infoList = contract_infoService.getContract_infoByLike(contract_info);
            }
            if ("contract_infoname".equals(cond)) {
                contract_info.setContract_infoname(name);
                contract_infoList = contract_infoService.getContract_infoByLike(contract_info);
            }
            if ("slogan".equals(cond)) {
                contract_info.setSlogan(name);
                contract_infoList = contract_infoService.getContract_infoByLike(contract_info);
            }
            if ("createdate".equals(cond)) {
                contract_info.setCreatedate(name);
                contract_infoList = contract_infoService.getContract_infoByLike(contract_info);
            }
            if ("contact".equals(cond)) {
                contract_info.setContact(name);
                contract_infoList = contract_infoService.getContract_infoByLike(contract_info);
            }
            if ("landlordid".equals(cond)) {
                contract_info.setLandlordid(name);
                contract_infoList = contract_infoService.getContract_infoByLike(contract_info);
            }
            if ("contents".equals(cond)) {
                contract_info.setContents(name);
                contract_infoList = contract_infoService.getContract_infoByLike(contract_info);
            }
        }
        map.put("contract_infoList", contract_infoList);
        return "admin/query";
    }

/*

    // 按主键查询数据
    @RequestMapping("getById.action")
    @ResponseBody
    public ResultUtil  getById(String id, Map<String, Object> map) {
        Map<String, Object> parameter = new HashMap<String, Object>();
        Contract_info contract_info = contract_infoService.getById(id);
        map.put("contract_info", contract_info);
        ///List<Landlord> landlordList = landlordService.getAll(parameter);
        ///map.put("landlordList", landlordList);
        return "admin/contract_info/edit";//跳转到WebContent目录下对应的JSP页面
    }
*/


}
