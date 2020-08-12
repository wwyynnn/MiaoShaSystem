package com.miaoshaSystem.service;

import com.miaoshaSystem.error.BusinessException;
import com.miaoshaSystem.service.model.OrderModel;

/**
 * @author: Wang Yannan
 * @date: 2020/7/2 7:05 下午
 */
public interface OrderService {
    //通过前段url传过来秒杀活动ID，然后下单接口内校验对应ID是否属于对应商品且活动已开始
    OrderModel createOrder(Integer userId, Integer itemId, Integer promoId, Integer amount) throws BusinessException;

}
