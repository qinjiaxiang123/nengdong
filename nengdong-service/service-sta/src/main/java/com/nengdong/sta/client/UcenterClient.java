package com.nengdong.sta.client;


import com.nengdong.utils.Result;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@Component
@FeignClient("service-member")
public interface UcenterClient {
    //统计注册人数远程调用
    @GetMapping("/member/edustudent/countRegister/{day}")
    public Result countRegister(@PathVariable("day") String day);

    @GetMapping("/member/edustudent/getStudentNumber/")
    public int getStudentNumber();


}
