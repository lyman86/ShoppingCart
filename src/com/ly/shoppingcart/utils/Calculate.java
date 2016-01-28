package com.ly.shoppingcart.utils;

import java.math.BigDecimal;

public class Calculate {
	
	public static float decimaAdd(float x,float y){
		BigDecimal b1 = new BigDecimal(String.valueOf(x));  
		BigDecimal b2 = new BigDecimal(String.valueOf(y)); 
		return b1.add(b2).floatValue();  
	}
	
	public static float decimaSub(float x,float y){
		BigDecimal b1 = new BigDecimal(String.valueOf(x));  
		BigDecimal b2 = new BigDecimal(String.valueOf(y)); 
		return b1.subtract(b2).floatValue();  
	}

}
