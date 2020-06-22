package com.qiuhang.changgou.search;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.data.elasticsearch.repository.config.EnableElasticsearchRepositories;

/**
 * @ProjectName: changgou_parent
 * @Package: com.qiuhang.changgou
 * @ClassName: SearchApplication
 * @Author: qiuhang
 * @Description: ${description}
 * @Date: 2020/5/27 10:35
 * @Version: 1.0
 */
@SpringBootApplication(exclude = {DataSourceAutoConfiguration.class})
@EnableEurekaClient
@EnableFeignClients(basePackages = {"com.qiuhang.changgou.goods.feign"})
@EnableElasticsearchRepositories(basePackages = "com.qiuhang.changgou.search.dao")
public class SearchApplication {
    public static void main(String[] args) {
        /**
         * 解决netty冲突后初始化client是还会抛出异常
         */
        System.setProperty("es.set.netty.runtime.available.processors","false");
        SpringApplication.run(SearchApplication.class);
    }
}
