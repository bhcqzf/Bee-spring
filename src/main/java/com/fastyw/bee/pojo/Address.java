package com.fastyw.bee.pojo;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Address {
    //地址id
    private Integer id;
    //用户id,外键
    private String openid;
    //详细地址
    private String address;
    //区县名
    private String areaStr;
    //区县ID
    private String districtId;
    //市名
    private String cityStr;
    //市id
    private String cityId;
    //省名
    private String provinceStr;
    //省ID
    private String provinceId;
    //是否为默认地址
    private Boolean isDefault;
    //收货人姓名
    private String linkMan;
    //收货人手机号
    private String mobile;

}
