package com.zsxb.controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.zsxb.po.Studio;
import com.zsxb.common.JsonResult;
import com.zsxb.service.StudioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 *
 * 演出厅操作控制层
 *
 * @author dz
 * @date 2023-05-09
 */
@RestController // 标识控制层和返回json格式数据
@RequestMapping("/studio") // 演出厅请求前缀
public class StudioController {

    // 注入演出厅service，可以直接使用
    @Autowired
    private StudioService studioService;

    /**
     * 分页查询演出厅
     * @param current   当前页
     * @param size  每页显示个数
     * @param studioName    演出厅名称（可为null）
     * @return
     */
    @PostMapping("/page/{current}/{size}")
    public JsonResult<Page> page(@PathVariable int current,
                                 @PathVariable int size,
                                 @RequestParam(required = false) String studioName) {
        // 根据当前页和每页显示个数构建分页page对象
        Page page = new Page(current, size);
        // 调用service的queryPage方法，查询分页演出厅数据，放在page.records属性里
        studioService.queryPage(page, studioName);

        // 返回page对象
        return JsonResult.ok(page);
    }


    /**
     * 添加演出厅
     * @param studio    添加的演出厅信息 @RequestBody 表示传过来的数据是json数据
     * @return
     */
    @PostMapping("/add")
    public JsonResult<Void> add(@RequestBody Studio studio) {

        // 添加演出厅
        studioService.add(studio);

        return JsonResult.ok();
    }


    /**
     * 删除演出厅
     * @param studioId  删除的演出厅id
     * @return
     */
    @PostMapping("/delete")
    public JsonResult<Void> delete(int studioId) {

        // 删除演出厅
        studioService.delete(studioId);

        return JsonResult.ok();
    }


    /**
     * 查询所有演出厅
     * @return
     */
    @PostMapping("/list")
    public JsonResult<List<Studio>> list() {

        // 调用list方法，查询所有演出厅
        List<Studio> studioList = studioService.list();

        return JsonResult.ok(studioList);
    }




    /**
     * 修改演出厅
     * @param studio 演出厅信息
     * @return
     */
    @PostMapping("/update")
    public JsonResult<Void> update(@RequestBody Studio studio) {

        // 修改演出厅信息
        studioService.update(studio);

        return JsonResult.ok();
    }

}
