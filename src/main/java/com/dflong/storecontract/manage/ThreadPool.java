package com.dflong.storecontract.manage;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

@Configuration
public class ThreadPool {

    @Bean("contractThreadPool")
    public Executor contractThreadPool() {
        return Executors.newFixedThreadPool(10);
    }

    @Bean("userThreadPool")
    public Executor userThreadPool() {
        return Executors.newFixedThreadPool(10);
    }

    @Bean("goodsThreadPool")
    public Executor goodsThreadPool() {
        return Executors.newFixedThreadPool(10);
    }

    @Bean("baseThreadPool")
    public Executor baseThreadPool() {
        return Executors.newFixedThreadPool(10);
    }

    @Bean("actThreadPool")
    public Executor actThreadPool() {
        return Executors.newFixedThreadPool(10);
    }

    @Bean("stockThreadPool")
    public Executor stockThreadPool() {
        return Executors.newFixedThreadPool(10);
    }

}
