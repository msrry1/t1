package com.zsxb.controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.zsxb.common.JsonResult;
import com.zsxb.po.Play;
import com.zsxb.exception.FileException;
import com.zsxb.service.PlayService;
import com.zsxb.service.impl.FileUploadService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

/**
 *
 * 剧目操作控制层
 *
 * @author dz
 * @date 2023-05-09
 */
@RestController  // 标识控制层、返回格式json
@RequestMapping("/play")    // 请求路径/play
public class PlayController {

    // 自动注入剧目的service，可以直接使用这个对象
    @Autowired
    private PlayService playService;

    // 自动注入fileUploadService，可以直接使用这个对象对剧目的图片进行上传
    @Autowired
    private FileUploadService fileUploadService;

    /**
     * 分页查询剧目
     * @param current   当前页
     * @param size  每页显示个数
     * @param playName    剧目名称（可为null）
     * @return
     */
    @PostMapping("/page/{current}/{size}")
    public JsonResult<Page> page(@PathVariable int current,
                                 @PathVariable int size,
                                 @RequestParam(required = false) String playName) {
        // 创建分页对象
        Page page = new Page(current, size);
        // 查询分页对象，查询完成后play集合会放在page.records属性里
        playService.queryPage(page, playName);

        // 返回page对象
        return JsonResult.ok(page);
    }


    /**
     * 添加剧目
     * @return
     */
    @PostMapping("/add")
    public JsonResult<Void> add(@RequestPart("file") MultipartFile file,
                                @RequestPart("play") Play play) {
        // 图片url
        String imageUrl;

        if (file != null) {
            // 传过来的图片不为空时调用upload方法得到 aliyun图片路径，上传图片
            imageUrl = fileUploadService.upload(file);
        } else {
            throw new FileException("文件上传失败！");
        }
        // 设置路径，空就用数据库默认
        play.setPlayImage(imageUrl);
        // 调用add方法，添加剧目
        playService.add(play);
        return JsonResult.ok();
    }


    /**
     * 删除剧目
     * @param playId 剧目id
     * @return
     */
    @PostMapping("/delete")
    public JsonResult<Void> delete(int playId) {

        // 调用delete方法，删除剧目
        playService.delete(playId);

        return JsonResult.ok();
    }

    /**
     * 修改剧目
     * @param play 剧目信息
     * @return
     */
    @PostMapping
    public JsonResult<Void> update(@RequestBody Play play) {

        // 调用update方法，修改剧目
        playService.update(play);

        return JsonResult.ok();
    }

}
