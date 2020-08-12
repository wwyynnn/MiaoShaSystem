package com.miaoshaSystem.service;

import com.miaoshaSystem.error.BusinessException;
import com.miaoshaSystem.service.model.UserModel;

/**
 * @author: Wang Yannan
 * @date: 2020/6/4 10:08 下午
 */
public interface UserService { //是接口，真正实现都在service/impl文件夹下对应的一个个xxxximpl.java文件
    //通过用户ID获取用户对象的方法
    UserModel getUserById(Integer id);

    //处理用户注册请求
    void register(UserModel userModel) throws BusinessException;
    /*
    phonenum:用户注册手机号
    password:用户加密后的密码
     */
    UserModel validateLogin(String telphone, String encrptPassword) throws BusinessException;
}
