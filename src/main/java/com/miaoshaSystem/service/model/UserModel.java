package com.miaoshaSystem.service.model;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

/**
 * @author: Wang Yannan
 * @date: 2020/6/4 10:50 下午
 */

//在service层也要有model文件，这才是真正定义SpringMVC中业务逻辑交互模型
//是真正意义上处理业务逻辑的核心模型。eneity里面仅仅是对数据裤的映射
public class UserModel {
    private Integer id;

    @NotBlank(message = "用户名不能为空")//说明下面这个字段既不能为空，也不能为null。如果出现错误，则返回message内的信息
    private String name;

    @NotNull(message = "性别不能为空")//不能为null
    private Byte gender;

    @NotNull(message = "年龄不能为空")
    @Min(value = 0, message = "年龄必须大于0")
    @Max(value = 150, message = "年龄必须小于150")
    private Integer age;

    @NotNull(message = "电话不能为空")
    private String telphone;

    private String registerMode;
    private String thirdPartyId;

    //虽然paseword属性在数据库中是分别存放在两张表（数据模型层不同），但是在Java的领域模型对象的概念来讲，password属于用户模型
    private String encrptPassword;

    public String getEncrptPassword() {
        return encrptPassword;
    }

    public void setEncrptPassword(String encrptPassword) {
        this.encrptPassword = encrptPassword;
    }

    public UserModel() {
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Byte getGender() {
        return gender;
    }

    public void setGender(Byte gender) {
        this.gender = gender;
    }

    public Integer getAge() {
        return age;
    }

    public void setAge(Integer age) {
        this.age = age;
    }

    public String getTelphone() {
        return telphone;
    }

    public void setTelphone(String telphone) {
        this.telphone = telphone;
    }

    public String getRegisterMode() {
        return registerMode;
    }

    public void setRegisterMode(String registerMode) {
        this.registerMode = registerMode;
    }

    public String getThirdPartyId() {
        return thirdPartyId;
    }

    public void setThirdPartyId(String thirdPartyId) {
        this.thirdPartyId = thirdPartyId;
    }


}
