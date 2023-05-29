package com.zsxb.mapper;

import com.zsxb.po.Schedule;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.zsxb.vo.SchedulePO;

import java.util.List;

/**
 *  Mapper 接口
 *
 * @author dz
 * @date 2023-05-09
 */
public interface ScheduleMapper extends BaseMapper<Schedule> {


    /**
     * 根据演出计划id和分页数据查询演出计划集合
     * @return
     */
    List<SchedulePO> selectpByPage(long current,
                                   long size);

    /**
     * 根据演出计划id查询演出计划
     * @param schedId
     * @return
     */
    SchedulePO selectPOByScheduleId(int schedId);

}
