package com.superCode.controller.admin;

import com.superCode.entity.Users;
import com.superCode.service.UsersService;
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
@RequestMapping("/admin/users")
public class UsersAction extends BaseAdminAction {
    // 注入AdminService
    @Resource
    private UsersService usersService;

    // 准备添加数据
/*
    @RequestMapping("create.action")
@ResponseBody
    public ResultUtil   create(Map<String, Object> map) {

        return ResultUtil.ok().data(map);//给前端数据
    }

    // 添加数据
    @RequestMapping("add.action")
@ResponseBody
    public ResultUtil   add(Users users) {
        usersService.insert(users);
        return ResultUtil.ok();//返回成功提示//返回成功提示
    }
*/

    // 通过主键删除数据
    @RequestMapping("delete.action")
    @ResponseBody
    public ResultUtil delete(String id) {
        usersService.delete(id);
        return ResultUtil.ok();//返回成功提示//返回成功提示
    }

    // 冻结数据
    @RequestMapping("iscooled.action")
    @ResponseBody
    public ResultUtil iscooled(String id, String iscooled) {
        Users users = usersService.getById(id);
        users.setIscooled(iscooled);
        usersService.update(users);
        return ResultUtil.ok();//返回成功提示//返回成功提示
    }

    /* // 更新数据
     @RequestMapping("update.action")
 @ResponseBody
     public ResultUtil   update(Users users) {
         usersService.update(users);
         return ResultUtil.ok();//返回成功提示//返回成功提示
     }
 */
    // 显示全部数据
    @RequestMapping("getAll.action")
    @ResponseBody
    public ResultUtil getAll(HttpServletRequest request, Map<String, Object> map) throws UnsupportedEncodingException {
        Map<String, Object> parameter = new HashMap<String, Object>();
        String keyword = request.getParameter("keyword");//搜索关键词
        if (keyword != null && keyword != "") parameter.put("keyword", keyword);//搜索参数
int rowCountTotal = usersService.getRowSize(parameter);//获取总数
        int pageSize = 10;// 分页大小
        int pageNumber = 1; //设定页面参数

        if (request.getParameter("pageNumber") != null && request.getParameter("pageNumber") != "") {
            pageNumber = Integer.parseInt(request.getParameter("pageNumber"));//当前页
        }
        parameter.put("limit", pageSize);//搜索参数
        parameter.put("start", (pageNumber - 1) * pageSize);//搜索参数
        List<Users> usersList = usersService.getAll(parameter);


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

        map.put("usersList", usersList);
        return ResultUtil.ok().data(map);//给前端数据
    }

    // 按条件查询数据 (模糊查询)
/*    @RequestMapping("queryUsersByCond.action")
@ResponseBody
    public ResultUtil   queryUsersByCond(String cond, String name, Map<String, Object> map) {
        List<Users> usersList = new ArrayList<Users>();
        Users users = new Users();
        if (cond != null) {
            if ("username".equals(cond)) {
                users.setUsername(name);
                usersList = usersService.getUsersByLike(users);
            }
            if ("password".equals(cond)) {
                users.setPassword(name);
                usersList = usersService.getUsersByLike(users);
            }
            if ("realname".equals(cond)) {
                users.setRealname(name);
                usersList = usersService.getUsersByLike(users);
            }
            if ("sex".equals(cond)) {
                users.setSex(name);
                usersList = usersService.getUsersByLike(users);
            }
            if ("createdate".equals(cond)) {
                users.setCreatedate(name);
                usersList = usersService.getUsersByLike(users);
            }
            if ("contact".equals(cond)) {
                users.setContact(name);
                usersList = usersService.getUsersByLike(users);
            }
            if ("image".equals(cond)) {
                users.setImage(name);
                usersList = usersService.getUsersByLike(users);
            }
            if ("regdate".equals(cond)) {
                users.setRegdate(name);
                usersList = usersService.getUsersByLike(users);
            }
        }
        map.put("usersList", usersList);
        return "admin/query";
    }*/


    // 按主键查询数据
    @RequestMapping("getById.action")
    @ResponseBody
    public ResultUtil getUsersById(String id, Map<String, Object> map) {
        Users users = usersService.getById(id);
        map.put("users", users);
        return ResultUtil.ok().data(map);//给前端数据
    }


}
