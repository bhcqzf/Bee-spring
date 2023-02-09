package com.fastyw.bee.service;

import com.fastyw.bee.dao.ShopDao;
import com.fastyw.bee.pojo.Shop;
import com.fastyw.bee.util.GeoUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;

@Service
public class ShopService {
    @Autowired
    private ShopDao shopDao;

    public ArrayList getShop(Double curlatitude,Double curlongitude  ){
        Shop[] shops = shopDao.selectAllShop();
        ArrayList shopArray = new ArrayList();
        for (Shop shop:
             shops) {
            Double lon1 = shop.getLongitude();
            Double lat1 = shop.getLatitude();
            shop.setDistance(GeoUtil.GetDistance(lon1,lat1,curlongitude,curlatitude));
            shopArray.add(shop);
        }
        return shopArray;
    }

    public Shop getAShop(Integer id){
        return shopDao.selectAShop(id);
    }
}
