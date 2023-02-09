package com.fastyw.bee.dao;

import com.fastyw.bee.pojo.Order;
import com.fastyw.bee.pojo.OrderBc;
import org.apache.ibatis.annotations.*;
import org.springframework.stereotype.Repository;

@Mapper
@Repository
public interface OrderDao {

    //插入一条orderlist数据
    @Insert("insert into orderlist(openid,address,cityId,districtId,linkMan,peisongType,mobile,provinceId,remark,shopIdZt,shopNameZt,goodsNumber,amountReal,status) values (#{openid},#{address},#{cityId},#{districtId},#{linkMan},#{peisongType},#{mobile},#{provinceId},#{remark},#{shopIdZt},#{shopNameZt},#{goodsNumber},#{amountReal},status)")
    Integer insertOrderList(String openid,String address,String cityId,String districtId,String linkMan,String peisongType,String mobile,String provinceId,
                            String remark,String shopIdZt,String shopNameZt,Integer goodsNumber,Double amountReal,Integer status);

    //获取最近插入成功的一条数据的id
    @Select("select id from orderlist where openid = #{openid} order by dateAdd desc limit 1")
    Integer selectOrderList(String openid);

    //插入数据到订单补充表
    @Insert("insert into order_bc(id,goodsid,number) values(#{id},#{goodsid},#{number})")
    Integer insertOrderBc(Integer id ,Integer goodsid,Integer number);

    //根据openid获取所有订单
    @Select("select * from orderlist where openid = #{openid} order by dateAdd desc ")
    Order[] selectAllOrder(String openid);

    //根据订单号返回商品补充信息
    @Select("select ob.id orderid,ob.goodsid goodsId,g.name goodsName,g.pic pic from order_bc ob left join goods g ON ob.goodsid = g.id  where ob.id = #{orderid}")
    OrderBc[] selectAllOrderBc(Integer orderid);

    //更新订单状态
    @Update("update orderlist set status = #{status} where openid = #{openid} and id = #{orderid}")
    Integer updateOrder(String openid,Integer orderid,Integer status);

    //删除一条订单
    @Delete("delete from orderlist where openid = #{openid} and id = #{orderid}")
    Integer deleteOrder(String openid,Integer orderid);
}
