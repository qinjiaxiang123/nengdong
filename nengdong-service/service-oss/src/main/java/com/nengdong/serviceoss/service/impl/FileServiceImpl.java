package com.nengdong.serviceoss.service.impl;

import com.aliyun.oss.OSS;
import com.aliyun.oss.OSSClientBuilder;
import com.nengdong.edu.manage.NengdongException;
import com.nengdong.serviceoss.service.FileService;
import com.nengdong.serviceoss.utils.ConstantPropertiesUtil;
import org.joda.time.DateTime;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;
import java.util.UUID;
//        // yourEndpoint填写Bucket所在地域对应的Endpoint。以华东1（杭州）为例，Endpoint填写为https://oss-cn-hangzhou.aliyuncs.com。
//        String endpoint = "oss-cn-shenzhen.aliyuncs.com";
//        // 阿里云账号AccessKey拥有所有API的访问权限，风险很高。强烈建议您创建并使用RAM用户进行API访问或日常运维，请登录RAM控制台创建RAM用户。
//        String accessKeyId = "LTAI5tSDAdPKRS43rQaioKL8";
//        String accessKeySecret = "PaYwQ1yuAxIu9VHpCQQxac4fgp7jsR";
//        String bucketName="guli-file20211125";
// yourEndpoint填写Bucket所在地域对应的Endpoint。以华东1（杭州）为例，Endpoint填写为https://oss-cn-hangzhou.aliyuncs.com。
// 阿里云账号AccessKey拥有所有API的访问权限，风险很高。强烈建议您创建并使用RAM用户进行API访问或日常运维，请登录RAM控制台创建RAM用户。
// 依次填写Bucket名称（例如examplebucket）和Object完整路径（例如exampledir/exampleobject.txt）。Object完整路径中不能包含Bucket名称。

@Service
public class FileServiceImpl implements FileService {
    @Override
    public String uploadFileOSS(MultipartFile file) {
        String ep = ConstantPropertiesUtil.END_POINT;
        String aki = ConstantPropertiesUtil.ACCESS_KEY_ID;
        String aks = ConstantPropertiesUtil.ACCESS_KEY_SECRET;
        String bn = ConstantPropertiesUtil.BUCKET_NAME;
        String fn = file.getOriginalFilename();
        // 根据信息创建OSS实例。
        OSS ossClient = new OSSClientBuilder().build(ep, aki, aks);
        try {
            InputStream inputStream = file.getInputStream();

            //添加UUID
            fn = UUID.randomUUID().toString()+fn;

            //添加日期
            String path = new DateTime().toString("yyyy/MM/dd");
            fn = path+"/"+fn;

            ossClient.putObject(bn,fn,inputStream);
            // 关闭OSSClient。
            ossClient.shutdown();
            String url = "https://"+bn+"."+ep+"/"+fn;
            return url;
        } catch (IOException e) {
            e.printStackTrace();
            throw new NengdongException(20001,"上传失败");
        }
    }
}
