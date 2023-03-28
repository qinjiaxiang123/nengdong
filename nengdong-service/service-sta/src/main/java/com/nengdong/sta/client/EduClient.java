package com.nengdong.sta.client;


import com.nengdong.utils.Result;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@Component
@FeignClient("service-education")
public interface EduClient {


    @GetMapping("/edu/teacher/getTeacherNumber/")
    public int getTeacherNumber();

    @GetMapping("/edu/course/getCourseNumber/")
    public int getCourseNumber();

    @GetMapping("/edu/teacher/countTeacher/{day}")
    public Result countTeacher(@PathVariable("day") String day);

    @GetMapping("/edu/course/countCourse/{day}")
    public Result countCourse(@PathVariable("day") String day);

}
