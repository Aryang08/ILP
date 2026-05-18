package com.tcs.ilp.servease.controller;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.Test;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import com.fasterxml.jackson.databind.ObjectMapper;

import com.tcs.ilp.servease.entity.ServiceDiagnostics;
import com.tcs.ilp.servease.bo.ServiceDiagnosticsBO;
import com.tcs.ilp.servease.repository.ServiceDiagnosticsRepository;

import com.tcs.ilp.servease.config.SessionManager;
import com.tcs.ilp.servease.config.SessionData;
import com.tcs.ilp.servease.config.Role;

@SpringBootTest
@AutoConfigureMockMvc(addFilters = false)
class ServiceDiagnosticsTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ServiceDiagnosticsBO service;

    @MockBean
    private ServiceDiagnosticsRepository repository;

    @MockBean
    private SessionManager sessionManager;

    @Autowired
    private ObjectMapper objectMapper;

    private static final String SESSION_ID = "testSession";

    // ===================== SERVICE TESTS =====================

    @Test
    void addServiceDiagnostics_shouldSave_whenNotExists() {
        ServiceDiagnostics diagnostics =
                new ServiceDiagnostics("D1", "S1", "Q1", "O1");

        when(repository.existsById("D1")).thenReturn(false);
        when(repository.save(diagnostics)).thenReturn(diagnostics);

        service.addServiceDiagnostics(diagnostics);

        verify(repository).save(diagnostics);
    }

    @Test
    void getServiceDiagnosticsById_shouldReturnEntity() {
        ServiceDiagnostics diagnostics =
                new ServiceDiagnostics("D1", "S1", "Q1", "O1");

        when(repository.findById("D1"))
                .thenReturn(Optional.of(diagnostics));

        ServiceDiagnostics result =
                service.getServiceDiagnosticsById("D1");

        assertNotNull(result);
    }

    @Test
    void deleteServiceDiagnostics_shouldCallRepository() {
        service.deleteServiceDiagnostics("D1");
        verify(repository).deleteById("D1");
    }

    // ===================== CONTROLLER TESTS =====================

    @Test
    void addServiceDiagnostic_controller_shouldReturnCreated() throws Exception {

        // ✅ CORRECT ROLE
        SessionData session = new SessionData();
        session.setUserId("U101");
        session.setRole(Role.CUSTOMER);   // ✅ REQUIRED

        when(sessionManager.getSession(SESSION_ID)).thenReturn(session);

        ServiceDiagnostics diagnostics =
                new ServiceDiagnostics("D1", "S1", "Q1", "O1");

        when(repository.existsById("D1")).thenReturn(false);
        when(repository.save(any(ServiceDiagnostics.class)))
                .thenReturn(diagnostics);

        mockMvc.perform(post("/api/service-diagnostics")
                .header("sessionId", SESSION_ID)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(diagnostics)))
                .andExpect(status().isCreated())
                .andExpect(content().string("Service diagnostic added successfully"));
    }

    @Test
    void getByDiagnosticId_controller_shouldReturn200() throws Exception {

        // ✅ ANY USER → ADMIN OK
        SessionData session = new SessionData();
        session.setUserId("U101");
        session.setRole(Role.ADMIN);

        when(sessionManager.getSession(SESSION_ID)).thenReturn(session);

        ServiceDiagnostics diagnostics =
                new ServiceDiagnostics("D1", "S1", "Q1", "O1");

        when(repository.findById("D1"))
                .thenReturn(Optional.of(diagnostics));

        mockMvc.perform(get("/api/service-diagnostics/D1")
                .header("sessionId", SESSION_ID))
                .andExpect(status().isOk());
    }

    @Test
    void getPositiveAnswers_controller_shouldReturnList() throws Exception {

        // ✅ REQUIRED ROLE
        SessionData session = new SessionData();
        session.setUserId("U101");
        session.setRole(Role.ADMIN);

        when(sessionManager.getSession(SESSION_ID)).thenReturn(session);

        List<ServiceDiagnostics> list =
                List.of(new ServiceDiagnostics("D1", "S1", "Q1", "O1")); // ✅ FIXED

        when(repository.findByServiceId("S1")).thenReturn(list);

        mockMvc.perform(get("/api/service-diagnostics/positive/S1")
                .header("sessionId", SESSION_ID))
                .andExpect(status().isOk());
    }
}