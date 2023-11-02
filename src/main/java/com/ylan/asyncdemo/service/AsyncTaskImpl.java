package com.ylan.asyncdemo.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.stereotype.Service;

/**
 * @author by pepsi-wyl
 * @date 2023-10-31 10:12
 */

//@EnableAsync // Service上添加@EnableAsync注解 开启用异步任务

@Slf4j
@Service
public class AsyncServiceImpl implements AsyncService{

    @Async
    @Override
    public void testAsync() {
        System.out.println("ThreadName:" + Thread.currentThread().getName());
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println("测试Spring 异步调用！");
    }

}