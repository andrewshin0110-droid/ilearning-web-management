//package com.itheima.Interceptor;
//
//import org.springframework.stereotype.Component;
//import org.springframework.web.servlet.HandlerInterceptor;
//import org.springframework.web.servlet.ModelAndView;
//
//import jakarta.servlet.http.HttpServletRequest;
//import jakarta.servlet.http.HttpServletResponse;
//import lombok.extern.slf4j.Slf4j;
//
//@Component
//@Slf4j
//public class DemoInterceptor implements HandlerInterceptor{
//
//	 @Override
//	    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
//	        log.info("preHandle .... ");
//	        
//	        return false; //true表示放行
//	    }
//
//	    //目標資源執行後執行
//	    @Override
//	    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {
//	        log.info("postHandle ... ");
//	    }
//
//	    //視圖渲覽完成後執行，最後執行
//	    @Override
//	    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {
//	        log.info("afterCompletion .... ");
//	    }
//}
