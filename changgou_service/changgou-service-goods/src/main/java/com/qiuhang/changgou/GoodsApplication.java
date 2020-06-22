package com.qiuhang.changgou;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.stereotype.Component;
import tk.mybatis.spring.annotation.MapperScan;

/**
 * @ProjectName: changgouparent
 * @Package: com.qiuhang.changgou
 * @ClassName: GoodsApplication
 * @Author: qiuhang
 * @Description: ${description}
 * @Date: 2020/4/2 10:07
 * @Version: 1.0
 */
@SpringBootApplication
@EnableEurekaClient
@MapperScan("com.qiuhang.changgou.goods.dao")
@Component("com.qiuhang.changgou")
public class GoodsApplication {
    public static void main(String[] args) {
        SpringApplication.run(GoodsApplication.class);
    }
}
