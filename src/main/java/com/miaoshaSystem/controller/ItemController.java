package com.miaoshaSystem.controller;

import com.miaoshaSystem.controller.view.ItemView;
import com.miaoshaSystem.error.BusinessException;
import com.miaoshaSystem.response.CommonReturnType;
import com.miaoshaSystem.service.ItemService;
import com.miaoshaSystem.service.model.ItemModel;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author: Wang Yannan
 * @date: 2020/6/21 10:31 上午
 */
@Controller("item")
@RequestMapping("/item") //url 路径访问后缀
@CrossOrigin(allowCredentials="true", allowedHeaders="*")//解决ajax跨域请求问题,做到session跨域共享
//DEFAULT_ALLOWED_HEADERS: 允许跨域传输所有header参数，将用于使用token放入header域做session共享的跨域请求
//DEFAULT_ALLOW_CREDENTIALS=true：需要配合前端设置xhrFields授信后是的跨域session共享
public class ItemController extends BaseController{

    @Autowired
    private ItemService itemService;



    //创建商品
    @RequestMapping(value = "/create",method = {RequestMethod.POST},consumes={CONTENT_TYPE_FORMED})
    @ResponseBody
    public CommonReturnType createItem(@RequestParam(name = "title")String title,
                                       @RequestParam(name = "description")String descriprion,
                                       @RequestParam(name = "price") BigDecimal price,
                                       @RequestParam(name = "stock")Integer stock,
                                       @RequestParam(name = "imgUrl")String imgUrl) throws BusinessException {
        //封装service请求用来创建商品
        ItemModel itemModel = new ItemModel();
        itemModel.setTitle(title);
        itemModel.setDescription(descriprion);
        itemModel.setStock(stock);
        itemModel.setPrice(price);
        itemModel.setImgUrl(imgUrl);

        ItemModel itemModelForReturn = itemService.createItem(itemModel);
        ItemView itemView = convertViewFromModel(itemModelForReturn);

        return CommonReturnType.create(itemView);

    }

    private ItemView convertViewFromModel(ItemModel itemModel){
        if(itemModel == null){
            return null;
        }
        ItemView itemView = new ItemView();
        BeanUtils.copyProperties(itemModel,itemView);
        if(itemModel.getPromoModel() != null){
            //说明有正在/即将进行的秒杀活动
            itemView.setPromoStatus(itemModel.getPromoModel().getStatus());
            itemView.setPromoId(itemModel.getPromoModel().getId());
            itemView.setStartDate(itemModel.getPromoModel().getStartDate().toString(DateTimeFormat.forPattern("yyyy-MM-dd HH:mm:ss")));//因为Model里面开始时间是DateTime类型，但是view模型里面是String，所以需要类型转换
            itemView.setPromoPrice(itemModel.getPromoModel().getPromoItemPrice());
        }else{
            itemView.setPromoStatus(0);
        }
        return itemView;
    }

    //商品详情页展示
    @RequestMapping(value = "/get",method = {RequestMethod.GET})
    @ResponseBody
    public CommonReturnType getItem(@RequestParam(name = "id")Integer id){
        ItemModel itemModel = itemService.getItemById(id);
        ItemView itemView = convertViewFromModel(itemModel);

        return CommonReturnType.create(itemView);
    }

    //商品列表页面浏览
    @RequestMapping(value = "/list",method = {RequestMethod.GET})
    @ResponseBody
    public CommonReturnType listItem(){
        List<ItemModel> itemModelList = itemService.listItem();
        //使用stream API将list内的itemModel转化为itemView
        List<ItemView> itemViewList = itemModelList.stream().map(itemModel -> {
            ItemView itemView = this.convertViewFromModel(itemModel);
            return itemView;
        }).collect(Collectors.toList());
        return CommonReturnType.create(itemViewList);
    }
}
