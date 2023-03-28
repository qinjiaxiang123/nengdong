package com.nengdong.service.controller;

import com.aliyun.vod.upload.impl.UploadVideoImpl;
import com.aliyun.vod.upload.req.UploadStreamRequest;
import com.aliyun.vod.upload.resp.UploadStreamResponse;
import com.aliyuncs.DefaultAcsClient;
import com.aliyuncs.exceptions.ClientException;
import com.aliyuncs.vod.model.v20170321.DeleteVideoRequest;
import com.aliyuncs.vod.model.v20170321.GetVideoPlayAuthRequest;
import com.aliyuncs.vod.model.v20170321.GetVideoPlayAuthResponse;
import com.nengdong.edu.manage.NengdongException;
import com.nengdong.service.utils.AliyunVodSDKUtils;
import com.nengdong.utils.Result;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;

@Api(description="视频管理")
@RestController
@RequestMapping("/eduvod/video")
@CrossOrigin
public class VideoAdminController {

    @ApiOperation(value = "上传视频")
    @PostMapping("uploadVideo")
    public Result uploadVideo(MultipartFile file){

        try {
            InputStream inputStream = file.getInputStream();
            String originalFilename = file.getOriginalFilename();
            String title = originalFilename.substring(0,originalFilename.lastIndexOf("."));
            UploadStreamRequest request = new UploadStreamRequest(
                    "LTAI5tSDAdPKRS43rQaioKL8",
                    "PaYwQ1yuAxIu9VHpCQQxac4fgp7jsR",
                    title,
                    originalFilename,
                    inputStream
            );
            UploadVideoImpl uploader = new UploadVideoImpl();
            UploadStreamResponse response = uploader.uploadStream(request);
            /* 如果设置回调URL无效，不影响视频上传，可以返回VideoId同时会返回错误码。其他情况上传失败时，VideoId为空，此时需要根据返回错误码分析具体错误原因 */

            String videoId = response.getVideoId();
            System.out.println(videoId);
            return Result.ok().data("videoId",videoId);

        } catch (IOException e) {
            e.printStackTrace();
            throw  new NengdongException(20001,"上传视频失败");

        }

    }


    @ApiOperation(value = "删除视频")
    @DeleteMapping("delVideo/{videoId}")
    public Result delVideo(@PathVariable("videoId") String videoId){

        try {
            //*初始化客户端对象
            DefaultAcsClient client = AliyunVodSDKUtils.initVodClient("LTAI5tSDAdPKRS43rQaioKL8",
                    "PaYwQ1yuAxIu9VHpCQQxac4fgp7jsR");
            //*创建请求对象（不同操作，类不同）
            DeleteVideoRequest request = new DeleteVideoRequest();
            //*创建响应对象（不同操作，类不同）
            //DeleteVideoResponse response = new DeleteVideoResponse();
            //*向请求中设置参数
            //支持传入多个视频ID，多个用逗号分隔
            request.setVideoIds(videoId);
            //*调用客户端对象方法发送请求，拿到响应
            client.getAcsResponse(request);
            return Result.ok();

        } catch (ClientException e) {
            e.printStackTrace();
            throw new NengdongException(20001,"删除视频失败");
        }

    }

    @ApiOperation(value = "批量删除视频")
    @DeleteMapping("delVideoList")
    public Result delVideo(@RequestParam("videoIdList") List<String> videoIdList){
        try {
            //*初始化客户端对象
            DefaultAcsClient client = AliyunVodSDKUtils.initVodClient("LTAI5tSDAdPKRS43rQaioKL8",
                    "PaYwQ1yuAxIu9VHpCQQxac4fgp7jsR");
            //*创建请求对象（不同操作，类不同）
            DeleteVideoRequest request = new DeleteVideoRequest();

            //*向请求中设置参数
            //支持传入多个视频ID，多个用逗号分隔
            //11,12,13
            String videoIds = org.apache.commons.lang.StringUtils.join(videoIdList.toArray(),",");
            request.setVideoIds(videoIds);
            //*调用客户端对象方法发送请求，拿到响应
            client.getAcsResponse(request);
            return Result.ok();

        } catch (ClientException e) {
            e.printStackTrace();
            throw new NengdongException(20001,"批量删除视频失败");
        }

    }

    @ApiOperation(value = "根据视频id获取视频播放凭证")
    @GetMapping("getPlayAuth/{vid}")
    public Result getPlayAuth(@PathVariable String vid){
        try {
            //（1）创建初始化对象
            DefaultAcsClient client = AliyunVodSDKUtils.initVodClient("LTAI5tSDAdPKRS43rQaioKL8", "PaYwQ1yuAxIu9VHpCQQxac4fgp7jsR");
            //（2）创建request、response对象
            GetVideoPlayAuthRequest request = new GetVideoPlayAuthRequest();
            GetVideoPlayAuthResponse response = new GetVideoPlayAuthResponse();
            //（3）向request设置视频id
            request.setVideoId(vid);
            //播放凭证有过期时间，默认值：100秒 。取值范围：100~3000。
            //request.setAuthInfoTimeout(200L);
            //（4）调用初始化方法实现功能

            System.out.println(vid);
            response = client.getAcsResponse(request);
            //（5）调用方法返回response对象，获取内容
            String playAuth = response.getPlayAuth();
            return Result.ok().data("playAuth",playAuth);
        } catch (ClientException e) {
            throw new NengdongException(20001,"获取视频凭证失败");
        }
    }




}
