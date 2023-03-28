package com.nengdong.smsservice.controller;


import com.nengdong.smsservice.Service.SmsService;
import com.nengdong.utils.Result;
import com.nengdong.utils.ValidateCodeUtils;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import java.util.concurrent.TimeUnit;


@Api(value = "短信发送")
@CrossOrigin
@RestController
@RequestMapping("/edumsm/sms")
public class SmsApiController {

    @Autowired
    private SmsService smsService;

    @Autowired
    private RedisTemplate<String, String> redisTemplate;

    @ApiOperation(value = "短信发送")
    @GetMapping(value = "/sendSmsPhone/{phone}")
    public Result code(@ApiParam(name="phone",value="电话",required = true) @PathVariable String phone) {
        String code = redisTemplate.opsForValue().get(phone);
        if(!StringUtils.isEmpty(code)) return Result.ok();

        code = ValidateCodeUtils.generateValidateCode4String(6);
        boolean isSend = smsService.send(phone, code);
        if(isSend) {
            redisTemplate.opsForValue().set(phone, code,5, TimeUnit.MINUTES);
            return Result.ok();
        } else {
            return Result.error().message("发送短信失败");
        }
    }
}
