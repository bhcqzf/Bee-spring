package com.fastyw.bee.service;

import com.fastyw.bee.dao.AddressDao;
import com.fastyw.bee.dao.CityDao;
import com.fastyw.bee.pojo.Address;
import com.fastyw.bee.pojo.City;
import com.fastyw.bee.util.JwtHelper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class CityService {
    @Autowired
    private CityDao cityDao;

    @Autowired
    private AddressDao addressDao;

    //获取所有的省
    public City[] getProvince(){
        return cityDao.selectProvince();
    }
    //根据pid获取城市
    public City[] getChild(Integer pid){
        return cityDao.selectChild(pid);
    }

    //添加一条地址
    public Address addAddress(String token,String linkMan,String address,String mobile,Boolean isDefault,
                              String provinceId,String cityId,String districtId ){
        //这里返回的地址和形参冲突了
        Address addressTmp = null;
        //获取openid
        JwtHelper jwtHelper = new JwtHelper();
        String openid = jwtHelper.verifyTokenAndGetUserId(token);

        //获取县区名
        String areaStr = cityDao.selectCity(Integer.valueOf(districtId));
        //获取市区名
        String cityStr = cityDao.selectCity(Integer.valueOf(cityId));
        //获取省名
        String provinceStr = cityDao.selectCity(Integer.valueOf(provinceId));

        log.info(openid,address,areaStr,districtId,cityStr,cityId,provinceStr,provinceId,isDefault,linkMan,mobile);
        int res = addressDao.insertAddress(openid,address,areaStr,districtId,cityStr,cityId,provinceStr,provinceId,isDefault,linkMan,mobile);

        if (res == 1){
            log.info("插入成功");
            addressTmp = addressDao.selectLastAddress();
        }else{
            log.info("插入失败");
        }
        return addressTmp;
    }
    //获取所有的地址
    public Address[] getAllAddress(String token){
        //获取openid
        JwtHelper jwtHelper = new JwtHelper();
        String openid = jwtHelper.verifyTokenAndGetUserId(token);
        return addressDao.selectAllAddress(openid);
    }
    //删除一条地址
    public Integer deleteOneAddress(String token,Integer id){
        //获取openid
        JwtHelper jwtHelper = new JwtHelper();
        String openid = jwtHelper.verifyTokenAndGetUserId(token);

        return addressDao.deleteOneAddress(openid,id);
    }
    //获取最近的一条默认地址
    public Address selectOneDefaultAddress(String token){
        //获取openid
        JwtHelper jwtHelper = new JwtHelper();
        String openid = jwtHelper.verifyTokenAndGetUserId(token);
        return addressDao.selectDefaultAddress(openid);

    }

    //根据id获取一条地址详情
    public Address selectOneAddressDetail(Integer id){
        return addressDao.selectAddressDetail(id);
    }



}
