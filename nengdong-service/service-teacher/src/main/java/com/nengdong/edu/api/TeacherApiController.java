package com.nengdong.edu.api;

import com.atguigu.commonutils.R;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.nengdong.edu.entity.Course;
import com.nengdong.edu.entity.Teacher;
import com.nengdong.edu.service.CourseService;
import com.nengdong.edu.service.TeacherService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@Api(description = "前台讲师展示")
@RestController
@RequestMapping("/edu/teacher")
@CrossOrigin
public class TeacherApiController {

    @Autowired
    private TeacherService teacherService;

    @Autowired
    private CourseService courseService;

    @ApiOperation(value = "前台分页查询讲师列表")
    @GetMapping("getTeacherApiPage/{current}/{limit}")
    public R getTeacherApiPage(@PathVariable Long current,
                               @PathVariable Long limit) {
        Page<Teacher> page = new Page<>(current,limit);
        Map<String,Object> map = teacherService.getTeacherApiPage(page);
        return R.ok().data(map);

    }

    @ApiOperation(value = "前台查询讲师详情")
    @GetMapping("getTeacherCourseById/{id}")
    public R getTeacherById(@PathVariable String id){
        Teacher eduTeacher = teacherService.getById(id);

        QueryWrapper<Course> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("teacher_id",id);
        List<Course> courseList = courseService.list(queryWrapper);
        return R.ok().data("eduTeacher",eduTeacher).data("courseList",courseList);
    }
}
