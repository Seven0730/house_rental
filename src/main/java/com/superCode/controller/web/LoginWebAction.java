package com.superCode.controller.web;

import com.superCode.entity.JwtEntityWeb;
import com.superCode.entity.Landlord;
import com.superCode.entity.Users;
import com.superCode.service.LandlordService;
import com.superCode.service.UsersService;
import com.superCode.util.JwtUtil;
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
public class LoginWebAction extends BaseWebAction {


    @Resource
    private UsersService usersService;
    // 注入AdminService 并getter setter
    /*-------------------------------------房东用户*/
    @Resource
    private LandlordService landlordService;

    /*-------------------------------------用户*/
    // 准备登录
    @RequestMapping("preLogin.action")
    @ResponseBody
    public ResultUtil prelogin() {

        return ResultUtil.ok();//给前端数据
    }

    // 用户登录
    @RequestMapping("login.action")
    @ResponseBody
    public ResultUtil login(Map<String, Object> map) {

        String username = this.getRequest().getParameter("username");
        String password = this.getRequest().getParameter("password");

        JwtEntityWeb jwtEntityWeb = new JwtEntityWeb();


        Users u = new Users();
        u.setUsername(username);
        List<Users> usersList = usersService.getByCond(u);
        if (usersList.size() == 0) {

            return ResultUtil.error().message("用户名不存在！");


        } else {
            Users users = usersList.get(0);
            if (password.equals(users.getPassword())) {
                if ("1".equals(users.getIscooled())) {

                    return ResultUtil.error().message("账户被冻结无法登录！");


                } else {

                    jwtEntityWeb.setUsersid(users.getUsersid());
                    jwtEntityWeb.setUsers(users);
                    jwtEntityWeb.setRole(1);
                    map.put("usersid", users.getUsersid());
                    map.put("role", 1);
                    map.put("info", users);////前端共用info

                    this.getSession().setAttribute("usersid", users.getUsersid());
                    this.getSession().setAttribute("users", users);//后端分开
                    this.getSession().setAttribute("role", 1);
                    jwtEntityWeb.setTime(new Date().getTime());
                    // 获取 jwt 数据，设置过期时间
                    String jwt = JwtUtil.getJwtToken(jwtEntityWeb, JwtUtil.DEFAULT_EXPIRE);

                    map.put("token", jwt);


                    return ResultUtil.ok().message("登录成功").data(map);


                }
            } else {
                return ResultUtil.error().message("密码错误！");


            }
        }
    }

    // 退出登录
    @RequestMapping("exit.action")
    @ResponseBody
    public ResultUtil exit() {

        // 生成并返回一个无效的 token
        String jwt = JwtUtil.getJwtToken(null, 1000L);
        return ResultUtil.ok().message("登出成功").data("token", jwt);
    }


    //------------------------------------房东用户登录
    // 准备登录
    @RequestMapping("preLogin_2.action")
    @ResponseBody
    public ResultUtil prelogin_2() {

        return ResultUtil.ok();//给前端数据
    }

    // 用户登录
    @RequestMapping("login_2.action")
    @ResponseBody
    public ResultUtil login_2(Map<String, Object> map) {

        String username = this.getRequest().getParameter("username");
        String password = this.getRequest().getParameter("password");
        JwtEntityWeb jwtEntityWeb = new JwtEntityWeb();
        Landlord d = new Landlord();
        d.setUsername(username);
        List<Landlord> dList = this.landlordService.getByCond(d);
        if (dList.size() == 0) {
            return ResultUtil.error().message("用户名不存在！");
        } else {
            Landlord landlord = dList.get(0);
            if (password.equals(landlord.getPassword())) {

                jwtEntityWeb.setUsersid(landlord.getLandlordid());
                jwtEntityWeb.setLandlord(landlord);
                jwtEntityWeb.setRole(2);

                map.put("usersid", landlord.getLandlordid());
                map.put("role", 2);
                map.put("info", landlord);////前端共用info

                this.getSession().setAttribute("usersid", landlord.getLandlordid());
                this.getSession().setAttribute("landlord", landlord);//后端分开
                this.getSession().setAttribute("role", 2);

                jwtEntityWeb.setTime(new Date().getTime());
                // 获取 jwt 数据，设置过期时间
                String jwt = JwtUtil.getJwtToken(jwtEntityWeb, JwtUtil.DEFAULT_EXPIRE);

                map.put("token", jwt);

                return ResultUtil.ok().message("登录成功").data(map);
            } else {
                return ResultUtil.error().message("密码错误！");

            }
        }
    }


    // 退出登录
    @RequestMapping("exit_2.action")
    @ResponseBody
    public ResultUtil exit_2() {

        // 生成并返回一个无效的 token
        String jwt = JwtUtil.getJwtToken(null, 1000L);
        return ResultUtil.ok().message("登出成功").data("token", jwt);
    }


}
