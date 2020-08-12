package com.miaoshaSystem;

import com.miaoshaSystem.dao.UserDOMapper;
import com.miaoshaSystem.eneity.UserDO;
import com.miaoshaSystem.eneity.UserDO;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Hello world!
 *
 */

//让springboot去扫描mybatis的配置文件（比如DOMapper以及service的封装）
@SpringBootApplication(scanBasePackages = {"com.miaoshaSystem"}) //与@EnableAutoConfiguration异曲同工（使得项目变成Springboot并且能够自动化配置。）
//主要含义是 将app的启动类当成一个自动化、可以支持配置的bean，并且能够开启整个基于Springboot工程类的基于自动化的配置
//将这个类被Springboot托管并指定其为一个主启动类
@RestController//跟下面@RequestMapping配合使用.,证明这是个controller接口，实现简单的mvc
@MapperScan("com.miaoshaSystem.dao") //设置dao文件夹
public class App 
{
    @Autowired  //它可以对类成员变量、方法及构造函数进行标注，让 spring 完成 bean 自动装配的工作。
    private UserDOMapper userDOMapper;

    @RequestMapping("/") //与上面@RestController 一起用，解决mvc的controller功能。括号里面的是网页路径。如果是"/start"，
                        //那么网页地址就是localhost:8080/start
    public String home(){
        UserDO userDO = userDOMapper.selectByPrimaryKey(1);//执行sql语句试试
        if(userDO == null){ //如果localhost有输出，那么说明数据库连接是正确的
            return "user no exist";
        }else{
            return userDO.getName();
        }

    }

    public static void main( String[] args )
    {
        System.out.println( "Hello World!" );
        SpringApplication.run(App.class,args); //配合@EnableAutoConfiguration 实现自动化配置。
        //根据输出，会看到有个"Tomcat started on port(s): 8080 (http) ..."，说明已经加载了web依赖，启动了tomcat，能够在localhost：8080打开
    }
}
