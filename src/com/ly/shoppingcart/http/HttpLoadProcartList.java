package com.ly.shoppingcart.http;

import java.util.ArrayList;
import java.util.List;
import org.json.JSONException;
import android.os.Message;
import com.ly.shoppingcart.bean.ProCartListBean;
import com.ly.shoppingcart.config.FlagConfig;
import com.ly.shoppingcart.utils.JsonUtils;

public class HttpLoadProcartList extends BaseLoad {
	
	public HttpLoadProcartList(String url) {
		super(url);
	}

	@Override
	public void handleMyMessage(Message msg) {
		List<ProCartListBean> result = (List<ProCartListBean>) msg.obj;
		if (onLoadListener != null) {
			if (result==null) {
					onLoadListener.onLoadFailed("网络连接超时");
			}else{
				onLoadListener.onLoadSuccess(result, FlagConfig.CART_LIST);
			}
			
		}

	}

	@Override
	public void load() {
		List<ProCartListBean> result = null;
		try {
			result = JsonUtils.getProListJson(url);
		} catch (JSONException e) {
			e.printStackTrace();
		}
		Message message = Message.obtain();
		message.obj = result;
		mHandler.sendMessage(message);

	}

}
