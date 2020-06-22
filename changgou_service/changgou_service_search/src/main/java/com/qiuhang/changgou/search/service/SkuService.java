package com.qiuhang.changgou.search.service;

import java.util.Map;

/**
 * @ProjectName: changgou_parent
 * @Package: com.qiuhang.changgou.service
 * @ClassName: SkuService
 * @Author: qiuhang
 * @Description: ${description}
 * @Date: 2020/6/1 10:00
 * @Version: 1.0
 */
public interface SkuService {
    void importData();

    Map<String,Object> search(Map<String,String> searchMap);
}
