package com.fastyw.bee.controller;


import com.fastyw.bee.pojo.Order;
import com.fastyw.bee.pojo.OrderBc;
import com.fastyw.bee.pojo.Result;
import com.fastyw.bee.service.OrderService;
import com.fastyw.bee.util.ResultUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

@Slf4j
@RequestMapping(value = "/order")
@RestController
public class OrderController {

    @Autowired
    private OrderService orderService;

    @PostMapping(value = "/create")
    public Result createOrder(@RequestBody Map<String, Object> body) {
        log.info(String.valueOf(body));

        Result result = null;
        try {
            Map<String, Object> data = new HashMap<>();
            if (body.containsKey("calculate")) {
                String goodsJsonStr = String.valueOf(body.get("goodsJsonStr"));
                Map<String, Object> map = orderService.getPrice(goodsJsonStr);
                Double price = (Double) map.get("price");
                Integer number = (Integer) map.get("number");
                result = ResultUtil.okResult();
                data.put("amountReal", price);
                data.put("amountTotle", price);
                data.put("amountTotleOriginal", price);
                data.put("goodsNumber", number);
                //配送费暂时写0
                data.put("amountLogistics", 0);
                result.setData(data);
            } else if (body.containsKey("address")) {
                String goodsJsonStr = String.valueOf(body.get("goodsJsonStr"));
                String address = String.valueOf(body.get("address"));
                String cityId = String.valueOf(body.get("cityId"));
                String districtId = String.valueOf(body.get("districtId"));
                String linkMan = String.valueOf(body.get("linkMan"));
                String peisongType = String.valueOf(body.get("peisongType"));
                String mobile = String.valueOf(body.get("mobile"));
                String provinceId = String.valueOf(body.get("provinceId"));
                String remark = String.valueOf(body.get("remark"));
                String shopIdZt = String.valueOf(body.get("shopIdZt"));
                String shopNameZt = String.valueOf(body.get("shopNameZt"));
                String token = String.valueOf(body.get("token"));

                Map<String, Object> map = orderService.getPrice(goodsJsonStr);
                Double price = (Double) map.get("price");
                Integer number = (Integer) map.get("number");

                Integer id = orderService.crateOrder(token, address, cityId, districtId, linkMan, peisongType, mobile, provinceId,
                        remark, shopIdZt, shopNameZt, number, price, 0, goodsJsonStr);

                if (id != 0) {
                    //插入订单成功
                    data.put("id", id);
                    data.put("status", 0);
                }

                data.put("amountReal", price);
                data.put("amountTotle", price);
                data.put("amountTotleOriginal", price);
                data.put("goodsNumber", number);
                //配送费暂时写0
                data.put("amountLogistics", 0);
                result.setData(data);

            }
        } catch (Exception e) {
            e.printStackTrace();
            result = ResultUtil.custResult(500, String.valueOf(e));
        }
        return result;
    }

    //返回当前用户所有订单
    @PostMapping(value = "/list")
    public Result getAllOrder(@RequestBody Map<String, Object> body) {
        Result result = null;
        try {
            Map<String, Object> data = new HashMap<>();
            Map<String, Object> goodsMap = new HashMap<>();

            String token = String.valueOf(body.get("token"));
            Order[] orders = orderService.getAllOrder(token);

            log.info("开始遍历订单");
            //遍历订单
            for (Order order :
                    orders) {
                log.info(String.valueOf(order));
                OrderBc[] orderBcs = orderService.getOrderBc(order.getId());
                ArrayList tempArray = new ArrayList();
                goodsMap.put(String.valueOf(order.getId()), orderBcs);
            }
            if (orders.length > 0) {
                data.put("orderList", orders);
                data.put("goodsMap", goodsMap);
                result = ResultUtil.okResult();
                result.setData(data);
            } else {
                result = ResultUtil.custResult(3, "无订单信息");
            }
        } catch (Exception e) {
            e.printStackTrace();
            result = ResultUtil.custResult(500, String.valueOf(e));
        }
        return result;
    }

    //关闭订单
    @PostMapping(value = "/close")
    public Result closeOrder(@RequestBody Map<String, Object> body) {
        Result result = null;
        try {
            String token = String.valueOf(body.get("token"));
            Integer orderId = (Integer) body.get("orderId");

            int res = orderService.closeOrder(token, orderId);
            if (res == 1) {
                log.info("更新成功");
                result = ResultUtil.okResult();
            }
        } catch (Exception e) {
            e.printStackTrace();
            result = ResultUtil.custResult(500, String.valueOf(e));
        }
        return result;
    }

    //删除订单
    @PostMapping(value = "/delete")
    public Result deleteOrder(@RequestBody Map<String, Object> body) {
        Result result = null;
        try {
            String token = String.valueOf(body.get("token"));
            Integer orderId = (Integer) body.get("orderId");

            int res = orderService.deleteOrder(token, orderId);
            if (res == 1) {
                log.info("删除成功");
                result = ResultUtil.okResult();
            }
        } catch (Exception e) {
            e.printStackTrace();
            result = ResultUtil.custResult(500, String.valueOf(e));
        }
        return result;
    }

}
