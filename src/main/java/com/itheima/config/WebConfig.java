package com.itheima.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import com.itheima.Interceptor.TokenInterceptor;

@Configuration
public class WebConfig implements WebMvcConfigurer {
    //攔截器對象
    @Autowired
    private TokenInterceptor tokenInterceptor;

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
       //註冊自定義攔截器對象
        registry.addInterceptor(tokenInterceptor).addPathPatterns("/**");
    }

    // 主動配置全球 CORS 跨域放行
    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/**")                         // 允許後端所有的 API 路徑
                .allowedOrigins("*")                        // 🌐 萬用字元 * 允許來自全球任何前端網域的請求
                .allowedMethods("GET", "POST", "PUT", "DELETE", "OPTIONS") // 放行標準的 RESTful 請求方法
                .allowedHeaders("*")                        // 允許前端攜帶任何自定義的請求頭（如 Authorization）
                .maxAge(3600);                              // 預檢請求（Preflight）的快取時間設定為 1 小時
    }
}
