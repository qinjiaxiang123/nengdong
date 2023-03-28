package com.nengdong.edu.controller;


import com.nengdong.edu.vo.subject.OneSubjectVo;
import com.nengdong.edu.service.SubjectService;
import com.nengdong.utils.Result;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import org.springframework.web.multipart.MultipartFile;

import java.util.List;

/**
 * <p>
 * 课程科目 前端控制器
 * </p>
 *
 * @author testjava
 * @since 2022-03-27
 */
@Api(description = "课程分类管理")
@RestController
@RequestMapping("/edu/subject")
@CrossOrigin
public class SubjectController {
    @Autowired
    private SubjectService subjectService;

    @ApiOperation(value = "批量导入课程分类")
    @PostMapping("addSubject")
    public Result addSubject(MultipartFile file){
        subjectService.addSubject(file,subjectService);
        return Result.ok();
    }

    @ApiOperation(value = "查询所有课程分类")
    @GetMapping("getAllSubject")
    public Result getAllSubject(){
        List<OneSubjectVo> allSubjecList = subjectService.getAllSubject();
        return Result.ok().data("allSubject",allSubjecList);
    }
}

