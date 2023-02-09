package com.fastyw.bee.dao;

import com.fastyw.bee.pojo.Address;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;

@Mapper
@Repository
public interface AddressDao {
    //插入一条地址
    @Insert("insert into address(openid,address,areaStr,districtId,cityStr,cityId,provinceStr,provinceId,isDefault,linkMan,mobile) values(#{openid},#{address},#{areaStr},#{districtId},#{cityStr},#{cityId},#{provinceStr},#{provinceId},#{isDefault},#{linkMan},#{mobile})")
    Integer insertAddress(String openid,String address,String areaStr,String districtId,String cityStr,String cityId,String provinceStr,String provinceId,Boolean isDefault,String linkMan,String mobile);

    //获取最近插入的一条数据
    @Select("select * from address order by timestamp desc limit 1")
    Address selectLastAddress();

    //获取所有的数据
    @Select("select * from address where openid = #{openid}")
    Address[] selectAllAddress(String openid);

    //删除一条地址
    @Delete("delete from address where openid = #{openid} and id = #{id}")
    Integer deleteOneAddress(String openid,Integer id);

    //获取最近的一条默认地址
    @Select("select * from address where openid = #{openid} and isDefault = true order by timestamp desc limit 1")
    Address selectDefaultAddress(String openid);

    //根据id获取一条地址
    @Select("select * from address where id = #{id}")
    Address selectAddressDetail(Integer id);

}
