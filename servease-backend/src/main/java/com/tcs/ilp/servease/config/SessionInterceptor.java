package com.tcs.ilp.servease.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Component
public class SessionInterceptor implements HandlerInterceptor {

    @Autowired
    private SessionManager sessionManager;

    @Override
    public boolean preHandle(
            HttpServletRequest request,
            HttpServletResponse response,
            Object handler
    ) throws Exception {

        String uri = request.getRequestURI();

        // ✅ ALLOW PUBLIC URLS (VERY IMPORTANT FIX)
        if (uri.equals("/") ||                             // ✅ ROOT
            uri.startsWith("/auth/login") ||               // ✅ LOGIN
            uri.startsWith("/auth/logout") ||              // ✅ LOGOUT

            uri.startsWith("/swagger-ui") ||               // ✅ SWAGGER UI
            uri.startsWith("/v3/api-docs") ||              // ✅ API DOCS
            uri.startsWith("/swagger-ui.html") ||
            uri.startsWith("/webjars") ||                  // ✅ STATIC FILES

            uri.equals("/user")                            // ✅ SIGNUP
        ) {
            return true;
        }

        // ✅ GET sessionId
        String sessionId = request.getHeader("sessionId");

        if (sessionId == null || sessionId.isEmpty()) {
            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            response.getWriter().write("Missing sessionId");
            return false;
        }

        try {
            SessionData session = sessionManager.getSession(sessionId);

            // ✅ attach session
            request.setAttribute("session", session);

        } catch (Exception e) {
            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            response.getWriter().write("Invalid session");
            return false;
        }

        return true;
    }
}
