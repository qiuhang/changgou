package com.qiuhang.changgou.goods.pojo;

import java.io.Serializable;
import java.util.List;

/**
 * @ProjectName: changgou_parent
 * @Package: com.qiuhang.changgou.goods.pojo
 * @ClassName: Goods
 * @Author: qiuhang
 * @Description: ${description}
 * @Date: 2020/5/21 10:00
 * @Version: 1.0
 */
public class Goods implements Serializable {

    private Spu spu;
    private List<Sku> skuList;

    public Spu getSpu() {
        return spu;
    }

    public void setSpu(Spu spu) {
        this.spu = spu;
    }

    public List<Sku> getSkuList() {
        return skuList;
    }

    public void setSkuList(List<Sku> skuList) {
        this.skuList = skuList;
    }
}
