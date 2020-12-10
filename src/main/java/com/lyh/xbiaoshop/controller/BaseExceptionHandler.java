package com.lyh.xbiaoshop.controller;
import com.baomidou.kaptcha.exception.KaptchaIncorrectException;
import com.lyh.xbiaoshop.entity.Result;

import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;


/**
 * 统一异常处理类
 */
@ControllerAdvice
public class BaseExceptionHandler {

    @ExceptionHandler(value = Exception.class)
    @ResponseBody
    public Result error(Exception e){
        e.printStackTrace();
	    if ( e instanceof KaptchaIncorrectException ){
		    return new Result(false,  "验证码错误");
	    }
        return new Result(false,  "执行出错");
    }
}
