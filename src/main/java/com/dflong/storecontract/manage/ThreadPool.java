package com.dflong.storecontract.manage;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

import java.util.concurrent.*;

@Configuration
public class ThreadPool {

    @Bean("contractThreadPool")
    public Executor contractThreadPool() {
        return createThreadPool("contract-thread-pool:");
    }

    @Bean("userThreadPool")
    public Executor userThreadPool() {
        return createThreadPool("user-thread-pool:");
    }

    @Bean("goodsThreadPool")
    public Executor goodsThreadPool() {
        return createThreadPool("goods-thread-pool:");
    }

    @Bean("baseThreadPool")
    public Executor baseThreadPool() {
        return createThreadPool("store-thread-pool:");
    }

    @Bean("actThreadPool")
    public Executor actThreadPool() {
        return createThreadPool("act-thread-pool:");
    }

    @Bean("stockThreadPool")
    public Executor stockThreadPool() {
        return createThreadPool("stock-thread-pool:");
    }

    @Bean("billingThreadPool")
    public Executor billingThreadPool() {
        return createThreadPool("billing-thread-pool:");
    }

    private ThreadPoolTaskExecutor createThreadPool(String threadNamePrefix) {
        ThreadPoolTaskExecutor executor = new ThreadPoolTaskExecutor();
        executor.setCorePoolSize(10);
        executor.setMaxPoolSize(10);
//        executor.setQueueCapacity(queueCapacity); 默认最大值
//        executor.setKeepAliveSeconds(keepAliveSeconds); 60s
        executor.setThreadNamePrefix(threadNamePrefix);
        executor.setRejectedExecutionHandler(new ThreadPoolExecutor.CallerRunsPolicy());
        executor.initialize();
        return executor;
    }
}
