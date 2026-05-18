package com.tcs.ilp.servease.util;

import com.tcs.ilp.servease.config.SessionData;
import com.tcs.ilp.servease.config.Role;

import jakarta.servlet.http.HttpServletRequest;

public class AuthUtil {

    // ✅ GET SESSION FROM REQUEST
    public static SessionData getSession(HttpServletRequest request) {

        SessionData session =
                (SessionData) request.getAttribute("session");

        if (session == null) {
            throw new RuntimeException("Invalid session");
        }

        return session;
    }

    // ✅ ROLE CHECKS (ENABLE THESE NOW ✅)

    public static void checkSupervisor(SessionData session) {
        if (session.getRole() != Role.SUPERVISOR) {
            throw new RuntimeException("Access Denied");
        }
    }

    public static void checkAdmin(SessionData session) {
        if (session.getRole() != Role.ADMIN) {
            throw new RuntimeException("Access Denied");
        }
    }

    public static void checkTechnician(SessionData session) {
        if (session.getRole() != Role.TECHNICIAN) {
            throw new RuntimeException("Access Denied");
        }
    }

    public static void checkCustomer(SessionData session) {
        if (session.getRole() != Role.CUSTOMER) {
            throw new RuntimeException("Access Denied");
        }
    }
}