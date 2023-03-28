package com.nengdong.edu.controller;


import com.nengdong.edu.client.VodClient;
import com.nengdong.edu.entity.Videoandfile;
import com.nengdong.edu.service.VideoandfileService;
import com.nengdong.edu.vo.videoandfile.VideoVo;
import com.nengdong.utils.Result;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * <p>
 * 课程视频 前端控制器
 * </p>
 *
 * @author testjava
 * @since 2022-03-27
 */
@Api(description="小节管理")
@RestController
@RequestMapping("/edu/videoandfile")
@CrossOrigin
public class VideoandfileController {
    @Autowired
    private VideoandfileService videoandfileService;


    @Autowired
    private VodClient vodClient;

    @ApiOperation(value = "添加小节")
    @PostMapping("addVideo")
    public Result addVideo(@RequestBody Videoandfile eduVideo){
        videoandfileService.save(eduVideo);
        return Result.ok();
    }
    @ApiOperation(value = "根据id删除小节")
    @DeleteMapping("delVideo/{id}")
    public Result delVideo(@PathVariable String id){
        Videoandfile eduVideo = videoandfileService.getById(id);
        String videoId = eduVideo.getVideoSourceId();
        System.out.println("------------------------------");
        System.out.println("------------------------------");

        System.out.println(videoId);
        System.out.println("------------------------------");

        System.out.println("------------------------------");

        if(videoId!=null){
            vodClient.delVideo(videoId);
        }
        videoandfileService.removeById(id);
        return Result.ok();
    }

    @ApiOperation(value = "根据id查询小节")
    @GetMapping("getVideoById/{id}")
    public Result getVideoById(@PathVariable String id){
        Videoandfile eduVideo = videoandfileService.getById(id);
        return Result.ok().data("eduVideo",eduVideo);
    }
    @ApiOperation(value = "修改小节")
    @PostMapping("updateVideo")
    public Result updateVideo(@RequestBody Videoandfile eduVideo){
        videoandfileService.updateById(eduVideo);
        return Result.ok();
    }
    //后加
    @ApiOperation(value = "新增课时")
    @PostMapping("save-video-info")
    public Result save(
            @ApiParam(name = "videoForm", value = "课时对象", required = true)
            @RequestBody VideoVo videoVo){

        videoandfileService.saveVideoInfo(videoVo);
        return Result.ok();
    }

    @ApiOperation(value = "根据ID查询课时")
    @GetMapping("video-info/{id}")
    public Result getVideInfoById(
            @ApiParam(name = "id", value = "课时ID", required = true)
            @PathVariable String id){

        VideoVo videoVo = videoandfileService.getVideoInfoFormById(id);
        return Result.ok().data("item", videoVo);
    }

    @ApiOperation(value = "更新课时")
    @PutMapping("update-video-info/{id}")
    public Result updateCourseInfoById(
            @ApiParam(name = "VideoInfoForm", value = "课时基本信息", required = true)
            @RequestBody VideoVo videoVo,

            @ApiParam(name = "id", value = "课时ID", required = true)
            @PathVariable String id){

        videoandfileService.updateVideoInfoById(videoVo);
        return Result.ok();
    }

    @ApiOperation(value = "根据ID删除课时")
    @DeleteMapping("{id}")
    public Result removeById(
            @ApiParam(name = "id", value = "课时ID", required = true)
            @PathVariable String id){

        boolean result = videoandfileService.removeVideoById(id);
        if(result){
            return Result.ok();
        }else{
            return Result.error().message("删除失败");
        }
    }

}

