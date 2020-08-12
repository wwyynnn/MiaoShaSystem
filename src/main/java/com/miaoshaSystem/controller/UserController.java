package com.miaoshaSystem.controller;

import com.alibaba.druid.util.StringUtils;
import com.miaoshaSystem.controller.view.UserView;
import com.miaoshaSystem.error.BusinessException;
import com.miaoshaSystem.error.EmBusinessError;
import com.miaoshaSystem.response.CommonReturnType;
import com.miaoshaSystem.service.UserService;
import com.miaoshaSystem.service.model.UserModel;
import org.apache.tomcat.util.security.MD5Encoder;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import sun.misc.BASE64Encoder;

import javax.servlet.http.HttpServletRequest;
import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Random;

/**
 * @author: Wang Yannan
 * @date: 2020/6/4 9:49 下午
 */

@Controller("user") //目的是让spring扫描到,这个controller的名字是"user"
@RequestMapping("/user") //url 路径访问后缀
@CrossOrigin(allowCredentials="true", allowedHeaders="*")//解决ajax跨域请求问题,做到session跨域共享
//DEFAULT_ALLOWED_HEADERS: 允许跨域传输所有header参数，将用于使用token放入header域做session共享的跨域请求
//DEFAULT_ALLOW_CREDENTIALS=true：需要配合前端设置xhrFields授信后是的跨域session共享
public class UserController extends BaseController {

    @Autowired
    private UserService userService;

    @Autowired
    private HttpServletRequest httpServletRequest;//拿到httpSession。是个单例模式，对应当前用户的http请求

    //用户注册接口
    @RequestMapping(value = "/register",method = {RequestMethod.POST},consumes={CONTENT_TYPE_FORMED})
    @ResponseBody
    public CommonReturnType register(@RequestParam(name="telphone")String telphone,
                                     @RequestParam(name="otpCode")String otpCode,
                                     @RequestParam(name="name")String name,
                                     @RequestParam(name="gender") Integer gender,
                                     @RequestParam(name="age")Integer age,
                                     @RequestParam(name="password")String password) throws BusinessException, UnsupportedEncodingException, NoSuchAlgorithmException {
        //验证手机号与otpCode相符
        String inSessionOptCode = (String)this.httpServletRequest.getSession().getAttribute(telphone);//因为原本方法获得的是object类型
        if(!StringUtils.equals(otpCode, inSessionOptCode)){
            throw new BusinessException("短信验证码不符合", EmBusinessError.PARAMETER_VALIDATION_ERROR);
        }
        //用户注册
        UserModel userModel = new UserModel();
        userModel.setName(name);
        userModel.setGender(new Byte(String.valueOf(gender.intValue())));
        userModel.setAge(age);
        userModel.setTelphone(telphone);
        userModel.setRegisterMode("byphone");
        userModel.setEncrptPassword(this.EncodeByMd5(password));//使用MD5Encoder进行密码加密

        userService.register(userModel);
        return CommonReturnType.create(null);
    }

    //MD5密码加密
    public String EncodeByMd5(String str) throws NoSuchAlgorithmException, UnsupportedEncodingException {
        //确定计算方法
        MessageDigest md5 = MessageDigest.getInstance("MD5");
        BASE64Encoder base64Encoder = new BASE64Encoder();
        //加密字符串
        String newstr = base64Encoder.encode(md5.digest(str.getBytes("utf-8")));

        return newstr;
    }

    //用户获取otp（动态口令）短信接口
    @RequestMapping(value = "/getotp",method = {RequestMethod.POST},consumes={CONTENT_TYPE_FORMED})
    @ResponseBody
    public CommonReturnType getOtp(@RequestParam(name="telphone")String telphone){
        //otp:需要按照一定规则生成otp验证码，目前采用随机数生成方法
        Random random = new Random();
        int randomInt = random.nextInt(99999);//生成随机数在[0,99999]
        randomInt += 10000;
        String otpCode = String.valueOf(randomInt);

        //将otp验证码同对应用户手机号相关联,目前使用httpSession，将用户目前httpSession和telphone和otpCode绑定。分布式上用redis更好
        httpServletRequest.getSession().setAttribute(telphone, otpCode); //关联
        System.out.println("telphone= "+ telphone + " & otpCode= "+otpCode);//一般是log4j方式输出

        //将otp验证码通过短信通道发送给用户（省略，第三方服务）

        return CommonReturnType.create(null);
    }

