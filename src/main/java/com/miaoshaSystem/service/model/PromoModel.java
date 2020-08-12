package com.miaoshaSystem.service.model;

import org.joda.time.DateTime;

import java.math.BigDecimal;
import java.util.Date;

/**
 * @author: Wang Yannan
 * @date: 2020/7/26 5:38 下午
 */
public class PromoModel {
    private Integer id;
    private String promoName;//秒杀活动名称
    private DateTime startDate; //秒杀开始时间，建议用joda的DataTime，不要用util里面的Date
    private DateTime endDate;//秒杀结束时间
    private Integer itemId;//秒杀活动的使用商品，目前暂定一个
    private BigDecimal promoItemPrice;//秒杀时的商品价格
    private Integer status; //秒杀活动状态，1：未开始 ｜ 2：正在进行 ｜ 3：已结束 （与数据库表无关）

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getPromoName() {
        return promoName;
    }

    public void setPromoName(String promoName) {
        this.promoName = promoName;
    }

    public DateTime getStartDate() {
        return startDate;
    }

    public void setStartDate(DateTime startDate) {
        this.startDate = startDate;
    }

    public DateTime getEndDate() {
        return endDate;
    }

    public void setEndDate(DateTime endDate) {
        this.endDate = endDate;
    }

    public Integer getItemId() {
        return itemId;
    }

    public void setItemId(Integer itemId) {
        this.itemId = itemId;
    }

    public BigDecimal getPromoItemPrice() {
        return promoItemPrice;
    }

    public void setPromoItemPrice(BigDecimal promoItemPrice) {
        this.promoItemPrice = promoItemPrice;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }
}
