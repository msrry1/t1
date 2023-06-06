package com.zsxb.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.zsxb.po.Play;

import java.util.List;

/**
 * 
 * 剧目业务层接口
 *
 * @author dz
 * @date 2023-05-09
 */
public interface PlayService {

    /**
     * 根据剧目名称查询分页剧目信息
     * @param page
     * @param playName 剧目名称（可以为null）
     */
    void queryPage(Page page, String playName);

    /**
     * 添加剧目
     * @param play
     */
    void add(Play play);

    /**
     * 修改剧目
     * @param play
     */
    void update(Play play);

    /**
     * 删除剧目
     * @param play_id
     */
    void delete(int play_id);

    /**
     * 查询所有剧目
     * @return
     */
    List<Play> list();

    /**
     * 根据剧目id获取剧目
     * @param playId
     * @return
     */
    Play selectById(Integer playId);

    /**
     * 根据剧目名字模糊查询剧目
     * @param playName
     * @return
     */
    List<Play> selectByPlayName(String playName);

}
