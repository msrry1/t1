package com.zsxb.mapper;

import com.zsxb.po.Sale;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.zsxb.vo.SaleCount;
import com.zsxb.vo.SalePerformance;
import com.zsxb.vo.SaleVO;
import com.zsxb.vo.ScheduleVO;

import java.util.List;

/**
 *  Mapper 接口
 *
 * @author dz
 * @date 2023-05-09
 */
public interface SaleMapper extends BaseMapper<Sale> {


    /**
     * 分页查询销售单
     * @param current
     * @param size
     * @param saleType  销售类型（1销售    -1退款）
     * @return
     */
    List<SaleVO> selectVOByPage(long current,
                                long size,
                                Integer saleType);

    /**
     * 查询单个销售记录
     * @param saleId
     * @return
     */
    SaleVO selectVOBySaleId(Integer saleId);

    /**
     * 查询所有或根据雇员用户名分页查询销售业绩
     * @return
     */
    List<SalePerformance> selectPerformanceVOByPage(long current,
                                                    long size,
                                                    String empUid);

    /**
     * 查询所有或根据剧目名分页查询销售统计
     * @return
     */
    List<SaleCount> selectSaleCountVOByPage(long current,
                                              long size,
                                              String playName);
}
