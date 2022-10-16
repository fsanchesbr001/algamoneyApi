package com.algaworks.algamoneyapi.token;

import org.apache.catalina.util.ParameterMap;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import javax.servlet.*;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletRequestWrapper;
import java.io.IOException;
import java.util.Map;

@Component
@Order(Ordered.HIGHEST_PRECEDENCE)
public class RefreshTokenCookiePreProcessorFilter implements Filter {
    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain)
            throws IOException, ServletException {
        HttpServletRequest req = (HttpServletRequest) servletRequest;
        if(req.getRequestURI().equalsIgnoreCase("/oauth/token")
                && req.getParameter("grant_type").equalsIgnoreCase("refresh_token")
                && req.getCookies()!=null){
            for(Cookie cookie:req.getCookies()){
                if(cookie.getName().equalsIgnoreCase("refreshToken")){
                    String refreshToken = cookie.getValue();
                    req = new MyServletRequestWrapper(req,refreshToken);
                }
            }
        }
        filterChain.doFilter(req,servletResponse);
    }

    static class MyServletRequestWrapper extends HttpServletRequestWrapper{
        private String refereshToken;
        public MyServletRequestWrapper(HttpServletRequest request, String refereshToken) {
            super(request);
            this.refereshToken = refereshToken;

        }

        @Override
        public Map<String, String[]> getParameterMap() {
            ParameterMap<String,String[]> map = new ParameterMap<>(getRequest().getParameterMap());
            map.put("refresh_token",new String[] { refereshToken });
            map.setLocked(true);
            return map;
        }
    }
}
