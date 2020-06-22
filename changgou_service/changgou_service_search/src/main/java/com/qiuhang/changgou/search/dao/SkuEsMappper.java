package com.qiuhang.changgou.search.dao;

import com.qiuhang.changgou.search.pojo.SkuInfo;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * @ProjectName: changgou_parent
 * @Package: com.qiuhang.changgou.dao
 * @ClassName: SkuEsMappper
 * @Author: qiuhang
 * @Description: ${description}
 * @Date: 2020/6/1 10:04
 * @Version: 1.0
 */
public interface SkuEsMappper extends ElasticsearchRepository<SkuInfo,Long> {
}
