package com.miaoshaSystem.service;

import com.miaoshaSystem.error.BusinessException;
import com.miaoshaSystem.service.model.ItemModel;

import java.util.List;

/**
 * @author: Wang Yannan
 * @date: 2020/6/16 9:59 下午
 */
public interface ItemService {
    //创建商品
    ItemModel createItem(ItemModel itemModel) throws BusinessException;
    //商品列表浏览
    List<ItemModel> listItem();
    //商品详情浏览
    ItemModel getItemById(Integer id);
    //库存扣减
    boolean decreaseStock(Integer itemId, Integer amount) throws BusinessException;
    //销量增加
    void increaseSales(Integer itemId, Integer amount) throws BusinessException;
}
