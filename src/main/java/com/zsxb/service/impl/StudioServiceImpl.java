package com.zsxb.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.zsxb.common.CommonDict;
import com.zsxb.po.Seat;
import com.zsxb.po.Studio;
import com.zsxb.exception.StudioException;
import com.zsxb.mapper.StudioMapper;
import com.zsxb.service.SeatService;
import com.zsxb.service.StudioService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

/**
 * 
 * 演出厅业务层
 *
 * @author dz
 * @date 2023-05-09
 */
@Service    // 标识业务层
public class StudioServiceImpl implements StudioService {

    // 注入演出厅的mapper
    @Autowired
    private StudioMapper studioMapper;

    // 注入座位的service
    @Autowired
    private SeatService seatService;

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
    @Transactional
    public void add(Studio studio) {

        // 演出厅名称
        String studioName = studio.getStudioName();

        // 1. 构建查询条件，判断演出厅名称是否重复
        LambdaQueryWrapper<Studio> studioNameQueryWrapper = new LambdaQueryWrapper<>();
        // 添加条件：演出厅名称必须相同
        studioNameQueryWrapper.eq(Studio::getStudioName, studioName);
        // 根据查询条件查询演出厅
        Studio queryStudio = studioMapper.selectOne(studioNameQueryWrapper);
        if (queryStudio != null) {
            // 查询出来的演出厅存在，所以名称重复
            throw new StudioException("演出厅已存在，请重新添加！");
        }

        // 2. 添加演出厅，返回影响的行数
        int result = studioMapper.insert(studio);
        if (result <= 0) {
            // 添加演出厅失败，请检查输入是否合法
            throw new StudioException("添加演出厅失败，请检查输入是否合法！");
        }
        List<Seat> seats = seatService.createSeats(studio.getStudioId(), studio.getStudioRowCount(), studio.getStudioColCount());
        seatService.saveBatch(seats);
    }



    @Override
    public void delete(int studioId) {
        // 1. 查询演出厅是否存在
        Studio studio = studioMapper.selectById(studioId);
        if (studio == null) {
            // 演出厅不存在，抛出异常
            throw new StudioException("删除的演出厅不存在！");
        }
        // 2. 根据演出厅id执行删除演出厅，返回受影响的行数
        int result = studioMapper.deleteById(studioId);
        if (result <= 0) {
            // 删除失败
            throw new StudioException("删除演出厅失败！");
        }
    }

    @Override
    public void update(Studio studio) {

        // 演出厅id
        Integer studioId = studio.getStudioId();

        // 1. 根据演出厅id查询演出厅是否存在
        Studio queryStudio = studioMapper.selectById(studioId);
        if (queryStudio == null) {
            // 查询的演出厅不存在，抛出异常
            throw new StudioException("修改的演出厅不存在！");
        }
        // 2. 修改演出厅，返回受影响的行数
        int result = studioMapper.updateById(studio);
        if (result <= 0) {
            throw new StudioException("修改演出厅失败！");
        }
    }

    @Override
    public List<Studio> list() {

        // 查询所有演出厅
        List<Studio> studioList = studioMapper.selectList(null);

        return studioList;
    }

    @Override
    public Studio selectById(Integer studioId) {

        // 根据演出厅id获取演出厅
        Studio studio = studioMapper.selectById(studioId);

        return studio;
    }
}
