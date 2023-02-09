package com.fastyw.bee.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Order {
    //订单号
    private Integer id;
    //姓名
    private String linkMan;
    //手机号
    private String mobile;
    //省id
    private String provinceId;
    //市id
    private String cityId;
    //区县id
    private String districtId;
    //地址
    private String address;
    //商店id
    private String shopIdZt;
    //商店名
    private String shopNameZt;
    //备注
    private String remark;
    //商品数量
    private Integer goodsNumber;
    //商品金额
    private Double amountReal;
    //订单状态
    private Integer status;
    //下单时间
    private String dateAdd;
}
