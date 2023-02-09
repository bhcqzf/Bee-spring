package com.fastyw.bee.util;

import com.fastyw.bee.pojo.Result;

public class ResultUtil {
    public static Result okResult(){
        Result result = new Result();
        result.setCode(0);
        result.setMsg("success");
        return result;
    }
    public static Result custResult(Integer code,String msg){
        Result result = new Result();
        result.setCode(code);
        result.setMsg(msg);
        return result;
    }
}
