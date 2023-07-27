package com.superCode.controller.admin;

import com.superCode.entity.Admin;
import com.superCode.service.AdminService;
import com.superCode.util.ResultUtil;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

//定义为控制器
@Controller
// 设置路径
@RequestMapping("/admin/admin")
public class AdminAction extends BaseAdminAction {
    // 注入AdminService
    @Resource
    private AdminService adminService;

    // 修改密码
    @RequestMapping("editpwd.action")
    @ResponseBody
    public ResultUtil editpwd(HttpServletRequest request) {
        String adminid = (String) request.getSession().getAttribute("adminid");
        String password = request.getParameter("password");
        String repassword = request.getParameter("repassword");
        Admin admin = adminService.getById(adminid);
        if (password.equals(admin.getPassword())) {
            admin.setPassword(repassword);
            adminService.update(admin);
        } else {

            return ResultUtil.error().message("旧密码错误！");

        }
        return ResultUtil.ok();//给前端数据
    }


    // 准备添加数据
    @RequestMapping("create.action")
    @ResponseBody
    public ResultUtil create(Map<String, Object> map) {

        return ResultUtil.ok().data(map);//给前端数据
    }

    // 添加数据
    @RequestMapping("add.action")
    @ResponseBody
    public ResultUtil add(Admin admin) {
        adminService.insert(admin);
        return ResultUtil.ok();//返回成功提示
    }

    // 通过主键删除数据
    @RequestMapping("delete.action")
    @ResponseBody
    public ResultUtil delete(String id) {
        adminService.delete(id);
        return ResultUtil.ok();//返回成功提示
    }

    // 更新数据
    @RequestMapping("update.action")
    @ResponseBody
    public ResultUtil update(Admin admin) {
        adminService.update(admin);
        return ResultUtil.ok();//返回成功提示
    }

    // 显示全部数据
    @RequestMapping("getAll.action")
    @ResponseBody
    public ResultUtil getAll(HttpServletRequest request, Map<String, Object> map) throws UnsupportedEncodingException {
        Map<String, Object> parameter = new HashMap<String, Object>();
        String keyword = request.getParameter("keyword");//搜索关键词
        if (keyword != null && keyword != "") parameter.put("keyword", keyword);//搜索参数

        int rowCountTotal = adminService.getRowSize(parameter);//获取总数
        int pageSize = 10;// 分页大小
        int pageNumber = 1; //设定页面参数

        if (request.getParameter("pageNumber") != null && request.getParameter("pageNumber") != "") {
            pageNumber = Integer.parseInt(request.getParameter("pageNumber"));//当前页
        }
        parameter.put("limit", pageSize);//搜索参数
        parameter.put("start", (pageNumber - 1) * pageSize);//搜索参数
        List<Admin> adminList = adminService.getAll(parameter);


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


        map.put("adminList", adminList);
        map.put("keyword", keyword);
        map.put("pager", pager);
        return ResultUtil.ok().data(map);//给前端数据
    }

    // 按条件查询数据 (模糊查询)----备用
    /*@RequestMapping("queryByCond.action")
@ResponseBody
    public ResultUtil   queryByCond(String cond, String name, Map<String, Object> map) {
        List<Admin> adminList = new ArrayList<Admin>();
        Admin admin = new Admin();
        if (cond != null) {
            if ("username".equals(cond)) {
                admin.setUsername(name);
                adminList = adminService.getByLike(admin);
            }
            if ("password".equals(cond)) {
                admin.setPassword(name);
                adminList = adminService.getByLike(admin);
            }
            if ("realname".equals(cond)) {
                admin.setRealname(name);
                adminList = adminService.getByLike(admin);
            }
            if ("contact".equals(cond)) {
                admin.setContact(name);
                adminList = adminService.getByLike(admin);
            }
        }
        map.put("adminList", adminList);
        return ResultUtil.ok().data(map);//给前端数据
    }*/

    // 按主键查询数据
    @RequestMapping("getById.action")
    @ResponseBody
    public ResultUtil getById(String id, Map<String, Object> map) {
        Admin admin = adminService.getById(id);
        map.put("admin", admin);
        return ResultUtil.ok().data(map);//给前端数据
    }


}
