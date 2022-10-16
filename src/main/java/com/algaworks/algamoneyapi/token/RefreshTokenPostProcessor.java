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

    @Override
    public DefaultOAuth2AccessToken beforeBodyWrite(DefaultOAuth2AccessToken body, MethodParameter returnType, MediaType selectedContentType, Class<? extends HttpMessageConverter<?>> selectedConverterType, ServerHttpRequest request, ServerHttpResponse response) {
        HttpServletRequest req = ((ServletServerHttpRequest) request).getServletRequest();
        HttpServletResponse resp = ((ServletServerHttpResponse) response).getServletResponse();

        //DefaultOAuth2AccessToken token = (DefaultOAuth2AccessToken) body;

        String refreshToken = body.getRefreshToken().getValue();
        adicionarRefreshTokenDoCookie(refreshToken,req,resp);
        removerAccessTokenDoBody(body);
        return body;
    }

    private void removerAccessTokenDoBody(DefaultOAuth2AccessToken token) {
        token.setRefreshToken(null);
    }

    private void adicionarRefreshTokenDoCookie(String refreshToken, HttpServletRequest req, HttpServletResponse resp) {
        Cookie refreshTokenCookie = new Cookie("refreshToken",refreshToken);
        refreshTokenCookie.setHttpOnly(true);
        refreshTokenCookie.setSecure(false);
        refreshTokenCookie.setMaxAge(2592000);
        refreshTokenCookie.setPath(req.getContextPath()+"/oauth/token");
        resp.addCookie(refreshTokenCookie);
    }
}
