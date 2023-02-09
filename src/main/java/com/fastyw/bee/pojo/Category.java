package com.fastyw.bee.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Category {
    //分类
    private Integer id;
    private String name;
    private Integer paixu;
    private Integer isUse;
}
