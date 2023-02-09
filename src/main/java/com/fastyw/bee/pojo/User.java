package com.fastyw.bee.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class User {
    //用户
    private String openId;
    private String avatarUrl;
    private String city;
    private String dateAdd;
    private Integer gender;
    private Long id;
    private String ipAdd;
    private String ipLogin;
    private String nick;
    private String province;
    private Integer source;
    private String sourceStr;
    private Integer status;
    private String statusStr;
}
