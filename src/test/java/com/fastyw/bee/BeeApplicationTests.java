package com.fastyw.bee;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.fastyw.bee.dao.GoodDao;
import com.fastyw.bee.dao.ShopDao;
import com.fastyw.bee.pojo.Category;
import com.fastyw.bee.pojo.Good;
import com.fastyw.bee.util.GeoUtil;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.HashMap;
import java.util.Map;

@SpringBootTest
class BeeApplicationTests {

	@Test
	void contextLoads() {
	}

	@Test
	void Test1(){
		String a = "{\"openId\":\"oBBMC50UtV1Ya2ZARnrPDOLl49uw\",\"nickName\":\"微信用户\",\"gender\":0,\"language\":\"\",\"city\":\"\",\"province\":\"\",\"country\":\"\",\"avatarUrl\":\"https://thirdwx.qlogo.cn/mmopen/vi_32/POgEwh4mIHO4nibH0KlMECNjjGxQUq24ZEaGT4poC6icRiccVGKSyXwibcPq4BWmiaIGuG1icwxaQX6grC9VemZoJ8rg/132\",\"watermark\":{\"timestamp\":1674028173,\"appid\":\"wx9e3b9ab1a89341b6\"}}";
		JSONObject jsonUserinfo = JSON.parseObject(a);
		System.out.println(a);
		System.out.println(jsonUserinfo);
	}
	@Autowired
	private ShopDao shopDao;
	@Test
	void Test2(){
		Map<String,Object> map = new HashMap<>();

		Category[] categories = shopDao.selectCategory();
//		for (Category category:categories
//		) {
//			System.out.println(category);
//		}
		map.put("data",categories);
		System.out.println(new JSONObject(map));
	}

	@Autowired
	private GoodDao goodDao;
	@Test
	void Test03(){
		Good[] goods =  goodDao.selectGood(1);
		for (Good good:
			 goods) {
			System.out.println(good);
		}
	}

	@Test
	void Test04(){
		Map<String,Object> map = new HashMap<>();
		map.put("msg","hello");
		System.out.println(map.containsKey("type"));
		System.out.println(map.containsKey("msg"));
	}
	@Test
	void Test05(){
//		JSONObject jsonObject =  JSON.parseObject();
//		JSONObject json=(JSONObject)JSONObject.toJSON(JSON.parse("[{\"goodsId\":1314941,\"number\":1,\"logisticsType\":0,\"inviter_id\":0},{\"goodsId\":1314946,\"number\":5,\"logisticsType\":0,\"inviter_id\":0}]"));
		JSONArray temp=JSONArray.parseArray("[{\"goodsId\":1314941,\"number\":1,\"logisticsType\":0,\"inviter_id\":0},{\"goodsId\":1314946,\"number\":5,\"logisticsType\":0,\"inviter_id\":0}]");
		for(int i=0;i<temp.size();i++){
			JSONObject obj=(JSONObject)temp.get(i);
			System.out.println(obj.getString("goodsId"));
			System.out.println(obj.getString("number"));
		}
		System.out.println(temp);
	}

	@Test
	void Test06(){
		//经纬度测距
		//经纬度1
		Double lat1 = 38.057178;
		Double lon1 = 114.353339;

		//经纬度2
		Double lat2 = 38.057739;
		Double lon2 = 114.362965;

		System.out.println(GeoUtil.GetDistance(lon2, lat2, lon1, lat1));

	}

}
