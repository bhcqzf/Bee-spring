package com.fastyw.bee.controller;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.fastyw.bee.dao.UserDao;
import com.fastyw.bee.pojo.Address;
import com.fastyw.bee.pojo.Result;
import com.fastyw.bee.pojo.User;
import com.fastyw.bee.pojo.UserInfoData;
import com.fastyw.bee.service.CityService;
import com.fastyw.bee.service.UserInfoService;
import com.fastyw.bee.util.JwtHelper;
import com.fastyw.bee.util.ResultUtil;
import com.fastyw.bee.util.WeChatUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@Slf4j
@RequestMapping(value = "/user")
@RestController
public class UserController {
    @Autowired
    private WeChatUtil weChatUtil;

    @Autowired
    private UserInfoService userInfoService;

    @Autowired
    private UserDao userDao;

    @Autowired
    private CityService cityService;

    //检查token是否过期
    @GetMapping(value = "/check-token")
    public Map<String, Object> checkToken(@RequestParam String token) {
        JwtHelper jwtHelper = new JwtHelper();
        String openid = jwtHelper.verifyTokenAndGetUserId(token);
//        log.info(openid);
        String msg = "";
        Integer code = 0;
        if (openid == null || "token过期".equals(openid)) {
            msg = "无效登录";
            code = 1;
        } else {
            msg = "success";
        }
        Map<String, Object> result = new HashMap<>();
        result.put("code", code);
        result.put("msg", msg);
        return result;
    }

    //返回用户具体信息
    @GetMapping(value = "/detail")
    public Map<String, Object> detail(@RequestParam String token) {
        JwtHelper jwtHelper = new JwtHelper();
        String openid = jwtHelper.verifyTokenAndGetUserId(token);
        User curUser = userDao.getUser(openid);
        Map<String, Object> result = new HashMap<>();
        Map<String, Object> data = new HashMap<>();
        Integer code = 0;
        String msg = "success";
        data.put("base", curUser);
        result.put("code", code);
        result.put("msg", msg);
        result.put("data", data);
        return result;
    }

    //用户登录
    @PostMapping(value = "/wxapp/login")
    public Map<String, Object> login(@RequestBody Map<String, String> body) {
        String code = body.get("code");
        Map<String, Object> result = new HashMap<>();
        String msg = "";
        try {
            JwtHelper jwtHelper = new JwtHelper();
            JSONObject sessionJson = weChatUtil.getSessionKeyAndOpenid(code);
            String openid = sessionJson.getString("openid");
            Map<String, Object> data = new HashMap<>();
            if (openid == null) {
                msg = "登录失败：code无效";
                result.put("code", 2);
            } else {
                String token = jwtHelper.createToken(openid);
//                log.info(token);
                data.put("openid", openid);
                data.put("token", token);
                result.put("code", 0);
                result.put("data", data);
                msg = "success";
            }
        } catch (Exception e) {
            log.error(String.valueOf(e));
            result.put("code", 1);
            msg = "fail";
        }
        result.put("msg", msg);
        return result;
    }

    //用户点击登录时，默认会注册一次
    @PostMapping(value = "/wxapp/register/complex")
    public Map<String, Object> toRegister(@RequestBody UserInfoData userInfoData) {
        String code = userInfoData.getCode();
        String encryptedData = userInfoData.getEncryptedData();
        String iv = userInfoData.getIv();

        String resUserInfo = userInfoService.getResUserinfo(code, encryptedData, iv);

        JSONObject jsonUserinfo = JSON.parseObject(resUserInfo);
        log.info(String.valueOf(jsonUserinfo));

        String msg = "";
        if (userDao.existUser(jsonUserinfo.getString("openId")) < 1) {
            //如果用户不存在
            User tempUser = new User();
            tempUser.setOpenId(jsonUserinfo.getString("openId"));
            tempUser.setNick(jsonUserinfo.getString("nickName"));
            tempUser.setAvatarUrl(jsonUserinfo.getString("avatarUrl"));
            tempUser.setGender(jsonUserinfo.getInteger("gender"));
            log.info(String.valueOf(tempUser));
            log.info("打印一下插入结果");
            msg = userDao.insertUser(tempUser) == 1 ? "success" : "用户新增失败";

        } else {
            //不是第一次注册
            msg = "用户已存在";
        }
        Map<String, Object> result = new HashMap<>();
        result.put("code", 0);
        result.put("msg", msg);

        return result;

    }

