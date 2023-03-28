package com.nengdong.edu.client;

import com.atguigu.commonutils.R;
import io.swagger.annotations.ApiOperation;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Component
@FeignClient(value = "service-vod", fallback = VodFileDegradeFeignClient.class)
public interface VodClient {

    //"删除视频"
    //请求url必须完整
    //参数注解参数名不能省略@PathVariable("videoId")
    @DeleteMapping("/eduvod/video/delVideo/{videoId}")
    public R delVideo(@PathVariable("videoId") String videoId);


    @ApiOperation(value = "批量删除视频")
    @DeleteMapping("/eduvod/video/delVideoList")
    public R delVideo(@RequestParam("videoIdList") List<String> videoIdList);

}
