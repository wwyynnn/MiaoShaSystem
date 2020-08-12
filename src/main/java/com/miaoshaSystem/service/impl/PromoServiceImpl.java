package com.miaoshaSystem.service.impl;

import com.miaoshaSystem.dao.PromoDOMapper;
import com.miaoshaSystem.eneity.PromoDO;
import com.miaoshaSystem.service.PromoService;
import com.miaoshaSystem.service.model.PromoModel;
import org.joda.time.DateTime;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;

/**
 * @author: Wang Yannan
 * @date: 2020/7/27 4:51 下午
 */
@Service
public class PromoServiceImpl implements PromoService {

    @Autowired
    private PromoDOMapper promoDOMapper;

    @Override
    public PromoModel getPromoByItemId(Integer itemId) {
        //获取对应商品的秒杀活动信息
        PromoDO promoDO = promoDOMapper.selectByItemId(itemId);

        //entity -> Model
        PromoModel promoModel = convertFromDataObject(promoDO);
        if(promoModel == null){
            return null;
        }
        //判断当前时间是否秒杀活动正在开始/即将进行
        DateTime now = new DateTime();
        if(promoModel.getStartDate().isAfterNow()){//开始时间晚于当前,说明未开始
            promoModel.setStatus(1);
        }else if(promoModel.getEndDate().isBeforeNow()){//结束时间早于当前，说明已结束
            promoModel.setStatus(3);
        }else{
            promoModel.setStatus(2);//说明正在进行
        }

        return promoModel;
    }

    private PromoModel convertFromDataObject(PromoDO promoDO){
        if(promoDO == null){
            return null;
        }
        PromoModel promoModel = new PromoModel();
        BeanUtils.copyProperties(promoDO,promoModel);

        promoModel.setPromoItemPrice(new BigDecimal(promoDO.getPromoItemPrice()));
        promoModel.setStartDate(new DateTime(promoDO.getStartDate()));
        promoModel.setEndDate(new DateTime(promoDO.getEndDate()));
        return promoModel;
    }
}
