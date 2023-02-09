package com.fastyw.bee.dao;

import com.fastyw.bee.pojo.City;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;

@Mapper
@Repository
public interface CityDao {
    //获取城市的dao
    @Select("select * from china where Pid = 0 and Id != 0")
    City[] selectProvince();

    //通过Pid获取城市
    @Select("select * from china where Pid = #{pid}")
    City[] selectChild(Integer pid);

    //通过id获取城市名
    @Select("select name from china where id = #{id}")
    String selectCity(Integer id);
}
