package com.fastyw.bee.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Good {
    //商品
    private Integer id;
    private String name;
    private Double originalPrice;
    private Double minPrice;
    private String characteristic;
    private String pic;
    private Boolean pingtuan;
    private String propertyIds;
    private Integer categoryId;
    private Integer stores;
    private Integer minBuyNumber;
}
