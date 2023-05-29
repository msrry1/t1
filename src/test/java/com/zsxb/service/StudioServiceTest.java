package com.zsxb.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.zsxb.entity.Employee;
import com.zsxb.entity.Studio;
import com.zsxb.service.impl.StudioServiceImpl;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

/**
 * ClassName: StudioServiceTest
 * Package: com.zsxb.service
 * Description:
 *
 * @Author lyh
 * @Create 2023/5/27 17:51
 * @Version 1.0
 */
@SpringBootTest
public class StudioServiceTest {

    @Autowired
    private StudioService studioService;


    @Test
    public void queryByPage() {
        Page<Studio> page = new Page<>(1, 2);


        System.out.println(page);
    }

    @Test
    public void queryByPage2() {
        Page<Studio> page = new Page<>(1, 2);


        System.out.println(page);
    }

    @Test
    public void add() {
        Studio studio = new Studio();
        studio.setStudioName("演出厅1")
                .setStudioIntroduction("很不错的")
                .setStudioColCount(10)
                .setStudioRowCount(10);
        studioService.add(studio);

    }

}
