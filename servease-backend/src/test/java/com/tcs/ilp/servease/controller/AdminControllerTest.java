package com.tcs.ilp.servease.controller;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import java.util.List;

import jakarta.servlet.http.HttpServletRequest;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.tcs.ilp.servease.bo.AdminBO;
import com.tcs.ilp.servease.config.Role;
import com.tcs.ilp.servease.config.SessionData;
import com.tcs.ilp.servease.entity.Admin;

@ExtendWith(MockitoExtension.class)   // ✅ FIX
class AdminControllerTest {

    @Mock
    private AdminBO adminBO;

    @Mock
    private HttpServletRequest request;

    @InjectMocks
    private AdminController adminController;

    @BeforeEach
    void setup() {

        // ✅ Mock session
        SessionData session = new SessionData();
        session.setUserId("admin1");
        session.setRole(Role.ADMIN);

        when(request.getAttribute("session")).thenReturn(session);
    }

    // ✅ ADD ADMIN
    @Test
    void addAdmin_success() {

        Admin admin = new Admin("a1", "u14");

        String result = adminController.addAdmin(admin, request);

        assertEquals("Admin added successfully", result);
        verify(adminBO, times(1)).addAdmin(admin);
    }

    // ✅ GET ADMIN
    @Test
    void getAdmin_success() {

        Admin admin = new Admin("a1", "u14");

        when(adminBO.getAdminById("a1")).thenReturn(admin);

        Object result = adminController.getAdminById("a1", request);

        assertTrue(result instanceof Admin);
        assertEquals("a1", ((Admin) result).getAdminId());
    }

    // ✅ DELETE ADMIN
    @Test
    void deleteAdmin_success() {

        doNothing().when(adminBO).deleteAdmin("a1");

        String result = adminController.deleteAdmin("a1", request);

        assertEquals("Admin deleted successfully", result);
        verify(adminBO, times(1)).deleteAdmin("a1");
    }

    // ✅ GET ALL ADMINS
    @Test
    void getAllAdmins_success() {

        Admin admin = new Admin("a1", "u14");

        when(adminBO.getAllAdmins()).thenReturn(List.of(admin));

        Object result = adminController.getAllAdmins(request);

        assertTrue(result instanceof List);
        assertEquals(1, ((List<?>) result).size());
    }
}
