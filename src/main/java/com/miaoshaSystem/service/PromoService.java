package com.miaoshaSystem.service;

import com.miaoshaSystem.service.model.PromoModel;

/**
 * @author: Wang Yannan
 * @date: 2020/7/27 11:32 上午
 */
public interface PromoService {
    //根据itemId获取正在进行/即将进行的秒杀活动
    PromoModel getPromoByItemId(Integer itemId);
}
