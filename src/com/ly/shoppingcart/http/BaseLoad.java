package com.ly.shoppingcart.http;
import com.ly.shoppingcart.myinterface.OnLoadListener;

import android.os.Handler;

public abstract class BaseLoad extends Thread {
	protected OnLoadListener onLoadListener;
	public String url;
	public BaseLoad(String url) {
		this.url = url;
	}
	public void setOnLoadListener(OnLoadListener onLoadListener){
			this.onLoadListener = onLoadListener;
	}
	
	public Handler mHandler = new Handler(){
		public void handleMessage(android.os.Message msg) {
			handleMyMessage(msg);
		};
	};
	public abstract void handleMyMessage(android.os.Message msg);
	public abstract void load();
	@Override
	public void run() {
		load();
	}
	
	
}
