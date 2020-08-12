package com.miaoshaSystem.controller;

import com.miaoshaSystem.error.BusinessException;
import com.miaoshaSystem.error.EmBusinessError;
import com.miaoshaSystem.response.CommonReturnType;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.Map;

/**
 * @author: Wang Yannan
 * @date: 2020/6/7 4:20 下午
 */
public class BaseController {//基类，能被所有controller调用的类
    //用于http传递时ajax的contentType方式
    public static final String CONTENT_TYPE_FORMED = "application/x-www-form-urlencoded";

    //拦截tomcat异常处理方式并解决对应问题
    //定义exceptionhandler解决未被controller层吸收的exception异常
    //设计思想：对于web系统来说，controller层的异常其实是业务处理的最后一道关口，如果它被处理掉，就会有一个很好的错误返回结果（钩子），
    // 如果没被处理掉，那么返回的就是500错误页
    @ExceptionHandler(Exception.class)
    @ResponseStatus(HttpStatus.OK)
    @ResponseBody
    //捕获异常
    //如果handlerException使用object处理，仅仅只能返回页面路径，不能返回处理getUser的responseBody形式。因此会返回404。需要增加@ResponsBody
    public Object handlerException(HttpServletRequest request, Exception ex){
        Map<String, Object> responseData = new HashMap<>();

        if(ex instanceof BusinessException){//判断返回的ex是不是BusinessException形式
            //因为原本返回的json是BusinessException的异常类的反序列化的递增方式，因此需要强转型
            BusinessException businessException = (BusinessException)ex;

            //通过下面2步将返回的json格式规范化
            responseData.put("errCode", businessException.getErrCode());
            responseData.put("errMsg", businessException.getErrMsg());
        }else{//此处是非business的错误形式
            responseData.put("errCode", EmBusinessError.UNKNOWN_ERROR.getErrCode());
            responseData.put("errMsg", EmBusinessError.UNKNOWN_ERROR.getErrMsg());
        }

        return CommonReturnType.create(responseData, "fail"); //这是使用create方法的简洁写法
        //这是另一种不简洁的写法：
//        CommonReturnType commonReturnType = new CommonReturnType();
//        commonReturnType.setStatus("fail");
//
//        commonReturnType.setData(responseData);
//        return commonReturnType;
    }
}
