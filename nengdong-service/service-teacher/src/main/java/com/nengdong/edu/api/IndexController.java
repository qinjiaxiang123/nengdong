package com.nengdong.edu.api;



import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.nengdong.edu.entity.Course;
import com.nengdong.edu.entity.Teacher;
import com.nengdong.edu.service.CourseService;
import com.nengdong.edu.service.TeacherService;
import com.nengdong.utils.Result;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@Api(description="首页显示")
@RestController
@RequestMapping("/edu/index")
@CrossOrigin
public class IndexController {

    @Autowired
    private CourseService courseService;
    @Autowired
    private TeacherService teacherService;

    @ApiOperation(value = "首页展示8条课程信息4条讲师信息")
    @GetMapping("getCourseTeacherList")
    public Result getCourseTeacherList(){
        //8条课程信息
        QueryWrapper<Course> courseWrapper = new QueryWrapper<>();
        courseWrapper.orderByDesc("gmt_create");
        courseWrapper.last("LIMIT 8");
        List<Course> courseList = courseService.list(courseWrapper);
        //8条讲师信息
        QueryWrapper<Teacher> teacherWrapper = new QueryWrapper<>();
        teacherWrapper.eq("is_register","1");
        teacherWrapper.orderByDesc("gmt_create");
        teacherWrapper.last("LIMIT 8");
        List<Teacher> teacherList = teacherService.list(teacherWrapper);
        return Result.ok().data("courseList",courseList).data("teacherList",teacherList);
        
    }


}
