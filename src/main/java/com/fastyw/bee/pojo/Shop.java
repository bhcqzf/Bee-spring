package com.fastyw.bee.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Shop {
    private Integer id;
    private Double distance;
    private String openingHours;
    private String pic;
    private String name;
    private String address;
    private String characteristic;
    private String linkPhone;
    private Double latitude;
    private Double longitude;
}
