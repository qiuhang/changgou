package com.qiuhang.changgou;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.server.EnableEurekaServer;

/**
 * @ProjectName: changgouparent
 * @Package: com.qiuhang.changgou
 * @ClassName: EurekaApplication
 * @Author: qiuhang
 * @Description: ${description}
 * @Date: 2020/4/2 9:32
 * @Version: 1.0
 */

@SpringBootApplication
@EnableEurekaServer
public class EurekaApplication {
    public static void main(String[] args) {
        SpringApplication.run(EurekaApplication.class);
    }
}
