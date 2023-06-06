package com.zsxb.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.zsxb.po.Sale;
import com.baomidou.mybatisplus.extension.service.IService;
import com.zsxb.po.Seat;

/**
 * 
 *
 * @author dz
 * @date 2023-05-09
 */
public interface SaleService extends IService<Sale> {

    /**
     * 查找所有/单个销售/退款订单
     * @param page
     * @param saleId
     * @param saleType
     */
    void queryPage(Page page, Integer saleId, Integer saleType);

    /**
     * 查询所有/单个管理员的业绩
     * @param page
     * @param empUid
     */
    void queryPerformancePage(Page page, String empUid);

    /**
     * 查找所有/单个剧目的销售统计
     * @param page
     * @param playName
     */
    void queryCountPage(Page page, String playName);
}
