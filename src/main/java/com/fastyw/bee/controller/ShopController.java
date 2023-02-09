package com.fastyw.bee.controller;

import com.fastyw.bee.dao.GoodDao;
import com.fastyw.bee.dao.ShopDao;
import com.fastyw.bee.pojo.Category;
import com.fastyw.bee.pojo.Good;
import com.fastyw.bee.pojo.Result;
import com.fastyw.bee.pojo.Shop;
import com.fastyw.bee.service.ShopService;
import com.fastyw.bee.util.GeoUtil;
import com.fastyw.bee.util.ResultUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

@Slf4j
@RequestMapping(value = "/shop")
@RestController
public class ShopController {

    @Autowired
    private ShopDao shopDao;

    @Autowired
    private GoodDao goodDao;

    @Autowired
    private ShopService shopService;

    //获取所有启用的分类
    @GetMapping(value = "/goods/category/all")
    public Map<String,Object> getAllcate() {
        Map<String,Object> result = new HashMap<>();
        Category[] categories = shopDao.selectCategory();
        result.put("code",0);
        result.put("msg","success");
        result.put("data",categories);
        return result;
    }

    //按分类获取货物
    @PostMapping(value = "/goods/list")
    public Result getGoodList(@RequestBody Map<String,String> body){
//    public Result getGoodList(HttpServletRequest request){
//        Map body = request.getParameterMap();

        Integer categoryId = Integer.valueOf(body.get("categoryId"));
//        Map<String,Object> result = new HashMap<>();
        Result result = ResultUtil.okResult();
        Good[] goods =  goodDao.selectGood(categoryId);
        result.setData(goods);
        return result;
    }

    //获取商店(这里打算先只设置一个商店)
    @PostMapping(value = "/subshop/list")
    public Result getAllShop(@RequestBody Map<String,Object> body){
        Result result = null;
        Double curlatitude = (Double) body.get("curlatitude");
        Double curlongitude = (Double) body.get("curlongitude");

        ArrayList shopArray = shopService.getShop(curlatitude,curlongitude);
        result = ResultUtil.okResult();
        result.setData(shopArray);

        return result;
    }

    @GetMapping(value = "/subshop/detail/v2")
    public Result getShopDetail(@RequestParam Integer id){
        Result result = null;
        Map<String ,Object> data = new HashMap<>();

        result = ResultUtil.okResult();

        data.put("info",shopService.getAShop(id));
        result.setData(data);

        return result;

    }

}
