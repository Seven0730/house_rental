package com.superCode.controller.admin;

import com.superCode.entity.Admin;
import com.superCode.entity.JwtEntityAdmin;
import com.superCode.service.AdminService;
import com.superCode.util.JwtUtil;
import com.superCode.util.ResultUtil;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import java.util.Date;
import java.util.List;
import java.util.Map;

/*后台登录*/
//定义为控制器
@Controller
// 设置路径
@RequestMapping("/admin/login")
public class LoginAction extends BaseAdminAction {

    @Resource
    private AdminService adminService;


    // 管理员  登录
    @RequestMapping("login.action")
    @ResponseBody
    public ResultUtil login(Map<String, Object> map) {
        String username = this.getRequest().getParameter("username");
        String password = this.getRequest().getParameter("password");
        String role = this.getRequest().getParameter("role");
        String code = this.getRequest().getParameter("code");

        JwtEntityAdmin jwtEntityAdmin = new JwtEntityAdmin();


        //if (role.equals("管理员")) {
        Admin adminEntity = new Admin();
        adminEntity.setUsername(username);
        List<Admin> adminlist = adminService.getByCond(adminEntity);
        if (adminlist.size() == 0) {
            return ResultUtil.error().message("用户名不存在！");

        } else {
            Admin admin = adminlist.get(0);
            if (password.equals(admin.getPassword())) {

                jwtEntityAdmin.setAdminid(admin.getAdminid());
                jwtEntityAdmin.setUsername(admin.getUsername());
                jwtEntityAdmin.setRealname(admin.getRealname());

                map.put("adminid", admin.getAdminid());
                map.put("username", admin.getUsername());
                map.put("info", admin);


                this.getRequest().getSession().setAttribute("adminid", admin.getAdminid());
                this.getRequest().getSession().setAttribute("username", admin.getUsername());
                this.getRequest().getSession().setAttribute("realname", admin.getRealname());
                jwtEntityAdmin.setTime(new Date().getTime());
                // 获取 jwt 数据，设置过期时间
                String jwt = JwtUtil.getJwtToken(jwtEntityAdmin, JwtUtil.DEFAULT_EXPIRE);

                map.put("token", jwt);


                return ResultUtil.ok().message("登录成功").data(map);

            } else {
                return ResultUtil.error().message("密码错误！");

            }
        }
        //}
    }


    // 管理员 退出登录
    @RequestMapping("exit.action")
    @ResponseBody
    public ResultUtil exit() {

        // 生成并返回一个无效的 token
        String jwt = JwtUtil.getJwtToken(null, 1000L);
        return ResultUtil.ok().message("登出成功").data("token", jwt);
    }


}
