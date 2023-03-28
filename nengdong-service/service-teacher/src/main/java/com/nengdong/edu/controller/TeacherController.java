package com.nengdong.edu.controller;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.nengdong.edu.entity.Teacher;
import com.nengdong.edu.vo.teacher.TeacherQuery;
import com.nengdong.utils.Result;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import com.nengdong.edu.service.TeacherService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * <p>
 * 讲师 前端控制器
 * </p>
 *
 * @author testjava
 * @since 2022-03-22
 */
@Api(description = "讲师管理")
@RestController
@RequestMapping("/edu/teacher")
@CrossOrigin
public class TeacherController {

    @Autowired
    private TeacherService teacherService;

    @ApiOperation(value = "所有讲师列表")
    @GetMapping
    public Result getAllTeacher(){

        List<Teacher> list = teacherService.list(null);
        return Result.ok().data("list",list);
    }

    @ApiOperation(value = "删除讲师")
    @DeleteMapping("{id}")
    public Result delTeacher(@PathVariable String id){
        boolean remove = teacherService.removeById(id);
        if(remove){
            return Result.ok();
        }else{
            return Result.error();
        }
    }

    @ApiOperation(value = "分页查询讲师列表")
    @GetMapping("getTeacherPage/{current}/{limit}")
    public Result getTeacherPage(@PathVariable Long current,
                            @PathVariable Long limit){
        Page<Teacher> page = new Page<>(current,limit);
        teacherService.page(page,null);
        List<Teacher> records = page.getRecords();
        long total = page.getTotal();
        //1、存入MAP
//        Map<String,Object> map = new HashMap<>();
//        map.put("list",records);
//        map.put("total",total);
//        return R.ok().data(map);
        //2、直接拼接
        return Result.ok().data("list",records).data("total",total);

    }


    @ApiOperation(value = "带条件分页查询讲师列表")
    @PostMapping("getTeacherPageVo/{current}/{limit}")
    public Result getTeacherPageVo(@PathVariable Long current,
                              @PathVariable Long limit,
                              @RequestBody TeacherQuery teacherQuery){
        //@RequestBody把json串转化成实体类
        //1、取出查询条件
        String name = teacherQuery.getName();
        Integer isregister = teacherQuery.getIsregister();
        String begin = teacherQuery.getBegin();
        String end = teacherQuery.getEnd();
        //2、判断条件是否为空，如不为空拼写sql
        QueryWrapper<Teacher> wrapper = new QueryWrapper<>();
        if(!StringUtils.isEmpty(name)){
            wrapper.like("name",name);
        }
        if(!StringUtils.isEmpty(isregister)){
            wrapper.eq("is_register",isregister);
        }
        if(!StringUtils.isEmpty(begin)){
            wrapper.ge("gmt_create",begin);
        }
        if(!StringUtils.isEmpty(end)){
            wrapper.le("gmt_create",end);
        }

        Page<Teacher> page = new Page<>(current,limit);
        teacherService.page(page,wrapper);
        List<Teacher> records = page.getRecords();
        long total = page.getTotal();
        //1、存入MAP
//        Map<String,Object> map = new HashMap<>();
//        map.put("list",records);
//        map.put("total",total);
//        return R.ok().data(map);
        //2、直接拼接
        return Result.ok().data("list",records).data("total",total);

    }

    @ApiOperation(value = "添加讲师")
    @PostMapping("addTeacher")
    public Result addTeacher( @RequestBody Teacher eduTeacher){
        boolean save = teacherService.save(eduTeacher);
        if(save){
            return Result.ok();
        }else{
            return Result.error();
        }
    }

    @ApiOperation(value = "根据id查询讲师")
    @GetMapping("getTeacherById/{id}")
    public Result getTeacherById(@PathVariable String id){
        Teacher eduTeacher = teacherService.getById(id);
        return Result.ok().data("eduTeacher",eduTeacher);
    }

    @PostMapping("updateTeacher")
    public Result updateTeacher( @RequestBody Teacher eduTeacher){
        boolean update = teacherService.updateById(eduTeacher);
        System.out.println(eduTeacher);
        if(update){
            return Result.ok();
        }else{
            return Result.error();
        }
    }

    @ApiOperation(value = "获取教师数量")
    @GetMapping("getTeacherNumber")
    public int getTeacherNumber(){
        QueryWrapper<Teacher> teacherQueryWrapper=new QueryWrapper<>();
        int count = teacherService.count(teacherQueryWrapper);
        return count;
    }

    @ApiOperation(value = "统计教师数量远程调用")
    @GetMapping("countTeacher/{day}")
    public Result countTeacher(@PathVariable("day") String day){
        Integer count = teacherService.countTeacher(day);
        return Result.ok().data("countTeacher",count);
    }

    @ApiOperation(value = "搜索教师")
    @GetMapping("getTeacherBySearch/{serachdata}")
    public Result getCourseBySearch(@PathVariable String serachdata){

        QueryWrapper<Teacher> courseQueryWrapper=new QueryWrapper<>();
        courseQueryWrapper.like("name",serachdata);
        courseQueryWrapper.eq("is_register",1);
        List<Teacher> list = teacherService.list(courseQueryWrapper);

        return Result.ok().data("teacherlist",list);
    }
}

