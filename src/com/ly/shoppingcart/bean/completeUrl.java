package com.ly.shoppingcart.bean;

import com.ly.shoppingcart.config.BaseUrl;
import com.ly.shoppingcart.config.FlagConfig;
import com.ly.shoppingcart.utils.StringUtils;

public class completeUrl {
		public static String getProCartUrl(){
			return StringUtils.getProCarListUrl(BaseUrl.EHSY_ADD_PROCART_LIST_URL,"58e767f9fb72c44e68922d7033cca5e5");
		}
		public static String getUpdateUrl(int number,String item){
			return StringUtils.getUpdateFromCartUrl(BaseUrl.EHSY_UPDATE_PRO_FROM_CART_URL,"58e767f9fb72c44e68922d7033cca5e5",String.valueOf(number),item);
		}
		public static String getDeleteUrl(String item){
			return StringUtils.getDeleteFromCartUrl(BaseUrl.EHSY_DELETE_PRO_FROM_CART_URL,"58e767f9fb72c44e68922d7033cca5e5",item);
		}

}
