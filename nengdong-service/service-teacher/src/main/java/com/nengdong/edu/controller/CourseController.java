package com.nengdong.edu.controller;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.nengdong.edu.entity.Course;
import com.nengdong.edu.entity.Teacher;
import com.nengdong.edu.service.CourseService;
import com.nengdong.edu.vo.course.CourseInfoForm;
import com.nengdong.edu.vo.course.CoursePublishVo;
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
@Api(description = "课程管理")
@RestController
@RequestMapping("/edu/course")
@CrossOrigin
public class CourseController {
    @Autowired
    private CourseService courseService;

    @ApiOperation(value = "添加课程信息")
    @PostMapping("addCourseInfo")
    public Result addCourseInfo(@RequestBody CourseInfoForm courseInfoForm){
        String courseId = courseService.addCourseInfo(courseInfoForm);
        return Result.ok().data("courseId",courseId);
    }

    @ApiOperation(value = "根据id课程信息")
    @GetMapping("getCourseInfoById/{id}")
    public Result getCourseInfoById(@PathVariable String id){
        CourseInfoForm courseInfoForm =  courseService.getCourseInfoById(id);
        return Result.ok().data("courseInfo",courseInfoForm);
    }

    @ApiOperation(value = "修改课程信息")
    @PostMapping("updateCourseInfo")
    public Result updateCourseInfo(@RequestBody CourseInfoForm courseInfoForm){
        courseService.updateCourseInfo(courseInfoForm);
        return Result.ok();
    }

    @ApiOperation(value = "根据课程id查询课程发布信息")
    @GetMapping("getCoursePublishById/{id}")
    public Result getCoursePublishById(@PathVariable String id){
        CoursePublishVo coursePublishVo =
                courseService.getCoursePublishById(id);
        return Result.ok().data("coursePublishVo",coursePublishVo);
    }


    @ApiOperation(value = "根据教师id查询课程发布信息")
    @GetMapping("getCoursePublishByTeacherId/{id}")
    public Result getCoursePublishByTeacherId(@PathVariable String id){
        List<Course> listvo =
                courseService.getCoursePublishByTeacherId(id);
        return Result.ok().data("coursePublishVo",listvo);
    }

    @ApiOperation(value = "根据id发布课程")
    @PutMapping("publishCourse/{id}")
    public Result publishCourse(@PathVariable String id){
        Course eduCourse = courseService.getById(id);
        eduCourse.setStatus("Normal");
        courseService.updateById(eduCourse);
        return Result.ok();
    }

    @ApiOperation(value = "查询所有课程信息")
    @GetMapping("getCourseInfo")
//TODO 实现带条件、带分页查询
    public Result getCourseInfo(){
        List<Course> list = courseService.list(null);
        return Result.ok().data("list",list);
    }

    @ApiOperation(value = "根据id删除课程相关信息")
    @DeleteMapping("delCourseInfo/{id}")
    public Result delCourseInfo(@PathVariable String id){
        courseService.delCourseInfo(id);
        return Result.ok();
    }

    @ApiOperation(value = "获取课程数量")
    @GetMapping("getCourseNumber")
    public int getCourseNumber(){
        System.out.println(123);
        QueryWrapper<Course> courseQueryWrapper=new QueryWrapper<>();
        int count = courseService.count(courseQueryWrapper);
        return count;
    }

    @ApiOperation(value = "统计课程数量远程调用")
    @GetMapping("countCourse/{day}")
    public Result countCourse(@PathVariable("day") String day){
        Integer count = courseService.countCourse(day);
        return Result.ok().data("countCourse",count);
    }

    @ApiOperation(value = "搜索课程")
    @GetMapping("getCourseBySearch/{serachdata}")
    public Result getCourseBySearch(@PathVariable String serachdata){

        QueryWrapper<Course> courseQueryWrapper=new QueryWrapper<>();

        courseQueryWrapper.like("title",serachdata);

        List<Course> list = courseService.list(courseQueryWrapper);

        return Result.ok().data("courselist",list);
    }


    @ApiOperation(value = "购买课程数据修改")
    @GetMapping("buyCourse/{courseid}")
    public Result buyCourse(@PathVariable String courseid){



        Boolean b=courseService.buyCourse(courseid);

        return Result.ok();
    }

    @ApiOperation(value = "观看课程数据修改")
    @GetMapping("viewCourse/{courseid}")
    public Result viewCourse(@PathVariable String courseid){



        Boolean b=courseService.viewCourse(courseid);

        return Result.ok();
    }

}

