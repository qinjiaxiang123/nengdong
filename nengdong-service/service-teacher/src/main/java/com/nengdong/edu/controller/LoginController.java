package com.nengdong.edu.controller;


import com.nengdong.utils.Result;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.*;

@Api(description = "模拟登录")
@RestController
@RequestMapping("/eduuser")
@CrossOrigin
public class LoginController {
    @ApiOperation(value = "登录")
    @PostMapping("login")
    //{"code":20000,"data":{"token":"admin"}}
    public Result login(){
        return Result.ok().data("token","admin");
    }

    @ApiOperation(value = "用户信息")
    @GetMapping("info")
    //{"code":20000,"data":{"roles":["admin"],"name":"admin","avatar":"https://wpimg.wallstcn.com/f778738c-e4f8-4870-b634-56703b4acafe.gif"}}
    public Result info(){
        return Result.ok().data("roles","admin")
                .data("name","admin")
                .data("avatar","https://wpimg.wallstcn.com/f778738c-e4f8-4870-b634-56703b4acafe.gif");
    }

    @ApiOperation(value = "用户登出")
    @GetMapping("logout")
    //{"code":20000,"data":{"roles":["admin"],"name":"admin","avatar":"https://wpimg.wallstcn.com/f778738c-e4f8-4870-b634-56703b4acafe.gif"}}
    public Result logout(){
        return Result.ok().data("token","");
    }
}
