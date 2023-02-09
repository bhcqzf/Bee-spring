package com.fastyw.bee.controller;

import com.fastyw.bee.pojo.Result;
import com.fastyw.bee.service.CityService;
import com.fastyw.bee.util.ResultUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;


@Slf4j
@RequestMapping(value = "/common")
@RestController
public class CommonController {

    @Autowired
    private CityService cityService;

    @GetMapping(value = "/region/v2/province")
    public Result getProvince() {
        Result result = null;
        try {
            result = ResultUtil.okResult();
            result.setData(cityService.getProvince());
        } catch (Exception e) {
            e.printStackTrace();
            result = ResultUtil.custResult(500, String.valueOf(e));
        }
        return result;
    }

    @GetMapping(value = "/region/v2/child")
    public Result getChild(@RequestParam Integer pid) {
        Result result = null;
        try {
            result = ResultUtil.okResult();
            result.setData(cityService.getChild(pid));
        } catch (Exception e) {
            e.printStackTrace();
            result = ResultUtil.custResult(500, String.valueOf(e));
        }
        return result;
    }

}