    //返回用户余额等信息,先写死，有时间再实现
    @GetMapping(value = "/amount")
    public String amount() {
        return "{\"code\":0,\"data\":{\"balance\":0.00,\"freeze\":0.00,\"fxCommisionPaying\":0.00,\"growth\":0.00,\"score\":0,\"scoreLastRound\":0,\"totalPayAmount\":0.00,\"totalPayNumber\":0,\"totalScore\":0,\"totalWithdraw\":0.00,\"totleConsumed\":0.00},\"msg\":\"success\"}";
    }

    @PostMapping(value = "/shipping-address/add")
    public Result addAddress(@RequestBody Map<String, Object> body) {
        Result result = null;
        try {
            String token = String.valueOf(body.get("token"));
            String linkMan = String.valueOf(body.get("linkMan"));
            String address = String.valueOf(body.get("address"));
            String mobile = String.valueOf(body.get("mobile"));
            Boolean isDefault = Boolean.valueOf(String.valueOf(body.get("isDefault")));
            String provinceId = String.valueOf(body.get("provinceId"));
            String cityId = String.valueOf(body.get("cityId"));
            String districtId = String.valueOf(body.get("districtId"));
            Address addressTmp = cityService.addAddress(token, linkMan, address, mobile, isDefault, provinceId, cityId, districtId);

            result = ResultUtil.okResult();
            result.setData(addressTmp);
        } catch (Exception e) {
            e.printStackTrace();
            result = ResultUtil.custResult(500, String.valueOf(e));
        }
        return result;
    }

    //获取当前用户所有的地址
    @GetMapping(value = "/shipping-address/list")
    public Result getAllAddress(@RequestParam String token) {
        Result result = null;
        try {
            JwtHelper jwtHelper = new JwtHelper();
            String openid = jwtHelper.verifyTokenAndGetUserId(token);

            Address[] addresses = cityService.getAllAddress(token);
            if (addresses.length > 0) {
                result = ResultUtil.okResult();
                result.setData(addresses);
            } else {
                result = ResultUtil.custResult(2, "收获地址为空");
            }
        } catch (Exception e) {
            e.printStackTrace();
            result = ResultUtil.custResult(500, String.valueOf(e));
        }
        return result;
    }

    //删除当前用户一条地址
    @PostMapping(value = "/shipping-address/delete")
    public Result deleteAddress(@RequestBody Map<String, Object> body) {
        String token = String.valueOf(body.get("token"));
        Integer id = Integer.valueOf(String.valueOf(body.get("id")));
        Result result = null;
        try {
            int res = cityService.deleteOneAddress(token, id);

            if (res == 1) {
                log.info("删除成功");
                result = ResultUtil.okResult();
            } else {
                result = ResultUtil.custResult(2, "未知错误");
            }
        } catch (Exception e) {
            e.printStackTrace();
            result = ResultUtil.custResult(500, String.valueOf(e));
        }

        return result;
    }

    //获取默认的地址
    @GetMapping(value = "/shipping-address/default/v2")
    public Result getDefalutAddress(@RequestParam String token) {
        Result result = null;
        try {
            Map<String, Object> info = new HashMap<>();
            result = ResultUtil.okResult();
            info.put("info", cityService.selectOneDefaultAddress(token));
            result.setData(info);
        } catch (Exception e) {
            e.printStackTrace();
            result = ResultUtil.custResult(500, String.valueOf(e));
        }

        return result;
    }

    //获取地址明细
    @GetMapping(value = "/shipping-address/detail/v2")
    public Result getAddressDetail(@RequestParam String token, @RequestParam Integer id) {
        Result result = null;
        try {
            Map<String, Object> info = new HashMap<>();
            result = ResultUtil.okResult();
            info.put("info", cityService.selectOneDefaultAddress(token));
            result.setData(info);
        } catch (Exception e) {
            e.printStackTrace();
            result = ResultUtil.custResult(500, String.valueOf(e));
        }
        return result;
    }

    //更新用户地址 /user/shipping-address/update
//    public

    @PostMapping(value = "/wxapp/bindMobile")
    public Result binMobile(@RequestBody UserInfoData userInfoData) {
        Result result = null;
        try {
            String code = userInfoData.getCode();
            String encryptedData = userInfoData.getEncryptedData();
            String iv = userInfoData.getIv();
            String resUserInfo = userInfoService.getResUserinfo(code, encryptedData, iv);

            JSONObject jsonUserinfo = JSON.parseObject(resUserInfo);
            String phoneNumber = jsonUserinfo.getString("phoneNumber");
            result = ResultUtil.okResult();
            result.setData(phoneNumber);

            System.out.println(resUserInfo);
        } catch (Exception e) {
            e.printStackTrace();
            result = ResultUtil.custResult(500, String.valueOf(e));
        }
        return result;
    }


}
