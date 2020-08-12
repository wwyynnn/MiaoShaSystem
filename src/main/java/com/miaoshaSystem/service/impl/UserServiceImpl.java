package com.miaoshaSystem.service.impl;

import com.miaoshaSystem.dao.UserDOMapper;
import com.miaoshaSystem.dao.UserPasswordDOMapper;
import com.miaoshaSystem.eneity.*;
import com.miaoshaSystem.eneity.UserDO;
import com.miaoshaSystem.error.BusinessException;
import com.miaoshaSystem.error.EmBusinessError;
import com.miaoshaSystem.service.UserService;
import com.miaoshaSystem.service.model.UserModel;
import com.miaoshaSystem.validator.ValidationResult;
import com.miaoshaSystem.validator.ValidatorImpl;
import org.apache.catalina.User;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * @author: Wang Yannan
 * @date: 2020/6/4 10:08 下午
 */
@Service //表明这是一个Spring的service
public class UserServiceImpl implements UserService {

    @Autowired //它可以对类成员变量、方法及构造函数进行标注，让 spring 完成 bean 自动装配的工作。
    private UserDOMapper userDOMapper;//创建mapper实例，即能在函数真实实现时使用mapper里面定义的方法

    @Autowired
    private UserPasswordDOMapper userPasswordDOMapper;

    @Autowired
    private ValidatorImpl validator;

    //对service层下对应接口文件的函数的真实实现。获取用户领域模型对象
    @Override
    public UserModel getUserById(Integer id) {
        //调用UserDOMapper获取到对应的用户eneity
        //企业应用中，entity中的UserDo是不能透漏给前端的。
        //SpringMVC对于model定义有三层，第一层是entity，与数据库一一映射，不含有逻辑，是最浅的ORMapping的映射
        //在service层也要有model文件，这才是真正定义SpringMVC中业务逻辑交互模型的概念
        UserDO userDO = userDOMapper.selectByPrimaryKey(id); //使用UserDOMapper里面定义的方法。userDo就是某个具体的用户
        if(userDO == null){
            return null;
        }
        //通过用户id获取对应的用户加密密码信息
        UserPasswordDO userPasswordDO = userPasswordDOMapper.selectByUserId(userDO.getId());
        return convertFromEntity(userDO, userPasswordDO);
    }

    @Override
    @Transactional//register 要包在一个事务里，即不能出现user表插入了数据但是password表没插入（不在一个事务中）
    public void register(UserModel userModel) throws BusinessException {
        if(userModel == null){
            throw new BusinessException(EmBusinessError.PARAMETER_VALIDATION_ERROR);
        }
        //实现字段判空的校验（这是个最简单的判空校验
//        if(StringUtils.isEmpty(userModel.getName() )|| userModel.getGender() == null ||
//                userModel.getAge() == null || StringUtils.isEmpty(userModel.getTelphone())){
//            throw new BusinessException(EmBusinessError.PARAMETER_VALIDATION_ERROR);
//        }
        //实现字段的各项校验
        ValidationResult result = validator.validate(userModel);
        if(result.isHasError()){
            throw new BusinessException(result.getErrMsg(), EmBusinessError.PARAMETER_VALIDATION_ERROR);
        }


        //实现model->entity方法,其实是为了写数据，将核心领域model里面的信息转移到eneity里面，方便插入数据库
        UserDO userDO = convertFromModel(userModel);
        try{
            userDOMapper.insertSelective(userDO);//使用insertSelective而不是insert,目的是避免为null的字段覆盖掉数据库默认值（看UserDOMapper.xml）
            //这个方法在update时候最合适，参数中没有值的字段代表不更新，不会覆盖
        }catch (DuplicateKeyException ex){//这里是因为数据库设置了telphone是唯一，因此在重复注册时要有明确错误提示
            throw new
                    BusinessException("手机号重复注册", EmBusinessError.PARAMETER_VALIDATION_ERROR);
        }

        userModel.setId(userDO.getId());//将user表的自增的ID取出并赋值给userModel，这样就能转发给对应的passowrd表的user_id字段

        UserPasswordDO userPasswordDO = converPasswordFromModel(userModel);
        userPasswordDOMapper.insertSelective(userPasswordDO);

        return;

    }

    @Override
    public UserModel validateLogin(String telphone, String encrptPassword) throws BusinessException {
        //通过用户手机获取用户信息
        UserDO userDO = userDOMapper.selectByTelphone(telphone);
        if(userDO == null){
            throw new BusinessException(EmBusinessError.USER_LOGIN_FAIL);
        }
        UserPasswordDO userPasswordDO = userPasswordDOMapper.selectByUserId(userDO.getId());
        UserModel userModel = convertFromEntity(userDO,userPasswordDO);

        //比对用户信息内加密密码是否和传输进来的密码相匹配
        if(!StringUtils.equals(encrptPassword,userModel.getEncrptPassword())){
            throw new BusinessException(EmBusinessError.USER_LOGIN_FAIL);
        }
        return userModel;
    }

    //实现model->entity的方法，即目的向user表写数据
    private UserDO convertFromModel(UserModel userModel){
        if(userModel == null){
            return null;
        }
        UserDO userDO = new UserDO();
        BeanUtils.copyProperties(userModel, userDO);
        return userDO;
    }

    //实现model->entity的方法，即目的向userpassword表写数据
    private UserPasswordDO converPasswordFromModel(UserModel userModel){
        if(userModel == null){
            return null;
        }
        UserPasswordDO userPasswordDO = new UserPasswordDO();
        userPasswordDO.setEncrptPassword(userModel.getEncrptPassword());
        userPasswordDO.setUserId(userModel.getId());
        return userPasswordDO;
    }

    //通userDo和userPasswordDO组装成一个完整的userModel（将不同数据库表的信息组合到一起）
    private UserModel convertFromEntity(UserDO userDO, UserPasswordDO userPasswordDO){
        if(userDO ==null){//判空
            return null;
        }
        UserModel userModel = new UserModel();
        BeanUtils.copyProperties(userDO,userModel); //复制属性，需要前后两个对象里面的参数的字段名与参数名（类型名）一致
        if(userPasswordDO != null){
            userModel.setEncrptPassword(userPasswordDO.getEncrptPassword());
        }


        return userModel;
    }
}
