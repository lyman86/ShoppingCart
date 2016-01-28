package com.ly.shoppingcart.utils;

import java.io.File;
import java.io.FileFilter;
import java.util.regex.Pattern;

import android.util.Log;

public class CpuNum {
	
	public static int getNumCores() {
	    class CpuFilter implements FileFilter {
	        @Override
	        public boolean accept(File pathname) {
	            if(Pattern.matches("cpu[0-9]", pathname.getName())) {
	                return true;
	            }
	            return false;
	        }      
	    }
	    try {
	        File dir = new File("/sys/devices/system/cpu/");
	        File[] files = dir.listFiles(new CpuFilter());
	        return files.length;
	    } catch(Exception e) {
	        e.printStackTrace();
	        return 1;
	    }
	}
}
