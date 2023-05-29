package com.zsxb.controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.zsxb.po.Studio;
import com.zsxb.common.JsonResult;
import com.zsxb.service.StudioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * 
 *
 * @author dz
 * @date 2023-05-09
 */
@RestController
@RequestMapping("/studio")
public class StudioController {

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
        Page page = new Page(current, size);
        studioService.queryPage(page, studioName);

        return JsonResult.ok(page);
    }


    /**
     * 添加演出厅
     * @return
     */
    @PostMapping("/add")
    public JsonResult<Void> add(@RequestBody Studio studio) {

        studioService.add(studio);

        return JsonResult.ok();
    }


    /**
     * 删除演出厅
     * @param studioId
     * @return
     */
    @PostMapping("/delete")
    public JsonResult<Void> delete(int studioId) {

        studioService.delete(studioId);

        return JsonResult.ok();
    }


    /**
     * 修改演出厅
     * @param studio 演出厅信息
     * @return
     */
    @PostMapping("/update")
    public JsonResult<Void> update(@RequestBody Studio studio) {
        studioService.update(studio);

        return JsonResult.ok();
    }

}
