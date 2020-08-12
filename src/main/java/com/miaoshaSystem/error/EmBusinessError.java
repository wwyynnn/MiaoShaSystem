package com.miaoshaSystem.error;

/**
 * @author: Wang Yannan
 * @date: 2020/6/6 10:38 下午
 */
public enum EmBusinessError implements CommonError{
    //全局错误码

    //通用错误类型 10001
    PARAMETER_VALIDATION_ERROR(10001,"参数不合法"),//用来解决入参校验，减少具体参数不合法的错误码，如电话/邮邮箱输入不合法
                                                                 //虽然错误码一样，但是针对不同参数不合法 其返回信息应该不一样（双引号内不一样）
                                                                 //也就是setErrorMsg函数存在的意义.
    UNKNOWN_ERROR(10002,"未知错误"),

    //20000开头为用户信息相关错误定义
    //之后可以无限增加不同的错误码代表不同的错误类型
    USER_NOT_EXIST(20001,"用户不存在"),//可以通过构造函数构造出一个实现了CommonError接口的enum子类（EmBusinessError）
    USER_LOGIN_FAIL(20002,"用户手机号/密码不正确"),
    USER_NOT_LOGIN(20003,"用户未登录"),

    //30000开头为交易信息错误定义
    STOCK_NOT_ENOUGH(30001,"库存不足")
    ;


    private EmBusinessError(int errCode, String errMsg){
        this.errCode = errCode;
        this.errMsg = errMsg;
    }
    //全局变量
    private int errCode;
    private String errMsg;

    @Override
    public int getErrCode() {
        return this.errCode;
    }

    @Override
    public String getErrMsg() {
        return this.errMsg;
    }

    @Override
    public CommonError setErrorMsg(String errMsg) {
        this.errMsg = errMsg;
        return this;
    }
}
