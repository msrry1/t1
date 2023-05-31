package com.zsxb.config;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;

/**
 * ClassName: RootInfoTest
 * Package: com.zsxb.config
 * Description:
 *
 * @Author lyh
 * @Create 2023/5/31 8:36
 * @Version 1.0
 */
@SpringBootTest
class RootInfoTest {

    @Autowired
    private RootInfo rootInfo;
    @Test
    public void m1() {
        System.out.println(rootInfo.getUsername());
        System.out.println(rootInfo.getPassword());
    }

    public void m2() {
        System.out.println(rootInfo);
    }

}