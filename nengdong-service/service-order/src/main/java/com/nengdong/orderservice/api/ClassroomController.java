package com.nengdong.orderservice.api;

import com.nengdong.orderservice.service.StudentOrderService;
import com.nengdong.utils.Result;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 教室展示
 *
 */
@Api(description="教室学生展示")
@RestController
@RequestMapping("/orderservice/classroom")
@CrossOrigin
public class ClassroomController {
    @Autowired
    StudentOrderService studentOrderService;

    @ApiOperation("根据课程查询学生")
    @GetMapping("getStudentByCourseId/{courseid}")
    public Result getStudentByCourseId(@PathVariable("courseid")String courseid){
        List<String> list=studentOrderService.getStudentByCourseId(courseid);
        return Result.ok().data("studentList",list);
    }



}
