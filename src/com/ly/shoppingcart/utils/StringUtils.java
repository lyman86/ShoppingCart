package com.ly.shoppingcart.utils;

public class StringUtils {
	
	public static String getProCarListUrl(String origin,String token){
		StringBuffer result = new StringBuffer(origin);
		String s1 = "token=";
		origin = result.insert(origin.indexOf(s1)+s1.length(),token).toString();
		return origin;
	}
	
	public static String getDeleteFromCartUrl(String origin,String token,String item){
		StringBuffer result = new StringBuffer(origin);
		String s1 = "token=";
		String s2 = "item=";
		origin = result.insert(origin.indexOf(s1)+s1.length(),token).toString();
		origin = result.insert(origin.lastIndexOf(s2)+s2.length(), item).toString();
		return origin;
	}
	
	public static String getUpdateFromCartUrl(String origin,String token,String number,String item){
		StringBuffer result = new StringBuffer(origin);
		String s1 = "token=";
		String s2 = "number=";
		String s3 = "item=";
		origin = result.insert(origin.indexOf(s1)+s1.length(),token).toString();
		origin = result.insert(origin.lastIndexOf(s2)+s2.length(), number).toString();
		origin = result.insert(origin.lastIndexOf(s3)+s3.length(), item).toString();
		return origin;
	}

}
