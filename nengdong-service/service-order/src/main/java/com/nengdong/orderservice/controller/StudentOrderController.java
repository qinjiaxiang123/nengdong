package com.nengdong.orderservice.controller;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.nengdong.orderservice.entity.StudentOrder;
import com.nengdong.orderservice.service.StudentOrderService;
import com.nengdong.utils.JwtUtils;
import com.nengdong.utils.Result;
import io.jsonwebtoken.Claims;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Map;

/**
 * <p>
 * 订单 前端控制器
 * </p>
 *
 * @author testjava
 * @since 2022-04-14
 */
@Api(description="前台课程展示")
@RestController
@RequestMapping("/orderservice/studentorder")
@CrossOrigin
public class StudentOrderController {
    @Autowired
    private StudentOrderService studentOrderService;


    @ApiOperation(value = "根据课程id、用户id创建订单")
    @PostMapping("createOrder/{courseId}")
    public Result createOrder(@PathVariable String courseId,
                              HttpServletRequest request){
        //String memberId = JwtUtils.getMemberIdByJwtToken(request);
        Claims memberIdByJwtToken = JwtUtils.getMemberIdByJwtToken(request);
        String memberId=(String)memberIdByJwtToken.get("id");
        System.out.println("+++");
        System.out.println(memberId);
        String orderNo = studentOrderService.createOrder(courseId,memberId);
        return Result.ok().data("orderNo",orderNo);
    }


    @ApiOperation(value = "根据订单编号查询订单信息")
    @GetMapping("getOrderInfo/{orderNo}")
    public Result getOrderInfo(@PathVariable String orderNo){
        QueryWrapper<StudentOrder> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("order_no",orderNo);
        StudentOrder studentOrder = studentOrderService.getOne(queryWrapper);
        return Result.ok().data("order",studentOrder);
    }

    @ApiOperation(value = "根据课程id、用户id查询是已购买,远程调用")
    @GetMapping("isBuyCourse/{courseId}/{memberId}")
    public boolean isBuyCourse(@PathVariable("courseId") String courseId,
                               @PathVariable("memberId") String memberId){
        QueryWrapper<StudentOrder> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("status",1);
        queryWrapper.eq("course_id",courseId);
        queryWrapper.eq("member_id",memberId);
        int count = studentOrderService.count(queryWrapper);
        if(count>0) {
            return true;
        } else {
            return false;
        }

    }


    @ApiOperation(value = "根据课用户id查询购买的课程")
    @GetMapping("getCoursebyUserId/{memberId}")
    public Result getCoursebyUserId(@PathVariable("memberId") String memberId){
        QueryWrapper<StudentOrder> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("member_id",memberId);
        List<StudentOrder> list = studentOrderService.list(queryWrapper);

        HashSet hashSet = new HashSet();
        for (StudentOrder s:list
             ) {
            hashSet.add(s.getCourseId());
        }
        return Result.ok().data("data",hashSet);

    }

    @ApiOperation(value = "获取订单数量")
    @GetMapping("getOrderNumber")
    public int getOrderNumber(){
        QueryWrapper<StudentOrder> studentOrderQueryWrapper=new QueryWrapper<>();
        int count = studentOrderService.count(studentOrderQueryWrapper);
        return count;
    }

    @ApiOperation(value = "统计订单数量远程调用")
    @GetMapping("countOrder/{day}")
    public Result countOrder(@PathVariable("day") String day){
        Integer count = studentOrderService.countOrder(day);
        return Result.ok().data("countOrder",count);
    }

    @ApiOperation(value = "根据课用户id查询订单信息")
    @GetMapping("getOrderbyUserId/{memberId}")
    public Result getOrderbyUserId(@PathVariable("memberId") String memberId){
        QueryWrapper<StudentOrder> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("member_id",memberId);
        queryWrapper.orderByAsc("gmt_create");
        List<StudentOrder> orderlist = studentOrderService.list(queryWrapper);


        return Result.ok().data("orderlist",orderlist);

    }

    @ApiOperation(value = "获取所有订单")
    @GetMapping("getAllOrder")
    public Result getAllOrder(){
        List<StudentOrder> list = studentOrderService.list(null);


        return Result.ok().data("orderlist",list);

    }

    @ApiOperation(value = "根据订单编号生成数据")
    @GetMapping("createNative/{orderNo}")
    public Result createNative(@PathVariable String orderNo){
        Map<String,Object> map= studentOrderService.createNative(orderNo);
        return Result.ok().data(map);
    }
}

