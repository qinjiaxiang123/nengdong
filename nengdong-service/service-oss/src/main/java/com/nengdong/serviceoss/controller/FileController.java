package com.nengdong.serviceoss.controller;


import com.nengdong.serviceoss.service.FileService;
import com.nengdong.utils.Result;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@Api(description="文件管理")
@RestController
@RequestMapping("/eduoss/fileoss")
@CrossOrigin
public class FileController {

    @Autowired
    private FileService fileService;

    @ApiOperation(value = "上传文件")
    @PostMapping("uploadFile")
    public Result uploadFile(MultipartFile file){
        //1获取文件
        //2调用接口上传文件，获取Url
        String url = fileService.uploadFileOSS(file);
        return Result.ok().data("url",url);
    }


    
}
