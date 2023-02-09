package com.fastyw.bee.controller;


import com.fastyw.bee.dao.ShopCardDao;
import com.fastyw.bee.pojo.Item;
import com.fastyw.bee.pojo.Result;
import com.fastyw.bee.service.ShopCardService;
import com.fastyw.bee.util.JwtHelper;
import com.fastyw.bee.util.ResultUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@Slf4j
@RequestMapping(value = "/shopping-cart")
@RestController
public class ShopCardController {

    @Autowired
    private ShopCardDao shopCardDao;

    @Autowired
    private ShopCardService shopCardService;

    @PostMapping(value = "/add")
    public Result addCard(@RequestBody Map<String,String> body){

        Result result;
        Map<String,Object> items_data = new HashMap<>();

        int numberSum = 0;
        double priceSum = 0.0;

        String token = body.get("token");
        log.info(token);
        Integer goodsId = Integer.valueOf(body.get("goodsId"));
        Integer number = Integer.valueOf(body.get("number"));

        Item[] items = shopCardService.addCard(token,goodsId,number);
        for (Item item:
             items) {
            log.info(String.valueOf(item));
            numberSum += item.getNumber();
            priceSum += item.getNumber() * item.getPrice();
        }
        if (items.length > 0 ){
            result = ResultUtil.okResult();
            items_data.put("number",numberSum);
            items_data.put("price",priceSum);
            items_data.put("items",items);
            result.setData(items_data);
        }else{
            result = ResultUtil.custResult(500,"未知错误");
        }
        return result;
    }

    @PostMapping(value = "/modifyNumber")
    public Result updateCardByNum(@RequestBody Map<String,String> body){
        Result result;

        Map<String,Object> items_data = new HashMap<>();

        int numberSum = 0;
        double priceSum = 0.0;

        String token = body.get("token");
        log.info(token);
        Integer goodsId = Integer.valueOf(body.get("goodsId"));
        Integer number = Integer.valueOf(body.get("number"));

        Item[] items = shopCardService.changeCard(token,goodsId,number);

        for (Item item:
                items) {
            log.info(String.valueOf(item));
            numberSum += item.getNumber();
            priceSum += item.getNumber() * item.getPrice();
        }
        if (items.length > 0 ){
            result = ResultUtil.okResult();
            items_data.put("number",numberSum);
            items_data.put("price",priceSum);
            items_data.put("items",items);
            result.setData(items_data);
        }else{
            result = ResultUtil.custResult(500,"未知错误");
        }
        return result;
    }

    @GetMapping(value = "/info")
    public Result getCardInfo(@RequestParam String token){
        Result result;
        Map<String,Object> items_data = new HashMap<>();

        int numberSum = 0;
        double priceSum = 0.0;

        JwtHelper jwtHelper = new JwtHelper();
        String openid = jwtHelper.verifyTokenAndGetUserId(token);
        Item[] items = shopCardDao.selectShopCard(openid);
        for (Item item:
                items) {
            log.info(String.valueOf(item));
            numberSum += item.getNumber();
            priceSum += item.getNumber() * item.getPrice();
        }
        if (items.length > 0) {
            result = ResultUtil.okResult();
            items_data.put("number",numberSum);
            items_data.put("price",priceSum);
            items_data.put("items",items);
            result.setData(items_data);
        }else{
            result = ResultUtil.custResult(2,"购物车为空");
        }
        return result;
    }
    @PostMapping(value = "/remove")
    public Result removeCard(@RequestBody Map<String,String> body){
        Result result;
        String token = body.get("token");
        log.info(token);
        Integer goodsId = Integer.valueOf(body.get("goodsId"));

        JwtHelper jwtHelper = new JwtHelper();
        String openid = jwtHelper.verifyTokenAndGetUserId(token);

        int res = shopCardDao.deleteShopCard(openid,goodsId);
        log.info(res == 1 ? "删除成功": "删除失败");
        if (res == 1) {
            result = ResultUtil.okResult();
        }else{
            result = ResultUtil.custResult(700, "暂无数据");
        }
        return result;
    }
    //清空购物车
    @PostMapping(value = "/empty")
    public Result emptyCard(@RequestBody Map<String,String> body){
        Result result;
        String token = body.get("token");
        log.info(token);
        JwtHelper jwtHelper = new JwtHelper();
        String openid = jwtHelper.verifyTokenAndGetUserId(token);
        int res = shopCardDao.deleteAllShopCard(openid);
        if (res > 0){
            result = ResultUtil.okResult();
        }else{
            result = ResultUtil.custResult(2,"清理失败");
        }
        return result;
    }
}
