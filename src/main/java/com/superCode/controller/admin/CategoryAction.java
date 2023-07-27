package com.superCode.controller.admin;

import com.superCode.entity.Category;
import com.superCode.service.CategoryService;
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
@RequestMapping("/admin/category")
public class CategoryAction extends BaseAdminAction {
    // 注入AdminService 并getter setter
    @Resource
    private CategoryService categoryService;

    // 准备添加数据
    @RequestMapping("create.action")
    @ResponseBody
    public ResultUtil create(Map<String, Object> map) {
        return ResultUtil.ok().data(map);//给前端数据
    }

    // 添加数据
    @RequestMapping("add.action")
    @ResponseBody
    public ResultUtil add(Category category) {
        categoryService.insert(category);
        return ResultUtil.ok();//返回成功提示
    }

    // 通过主键删除数据
    @RequestMapping("delete.action")
    @ResponseBody
    public ResultUtil delete(String id) {
        categoryService.delete(id);
        return ResultUtil.ok();//返回成功提示
    }

    // 更新数据
    @RequestMapping("update.action")
    @ResponseBody
    public ResultUtil update(Category category) {
        categoryService.update(category);
        return ResultUtil.ok();//返回成功提示
    }

    // 显示全部数据
    @RequestMapping("getAll.action")
    @ResponseBody
    public ResultUtil getAll(HttpServletRequest request, Map<String, Object> map) throws UnsupportedEncodingException {
        Map<String, Object> parameter = new HashMap<String, Object>();
        String keyword = request.getParameter("keyword");//搜索关键词
        if (keyword != null && keyword != "") parameter.put("keyword", keyword);//搜索参数
int rowCountTotal = categoryService.getRowSize(parameter);//获取总数
        int pageSize = 10;// 分页大小
        int pageNumber = 1; //设定页面参数

        if (request.getParameter("pageNumber") != null && request.getParameter("pageNumber") != "") {
            pageNumber = Integer.parseInt(request.getParameter("pageNumber"));//当前页
        }
        parameter.put("limit", pageSize);//搜索参数
        parameter.put("start", (pageNumber - 1) * pageSize);//搜索参数
        List<Category> categoryList = categoryService.getAll(parameter);


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

        map.put("categoryList", categoryList);
        return ResultUtil.ok().data(map);//给前端数据
    }

    // 按条件查询数据 (模糊查询)----备用
/*
    @RequestMapping("queryCategoryByCond.action")
@ResponseBody
    public ResultUtil   queryCategoryByCond(String cond, String name, Map<String, Object> map) {
        List<Category> categoryList = new ArrayList<Category>();
        Category category = new Category();
        if (cond != null) {
            if ("categoryname".equals(cond)) {
                category.setCategoryname(name);
                categoryList = categoryService.getCategoryByLike(category);
            }
        }
        map.put("categoryList", categoryList);
        return "admin/query";
    }
*/

    // 按主键查询数据
    @RequestMapping("getById.action")
    @ResponseBody
    public ResultUtil getCategoryById(String id, Map<String, Object> map) {
        Category category = categoryService.getById(id);
        map.put("category", category);
        return ResultUtil.ok().data(map);//给前端数据
    }


}
