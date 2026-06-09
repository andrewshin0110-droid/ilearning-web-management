package com.itheima.utils;

public class CurrentHolder {

	private static final ThreadLocal<Integer> CURRENT_LOCAL = new ThreadLocal<Integer>();
	
	// 設置當前綫程的线程局部變量的值
	public static void setCurrentId(Integer employeeId) {
		CURRENT_LOCAL.set(employeeId);
	}
	// 返回当前綫程所對應的线程局部變量的值
	public static Integer getCurrentId() {
		return CURRENT_LOCAL.get();
	}
	// 移除当前綫程的线程局部變量
	public static void remove() {
		CURRENT_LOCAL.remove();
	}
	
	
}
