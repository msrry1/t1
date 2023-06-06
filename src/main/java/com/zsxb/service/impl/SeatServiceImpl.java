package com.zsxb.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.zsxb.common.CommonDict;
import com.zsxb.po.Schedule;
import com.zsxb.po.Seat;
import com.zsxb.mapper.SeatMapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.zsxb.service.ScheduleService;
import com.zsxb.service.SeatService;
import com.zsxb.vo.ScheduleVO;
import com.zsxb.vo.SeatVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * 
 *
 * @author dz
 * @date 2023-05-09
 */
@Service
public class SeatServiceImpl extends ServiceImpl<SeatMapper, Seat> implements SeatService {

    @Autowired
    private SeatMapper seatMapper;

    @Autowired
    private ScheduleService scheduleService;


    // 创建座位
    public List<Seat> createSeats(Integer studioId, Integer studioRowCount, Integer studioColCount) {

        // 总座位数
        int count = studioRowCount * studioColCount;
        List<Seat> seats = new ArrayList<Seat>(count);
        for (int row = 1; row <= studioRowCount; row++) {
            for (int col = 1; col <= studioColCount; col++) {
                seats.add(new Seat().setStudioId(studioId).
                        setSeatRow(row).
                        setSeatColumn(col));
            }
        }
        return seats;
    }

    @Override
    public void queryPageBySchedId(Page page, Integer schedId) {
        List<SeatVO> records = null;

        // 查询所有座位
        records = seatMapper.selectVOPageBySchedId((page.getCurrent() - 1) * page.getSize(), page.getSize(),schedId);
        LambdaQueryWrapper<Seat> seatLambdaQueryWrapper = new LambdaQueryWrapper<>();
        Schedule schedule = scheduleService.selectById(schedId);
        seatLambdaQueryWrapper.eq(Seat::getStudioId, schedule.getStudioId());
        Long count = seatMapper.selectCount(seatLambdaQueryWrapper);
        // 设置分页的records和total
        page.setTotal(count);
        page.setRecords(records);
    }
}
