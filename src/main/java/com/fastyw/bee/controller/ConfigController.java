package com.fastyw.bee.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

//一个获取全局配置的类,暂时写死，有时间再改
@RequestMapping(value = "/config")
@RestController
public class ConfigController {
    @GetMapping(value = "/values")
    public String configGet(){
        return "{\"code\":0,\"data\":[{\"dateUpdate\":\"2021-01-19 11:11:15\",\"key\":\"mallName\",\"remark\":\"商城名称\",\"value\":\"beeOrder小食\"},{\"key\":\"myBg\",\"remark\":\"我的页面的背景图片\",\"value\":\"https://dcdn.it120.cc/2020/08/01/252f429e-1a7f-4bc6-9e06-92b210c437b4.png\"},{\"dateUpdate\":\"2020-08-01 22:39:06\",\"key\":\"order_hx_uids\",\"remark\":\"核销人员编号\",\"value\":\"1545780\"},{\"key\":\"share_profile\",\"remark\":\"分享文案\",\"value\":\"清凉一夏尽情狂欢\"},{\"key\":\"mapPos\",\"remark\":\"地址上标记图片\",\"value\":\"https://dcdn.it120.cc/2020/08/05/83f02ea6-4449-4e19-92f0-274ac614a134.png\"},{\"dateUpdate\":\"2020-08-10 09:42:10\",\"key\":\"zxdz\",\"remark\":\"在线订座预约项目id\",\"value\":\"377\"},{\"dateUpdate\":\"2020-10-06 13:59:15\",\"key\":\"admin_uids\",\"remark\":\"管理员绑定的用户编号，多个用户用英文逗号隔开\",\"value\":\"1545780\"}],\"msg\":\"success\"}";
    }
}
