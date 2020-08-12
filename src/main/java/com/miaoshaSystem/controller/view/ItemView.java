package com.miaoshaSystem.controller.view;

import org.joda.time.DateTime;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;

/**
 * @author: Wang Yannan
 * @date: 2020/6/21 10:32 上午
 */
public class ItemView {
    private Integer id;

    private String title; //商品名

    private BigDecimal price;//价格

    private Integer stock; //商品库存

    private String description;//描述

    private Integer sales;//销量

    private String imgUrl;//描述图片

    private Integer promoStatus;//记录商品是否在秒杀活动中，0：没有秒杀活动 ｜ 1：未开始 ｜ 2：正在进行

    private BigDecimal promoPrice;//秒杀活动价格

    private Integer promoId;//秒杀项目id

    private String startDate;//秒杀活动开始时间,如果使用DateTime，将会造成前段json格式的问题，无法读出时间


    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public Integer getStock() {
        return stock;
    }

    public void setStock(Integer stock) {
        this.stock = stock;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Integer getSales() {
        return sales;
    }

    public void setSales(Integer sales) {
        this.sales = sales;
    }

    public String getImgUrl() {
        return imgUrl;
    }

    public void setImgUrl(String imgUrl) {
        this.imgUrl = imgUrl;
    }

    public Integer getPromoStatus() {
        return promoStatus;
    }

    public void setPromoStatus(Integer promoStatus) {
        this.promoStatus = promoStatus;
    }

    public BigDecimal getPromoPrice() {
        return promoPrice;
    }

    public void setPromoPrice(BigDecimal promoPrice) {
        this.promoPrice = promoPrice;
    }

    public Integer getPromoId() {
        return promoId;
    }

    public void setPromoId(Integer promoId) {
        this.promoId = promoId;
    }

    public String getStartDate() {
        return startDate;
    }

    public void setStartDate(String startDate) {
        this.startDate = startDate;
    }
}
