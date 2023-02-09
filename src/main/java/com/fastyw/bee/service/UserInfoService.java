package com.fastyw.bee.service;

import com.alibaba.fastjson.JSONObject;
import com.fastyw.bee.util.WeChatUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class UserInfoService {

    @Autowired
    private WeChatUtil weChatUtil;
    //调用工具类，返回用户信息
    public String getResUserinfo(String code,String encryptedData,String iv){
        JSONObject sessionJson =  weChatUtil.getSessionKeyAndOpenid(code);
        String resUserinfo = weChatUtil.getUserInfo(encryptedData,sessionJson.getString("session_key"),iv);
        log.info(resUserinfo);
        return resUserinfo;
    }
}
