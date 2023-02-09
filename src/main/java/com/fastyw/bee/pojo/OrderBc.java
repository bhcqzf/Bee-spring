package com.fastyw.bee.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

//订单补充信息(暂时只取id和pic，目前和mysql表不对应)
@Data
@NoArgsConstructor
@AllArgsConstructor
public class OrderBc {

    //订单id
    private Integer orderId;

    //商品id
    private Integer goodsId;

    //商品名称
    private String goodsName;

    //商品图片链接
    private String pic;

}
