package com.fastyw.bee.Config;


import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.fastyw.bee.pojo.Result;
import com.fastyw.bee.util.JwtHelper;
import com.fastyw.bee.util.RequestBodyWrapper;
import com.fastyw.bee.util.ResultUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.PrintWriter;


/**
 * @auther xxx
 * @create 2020/4/10
 */

public class RequestInterceptor implements HandlerInterceptor {

    private Logger logger = LoggerFactory.getLogger(RequestInterceptor.class);


    /**
     * 进入controller层之前拦截请求
     * @param httpServletRequest
     * @param httpServletResponse
     * @param o
     * @return
     * @throws Exception
     */
    @Override
    public boolean preHandle(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object o) throws Exception {
        String token = "";

        String method = httpServletRequest.getMethod();
        if ("POST".equals(method)) {
            RequestBodyWrapper requestBodyWrapper = new RequestBodyWrapper(httpServletRequest);
            String body = requestBodyWrapper.getBody();
            System.out.println(body);
            try {
                token = JSON.parseObject(body).get("token").toString();
                logger.info("post方法中的token---->" + token);
            }catch (Exception e){
                e.printStackTrace();
            }
        }
        if ("GET".equals(method)) {
            try {
                token = httpServletRequest.getParameter("token");
                logger.info("get方法中的token---->" + token);
            }catch (Exception e){
                e.printStackTrace();
            }

        }
        String url = httpServletRequest.getRequestURI();

        logger.info(url);
        logger.info(method);
        logger.info(token);

        if ("".equals(token)||null == token){
            httpServletResponse.setCharacterEncoding("UTF-8");
            httpServletResponse.setContentType("application/json; charset=utf-8");
            PrintWriter out = null ;
            Result result = null;
            result = ResultUtil.custResult(2000,"当前登录token无效，请重新登录");

            httpServletResponse.setContentType("application/json");
            out = httpServletResponse.getWriter();
            httpServletResponse.setStatus(200);

            // 返回json信息给前端
            out.append(JSONObject.toJSON(result).toString());

            out.flush();
            return false;
        }


        JwtHelper jwtHelper = new JwtHelper();
        String openid = jwtHelper.verifyTokenAndGetUserId(token);

        if (null == openid || "token过期".equals(openid)){
            httpServletResponse.setCharacterEncoding("UTF-8");
            httpServletResponse.setContentType("application/json; charset=utf-8");
            PrintWriter out = null ;
            try{
                Result result = null;
                result = ResultUtil.custResult(2000,"当前登录token无效，请重新登录");

                httpServletResponse.setContentType("application/json");
                out = httpServletResponse.getWriter();
//                httpServletResponse.setStatus(200);
                // 返回json信息给前端
                out.append(result.toString());
                out.flush();
                return false;
            } catch (Exception e){
                e.printStackTrace();
                httpServletResponse.sendError(500);
                return false;
            }
        }
        return true;
    }

    @Override
    public void postHandle(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object o, ModelAndView modelAndView) throws Exception {
//         System.out.println("处理请求完成后视图渲染之前的处理操作");
    }

    @Override
    public void afterCompletion(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object o, Exception e) throws Exception {
//         System.out.println("视图渲染之后的操作");
    }

}

