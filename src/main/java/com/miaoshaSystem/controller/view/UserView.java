package com.miaoshaSystem.controller.view;

/**
 * @author: Wang Yannan
 * @date: 2020/6/6 4:21 下午
 */
public class UserView {
    //下面这些属性是前端需要的，而注册模式/密码等不需要传递给前端，因此真正传递给前端的其实是userView，而不是全部信息都有的userModel
    private Integer id;
    private String name;
    private Byte gender;
    private Integer age;
    private String telphone;

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



}
