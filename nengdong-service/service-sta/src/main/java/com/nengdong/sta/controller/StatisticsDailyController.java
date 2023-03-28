package com.nengdong.sta.controller;


import com.nengdong.sta.client.EduClient;
import com.nengdong.sta.client.OrderClient;
import com.nengdong.sta.client.UcenterClient;
import com.nengdong.sta.service.StatisticsDailyService;
import com.nengdong.utils.Result;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * <p>
 * 网站统计日数据 前端控制器
 * </p>
 *
 * @author testjava
 * @since 2022-03-28
 */
@Api(description = "统计分析管理")
@RestController
@RequestMapping("/sta/statisticsdaily")
@CrossOrigin
@EnableSwagger2
public class StatisticsDailyController {
    @Autowired
    private StatisticsDailyService dailyService;

    @Autowired
    private UcenterClient ucenterClient;

    @Autowired
    private EduClient eduClient;

    @Autowired
    private OrderClient orderClient;

    @ApiOperation(value = "生成统计数据")
    @PostMapping("createStaDaily/{day}")
    public Result createStaDaily(@PathVariable String day){
        dailyService.createStaDaily(day);
        return Result.ok();

    }

    @ApiOperation(value = "查询统计数据")
    @GetMapping("getStaDaily/{type}/{begin}/{end}")
    public Result getStaDaily(@PathVariable String type,
                         @PathVariable String  begin,
                         @PathVariable String  end){
        Map<String,Object> map = dailyService.getStaDaily(type,begin,end);
        return Result.ok().data(map);
    }

    @ApiOperation(value = "查询统计数据")
    @GetMapping("getHomeSta")
    public Result getHomeSta(){
        int courseNumber = eduClient.getCourseNumber();
        int orderNumber = orderClient.getOrderNumber();
        int teacherNumber = eduClient.getTeacherNumber();
        int studentNumber = ucenterClient.getStudentNumber();

        return Result.ok().data("courseNumber",courseNumber).data("orderNumber",orderNumber).data("teacherNumber",teacherNumber).data("studentNumber",studentNumber);
    }


    @ApiOperation(value = "一键生成统计数据")
    @GetMapping("createStaDailyAll")
    public Result createStaDailyAll(){

        Date date = new Date();
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        String endday = dateFormat.format(date);


        List<String> result = new ArrayList<String>();


        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        Date start_date = null;
        Date end_date = null;
        try {
            start_date = sdf.parse("2022-1-1");
            end_date = sdf.parse(endday);
            Calendar tempStart = Calendar.getInstance();
            tempStart.setTime(start_date);
            Calendar tempEnd = Calendar.getInstance();
            tempEnd.setTime(end_date);
            while (tempStart.before(tempEnd)||tempStart.equals(tempEnd)) {
                result.add(sdf.format(tempStart.getTime()));
                tempStart.add(Calendar.DAY_OF_YEAR, 1);
            }
        } catch (ParseException e) {
            e.printStackTrace();
        }


        for (int i = 0; i <result.size(); i++) {
            dailyService.createStaDaily(result.get(i));
        }

        return Result.ok();

    }

}

