package com.fastyw.bee.service;

import com.fastyw.bee.dao.ShopCardDao;
import com.fastyw.bee.pojo.Item;
import com.fastyw.bee.util.JwtHelper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class ShopCardService {

    @Autowired
    private ShopCardDao shopCardDao;

    public Item[] addCard(String token, Integer goodsId, Integer number){
        JwtHelper jwtHelper = new JwtHelper();
        String openid = jwtHelper.verifyTokenAndGetUserId(token);

        //查询该条数据是否存在
        int res = shopCardDao.selectShopCardByog(openid,goodsId);
        if (res > 0 ){
            //如果存在，则在原来的基础上修改数据
            res = shopCardDao.updateShopCard(openid,goodsId,number);
            log.info(res==1?"更新成功":"更新失败");
        }else{
            //如果不存在，则添加一条数据
            res = shopCardDao.insertShopCard(openid,goodsId,number);
            log.info(res==1?"插入成功":"插入失败");
        }
        return shopCardDao.selectShopCard(openid);
    }

    public Item[] changeCard(String token,Integer goodsId, Integer number){
        JwtHelper jwtHelper = new JwtHelper();
        String openid = jwtHelper.verifyTokenAndGetUserId(token);
        int res = shopCardDao.updateShopCardByNum(openid,goodsId,number);

        log.info(res == 1 ? "更新成功": "更新失败");
        return shopCardDao.selectShopCard(openid);
    }

}
