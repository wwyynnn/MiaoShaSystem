package com.miaoshaSystem.service.impl;

import com.miaoshaSystem.dao.OrderDOMapper;
import com.miaoshaSystem.dao.SequenceDOMapper;
import com.miaoshaSystem.eneity.OrderDO;
import com.miaoshaSystem.eneity.SequenceDO;
import com.miaoshaSystem.error.BusinessException;
import com.miaoshaSystem.error.EmBusinessError;
import com.miaoshaSystem.service.ItemService;
import com.miaoshaSystem.service.UserService;
import com.miaoshaSystem.service.model.ItemModel;
import com.miaoshaSystem.service.model.OrderModel;
import com.miaoshaSystem.service.OrderService;
import com.miaoshaSystem.service.model.UserModel;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/**
 * @author: Wang Yannan
 * @date: 2020/7/2 7:06 下午
 */
@Service
public class OrderServiceImpl implements OrderService {

    @Autowired
    private ItemService itemService;

    @Autowired
    private UserService userService;

    @Autowired
    private OrderDOMapper orderDOMapper;

    @Autowired
    private SequenceDOMapper sequenceDOMapper;

    @Override
    @Transactional
    public OrderModel createOrder(Integer userId, Integer itemId, Integer promoId, Integer amount) throws BusinessException {
        //校验下单状态：下单商品是否存在/用户是否合法/购买数量是否正确
        ItemModel itemModel = itemService.getItemById(itemId);
        if(itemModel == null){
            throw new BusinessException("商品不存在", EmBusinessError.PARAMETER_VALIDATION_ERROR);
        }

        UserModel userModel = userService.getUserById(userId);
        if(userModel == null){
            throw new BusinessException("用户不存在", EmBusinessError.PARAMETER_VALIDATION_ERROR);
        }

        if(amount <= 0 || amount > 99){
            throw new BusinessException("购买数量不正确",EmBusinessError.PARAMETER_VALIDATION_ERROR);
        }

        //校验活动信息[通过前段url传过来秒杀活动ID，然后下单接口内校验对应ID是否属于对应商品且活动已开始]
        //因为可能某个商品在多个秒杀活动中（也可能不在），并且开始与否状态不一，因此需要校验
        if(promoId != null){
            //校验传过来的活动商品是否真的存在某个活动中（与商品model里面的活动model进行比较），避免出现传来的商品其实并没有参与任何秒杀活动（数据库无信息）
            if(promoId.intValue() != itemModel.getPromoModel().getId()){
                throw new BusinessException("活动信息不正确",EmBusinessError.PARAMETER_VALIDATION_ERROR);
            }else if(itemModel.getPromoModel().getStatus().intValue() != 2){
                //校验当前活动是否进行中（到这一步说明上述商品确实存在于某个活动，现在判断活动是否开始）
                throw new BusinessException("活动信息未开始",EmBusinessError.PARAMETER_VALIDATION_ERROR);
                //若这两步都没经历，说明确实该商品确实存在某个活动中，且活动正在进行，因此购买时价格应该是promoPrice，而不是itemPrice
            }
        }
        //下单减库存（支付之前就将商品锁定，给用户使用）/支付减库存（下单时不锁库存，只是检验。支付成功后才真正扣存
        //目前采用下单减库存
        boolean result = itemService.decreaseStock(itemId, amount);
        if(!result){//是false
            throw new BusinessException(EmBusinessError.STOCK_NOT_ENOUGH);
        }

        //订单入库
        OrderModel orderModel = new OrderModel();
        orderModel.setUserId(userId);
        orderModel.setItemId(itemId);
        orderModel.setPromoId(promoId);
        orderModel.setAmount(amount);
        if(promoId != null){//该商品确实存在某个活动中，且活动正在进行，因此购买时价格应该是promoPrice，而不是itemPrice
            orderModel.setItemPrice(itemModel.getPromoModel().getPromoItemPrice());
        }else{//不存在活动/活动未开始，使用itemPrice
            orderModel.setItemPrice(itemModel.getPrice());
        }
        orderModel.setOrderPrice(orderModel.getItemPrice().multiply(new BigDecimal(amount)));//注意金额用的已经是orderModel里面的单价

        //生成交易流水号（订单号）
        orderModel.setId(gengerateOrderNo());
        OrderDO orderDO = this.convertFromOrderModel(orderModel);
        orderDOMapper.insertSelective(orderDO);

        //增加商品的销量
        itemService.increaseSales(itemId, amount);

        //返回前端
        return orderModel;
    }

    //实现model->entity转化
    private OrderDO convertFromOrderModel(OrderModel orderModel){
        if(orderModel == null){
            return null;
        }
        OrderDO orderDO = new OrderDO();
        BeanUtils.copyProperties(orderModel, orderDO);
        orderDO.setItemPrice(orderModel.getItemPrice().doubleValue());//是因为在orderModel中，price用的是BigDecimal格式，但是在orderDO以及数据库中price用的都是double，因此需要进行格式转换，否则无法将price存入数据库
        orderDO.setOrderPrice(orderModel.getOrderPrice().doubleValue());//原因同上
        return orderDO;
    }

    //有这样一个问题：因为前面createOrder函数是@Transactional的，是一致性的，因此如果函数出现任何一步的问题需要回滚，那么
    //包括gengerateOrderNo函数都会回滚。则当下一次成功实现创建订单函数时，拿到的id跟回滚之前拿到的id是一样的，这样不符合全局唯一性
    //因此使用下面的标识符解决此问题：

    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public String gengerateOrderNo(){
        //订单号20位
        StringBuilder stringBuilder = new StringBuilder();

        //前14位时间信息（年月日时分秒）
        LocalDateTime now = LocalDateTime.now(); //当前时间
//        LocalDate date = now.toLocalDate();//获取日期，格式yyyy-MM-dd
//        LocalTime time = now.toLocalTime();//获取时间，格式HH:mm:ss,但是秒是带小数点的，如19.645，不能直接用
        int hour = now.getHour();
        int minu = now.getMinute();
        int sec = now.getSecond();
        String nowDate = now.format(DateTimeFormatter.ISO_DATE).replace("-", "");
        //不能直接String nowTime = now..format(DateTimeFormatter.ISO_TIME).replace(":","")，因为秒是带小数点的，如23.173，会变成214819.645
        stringBuilder.append(nowDate);
        stringBuilder.append(hour);
        stringBuilder.append(minu);
        stringBuilder.append(sec);

        //中间6位为自增序列（目前）（通过新建一张表sequence_info来实现自增序列值)
        //获取当前sequence
        int sequence = 0;
        SequenceDO sequenceDO = sequenceDOMapper.getSequenceByName("order_info");
        sequence = sequenceDO.getCurrentValue();
        sequenceDO.setCurrentValue(sequenceDO.getCurrentValue()+sequenceDO.getStep());//更新currentValue（加上步长 值）
        sequenceDOMapper.updateByPrimaryKeySelective(sequenceDO);//反写会数据库，更新更新currentValue

        String sequenceStr = String.valueOf(sequence);
        for(int i=0;i<6-sequenceStr.length();i++){//这里其实有个问题，如果自增后sequence长度大于6怎么办？
            // 在这里应该对数据库sequence_info再多一列，用于存储sequence的最大值，如果更新后的currentValue大于最大值，
            // 那么此时应该实现循环，即currentValue再次变为0，从头开始叠加
            stringBuilder.append(0);//不足6位的用0占位
        }
        stringBuilder.append(sequenceStr);

        //最后两位为分库分表位（00-99）订单水平拆分用（暂时写死）
        stringBuilder.append("00");


        return stringBuilder.toString();
    }
}
