package com.zsxb.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.zsxb.po.Seat;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
 * 
 *
 * @author dz
 * @date 2023-05-09
 */
public interface SeatService extends IService<Seat> {

    /**
     * 根据演出厅id，演出厅行列批量生成座位
     * @param studioId
     * @param studioRowCount
     * @param studioColCount
     * @return
     */
    List<Seat> createSeats(Integer studioId, Integer studioRowCount, Integer studioColCount);

    /**
     * 根据演出计划id分页查询座位VO
     * @param page
     * @param schedId
     */
    void queryPageBySchedId(Page page, Integer schedId);
}
