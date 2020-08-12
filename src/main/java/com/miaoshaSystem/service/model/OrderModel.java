package com.miaoshaSystem.service.model;

import java.math.BigDecimal;

/**
 * @author: Wang Yannan
 * @date: 2020/6/23 11:05 上午
 */

//用户下单的交易模型
public class OrderModel {
    //交易号，因为会有含义所以长度可能超出int,如2018102100012828
    private String id;

    //用户id
    private Integer userId;

    //商品ID
    private Integer itemId;

    //若id非空，则代表以秒杀形式下单，价格采用秒杀价格
    private Integer promoId;

    //商品单价。若promoId非空，则表示秒杀价格
    private BigDecimal itemPrice;

    //购买数量
    private Integer amount;

    //购买金额。若promoId非空，则表示秒杀价格
    private BigDecimal orderPrice;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public Integer getItemId() {
        return itemId;
    }

    public void setItemId(Integer itemId) {
        this.itemId = itemId;
    }

    public Integer getPromoId() {
        return promoId;
    }

    public void setPromoId(Integer promoId) {
        this.promoId = promoId;
    }

    public BigDecimal getItemPrice() {
        return itemPrice;
    }

    public void setItemPrice(BigDecimal itemPrice) {
        this.itemPrice = itemPrice;
    }

    public Integer getAmount() {
        return amount;
    }

    public void setAmount(Integer amount) {
        this.amount = amount;
    }

    public BigDecimal getOrderPrice() {
        return orderPrice;
    }

    public void setOrderPrice(BigDecimal orderPrice) {
        this.orderPrice = orderPrice;
    }
}
