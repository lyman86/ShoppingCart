package com.ly.shoppingcart.utils;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class MyThreadPool {
	private static MyThreadPool instance;
	private static ExecutorService executorService;

	public static synchronized MyThreadPool getInstance() {
		if (instance == null) {
			synchronized (MyThreadPool.class) {
				if (instance == null) {
					instance = new MyThreadPool();
					generateThreadPool();
				}
			}
		}
		return instance;
	}

	private static void generateThreadPool() {
		executorService = Executors.newFixedThreadPool(CpuNum.getNumCores()*2);
	}

	public MyThreadPool ExecuteMyThread(Thread task) {
		executorService.execute(task);
		return this;
	}

}
