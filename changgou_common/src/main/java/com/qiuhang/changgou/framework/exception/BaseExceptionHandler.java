package com.qiuhang.changgou.framework.exception;

import com.qiuhang.changgou.entity.Result;
import com.qiuhang.changgou.entity.StatusCode;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * @ProjectName: changgouparent
 * @Package: com.qiuhang.changgou.framework.exception
 * @ClassName: BaseExceptionHandler
 * @Author: qiuhang
 * @Description: ${description}
 * @Date: 2020/4/2 11:03
 * @Version: 1.0
 */
@ControllerAdvice
public class BaseExceptionHandler {

    /***
     * 异常处理
     * @param e
     * @return
     */
    @ExceptionHandler(value = Exception.class)
    @ResponseBody
    public Result error(Exception e) {
        e.printStackTrace();
        return new Result(false, StatusCode.ERROR, e.getMessage());
    }
}