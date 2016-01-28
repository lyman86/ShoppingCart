package com.ly.shoppingcart.utils;
import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.ly.shoppingcart.bean.CustomBean;
import com.ly.shoppingcart.bean.ProCartListBean;

public class JsonUtils {
	static String jsonString = "";
	private static UrlResponse urlResPonse = new UrlResponse();
	
	public static List<ProCartListBean> getProListJson(String url) throws JSONException{
		jsonString = urlResPonse.getURLResponse(url);
		JSONObject jsonObject = new JSONObject(jsonString);
		System.out.println(jsonString);
		ProCartListBean bean;
		List<ProCartListBean>list= new ArrayList<ProCartListBean>();
		if (jsonObject.length()==1) {
			bean = new ProCartListBean();
			bean.code = jsonObject.getString("code");
			bean.info = jsonObject.getString("info");
			list.add(bean);
		}else{
			JSONArray jsonArray = jsonObject.getJSONArray("shopcart");
			for (int i = 0; i < jsonArray.length(); i++) {
				bean = new ProCartListBean();
				jsonObject = jsonArray.getJSONObject(i);
				bean.brand = jsonObject.getString("brand");
				bean.item= jsonObject.getString("item");
				bean.model = jsonObject.getString("model");
				bean.number = Integer.parseInt(jsonObject.getString("number"));
				bean.minbuy = jsonObject.getString("minbuy");
				bean.price = Float.valueOf(jsonObject.getString("price"));
				bean.thumb = jsonObject.getString("thumb");
				bean.title = jsonObject.getString("title");
				bean.isSelect = false;
				bean.changeNumber = false;
				bean.subTotal = bean.price*bean.number;
				list.add(bean);
			}
			
		}
		return list;
	}
	
	
	public static CustomBean getCustomBean(String url) throws JSONException{
		jsonString = urlResPonse.getURLResponse(url);
		final JSONObject jsonObject = new JSONObject(jsonString);
		final CustomBean bean = new CustomBean();
		bean.code = jsonObject.getString("code");
		bean.info = jsonObject.getString("info");
		return bean;
	}
	
}
