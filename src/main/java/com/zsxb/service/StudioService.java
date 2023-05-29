package com.zsxb.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.zsxb.entity.Studio;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
 * 
 *
 * @author dz
 * @date 2023-05-09
 */
public interface StudioService {

    /**
     * 分页查询演出厅
     * @param page  分页
     * @param studioName    演出厅名称（可以为null）
     */
    void queryPage(Page page, String studioName);

    /**
     * 添加演出厅
     * @param studio
     */
    void add(Studio studio);

    /**
     * 删除演出厅
     * @param studioId  演出厅id
     */
    void delete(int studioId);

    /**
     * 修改演出厅
     * @param studio
     */
    void update(Studio studio);
}
