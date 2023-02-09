package com.fastyw.bee.service;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.fastyw.bee.dao.GoodDao;
import com.fastyw.bee.dao.OrderDao;
import com.fastyw.bee.pojo.Order;
import com.fastyw.bee.pojo.OrderBc;
import com.fastyw.bee.util.JwtHelper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
import java.util.Map;

@Slf4j
@Service
public class OrderService {

    @Autowired
    private GoodDao goodDao;

    @Autowired
    private OrderDao orderDao;

    //获取购物车中货物总价及总数
    public Map<String,Object> getPrice(String goodsJsonStr){
        Map<String,Object> map = new HashMap<>();

        Double priceSum = 0.0;
        Integer numberSum = 0;
        JSONArray temp=JSONArray.parseArray(goodsJsonStr);
        for(int i=0;i<temp.size();i++){
            JSONObject obj=(JSONObject)temp.get(i);
            priceSum += goodDao.selectGoodsPrice(obj.getInteger("goodsId")) * obj.getInteger("number");
            numberSum += obj.getInteger("number");
        }
        map.put("price",priceSum);
        map.put("number",numberSum);
        return map;
    }
    //插入一条订单数据,返回是否成功
    @Transactional
    public Integer crateOrder(String token,String address,String cityId,String districtId,String linkMan,String peisongType,String mobile,String provinceId,
                              String remark,String shopIdZt,String shopNameZt,Integer goodsNumber,Double price,Integer status,String goodsJsonStr){

        //获取openid
        JwtHelper jwtHelper = new JwtHelper();
        String openid = jwtHelper.verifyTokenAndGetUserId(token);

        Double amountReal = price;

        /*
        * 订单未支付销毁功能
        * 尚未实现：
        * 1、redis（付款后入库）
        * 2、rabbitmq(延迟队列)
        * 3、定时器(程序重启后是个问题)
        * */


        /*
        * 在插入订单前，需要先判断订单数是否大于库存数，否则下单失败
        * */

        int res = orderDao.insertOrderList(openid,address,cityId,districtId,linkMan,peisongType,mobile,provinceId,
            remark,shopIdZt,shopNameZt,goodsNumber,amountReal,status);

        if (res == 1){
            //如果插入订单成功，遍历数组，插入货物数量到补充表
            log.info("数据插入成功");
            Integer id = orderDao.selectOrderList(openid);
            JSONArray temp=JSONArray.parseArray(goodsJsonStr);
            int num;
            int goodsid;
            for(int i=0;i<temp.size();i++){
                JSONObject obj=(JSONObject)temp.get(i);
                num = obj.getInteger("number");
                goodsid = obj.getInteger("goodsId");
                //插入数据到订单补充表
                orderDao.insertOrderBc(id,goodsid,num);
            }
            return id;

        }
        return 0;
    }
    //返回所有订单
    public Order[] getAllOrder(String token){
        //获取openid
        JwtHelper jwtHelper = new JwtHelper();
        String openid = jwtHelper.verifyTokenAndGetUserId(token);
        Order[] orders  = orderDao.selectAllOrder(openid);
        return orders;
    }
    //根据订单id返回订单补充信息（目前只返回物品id和图片链接）
    public OrderBc[] getOrderBc(Integer orderid){
        OrderBc[] orderBcs = orderDao.selectAllOrderBc(orderid);
        for (OrderBc orderBc:
             orderBcs) {
            log.info(String.valueOf(orderBc));
        }
        return orderBcs;
    }

    //关闭订单
    public Integer closeOrder(String token,Integer orderId){
        //获取openid
        JwtHelper jwtHelper = new JwtHelper();
        String openid = jwtHelper.verifyTokenAndGetUserId(token);
        return orderDao.updateOrder(openid,orderId,-1);
    }

    //删除订单
    public Integer deleteOrder(String token,Integer orderId){
        //获取openid
        JwtHelper jwtHelper = new JwtHelper();
        String openid = jwtHelper.verifyTokenAndGetUserId(token);
        return orderDao.deleteOrder(openid,orderId);
    }




}
