package com.zsxb.service.impl;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.zsxb.vo.SchedulePO;
import com.zsxb.service.ScheduleService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

/**
 * ClassName: ScheduleServiceImplTest
 * Package: com.zsxb.service.impl
 * Description:
 *
 * @Author lyh
 * @Create 2023/5/29 11:10
 * @Version 1.0
 */
@SpringBootTest
class ScheduleServiceImplTest {

    @Autowired
    private ScheduleService scheduleService;

    @Test
    void queryPage() {
        Page<SchedulePO> page = new Page<>(1,2);
        scheduleService.queryPage(page, 2);
        System.out.println(page.getRecords());
    }
}