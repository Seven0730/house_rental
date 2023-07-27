package com.superCode.controller;

import com.superCode.util.ResultUtil;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Map;

/*上传*/
@Controller
@RequestMapping("/system")
public class SystemAction {

    // 后台主界面
    @RequestMapping("main.action")
    @ResponseBody
    public ResultUtil main(Map<String, Object> map) {
        return ResultUtil.ok().data(map);//给前端数据
    }


    /*上传图片*/
    @RequestMapping(value = "/image.action")
    @ResponseBody
    public ResultUtil upload(@RequestParam(value = "image", required = false) MultipartFile file, HttpServletRequest request, ModelMap model) {

        String path = request.getSession().getServletContext().getRealPath("upfiles");//获取文件在服务器的储存位置
        String fileName = file.getOriginalFilename(); //获取原始文件名称(包含格式)
        int i = fileName.lastIndexOf(".");


        Date currentTime = new Date();
        SimpleDateFormat formatter = new SimpleDateFormat("MMddHHmmss");//获取时间用于创建的文件名称
        String name = formatter.format(currentTime);

        String type = fileName.substring(i + 1);//获取文件类型，以最后一个`.`为标识
        fileName = name + "." + type;//文件名称
        File targetFile = new File(path, fileName);//在指定路径下创建一个文件   //将文件保存到服务器指定位置
        if (!targetFile.exists()) {
            targetFile.mkdirs();
        }

        // 保存
        try {
            file.transferTo(targetFile);
            // return "aa";
            return ResultUtil.ok().data("imageFileName", "upfiles/" + fileName);//给前端数据
        } catch (Exception e) {
            e.printStackTrace();


            return ResultUtil.error().message("上传失败");
        }

    }


}
