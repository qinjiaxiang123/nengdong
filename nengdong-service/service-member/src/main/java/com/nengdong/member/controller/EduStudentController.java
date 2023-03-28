package com.nengdong.member.controller;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.nengdong.member.entity.EduStudent;
import com.nengdong.member.entity.EduTeacher;
import com.nengdong.member.service.EduStudentService;
import com.nengdong.member.service.EduTeacherService;
import com.nengdong.member.utils.MD5;
import com.nengdong.member.vo.LoginVo;
import com.nengdong.member.vo.RegisterVo;
import com.nengdong.utils.JwtUtils;
import com.nengdong.utils.Result;
import com.nengdong.utils.vo.UcenterMemberForOrder;
import io.jsonwebtoken.Claims;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.List;


/**
 * <p>
 * 学生表 前端控制器
 * </p>
 *
 * @author testjava
 * @since 2022-03-29
 */
@Api(description = "前台会员用户管理")
@RestController
@RequestMapping("/member/edustudent")
@CrossOrigin
public class EduStudentController {

    @Autowired
    private EduStudentService edustudentService;

    @Autowired
    private EduTeacherService eduTeacherService;

    @ApiOperation(value = "用户注册")
    @PostMapping("register")
    public Result register(@RequestBody RegisterVo registerVo){
        if(registerVo.getType().equals("student")) {
            edustudentService.register(registerVo);
        }else {
            eduTeacherService.register(registerVo);
        }
        return Result.ok();
    }

    @ApiOperation(value = "用户登录")
    @PostMapping("login")
    public Result login(@RequestBody LoginVo loginVo){
        String token="";
        if(loginVo.getType().equals("student")) {
            token = edustudentService.login(loginVo);
        }else{
            token = eduTeacherService.login(loginVo);
        }
        return Result.ok().data("token",token);
    }

    @ApiOperation(value = "根据token字符串获取用户信息")
    @GetMapping("getUcenterByToken")
    public Result getUcenterByToken(HttpServletRequest request){

        Claims memberIdByJwtToken = JwtUtils.getMemberIdByJwtToken(request);


        String id = (String) memberIdByJwtToken.get("id");
        String type = (String) memberIdByJwtToken.get("type");
        if(type.equals("student")){
            EduStudent edustudent = edustudentService.getById(id);
            return Result.ok().data("ucenterMember",edustudent);
        }else if(type.equals("teacher")){
            EduTeacher eduTeacher = eduTeacherService.getById(id);
            System.out.println(eduTeacher);
            return Result.ok().data("ucenterMember",eduTeacher);
        }else {
            return Result.error();
        }
    }

    @ApiOperation(value = "根据memberId获取用户信息跨模块")
    @GetMapping("getUcenterForOrder/{memberId}")
    public UcenterMemberForOrder getUcenterForOrder(
            @PathVariable("memberId") String memberId){
        EduStudent edustudent = edustudentService.getById(memberId);
        UcenterMemberForOrder ucenterMemberForOrder = new UcenterMemberForOrder();
        BeanUtils.copyProperties(edustudent,ucenterMemberForOrder);
        return ucenterMemberForOrder;
    }


    @ApiOperation(value = "统计注册人数远程调用")
    @GetMapping("countRegister/{day}")
    public Result countRegister(@PathVariable("day") String day){
        Integer count = edustudentService.countRegister(day);
        return Result.ok().data("countRegister",count);
    }


    @ApiOperation(value = "根据id获取学生信息")
    @GetMapping("getStudentUcenterById/{memberId}")
    public Result getStudentUcenterById(
            @PathVariable("memberId") String memberId){
        EduStudent edustudent = edustudentService.getById(memberId);
        return Result.ok().data("data",edustudent);
    }

    @ApiOperation(value = "根据id获取教师信息")
    @GetMapping("getTeacherUcenterById/{memberId}")
    public Result getTeacherUcenterById(
            @PathVariable("memberId") String memberId){
        EduTeacher eduTeacher  = eduTeacherService.getById(memberId);
        return Result.ok().data("data",eduTeacher);
    }

    @ApiOperation(value = "改变学生信息")
    @PostMapping("addStudentUcenter")
    public Result getStudentUcenterById(
            @RequestBody EduStudent eduStudent){
        edustudentService.updateStudent(eduStudent);
        return Result.ok();
    }

    @ApiOperation(value = "改变学生密码")
    @PostMapping("updateStudentUcenter")
    public Result updateStudentUcenter(
            @RequestBody EduStudent eduStudent){
        String encrypt = MD5.encrypt(eduStudent.getPassword());
        eduStudent.setPassword(encrypt);
        edustudentService.updateById(eduStudent);

        return Result.ok();
    }

    @ApiOperation(value = "检查旧密码")
    @PostMapping("checkPassword")
    public Result checkPassword(
            @RequestBody EduStudent eduStudent){
        boolean b=edustudentService.checkPassword(eduStudent);
        if(b){
            return Result.ok();

        }else {
            return Result.error();
        }

    }



    @ApiOperation(value = "根据list获取学生信息")
    @PostMapping("getStudentUcenterByList")
    public Result getStudentUcenterByList(
            @RequestBody List<String> list){

         List<EduStudent> eduStudentList= edustudentService.getStudentUcenterByList(list);
        return Result.ok().data("eduStudentList",eduStudentList);
    }

    @ApiOperation(value = "获取学生数量")
    @GetMapping("getStudentNumber")
    public int getStudentNumber(){
        QueryWrapper<EduStudent> eduStudentQueryWrapper=new QueryWrapper<>();
        int count = edustudentService.count(eduStudentQueryWrapper);
        return count;
    }
}

