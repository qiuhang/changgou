package com.qiuhang.changgou.entity;

import java.util.List;

/**
 * @ProjectName: changgouparent
 * @Package: com.qiuhang.changgou.entity
 * @ClassName: PageResult
 * @Author: qiuhang
 * @Description: ${description}
 * @Date: 2020/4/2 9:55
 * @Version: 1.0
 */
public class PageResult<T> {
    private Long total;//总记录数
    private List<T> rows;//记录

    public PageResult(Long total, List<T> rows) {
        this.total = total;
        this.rows = rows;
    }

    public PageResult() {
    }

    public List<T> getRows() {
        return rows;
    }

    public Long getTotal() {
        return total;
    }

    public void setRows(List<T> rows) {
        this.rows = rows;
    }

    public void setTotal(Long total) {
        this.total = total;
    }
}
