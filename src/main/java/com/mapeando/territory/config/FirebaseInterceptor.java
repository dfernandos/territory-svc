package com.mapeando.territory.config;

import com.google.firebase.FirebaseException;
import com.google.firebase.auth.FirebaseAuth;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Component
public class FirebaseInterceptor implements HandlerInterceptor {

    @Override
    public boolean preHandle(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object handler) throws Exception{
        try{
            String idTokenParsed = httpServletRequest.getHeader("Authorization").substring(7);
            FirebaseAuth.getInstance().verifyIdToken(idTokenParsed);
            return true;

        } catch (FirebaseException ex){
            httpServletResponse.setStatus(403);
            return false;
        }
    }
}
