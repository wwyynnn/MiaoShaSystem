package com.miaoshaSystem.error;

/**
 * @author: Wang Yannan
 * @date: 2020/6/6 10:57 下午
 */

//设计模式：包装器业务异常类 实现
public class BusinessException extends Exception implements CommonError{
    private CommonError commonError;//强关联一个commonError,其实就是enum那个类

    //直接接收EmBusinessError的传参，用于构造业务异常
    public BusinessException(CommonError commonError) {
        super();//对exception有一些初始化机制，因此要调用父构造函数
        this.commonError = commonError;
    }
    //接收自定义errMsg的方式构造业务异常
    public BusinessException(String errMsg, CommonError commonError) {
        super();
        this.commonError = commonError;
        this.commonError.setErrorMsg(errMsg);
    }

    @Override
    public int getErrCode() {
        return this.commonError.getErrCode();
    }

    @Override
    public String getErrMsg() {
        return this.commonError.getErrMsg();
    }

    @Override
    public CommonError setErrorMsg(String errMsg) {
        this.commonError.setErrorMsg(errMsg);
        return this;
    }
}
