package com.ylan.asyncdemo.config;

import org.springframework.aop.interceptor.AsyncUncaughtExceptionHandler;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.AsyncConfigurer;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

import java.lang.reflect.Method;
import java.util.concurrent.Executor;
import java.util.concurrent.ThreadPoolExecutor;

/**
 * @author by pepsi-wyl
 * @date 2023-10-31 10:15
 * Spring @Async 配置类
 */

@EnableAsync
@Configuration
public class SpringAsyncConfig implements AsyncConfigurer {

    @Override
    public Executor getAsyncExecutor() {
        ThreadPoolTaskExecutor executor = new ThreadPoolTaskExecutor();

        // 线程名称的前缀
        executor.setThreadNamePrefix("ThreadPoolTaskExecutor-");

        // 核心线程池数量，方法: 返回可用处理器的Java虚拟机的数量。
        executor.setCorePoolSize(Runtime.getRuntime().availableProcessors());
        // 最大线程数量
        executor.setMaxPoolSize(Runtime.getRuntime().availableProcessors()*5);
        // 线程池的队列容量
        executor.setQueueCapacity(Runtime.getRuntime().availableProcessors()*2);
        // setRejectedExecutionHandler：当pool已经达到max size的时候，如何处理新任务
        // CallerRunsPolicy：不在新线程中执行任务，而是由调用者所在的线程来执行
        executor.setRejectedExecutionHandler(new ThreadPoolExecutor.CallerRunsPolicy());

        executor.initialize();
        return executor;
    }


    /**
     * 异步任务中异常处理
     * @return
     */
    @Override
    public AsyncUncaughtExceptionHandler getAsyncUncaughtExceptionHandler() {
        return (Throwable ex, Method method, Object... params)->{
            // todo 异步方法异常处理
            System.out.println("class#method: " + method.getDeclaringClass().getName() + "#" + method.getName());
            System.out.println("type        : " + ex.getClass().getName());
            System.out.println("exception   : " + ex.getMessage());
        };
    }

}