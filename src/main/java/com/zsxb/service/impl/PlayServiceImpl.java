package com.zsxb.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.zsxb.common.CommonDict;
import com.zsxb.po.Play;
import com.zsxb.exception.PlayException;
import com.zsxb.exception.StudioException;
import com.zsxb.mapper.PlayMapper;
import com.zsxb.service.PlayService;
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
public class PlayServiceImpl implements PlayService {

    @Autowired
    private PlayMapper playMapper;

    @Override
    public void queryPage(Page page, String playName) {
        // 构建查询条件
        LambdaQueryWrapper<Play> playLambdaQueryWrapper = new LambdaQueryWrapper<>();
        if (!StringUtils.isBlank(playName)) {
            // 剧目名称不为空，添加查询条件
            // 模糊查询，包含剧目名字即可
            playLambdaQueryWrapper.like(Play::getPlayName, playName);
        }
        // 根据剧目id升序
        playLambdaQueryWrapper.orderByAsc(Play::getPlayId);
        // 查询不包含下线剧目
        playLambdaQueryWrapper.ne(Play::getPlayStatus, CommonDict.PLAY_STATUS_DOWN);

        // 查询演出厅
        playMapper.selectPage(page, playLambdaQueryWrapper);
    }

    @Override
    public void add(Play play) {

        String playName = play.getPlayName();
        // 1. 判断剧目名称是否重复
        LambdaQueryWrapper<Play> playNameQueryWrapper = new LambdaQueryWrapper<>();
        playNameQueryWrapper.eq(Play::getPlayName, playName);
        Play queryPlay = playMapper.selectOne(playNameQueryWrapper);
        if (queryPlay != null) {
            // 剧目名称重复
            throw new PlayException("剧目已存在，请重新添加！");
        }

        int result = playMapper.insert(play);

        if (result <= 0) {
            // 添加剧目失败，请检查输入是否合法
            throw new PlayException("添加剧目失败，请检查输入是否合法！");
        }

    }

    @Override
    public void delete(int playId) {
        // 1. 查询剧目是否存在
        Play play = playMapper.selectById(playId);
        if (play == null) {
            // 剧目不存在，抛出异常
            throw new PlayException("删除的剧目不存在！");
        }
        // 2. 执行删除剧目
        int result = playMapper.delete(playId);
        if (result <= 0) {
            // 删除失败
            throw new StudioException("删除剧目失败！");
        }
    }

    @Override
    public void update(Play play) {
        // 1. 查询剧目是否存在
        Play queryPlay = playMapper.selectById(play.getPlayId());
        if (queryPlay == null) {
            // 剧目不存在，抛出异常
            throw new PlayException("修改的剧目不存在！");
        }
        // 2. 修改剧目
        int result = playMapper.updateById(play);
        if (result <= 0) {
            throw new PlayException("修改剧目失败！");
        }
    }
}
