package com.zsxb.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.zsxb.common.CommonDict;
import com.zsxb.mapper.SeatMapper;
import com.zsxb.po.*;
import com.zsxb.mapper.SaleMapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.zsxb.service.EmployeeService;
import com.zsxb.service.PlayService;
import com.zsxb.service.SaleService;
import com.zsxb.vo.SaleCount;
import com.zsxb.vo.SalePerformance;
import com.zsxb.vo.SaleVO;
import com.zsxb.vo.ScheduleVO;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

/**
 * 
 *
 * @author dz
 * @date 2023-05-09
 */
@Service
public class SaleServiceImpl extends ServiceImpl<SaleMapper, Sale> implements SaleService {

    @Autowired
    private SaleMapper saleMapper;

    @Autowired
    private EmployeeService employeeService;

    @Autowired
    private PlayService playService;

    @Override
    public void queryPage(Page page, Integer saleId, Integer saleType) {
        List<SaleVO> records = null;

        if (saleId == null) {
            // 查询所有销售信息
            records = saleMapper.selectVOByPage((page.getCurrent() - 1) * page.getSize(), page.getSize(), saleType);
        } else {
            // 查询单个销售信息
            SaleVO saleVO = saleMapper.selectVOBySaleId(saleId);
            // 判断销售的类型是否满足
            if (saleVO != null && saleVO.getSaleType() == saleType) {
                records = new ArrayList<>(1);
                records.add(saleVO);
            }
        }
        // 查询总记录数，符合状态
        long count = 1;
        if (saleId == null) {
            // 查询所有符合状态的销售记录个数
            LambdaQueryWrapper<Sale> saleVOLambdaQueryWrapper = new LambdaQueryWrapper<>();
            saleVOLambdaQueryWrapper.eq(Sale::getSaleType, saleType);
            count = saleMapper.selectCount(saleVOLambdaQueryWrapper);
        }
        page.setTotal(count);
        page.setRecords(records);
    }

    @Override
    public void queryPerformancePage(Page page, String empUid) {

        // 每个管理员的售票额-退票额
        List<SalePerformance> records = saleMapper.selectPerformanceVOByPage((page.getCurrent() - 1) * page.getSize(),
                page.getSize(),
                empUid);
        // 查询所有管理员
        long total = 0l;
        LambdaQueryWrapper<Sale> saleLambdaQueryWrapper = new LambdaQueryWrapper<>();
        if (!StringUtils.isBlank(empUid)) {
            // 用户名为一个条件
            List<Employee> queryEmployee = employeeService.selectByEmpUid(empUid);
            total = queryEmployee.size();
            // 把不是该用户名的管理员删除
        } else {
            // total为所有管理员
            List<Employee> list = employeeService.list();
            total = list.size();
        }
        page.setTotal(total);
        page.setRecords(records);
    }

    @Override
    public void queryCountPage(Page page, String playName) {
        // 每个下线/已安排演出的剧目的售票额-退票额
        List<SaleCount> records = saleMapper.selectSaleCountVOByPage((page.getCurrent() - 1) * page.getSize(),
                page.getSize(),
                playName);
        // 查询所有剧目
        long total = 0l;
        LambdaQueryWrapper<Sale> saleLambdaQueryWrapper = new LambdaQueryWrapper<>();
        if (!StringUtils.isBlank(playName)) {
            // 剧目为一个条件
            List<Play> playList = playService.selectByPlayName(playName);
            total = playList.size();
        } else {
            // total为所有剧目
            List<Play> list = playService.list();
            total = list.size();
        }
        page.setTotal(total);
        page.setRecords(records);
    }
}
