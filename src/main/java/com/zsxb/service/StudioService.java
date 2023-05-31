package com.zsxb.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.zsxb.po.Studio;

import java.util.List;

/**
 * 
 * 演出厅业务层接口
 *
 * @author dz
 * @date 2023-05-09
 */
public interface StudioService {

    /**
     * 分页查询演出厅
     * @param page  分页
     * @param studioName    演出厅名称（可以为null），null表示查询所有演出厅
     */
    void queryPage(Page page, String studioName);

    /**
     * 添加演出厅
     * @param studio    演出厅信息
     */
    void add(Studio studio);

    /**
     * 删除演出厅
     * @param studioId  演出厅id
     */
    void delete(int studioId);

    /**
     * 修改演出厅
     * @param studio    演出厅信息
     */
    void update(Studio studio);

    /**
     * 查询所有演出厅
     * @return
     */
    List<Studio> list();

    /**
     * 根据演出厅id查询演出厅
     * @param studioId
     * @return
     */
    Studio selectById(Integer studioId);
}
