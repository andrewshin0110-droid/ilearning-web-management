package com.itheima.Interceptor;

import com.itheima.utils.JwtUtils;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.servlet.HandlerInterceptor;

@Slf4j
@Component
public class TokenInterceptor implements HandlerInterceptor {
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler){
        //1. 獲取请求url。
        String url = request.getRequestURL().toString();

        //2. 判斷請求url中是否包含login，如果包含，說明是登錄操作，放行。
        if(url.contains("login")){ //登錄请求
            log.info("登錄请求 , 直接放行");
            return true;
        }

        //3. 獲取請求頭中的令牌（token）。
        String jwt = request.getHeader("token");

        //4. 判断令牌是否存在，如果不存在，返回錯誤结果（未登錄）。
        if(!StringUtils.hasLength(jwt)){ //jwt為空
            log.info("獲取到jwt令牌為空, 返回錯誤结果");
            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            return false;
        }

        //5. 解析token，如果解析失敗，返回錯誤结果（未登錄）。
        try {
            JwtUtils.parseJWT(jwt);
        } catch (Exception e) {
            //會把異常堆疊直接印在伺服器的標準輸出（Console），這非常消耗記憶體與 CPU 效能，在高併發時甚至可能導致伺服器卡死。此外，這也是資安漏洞，萬一報錯訊息直接流向前端，會暴露後端資料庫結構。
            //e.printStackTrace();
            //業界標準防禦寫法：改使用 log.error 記錄異常
            log.error("Token 解析失敗，非法請求！錯誤訊息: ", e);
            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            return false;
        }

        //6. 放行。
        log.info("令牌合法, 放行");
        return true;
    }

}