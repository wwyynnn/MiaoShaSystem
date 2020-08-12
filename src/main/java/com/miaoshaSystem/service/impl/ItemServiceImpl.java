package com.miaoshaSystem.service.impl;

import com.miaoshaSystem.dao.ItemDOMapper;
import com.miaoshaSystem.dao.ItemStockDOMapper;
import com.miaoshaSystem.eneity.ItemDO;
import com.miaoshaSystem.eneity.ItemStockDO;
import com.miaoshaSystem.error.BusinessException;
import com.miaoshaSystem.error.EmBusinessError;
import com.miaoshaSystem.service.ItemService;
import com.miaoshaSystem.service.PromoService;
import com.miaoshaSystem.service.model.ItemModel;
import com.miaoshaSystem.service.model.PromoModel;
import com.miaoshaSystem.validator.ValidationResult;
import com.miaoshaSystem.validator.ValidatorImpl;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author: Wang Yannan
 * @date: 2020/6/16 10:01 下午
 */

@Service
public class ItemServiceImpl implements ItemService {

    @Autowired
    private ValidatorImpl validator;

    @Autowired
    private ItemDOMapper itemDOMapper;

    @Autowired
    private ItemStockDOMapper itemStockDOMapper;

    @Autowired
    private PromoService promoService;

    //实现model->entity转化
    private ItemDO convertItemDOFromItemModel(ItemModel itemModel){
        if(itemModel == null){
            return null;
        }
        ItemDO itemDO = new ItemDO();
        BeanUtils.copyProperties(itemModel,itemDO);
        itemDO.setPrice(itemModel.getPrice().doubleValue()); //是因为price是BigDecimal类型，需要类型转换（model与DO中类型不一样）
        return itemDO;
    }
    private ItemStockDO convertItemStockDOFromItemModel(ItemModel itemModel){
        if(itemModel == null){
            return null;
        }
        ItemStockDO itemStockDO = new ItemStockDO();
        itemStockDO.setItemId(itemModel.getId());
        itemStockDO.setStock(itemModel.getStock());
        return itemStockDO;
    }

    @Override
    @Transactional//register 要包在一个事务里，即不能出现user表插入了数据但是password表没插入（不在一个事务中）
    public ItemModel createItem(ItemModel itemModel) throws BusinessException {
        //校验入参
        ValidationResult result = validator.validate(itemModel);
        if(result.isHasError()){
            throw new BusinessException(result.getErrMsg(),EmBusinessError.PARAMETER_VALIDATION_ERROR);
        }
        //转化itemModel到entity
        ItemDO itemDO = this.convertItemDOFromItemModel(itemModel);

        //写入数据库
        itemDOMapper.insertSelective(itemDO);
        itemModel.setId(itemDO.getId());//获取到id就说明成功插入数据库了

        ItemStockDO itemStockDO = this.convertItemStockDOFromItemModel(itemModel);//这个必须在itemDO插入后才能执行，这样才能获取到itemDO的id并传给itemStockDO
        itemStockDOMapper.insertSelective(itemStockDO);

        //返回创建完成的对象
        return this.getItemById(itemModel.getId());
    }

    @Override
    public List<ItemModel> listItem() {
        List<ItemDO> itemDOList = itemDOMapper.listItem();
        //使用stream API将list内的itemDO转化为itemModel
        List<ItemModel> itemModelLisy = itemDOList.stream().map(itemDO -> { //将每个itemDo map成itemModel
            ItemStockDO itemStockDO = itemStockDOMapper.selectByItemId(itemDO.getId());
            ItemModel itemModel = this.convertMoldeFromEntity(itemDO, itemStockDO);
            return itemModel;
        }).collect(Collectors.toList());//再转成list对象，里面的值是一个个itemModel
        return itemModelLisy;
    }

    @Override
    public ItemModel getItemById(Integer id) {
        ItemDO itemDO = itemDOMapper.selectByPrimaryKey(id);
        if(itemDO == null){
            return null;
        }
        //获取库存数量
        ItemStockDO itemStockDO = itemStockDOMapper.selectByItemId(itemDO.getId());

        //将两个entity转化成model
        ItemModel itemModel = this.convertMoldeFromEntity(itemDO,itemStockDO);

        //获取秒杀商品信息
        PromoModel promoModel = promoService.getPromoByItemId(itemModel.getId());
        if(promoModel != null && promoModel.getStatus().intValue() !=3){
            itemModel.setPromoModel(promoModel);
        }
        return itemModel;
    }

    @Override
    @Transactional
    public boolean decreaseStock(Integer itemId, Integer amount) throws BusinessException {
        //主要通过SQL语句实现
        int affectedRow = itemStockDOMapper.decreaseStock(itemId, amount); //影响的条目数。如果SQL在执行时发现没有符合的行数，那么SQL不会报错，但是会返回0
        if(affectedRow >0){
            return true;//更新成功
        }else{
            return false;//更新失败
        }
    }

    @Override
    @Transactional
    //增加销量
    public void increaseSales(Integer itemId, Integer amount) throws BusinessException {
        itemDOMapper.increaseSales(itemId,amount);
    }

    private ItemModel convertMoldeFromEntity(ItemDO itemDO,ItemStockDO itemStockDO){
        ItemModel itemModel = new ItemModel();
        BeanUtils.copyProperties(itemDO,itemModel);
        itemModel.setPrice(new BigDecimal(itemDO.getPrice()));
        itemModel.setStock(itemStockDO.getStock());

        return itemModel;
    }


}
