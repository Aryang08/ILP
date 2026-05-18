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

import com.tcs.ilp.servease.bo.ServiceMainBO;
import com.tcs.ilp.servease.config.Role;
import com.tcs.ilp.servease.config.SessionData;
import com.tcs.ilp.servease.entity.ServiceMain;

@ExtendWith(MockitoExtension.class)   // ✅ FIX
class ServiceControllerTest {

    @Mock
    private ServiceMainBO serviceMainBO;

    @Mock
    private HttpServletRequest request;

    @InjectMocks
    private ServiceMainController serviceMainController;

    @BeforeEach
    void setup() {

        // ✅ Default session (CUSTOMER for addService)
        SessionData session = new SessionData();
        session.setUserId("cust1");
        session.setRole(Role.CUSTOMER);

        when(request.getAttribute("session")).thenReturn(session);
    }

    // ✅ 1. ADD SERVICE
    @Test
    void addService_success() {

        ServiceMain s = new ServiceMain();
        s.setServiceId("s1");
        s.setCustomerId("c1");
        s.setStatus("PENDING");

        when(serviceMainBO.addService(s)).thenReturn(s);

        String result = serviceMainController.addService(s, request);

        assertEquals("Service added successfully", result);
        verify(serviceMainBO).addService(s);
    }

    // ✅ 2. GET SERVICE BY ID
    @Test
    void getService_success() {

        ServiceMain s = new ServiceMain();
        s.setServiceId("s1");

        when(serviceMainBO.getServiceById("s1")).thenReturn(s);

        ServiceMain result =
                serviceMainController.getServiceById("s1", request);

        assertNotNull(result);
        assertEquals("s1", result.getServiceId());
    }

    // ✅ 3. UPDATE STATUS (SUPERVISOR ONLY)
    @Test
    void updateServiceStatus_success() {

        SessionData session = new SessionData();
        session.setRole(Role.SUPERVISOR);
        when(request.getAttribute("session")).thenReturn(session);

        ServiceMain s = new ServiceMain();
        s.setServiceId("s1");
        s.setStatus("COMPLETED");

        when(serviceMainBO.updateServiceStatus("s1", "COMPLETED"))
                .thenReturn(s);

        String result =
                serviceMainController.updateServiceStatus("s1", "COMPLETED", request);

        assertEquals("Service status updated successfully", result);
        verify(serviceMainBO).updateServiceStatus("s1", "COMPLETED");
    }

    // ✅ 4. DELETE SERVICE (ADMIN ONLY)
    @Test
    void deleteService_success() {

        SessionData session = new SessionData();
        session.setRole(Role.ADMIN);
        when(request.getAttribute("session")).thenReturn(session);

        doNothing().when(serviceMainBO).deleteService("s1");

        String result =
                serviceMainController.deleteService("s1", request);

        assertEquals("Service deleted successfully", result);
        verify(serviceMainBO).deleteService("s1");
    }

    // ✅ 5. GET ALL SERVICES
    @Test
    void getAllServices_success() {

        SessionData session = new SessionData();
        session.setRole(Role.ADMIN);
        when(request.getAttribute("session")).thenReturn(session);

        ServiceMain s = new ServiceMain();
        s.setServiceId("s1");

        when(serviceMainBO.getAllServices()).thenReturn(List.of(s));

        List<ServiceMain> list =
                serviceMainController.getAllServices(request);

        assertEquals(1, list.size());
    }
}