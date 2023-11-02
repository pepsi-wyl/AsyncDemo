package com.ylan.asyncdemo.api;

import com.ylan.asyncdemo.service.AsyncTask;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;

/**
 * @author by pepsi-wyl
 * @date 2023-10-31 10:17
 */

//@EnableAsync // Controller上添加@EnableAsync注解 开启用异步任务

@Slf4j
@RestController
@RequestMapping("/async")
public class AsyncController {
    private AsyncTask asyncTask;

    public AsyncController(AsyncTask asyncService) {
        this.asyncTask = asyncService;
    }

    // GET http://localhost:8080/async/testAsync1
    @GetMapping("/testAsync1")
    public void testAsync1(){
        log.info("ThreadName:" + Thread.currentThread().getName());
        log.info("当前线程开始执行测试函数......");
        asyncTask.testAsync();
        for (int i = 1; i <= 100; i++) {
            System.out.print(i + " ");
            if (i % 10 == 0) {
                System.out.println();
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
        log.info("当前线程测试函数执行完毕......");
    }

    // GET http://localhost:8080/async/testAsync2
    @GetMapping("/testAsync2")
    public void testAsync2(){
        asyncTask.asyncInvokeWithException("testAsync2");
    }

    // GET http://localhost:8080/async/testAsync3
    @GetMapping("/testAsync3")
    public void testAsync3() throws ExecutionException, InterruptedException {
        Future<String> future = asyncTask.asyncInvokeReturnFuture("testAsync3");
        log.info(future.get());
    }

}