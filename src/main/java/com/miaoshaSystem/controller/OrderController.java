package com.miaoshaSystem.controller;

import com.miaoshaSystem.error.BusinessException;
import com.miaoshaSystem.error.EmBusinessError;
import com.miaoshaSystem.response.CommonReturnType;
import com.miaoshaSystem.service.model.OrderModel;
import com.miaoshaSystem.service.OrderService;
import com.miaoshaSystem.service.model.UserModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

import static com.miaoshaSystem.controller.BaseController.CONTENT_TYPE_FORMED;

/**
 * @author: Wang Yannan
 * @date: 2020/7/3 4:33 下午
 */

@Controller("order") //目的是让spring扫描到,这个controller的名字是"order"
@RequestMapping("/order") //url 路径访问后缀
@CrossOrigin(allowCredentials="true", allowedHeaders={"*"})//解决ajax跨域请求问题,做到session跨域共享
public class OrderController extends BaseController{
    @Autowired
    private OrderService orderService;

    @Autowired
    private HttpServletRequest httpServletRequest;

    //封装下单请求
    @RequestMapping(value = "/createorder", method = {RequestMethod.POST}, consumes = {CONTENT_TYPE_FORMED})
    @ResponseBody
    public CommonReturnType createOrder(@RequestParam(name = "itemId") Integer itemId,
                                        @RequestParam(name = "amount") Integer amount,
                                        @RequestParam(name = "promoId",required = false) Integer promoId) throws BusinessException {

        //获取用户的登陆信息（从userController的login的session中拿去信息）
        Boolean isLogin = (Boolean) httpServletRequest.getSession().getAttribute("IS_LOGIN");
        if (isLogin == null || !isLogin.booleanValue()) {
            throw new BusinessException("用户还未登录，不能下单", EmBusinessError.USER_NOT_LOGIN);
        }
        UserModel userModel = (UserModel) this.httpServletRequest.getSession().getAttribute("LOGIN_USER");


        OrderModel orderModel = orderService.createOrder(userModel.getId(), itemId, promoId, amount);

        return CommonReturnType.create(null);
    }
}
