package com.fastyw.bee.dao;

import com.fastyw.bee.pojo.Item;
import org.apache.ibatis.annotations.*;
import org.springframework.stereotype.Repository;

@Mapper
@Repository
public interface ShopCardDao {
    //购物车add动作
    @Insert("insert into shopcart(openId,goodsid,`number`) values(#{openid},#{goodsid},#{number})")
    Integer insertShopCard(String openid,Integer goodsid,Integer number);

    //获取购物车中的货物
    @Select("select g.categoryId,g.id,g.id goodsId,g.minbuynumber,g.name,s.`number`,g.pic,g.minprice price,g.stores  from shopcart s left join goods g on s.goodsid = g.id where openId = #{openid} ")
    Item[] selectShopCard(String openid);

    //删除一条购物车物品
    @Delete("delete from shopcart where openId = #{openid} and goodsid = #{goodsid}")
    Integer deleteShopCard(String openid,Integer goodsid);

    //清除该用户所有购物车
    @Delete("delete from shopcart where openId = #{openid}")
    Integer deleteAllShopCard(String openid);

    //使用openid和goodsid查询数据是否存在
    @Select("select count(*) from shopcart where openId = #{openid} and goodsid = #{goodsid}")
    Integer selectShopCardByog(String openid,Integer goodsid);

    //购物车中货物数量增减
    @Update("update shopcart set number = number+ #{number} where openId = #{openid} and goodsid = #{goodsid}")
    Integer updateShopCard(String openid,Integer goodsid,Integer number);

    //指定购物车中货物数量
    @Update("update shopcart set number = #{number} where openId = #{openid} and goodsid = #{goodsid}")
    Integer updateShopCardByNum(String openid,Integer goodsid,Integer number);


}
