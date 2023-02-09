package com.fastyw.bee.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.jca.cci.RecordTypeNotSupportedException;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RequestMapping(value = "/banner")
@RestController
public class BannerController {

    //获取banner的链接，暂时写死了
    @GetMapping(value = "/list")
    public String getBannerList(){
        return "{\"code\":0,\"data\":[{\"businessId\":0,\"dateAdd\":\"2023-01-16 12:59:58\",\"id\":263147,\"linkType\":0,\"paixu\":0,\"picUrl\":\"https://dcdn.it120.cc/2020/08/14/a6e9e0ae-6256-457e-82e4-71f400239205.png\",\"shopId\":0,\"status\":0,\"statusStr\":\"显示\",\"title\":\"1\",\"type\":\"1\",\"userId\":50949},{\"businessId\":0,\"dateAdd\":\"2023-01-16 12:59:58\",\"id\":263146,\"linkType\":0,\"paixu\":0,\"picUrl\":\"https://dcdn.it120.cc/2020/08/14/1cc1be28-e8f2-405b-9e43-a472677762b2.png\",\"shopId\":0,\"status\":0,\"statusStr\":\"显示\",\"title\":\"2\",\"type\":\"2\",\"userId\":50949}],\"msg\":\"success\"}";
    }
}
