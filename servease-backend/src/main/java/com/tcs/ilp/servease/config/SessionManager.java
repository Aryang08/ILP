package com.tcs.ilp.servease.config;

import org.springframework.stereotype.Component;

import java.util.Map;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

@Component
public class SessionManager {

    private final Map<String, SessionData> sessions = new ConcurrentHashMap<>();

    // ✅ CREATE SESSION
    public String createSession(String userId, Role role) {

        String sessionId = UUID.randomUUID().toString();
        sessions.put(sessionId, new SessionData(userId, role));
        return sessionId;
    }
    

    // ✅ HELPER METHOD
    public SessionData getSession(String sessionId) {

        SessionData session = sessions.get(sessionId);

        if (session == null) {
            throw new RuntimeException("Invalid session");
        }

        return session;
    }

    // ✅ INVALIDATE
    public void invalidateSession(String sessionId) {
        sessions.remove(sessionId);
    }
}