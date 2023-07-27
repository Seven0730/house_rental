package com.superCode.controller.web;

import com.superCode.entity.Users;
import com.superCode.service.UsersService;
import com.superCode.util.ResultUtil;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import java.util.Date;
import java.util.List;
import java.util.Map;

//定义为控制器
@Controller
// 设置路径
@RequestMapping("/web")
public class UserWebAction extends BaseWebAction {


    @Resource
    private UsersService usersService;
    // 注入AdminService 并getter setter

    // 准备注册
    @RequestMapping("preReg.action")
    @ResponseBody
    public ResultUtil preReg() {

        return ResultUtil.ok();//给前端数据
    }

    // 用户注册
    @RequestMapping("register.action")
    @ResponseBody
    public ResultUtil register(Users users, Map<String, Object> map) {

        Users u = new Users();
        u.setUsername(users.getUsername());
        List<Users> usersList = usersService.getByCond(u);
        if (usersList.size() == 0) {

            users.setRegdate(new Date());
            usersService.insert(users);


            return ResultUtil.ok().message("注册成功,请登录");

        } else {
            return ResultUtil.error().message("用户名已存在");//给前端数据
        }
    }


    // 准备修改密码
    @RequestMapping("prePwd.action")
    @ResponseBody
    public ResultUtil prePwd() {


        return ResultUtil.ok();//给前端数据
    }

    // 修改密码
    @RequestMapping("editpwd.action")
    @ResponseBody
    public ResultUtil editpwd(Map<String, Object> map) {

        String usersid = (String) this.getSession().getAttribute("usersid");
        String password = this.getRequest().getParameter("password");
        String repassword = this.getRequest().getParameter("repassword");
        Users users = usersService.getById(usersid);
        if (password.equals(users.getPassword())) {
            users.setPassword(repassword);
            usersService.update(users);
            return ResultUtil.ok().message("修改成功 ");

        } else {
            return ResultUtil.error().message("旧密码错误");//给前端数据

        }
    }


    // 准备修改信息
    @RequestMapping("preInfo.action")
    @ResponseBody

    public ResultUtil preInfo(String id, Map<String, Object> map) {

        Users users = usersService.getById(id);
        map.put("users", users);
        return ResultUtil.ok().data(map);//给前端数据

    }

    // 修改信息
    @RequestMapping("editinfo.action")
    @ResponseBody
    public ResultUtil editinfo(Map<String, Object> map) {

        String usersid = (String) this.getSession().getAttribute("usersid");
        String realname = this.getRequest().getParameter("realname");
        String sex = this.getRequest().getParameter("sex");
        String createdate = this.getRequest().getParameter("createdate");
        String contact = this.getRequest().getParameter("contact");
        String image = this.getRequest().getParameter("image");
        String college = this.getRequest().getParameter("college");
        String major = this.getRequest().getParameter("major");
        String address = this.getRequest().getParameter("address");
        String qq = this.getRequest().getParameter("qq");
        String idcard = this.getRequest().getParameter("idcard");
        String email = this.getRequest().getParameter("email");
        String contract_info0 = this.getRequest().getParameter("contract_info0");
        String contract_info1 = this.getRequest().getParameter("contract_info1");
        String contract_info2 = this.getRequest().getParameter("contract_info2");
        String contract_info3 = this.getRequest().getParameter("contract_info3");
        String contract_info4 = this.getRequest().getParameter("contract_info4");
        String contract_info5 = this.getRequest().getParameter("contract_info5");
        String contract_info6 = this.getRequest().getParameter("contract_info6");


        Users users = usersService.getById(usersid);
        users.setRealname(realname);
        users.setSex(sex);
        users.setCreatedate(createdate);
        users.setContact(contact);
        users.setImage(image);
        users.setCollege(college);
        users.setMajor(major);
        users.setAddress(address);
        users.setQq(qq);

        users.setIdcard(idcard);
        users.setEmail(email);
        users.setContract_info0(contract_info0);
        users.setContract_info1(contract_info1);
        users.setContract_info2(contract_info2);
        users.setContract_info3(contract_info3);
        users.setContract_info4(contract_info4);
        users.setContract_info5(contract_info5);
        users.setContract_info6(contract_info6);
        usersService.update(users);
        return ResultUtil.ok().message("修改成功");


    }


      

}
