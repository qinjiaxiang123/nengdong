package com.nengdong.orderservice.client;

import com.nengdong.utils.vo.CourseWebVoForOrder;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@Component
@FeignClient("service-education")
public interface EduClient {
    //根据课程id查询课程相关信息跨模块
    @GetMapping("/edu/courseapi/getCourseInfoForOrder/{courseId}")
    public CourseWebVoForOrder getCourseInfoForOrder(
            @PathVariable("courseId") String courseId);

}

