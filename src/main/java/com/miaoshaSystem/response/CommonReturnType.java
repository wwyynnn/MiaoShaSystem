package com.miaoshaSystem.response;

/**
 * @author: Wang Yannan
 * @date: 2020/6/6 7:14 下午
 */
public class CommonReturnType {
    //改变返回给前端的json格式
    //表明对应请求的返回处理结果success或者fail
    //通过业务逻辑错误标示来提示前端出现什么错误。正常则返回success，不正常就返回fail。
    //若status=success，则data返回前端需要的json数据
    //若status=fail，则data内使用通用的错误码格式
    private String status;
    private Object data;

    //定义一个通用的创建方法
    public static CommonReturnType create(Object result){
        //当controller完成处理并调用对应的create方法，如果参数没有status，那么默认status为success，然后创建CommonReturnType并返回对应的值
        return CommonReturnType.create(result,"success");
    }
    public static CommonReturnType create(Object result, String status){
        //这里改变了传递给前端的json格式。原本是{"id":xx,"name":xx, ...}的形式
        //现在的返回的json格式是{"status":"success","data":{"id":xx,"name":xx, ...}}格式
        //方便前端进行统一使用（以前针对不同的返回内容，前端处理的json格式以及参数都不一样，现在都变成两大类，status跟data，只需要对data进行
        //统一处理即可）
        CommonReturnType type = new CommonReturnType();
        type.setStatus(status);
        type.setData(result);
        return type;
    }
    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Object getData() {
        return data;
    }

    public void setData(Object data) {
        this.data = data;
    }
}
