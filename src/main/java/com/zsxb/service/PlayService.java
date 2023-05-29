package com.zsxb.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.zsxb.po.Play;

/**
 * 
 *
 * @author dz
 * @date 2023-05-09
 */
public interface PlayService {

    /**
     * 根据剧目名称查询分页剧目信息
     * @param page
     * @param playName
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
}
