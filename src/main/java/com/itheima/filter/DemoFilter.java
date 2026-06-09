//package com.itheima.filter;
//
//import java.io.IOException;
//
//import jakarta.servlet.Filter;
//import jakarta.servlet.FilterChain;
//import jakarta.servlet.FilterConfig;
//import jakarta.servlet.ServletException;
//import jakarta.servlet.ServletRequest;
//import jakarta.servlet.ServletResponse;
//import jakarta.servlet.annotation.WebFilter;
//import lombok.extern.slf4j.Slf4j;
//
//@Slf4j
//@WebFilter(urlPatterns = "/*") //配置过滤器要拦截的请求路径（ /* 表示拦截浏览器的所有请求 ）
//public class DemoFilter implements Filter {
//
//	//初始化方法, web服务器启动, 创建Filter实例时调用, 只调用一次
//    public void init(FilterConfig filterConfig) throws ServletException {
//        log.info("init ...");
//        
//    }
//
//	@Override
//	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
//			throws IOException, ServletException {
//		log.info("拦截到了请求...");
//		
//	}
//	
//	 //销毁方法, web服务器关闭时调用, 只调用一次
//    public void destroy() {
//        log.info("destroy ... ");
//    }
//
//}
