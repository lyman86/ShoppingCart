package com.ly.shoppingcart.utils;

import android.app.Activity;
import android.util.DisplayMetrics;

public class DialogWindow {
	private int screenWidth;
	private int screenHeight;

	public DialogWindow(Activity activity) {
		DisplayMetrics metric = new DisplayMetrics();
        activity.getWindowManager().getDefaultDisplay().getMetrics(metric);
        
        screenWidth = metric.widthPixels;     // 屏幕宽度（像素）
        screenHeight = metric.heightPixels;   // 屏幕高度（像素）
	}
	
	public int getLoadingDilogWidth(){
		return screenWidth*3/4;
	}
	
	public int getLoadingDialogHeight(){
		return screenHeight/8;
	}
	
	public int getAlertDilogWidth(){
		return screenWidth*3/4;
	}
	
	public int getAlertDialogHeight(){
		return screenHeight/5;
	}
	
	

}
