package com.qiuhang.changgou.search.controller;

import com.qiuhang.changgou.entity.Result;
import com.qiuhang.changgou.entity.StatusCode;
import com.qiuhang.changgou.search.service.SkuService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

/**
 * @ProjectName: changgou_parent
 * @Package: com.qiuhang.changgou.controller
 * @ClassName: SkuController
 * @Author: qiuhang
 * @Description: ${description}
 * @Date: 2020/6/1 10:10
 * @Version: 1.0
 */
@RestController
@RequestMapping("search")
@CrossOrigin
public class SkuController {

    @Autowired
    private SkuService skuService;

    @GetMapping("/import")
    public Result importData(){
        skuService.importData();

        return new Result(true, StatusCode.OK,"导入成功");
    }

    @GetMapping
    public Result search(@RequestParam  Map searchMap){
        Map search = skuService.search(searchMap);
        return new Result(true, StatusCode.OK,"查询成功",search);
    }
}
