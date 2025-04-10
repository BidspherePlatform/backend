package com.bidsphere.bidsphere.services;

import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

@Component
public class Tokenization {
    public static String getToken() {
        ServletRequestAttributes requestAttributes = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes());

        if (requestAttributes == null) {
            return "";
        }

        String authorization = requestAttributes.getRequest().getHeader("Authorization");

        if (authorization == null) {
            return "";
        }

        return authorization.split(" ")[1];
    }
}