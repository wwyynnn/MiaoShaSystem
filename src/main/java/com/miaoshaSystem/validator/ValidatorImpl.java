package com.miaoshaSystem.validator;

import org.springframework.beans.factory.InitializingBean;
import org.springframework.stereotype.Component;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import java.util.Set;


/**
 * @author: Wang Yannan
 * @date: 2020/6/11 10:39 上午
 */

@Component //说明是个Spring的Bean
public class ValidatorImpl implements InitializingBean {

    //目的是要包装出这个类，是真正通过javax定义的实现的validator的工具的接口
    private Validator validator;

    //实现校验方法并返回校验结果
    public  ValidationResult validate(Object bean){
        ValidationResult result = new ValidationResult();
        Set<ConstraintViolation<Object>> constraintViolationSet = validator.validate(bean);
        if(constraintViolationSet.size()>0){
            //说明有错误
            result.setHasError(true);
            //遍历错误信息
            constraintViolationSet.forEach(constraintViolation ->{//constraintViolation是constraintViolationSet的每一个元素
                String errMsg = constraintViolation.getMessage();
                String propertyName = constraintViolation.getPropertyPath().toString();//获取是哪个字段发生错误
                result.getErrorMsgMap().put(propertyName, errMsg);//装入map中
            });
        }
        return result;
    }

    @Override
    public void afterPropertiesSet() throws Exception {
        //将hibernate validator通过通常的初始化方式使其实例话。
        //通过buildDefaultValidatorFactory方法我们build了一个实例话的实现了javax的validator接口的校验器
        this.validator = Validation.buildDefaultValidatorFactory().getValidator();
    }
}
