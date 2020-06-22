package com.qiuhang.controller;


import com.qiuhang.changgou.entity.Result;
import com.qiuhang.changgou.entity.StatusCode;
import com.qiuhang.file.FastDFSFile;
import com.qiuhang.util.FastDFSClient;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

/**
 * @ProjectName: changgouparent
 * @Package: com.qiuhang.changgou.controller
 * @ClassName: FileController
 * @Author: qiuhang
 * @Description: ${description}
 * @Date: 2020/4/2 15:30
 * @Version: 1.0
 */
@RestController
@CrossOrigin
@RequestMapping("file")
public class FileController {

    @PostMapping("/upload")
    public Result upload(MultipartFile file) throws IOException {
        FastDFSFile fastDFSFile=new FastDFSFile(file.getOriginalFilename(),file.getBytes(),"jpg");
        String[] upload = FastDFSClient.upload(fastDFSFile);
        String url="192.168.211.132:8080/"+upload[0]+"/"+upload[1];
        return new Result(true, StatusCode.OK,url,"文件上传成功");
    }

    @DeleteMapping("/delete")
    public Result delete(String groupName, String remoteFileName) throws Exception {
        int i=FastDFSClient.deleteFile(groupName,remoteFileName);
        return new Result(true, StatusCode.OK,i+"","文件删除成功");
    }

}
