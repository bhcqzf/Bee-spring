package com.fastyw.bee.dao;

import com.fastyw.bee.pojo.Category;
import com.fastyw.bee.pojo.Shop;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;

@Mapper
@Repository
public interface ShopDao {
    //获取所有启用的分类
    @Select("select * from categories where isuse = 1")
    Category[] selectCategory();

    //获取所有的商店
    @Select("select * from shop")
    Shop[] selectAllShop();

    //通过id获取商店
    @Select("select * from shop where id = #{id}")
    Shop selectAShop(Integer id);
}
