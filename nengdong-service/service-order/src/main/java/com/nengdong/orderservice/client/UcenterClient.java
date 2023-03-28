package com.nengdong.orderservice.client;

import com.nengdong.utils.vo.UcenterMemberForOrder;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@Component
@FeignClient("service-member")
public interface UcenterClient {

    //根据memberId获取用户信息跨模块
    @GetMapping("/member/edustudent/getUcenterForOrder/{memberId}")
    public UcenterMemberForOrder getUcenterForOrder(
            @PathVariable("memberId") String memberId);
}
