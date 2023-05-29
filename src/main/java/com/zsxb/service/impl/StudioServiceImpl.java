package com.zsxb.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.zsxb.entity.Studio;
import com.zsxb.exception.StudioException;
import com.zsxb.mapper.StudioMapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.zsxb.service.StudioService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * 
 *
 * @author dz
 * @date 2023-05-09
 */
@Service
public class StudioServiceImpl implements StudioService {

    @Autowired
    private StudioMapper studioMapper;

    @Override
    public void queryPage(Page page, String studioName) {

        // 构建查询条件
        LambdaQueryWrapper<Studio> studioLambdaQueryWrapper = new LambdaQueryWrapper<>();
        if (!StringUtils.isBlank(studioName)) {
            // 演出厅名称不为空，添加查询条件
            // 模糊查询，包含演出厅名字即可
            studioLambdaQueryWrapper.like(Studio::getStudioName, studioName);
        }
        // 根据演出厅id升序
        studioLambdaQueryWrapper.orderByAsc(Studio::getStudioId);

        // 查询演出厅
        studioMapper.selectPage(page, studioLambdaQueryWrapper);
    }

    @Override
    public void add(Studio studio) {

        String studioName = studio.getStudioName();

        // 1. 判断演出厅名称是否重复
        LambdaQueryWrapper<Studio> studioNameQueryWrapper = new LambdaQueryWrapper<>();
        studioNameQueryWrapper.eq(Studio::getStudioName, studioName);
        Studio queryStudio = studioMapper.selectOne(studioNameQueryWrapper);
        if (queryStudio != null) {
            // 名称重复
            throw new StudioException("演出厅已存在，请重新添加！");
        }

        // 2. 添加演出厅
        int result = studioMapper.insert(studio);
        if (result <= 0) {
            // 添加演出厅失败，请检查输入是否合法
            throw new StudioException("添加演出厅失败，请检查输入是否合法！");
        }

    }

    @Override
    public void delete(int studioId) {
        // 1. 查询演出厅是否存在
        Studio studio = studioMapper.selectById(studioId);
        if (studio == null) {
            // 演出厅不存在，抛出异常
            throw new StudioException("删除的演出厅不存在！");
        }
        // 2. 执行删除演出厅
        int result = studioMapper.deleteById(studioId);
        if (result <= 0) {
            // 删除失败
            throw new StudioException("删除演出厅失败！");
        }
    }

    @Override
    public void update(Studio studio) {
        // 1. 查询演出厅是否存在
        Studio queryStudio = studioMapper.selectById(studio.getStudioId());
        if (queryStudio == null) {
            // 演出厅不存在，抛出异常
            throw new StudioException("修改的演出厅不存在！");
        }
        // 2. 修改演出厅
        int result = studioMapper.updateById(studio);
        if (result <= 0) {
            throw new StudioException("修改演出厅失败！");
        }
    }
}
