package com.fastyw.bee.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Item {
    //购物车里的商品
    private Integer categoryId;
    private Integer id;
    private Integer goodsId;
    private Integer minbuynumber;
    private String name;
    private Integer number;
    private String pic;
    private Double price;
    private Integer stores;
}
