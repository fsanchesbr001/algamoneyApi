package com.algaworks.algamoneyapi.token;

import org.springframework.core.MethodParameter;
import org.springframework.http.MediaType;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.server.ServerHttpRequest;
import org.springframework.http.server.ServerHttpResponse;
import org.springframework.http.server.ServletServerHttpRequest;
import org.springframework.http.server.ServletServerHttpResponse;
import org.springframework.security.oauth2.common.DefaultOAuth2AccessToken;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.ResponseBodyAdvice;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@ControllerAdvice
public class RefreshTokenPostProcessor implements ResponseBodyAdvice<DefaultOAuth2AccessToken> {

    @Override
    public boolean supports(MethodParameter returnType, Class<? extends HttpMessageConverter<?>> converterType) {
        return returnType.getMethod().getName().equalsIgnoreCase("postAccessToken");
    }

    public DefaultOAuth2AccessToken beforeBodyWrite(DefaultOAuth2AccessToken body, MethodParameter returnType,
                                                    MediaType selectedContentType, Class<? extends HttpMessageConverter<?>> selectedConverterType,
                                                    ServerHttpRequest request, ServerHttpResponse response) {
        String refreshToken = body.getRefreshToken().getValue();
        HttpServletRequest req = ((ServletServerHttpRequest) request).getServletRequest();
        HttpServletResponse resp = ((ServletServerHttpResponse) response).getServletResponse();

        adicionarRefreshTokenNoCookie(refreshToken,req,resp);
        removerRefreshTokenDoBody(body);
        return body;
    }

    private void removerRefreshTokenDoBody(DefaultOAuth2AccessToken body) {
        body.setRefreshToken(null);
    }

    private void adicionarRefreshTokenNoCookie(String refreshToken, HttpServletRequest req,
                                               HttpServletResponse resp) {
        Cookie refreshTokenCookie = new Cookie("refreshToken",refreshToken);
        refreshTokenCookie.setHttpOnly(true);
        refreshTokenCookie.setSecure(false); // Mudar para true em Prod
        refreshTokenCookie.setPath(req.getContextPath() + "oauth/token");
        refreshTokenCookie.setMaxAge(3600*24*30);
        resp.addCookie(refreshTokenCookie);
    }
}
