package com.miaoshaSystem.validator;

import org.apache.commons.lang3.StringUtils;

import java.util.HashMap;
import java.util.Map;

/**
 * @author: Wang Yannan
 * @date: 2020/6/11 10:33 上午
 */
public class ValidationResult {
    //校验结果是否有错
    private boolean hasError=false;

    //存放错误信息的map
    private Map<String, String> errorMsgMap = new HashMap<>();

    public boolean isHasError() {
        return hasError;
    }

    public void setHasError(boolean hasError) {
        this.hasError = hasError;
    }

    public Map<String, String> getErrorMsgMap() {
        return errorMsgMap;
    }

    public void setErrorMsgMap(Map<String, String> errorMsgMap) {
        this.errorMsgMap = errorMsgMap;
    }

    //实现通用的通过格式化字符串信息获取错误结果的msg方法
    public String getErrMsg(){
        //获取所有msg的值并返回
       return StringUtils.join(errorMsgMap.values().toArray(),",");

    }
}
