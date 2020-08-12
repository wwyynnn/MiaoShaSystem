package com.miaoshaSystem.error;

/**
 * @author: Wang Yannan
 * @date: 2020/6/6 10:29 下午
 */
public interface CommonError {
    //实现可视化的完整的错误描述，即json错误信息返回也是规范格式


    public int getErrCode();
    public String getErrMsg();
    public CommonError setErrorMsg(String errMsg);
}
