package com.dflong.storecontract;

import org.apache.dubbo.config.spring.context.annotation.EnableDubbo;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@MapperScan("com.dflong.storecontract.mapper")
@EnableDubbo(scanBasePackages = "com.dflong.storecontract.rpc")
public class StoreContractApplication {

	// https://github.com/dflong/storeContract.git
	public static void main(String[] args) {

		SpringApplication.run(StoreContractApplication.class, args);
	}

	/**
	 * 方法2：最大值最小值判断法（最常用）
	 * 判断两个闭区间是否有交集
	 * 原理：两个区间的最大开始时间 <= 最小结束时间
	 */
	public static boolean hasIntersection2(long start1, long end1, long start2, long end2) {
		return Math.max(start1, start2) <= Math.min(end1, end2);
	}
}
