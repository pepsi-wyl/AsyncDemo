package com.ylan.asyncdemo.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.AsyncResult;
import org.springframework.stereotype.Service;

import java.util.concurrent.Future;

/**
 * @author by pepsi-wyl
 * @date 2023-10-31 10:12
 */

//@EnableAsync // Service上添加@EnableAsync注解 开启用异步任务

//@Async  加到类上开启类中所有方法异步调用，不推荐使用 推荐加到方法上
@Slf4j
@Service
public class AsyncTaskImpl implements AsyncTask {


    // name属性指定使用的线程池
    @Async("ylanTaskExecutor")
    @Override
    public void testAsync() {
        log.info("ThreadName:" + Thread.currentThread().getName());
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        log.info("测试Spring 异步调用！");
    }


    /**
     * 异常调用返回 Void
     *    对于返回值是void，异常会被AsyncUncaughtExceptionHandler处理掉
     * @param s
     */
    @Async("taskExecutor")
    public void asyncInvokeWithException(String s) {
        log.info("ThreadName:" + Thread.currentThread().getName());
        log.info("asyncInvokeWithParameter, parementer={}", s);
        throw new IllegalArgumentException(s);
    }

    /**
     * 异常调用返回Future
     *    对于返回值是Future，不会被AsyncUncaughtExceptionHandler处理，需要我们在方法中捕获异常并处理
     *    或者在调用方在调用Futrue.get时捕获异常进行处理
     * @param s
     * @return
     */
    @Async
    public Future<String> asyncInvokeReturnFuture(String s) {
        log.info("ThreadName:" + Thread.currentThread().getName());
        log.info("asyncInvokeReturnFuture, parementer={}", s);
        Future<String> future;
        try {
            Thread.sleep(1000 * 1);
            future = new AsyncResult<>("success:" + s);
            throw new IllegalArgumentException("a");
        } catch (InterruptedException e) {
            future = new AsyncResult<>("error");
        } catch(IllegalArgumentException e){
            future = new AsyncResult<>("error-IllegalArgumentException");
        }
        return future;
    }

}