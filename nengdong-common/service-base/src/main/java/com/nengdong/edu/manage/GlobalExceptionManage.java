package com.nengdong.edu.manage;



import com.nengdong.utils.Result;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

@ControllerAdvice
public class GlobalExceptionManage {
    @ExceptionHandler(Exception.class)
    @ResponseBody
    public Result error(Exception e){
        e.printStackTrace();
        return Result.error();
    }

    @ExceptionHandler(ArithmeticException.class)
    @ResponseBody
    public Result error(ArithmeticException e){
        e.printStackTrace();
        return Result.error().message("特殊异常处理");
    }


    @ExceptionHandler(NengdongException.class)
    @ResponseBody
    public Result error(NengdongException e){
        e.printStackTrace();
        return Result.error().message(e.getMsg()).code(e.getCode());
    }

}

