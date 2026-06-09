package com.itheima.filter;

import java.io.IOException;
import java.net.http.HttpRequest;

import org.springframework.http.HttpStatus;
import org.springframework.util.StringUtils;

import com.itheima.utils.CurrentHolder;
import com.itheima.utils.JwtUtils;

import io.jsonwebtoken.Claims;
import jakarta.servlet.Filter;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.ServletRequest;
import jakarta.servlet.ServletResponse;
import jakarta.servlet.annotation.WebFilter;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@WebFilter(urlPatterns = "/*")
public class TokenFilter implements Filter{

	@Override
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
			throws IOException, ServletException {
		// TODO Auto-generated method stub
		
		HttpServletRequest httpServletRequest = (HttpServletRequest)request;
		HttpServletResponse httpServletResponse = (HttpServletResponse)response;
		
		String url = httpServletRequest.getRequestURL().toString();
		
		if(url.contains("login")) {
			log.info("登入請求，直接放行");
			chain.doFilter(httpServletRequest, httpServletResponse);
			return;
		}
		
		String jwt = httpServletRequest.getHeader("token");
		
		if(!StringUtils.hasLength(jwt)) {
			log.info("獲取到jwt令牌為空, 返回错誤结果");
			httpServletResponse.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
			return;
		}
		
		try {
			Claims claims = JwtUtils.parseJWT(jwt);
			Integer empId = Integer.valueOf(claims.get("id").toString());
			CurrentHolder.setCurrentId(empId);
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
			log.info("解析令牌失敗, 返回錯誤结果");
			httpServletResponse.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
			return;
		}
		
		log.info("合法令牌,直接放行");
		chain.doFilter(httpServletRequest, httpServletResponse);
		
		//7. 清空當前綫程綁定的id
		CurrentHolder.remove();
	}

}
