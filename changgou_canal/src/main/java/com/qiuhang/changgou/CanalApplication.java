package com.qiuhang.changgou;

import com.xpand.starter.canal.annotation.EnableCanalClient;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * @ProjectName: changgou_parent
 * @Package: com.qiuhang.changgou
 * @ClassName: CanalApplication
 * @Author: qiuhang
 * @Description: ${description}
 * @Date: 2020/5/25 11:18
 * @Version: 1.0
 */
@SpringBootApplication
@EnableCanalClient
public class CanalApplication {

    public static void main(String[] args) {
        SpringApplication.run(CanalApplication.class, args);
    }
}
