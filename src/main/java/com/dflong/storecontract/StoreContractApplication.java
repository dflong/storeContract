package com.dflong.storecontract;

import org.apache.dubbo.config.spring.context.annotation.EnableDubbo;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@MapperScan("com.dflong.storecontract.mapper")
@EnableDubbo(scanBasePackages = "com.dflong.storecontract.rpc")
public class StoreContractApplication {

	public static void main(String[] args) {
		SpringApplication.run(StoreContractApplication.class, args);
	}

}
