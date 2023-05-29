package com.zsxb.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;

/**
 * 
 *
 * @author dz
 * @date 2023-05-09
 */
public interface TicketService {

    /**
     * 分页查询票务信息
     * @param page
     * @param ticketStatus 查询票的状态（0待销售 9卖出）
     */
    void queryPage(Page page, Integer ticketStatus);

}
