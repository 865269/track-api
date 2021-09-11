package com.matthew.track.security;

import org.springframework.security.web.RedirectStrategy;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class NoRedirectStrategy implements RedirectStrategy {
    @Override
    public void sendRedirect(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, String s) throws IOException {
        // rest api so no redirect needed
    }
}
