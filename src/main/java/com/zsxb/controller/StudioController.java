package com.zsxb.controller;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.zsxb.entity.Studio;
import com.zsxb.service.IStudioService;
import com.zsxb.service.impl.StudioServiceImpl;
import com.zsxb.util.JsonResult;
import io.swagger.annotations.Api;
import io.swagger.util.Json;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * 
 *
 * @author dz
 * @date 2023-05-09
 */
@RestController
@RequestMapping("/studio")
@Api(value = "",tags = "")
public class StudioController {

    @Autowired
    private IStudioService studioService;

    @PostMapping("/search")
    public JsonResult<List<Studio>> search(String name) {

        List<Studio> studioList;
        if (StringUtils.isEmpty(name)) {
            studioList = studioService.list();
        } else {
            LambdaQueryWrapper<Studio> query = new LambdaQueryWrapper<>();
            query.eq(Studio::getStudioName, name);
            query.eq(Studio::getStudioStatus, 1);
            studioList = studioService.list(query);
        }
        return new JsonResult<>(200, studioList);
    }

}
