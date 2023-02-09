package com.fastyw.bee.dao;

import com.fastyw.bee.pojo.Good;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;

@Mapper
@Repository
public interface GoodDao {
    //通过分类id获取该分类下所有商品
    @Select("select * from goods where categoryId = #{categoryId}")
    Good[] selectGood(Integer categoryId);

    //通过货物id获取该商品价格
    @Select("select minprice price from goods where id = #{goodsid}")
    Double selectGoodsPrice(Integer goodsid);
}