    //用户登登录接口
    @RequestMapping(value = "/login",method = {RequestMethod.POST},consumes={CONTENT_TYPE_FORMED})
    @ResponseBody
    public CommonReturnType login(@RequestParam(name="telphone")String telphone,
                                  @RequestParam(name="password")String password) throws BusinessException, UnsupportedEncodingException, NoSuchAlgorithmException {
        //入参校验
        if(StringUtils.isEmpty(telphone) || StringUtils.isEmpty(password)){
            throw new BusinessException("账号/密码为空",EmBusinessError.PARAMETER_VALIDATION_ERROR);
        }
        //用户登录服务，校验用户登录是否合法
        UserModel userModel = userService.validateLogin(telphone, this.EncodeByMd5(password));

        //如果没有异常，则将登录凭证加入到用户用户登录成功的session内（简单假设用户是单点登录）
        this.httpServletRequest.getSession().setAttribute("IS_LOGIN",true);
        this.httpServletRequest.getSession().setAttribute("LOGIN_USER",userModel);

//        System.out.println(this.httpServletRequest.getSession().getAttribute("IS_LOGIN"));
//        UserModel um = (UserModel)this.httpServletRequest.getSession().getAttribute("LOGIN_USER");
//        System.out.println(um.getId()+" "+um.getName()+" "+um.getAge()+" "+um.getGender()+" "+um.getEncrptPassword()+" "+um.getTelphone());
        return CommonReturnType.create(null);
    }

    @RequestMapping("/get")
    @ResponseBody
    //@RequestParam(..)用来映射请求参数，后面跟着的参数是该参数被赋值的对象。
    //他的表现形式是：在url中为localhost:8080/UserController/get?id=xx（？后面的就是@RequestParam的参数，""内的作为比参数名，后面的是传入值）
    public CommonReturnType getUser(@RequestParam(name = "id")Integer id) throws BusinessException {
        //调用service服务获取对应ID的用户对象，并返回给前端
        UserModel userModel = userService.getUserById(id);

        //若获取的对应用户信息不存在，抛出异常并且返回特定错误码与错误信息
        if(userModel == null){
           // userModel.setEncrptPassword(("123"));
            throw new BusinessException(EmBusinessError.USER_NOT_EXIST);//直接抛到到tomcat容器层（tomcat容器层处理是返回500 错误页）
        }

        //在这里，userModel这个领域模型应该是后端自己使用，而不能返回给前端，否则攻击者可以通过xxx/user/get?id=n的方法获取用户数据库内加密的密码
        //这样极度不安全！
        //前端应该仅能拿到他需要做处理的字段即可，不需要拿到整个领域模型.
        //因此需要controller下面有view文件夹，里面存的才是传递给前端的信息
        //因此返回给前端的不是包含所有信息的usermodel，而是仅仅前端可能需要的userview（信息更少，比如没有密码）
        UserView userView = convertFromModel(userModel);

        //为了给前端返回有规律的数据（json格式统一）以及有意义的错误信息，我们需要对获取到的数据进行规范化，
        //因此有了commonreturntype文件夹，目的是处理给http返回的内容
        //返回通用对象，相当于改变了json输出的格式
        return CommonReturnType.create(userView);
    }

    //将核心领域模型对象转化为可供前端UI使用的view
    public UserView convertFromModel(UserModel userModel){
        if(userModel == null){
            return null;
        }
        UserView userView = new UserView();
        BeanUtils.copyProperties(userModel, userView);
        return userView;
    }


}
