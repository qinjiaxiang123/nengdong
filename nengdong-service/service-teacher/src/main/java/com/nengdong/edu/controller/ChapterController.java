package com.nengdong.edu.controller;


import com.nengdong.edu.entity.Chapter;
import com.nengdong.edu.service.ChapterService;
import com.nengdong.edu.vo.chapter.ChapterVo;
import com.nengdong.utils.Result;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * <p>
 * 课程 前端控制器
 * </p>
 *
 * @author testjava
 * @since 2022-03-27
 */
@Api(description="章节管理")
@RestController
@RequestMapping("/edu/chapter")
@CrossOrigin
public class ChapterController {
    @Autowired
    private ChapterService chapterService;

    @ApiOperation(value = "根据课程id查询章节、小节信息")
    @GetMapping("getChapterVideoById/{courseId}")
    public Result getChapterVideoById(@PathVariable String courseId){
        List<ChapterVo> chapterVoList = chapterService.getChapterVideoById(courseId);
        return Result.ok().data("chapterVideoList",chapterVoList);
    }

    @ApiOperation(value = "添加章节")
    @PostMapping("addChapter")
    public Result addChapter(@RequestBody Chapter eduChapter){
        chapterService.save(eduChapter);
        return Result.ok();
    }
    @ApiOperation(value = "根据id删除章节")
    @DeleteMapping("delChapter/{id}")
    public Result delChapter(@PathVariable String id){
        chapterService.removeById(id);
        return Result.ok();
    }
    @ApiOperation(value = "根据id查询章节")
    @GetMapping("getChapterById/{id}")
    public Result getChapterById(@PathVariable String id){
        Chapter eduChapter = chapterService.getById(id);
        return Result.ok().data("eduChapter",eduChapter);
    }
    @ApiOperation(value = "修改章节")
    @PostMapping("updateChapter")
    public Result updateChapter(@RequestBody Chapter eduChapter){
        chapterService.updateById(eduChapter);
        return Result.ok();
    }
}

