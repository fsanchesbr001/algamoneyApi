package com.algaworks.algamoneyapi.controller;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@RestController
@RequestMapping("/tokens")
public class TokenController {
    @DeleteMapping("/revoke")
    public void revoke(HttpServletRequest request, HttpServletResponse response){
        Cookie cookie = new Cookie("refreshToken",null);
        cookie.setHttpOnly(true);
        cookie.setSecure(false); //true em Prod
        cookie.setPath(request.getContextPath() + "/oauth/token");
        cookie.setMaxAge(0);

        response.addCookie(cookie);
        response.setStatus(HttpStatus.NO_CONTENT.value());

    }
}
