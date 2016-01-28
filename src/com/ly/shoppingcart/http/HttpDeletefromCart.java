package com.ly.shoppingcart.http;


import android.os.Message;

import com.ly.shoppingcart.bean.CustomBean;
import com.ly.shoppingcart.config.FlagConfig;
import com.ly.shoppingcart.utils.JsonUtils;

public class HttpDeletefromCart extends BaseLoad {

	public HttpDeletefromCart(String url) {
		super(url);
	}

	@Override
	public void handleMyMessage(Message msg) {
		CustomBean result = (CustomBean) msg.obj;
		if (onLoadListener != null) {
			if (result!=null) {
				if (result.code.equals("success")) {
					onLoadListener.onLoadSuccess(result.info, FlagConfig.PRO_DELETE);
				} else {
					onLoadListener.onLoadFailed(result.info);
				}
			}else{
				onLoadListener.onLoadFailed("网络连接超时");
			}
			
		}
	}

	@Override
	public void load() {
		CustomBean result = null;
		try {
		  result = JsonUtils.getCustomBean(url);
		} catch (Exception e) {
			e.printStackTrace();
		}
		Message message = Message.obtain();
		message.obj = result;
		mHandler.sendMessage(message);

	}

}
