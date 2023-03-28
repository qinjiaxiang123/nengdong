package com.nengdong.sta.client;


import com.nengdong.utils.Result;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@Component
@FeignClient("service-order")
public interface OrderClient {

    @GetMapping("/orderservice/studentorder/getOrderNumber/")
    public int getOrderNumber();

    @GetMapping("/orderservice/studentorder/countOrder/{day}")
    public Result countOrder(@PathVariable("day") String day);
}
