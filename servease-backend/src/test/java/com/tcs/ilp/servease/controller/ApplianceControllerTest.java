package com.tcs.ilp.servease.controller;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import java.time.LocalDate;
import java.util.*;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.tcs.ilp.servease.bo.ApplianceBO;
import com.tcs.ilp.servease.controller.ApplianceController;
import com.tcs.ilp.servease.dto.ApplianceDTO;
import com.tcs.ilp.servease.config.Role;
import com.tcs.ilp.servease.config.SessionData;
import com.tcs.ilp.servease.config.SessionManager;
import com.tcs.ilp.servease.config.SessionInterceptor;

import org.junit.jupiter.api.Test;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

@WebMvcTest(ApplianceController.class)
@AutoConfigureMockMvc(addFilters = false)
public class ApplianceControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private ApplianceBO applianceBO;

    // ✅ REQUIRED
    @MockBean
    private SessionManager sessionManager;

    @MockBean
    private SessionInterceptor sessionInterceptor;

    @Autowired
    private ObjectMapper objectMapper;

    // ✅ SESSION MOCK
    private SessionData getMockSession() {
        SessionData session = new SessionData();
        session.setUserId("admin1");
        session.setRole(Role.ADMIN);
        return session;
    }

    private ApplianceDTO getDTO() {
        ApplianceDTO dto = new ApplianceDTO();
        dto.setApplianceId("A1");
        dto.setCustomerId("C1");
        dto.setName("TV");
        return dto;
    }

    @Test
    void testGetAllAppliances() throws Exception {
        when(applianceBO.getAllAppliances())
                .thenReturn(List.of(getDTO()));

        mockMvc.perform(get("/appliance/all")
                .requestAttr("session", getMockSession()))
                .andExpect(status().isOk());
    }

    @Test
    void testGetByIdSuccess() throws Exception {
        when(applianceBO.getApplianceById("A1"))
                .thenReturn(getDTO());

        mockMvc.perform(get("/appliance/A1")
                .requestAttr("session", getMockSession()))
                .andExpect(status().isOk());
    }

    @Test
    void testDeleteAppliance() throws Exception {
        doNothing().when(applianceBO).deleteAppliance("A1");

        mockMvc.perform(delete("/appliance/A1")
                .requestAttr("session", getMockSession()))
                .andExpect(status().isOk());
    }

    @Test
    void testGetTotal() throws Exception {
        when(applianceBO.getTotalAppliances()).thenReturn(5);

        mockMvc.perform(get("/appliance/stats/total")
                .requestAttr("session", getMockSession()))
                .andExpect(status().isOk());
    }

    @Test
    void testGetInWarranty() throws Exception {
        when(applianceBO.getInWarrantyCount()).thenReturn(3L);

        mockMvc.perform(get("/appliance/stats/in-warranty")
                .requestAttr("session", getMockSession()))
                .andExpect(status().isOk());
    }

    @Test
    void testGetExpired() throws Exception {
        when(applianceBO.getExpiredWarrantyCount()).thenReturn(2L);

        mockMvc.perform(get("/appliance/stats/expired")
                .requestAttr("session", getMockSession()))
                .andExpect(status().isOk());
    }

    @Test
    void testGetByModel() throws Exception {
        Map<String, Long> map = Map.of("M101", 2L);

        when(applianceBO.getAppliancesByModel()).thenReturn(map);

        mockMvc.perform(get("/appliance/stats/by-model")
                .requestAttr("session", getMockSession()))
                .andExpect(status().isOk());
    }

    @Test
    void testGetByCustomer() throws Exception {
        Map<String, Long> map = Map.of("C1", 3L);

        when(applianceBO.getAppliancesPerCustomer()).thenReturn(map);

        mockMvc.perform(get("/appliance/stats/by-customer")
                .requestAttr("session", getMockSession()))
                .andExpect(status().isOk());
    }
}