package com.zsxb.mapper;

import com.zsxb.po.Ticket;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.zsxb.vo.ScheduleVO;
import com.zsxb.vo.TicketVO;

import java.util.List;

/**
 *  Mapper 接口
 *
 * @author dz
 * @date 2023-05-09
 */
public interface TicketMapper extends BaseMapper<Ticket> {
    /**
     * 查询该顾客所有买了没退的票
     * @return
     */
    List<TicketVO> selectVOByPage(long current,
                                 long size,
                                 Integer cusId);

    /**
     * 根据演出票id查询演出票
     * @param ticketId
     * @return
     */
    TicketVO selectVOByTicketId(int ticketId);
}
