package com.zsxb.mapper;

import com.zsxb.po.Seat;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.zsxb.vo.SeatVO;

import java.util.List;

/**
 *  Mapper 接口
 *
 * @author dz
 * @date 2023-05-09
 */
public interface SeatMapper extends BaseMapper<Seat> {

    /**
     * 根据演出计划id分页查询所有座位VO
     * @param current
     * @param size
     * @param schedId
     * @return
     */
    List<SeatVO> selectVOPageBySchedId(Long current,
                                       Long size,
                                       Integer schedId);
}
