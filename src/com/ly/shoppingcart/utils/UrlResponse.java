package com.ly.shoppingcart.utils;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

public class UrlResponse {
	/**
	 * 获取指定URL的响应字符串
	 * 
	 * @param urlString
	 * @return
	 */
	public String getURLResponse(String urlString) {
		HttpURLConnection conn = null; // 连接对象
		InputStream is = null;
		StringBuffer stringBuffer = new StringBuffer();
		try {
			URL url = new URL(urlString); // URL对象
			conn = (HttpURLConnection) url.openConnection(); // 使用URL打开一个链接
			conn.setDoInput(true); // 允许输入流，即允许下载
			conn.setDoOutput(true); // 允许输出流，即允许上传
			conn.setUseCaches(false); // 不使用缓冲
			conn.setRequestMethod("POST"); 
			conn.setConnectTimeout(2000);
			conn.setReadTimeout(2000);
			conn.connect();
		    	   is = conn.getInputStream(); // 获取输入流，此时才真正建立链接
		    	   InputStreamReader isr = new InputStreamReader(is,"utf-8");
					BufferedReader bufferReader = new BufferedReader(isr);
					String inputLine = "";
					while ((inputLine = bufferReader.readLine()) != null) {
						stringBuffer.append(inputLine);
					}

		} catch (MalformedURLException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			if (is != null) {
				try {
					is.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
			if (conn != null) {
				conn.disconnect();
			}
		}

		return stringBuffer.toString();
	}

}
