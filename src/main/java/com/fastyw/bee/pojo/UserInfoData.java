package com.fastyw.bee.pojo;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserInfoData {
    //用于微信用户数据解密
    private String code;
    private String encryptedData;
    private String iv;
    private String referrer;
}
