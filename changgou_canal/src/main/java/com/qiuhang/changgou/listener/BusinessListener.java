package com.qiuhang.changgou.listener;

import com.alibaba.otter.canal.protocol.CanalEntry;
import com.xpand.starter.canal.annotation.*;

/**
 * @ProjectName: changgou_parent
 * @Package: com.qiuhang.changgou.listener
 * @ClassName: BusinessListener
 * @Author: qiuhang
 * @Description: ${description}
 * @Date: 2020/5/25 11:20
 * @Version: 1.0
 */
@CanalEventListener
public class BusinessListener {
    /**
     * 增加
     * @param eventType 当前操作的类型  增加数据
     * @param rowData 发生变更的数据
     */
    @InsertListenPoint
    public void onEventInsert(CanalEntry.EventType eventType, CanalEntry.RowData rowData) {
        System.err.println("广告数据发生变化");
        rowData.getAfterColumnsList().forEach((c) -> System.err.println("增加数据: " + c.getName() + " :: " + c.getValue()));
    }

    /**
     * 修改
     */
    @UpdateListenPoint
    public void onEventUpdate(CanalEntry.EventType eventType, CanalEntry.RowData rowData) {
        System.err.println("广告数据发生变化");
        rowData.getBeforeColumnsList().forEach((c) -> System.err.println("更改前数据: " + c.getName() + " :: " + c.getValue()));
        rowData.getAfterColumnsList().forEach((c) -> System.err.println("更改后数据: " + c.getName() + " :: " + c.getValue()));
    }
    /**
     * 删除
     */
    @DeleteListenPoint
    public void onEventDelete(CanalEntry.EventType eventType, CanalEntry.RowData rowData) {
        System.err.println("广告数据发生变化");
        rowData.getBeforeColumnsList().forEach((c) -> System.err.println("删除前数据: " + c.getName() + " :: " + c.getValue()));
    }


    /**
     * 自定义操作
     */
    @ListenPoint(eventType = {CanalEntry.EventType.UPDATE, CanalEntry.EventType.DELETE,
            CanalEntry.EventType.INSERT},
            schema = {"changgou_content"},
            table = {"tb_content"},
            destination = "example"   //指定实例地址
    )
    public void onMyEvent(CanalEntry.EventType eventType, CanalEntry.RowData rowData) {
        System.err.println("广告数据发生变化");
        rowData.getBeforeColumnsList().forEach((c) -> System.err.println("更改前数据: " + c.getName() + " :: " + c.getValue()));
        rowData.getAfterColumnsList().forEach((c) -> System.err.println("更改后数据: " + c.getName() + " :: " + c.getValue()));
    }

}