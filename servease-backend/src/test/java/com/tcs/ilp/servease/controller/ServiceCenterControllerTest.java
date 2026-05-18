package com.tcs.ilp.servease.controller;

import static org.mockito.Mockito.when;
import static org.mockito.Mockito.verify;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.tcs.ilp.servease.bo.ServiceCenterBO;
import com.tcs.ilp.servease.config.Role;
import com.tcs.ilp.servease.config.SessionData;
import com.tcs.ilp.servease.config.SessionManager;
import com.tcs.ilp.servease.config.SessionInterceptor;   // ✅ ADD
import com.tcs.ilp.servease.entity.ServiceCenter;

import org.junit.jupiter.api.Test;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.mock.mockito.MockBean;

import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

@WebMvcTest(ServiceCenterController.class)
@AutoConfigureMockMvc(addFilters = false)
public class ServiceCenterControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private ServiceCenterBO bo;

    @MockBean
    private SessionManager sessionManager;

    @MockBean
    private SessionInterceptor sessionInterceptor;   // ✅ FIX ADDED

    // ✅ SESSION MOCK
    private SessionData getMockSession() {
        SessionData session = new SessionData();
        session.setUserId("admin1");
        session.setRole(Role.ADMIN);
        return session;
    }

    @Test
    public void testGetById() throws Exception {

        ServiceCenter sc = new ServiceCenter();
        sc.setScId("sc1");

        when(bo.getById("sc1")).thenReturn(sc);

        mockMvc.perform(get("/servicecenter/get/sc1")
                .requestAttr("session", getMockSession()))
                .andExpect(status().isOk());
    }

    @Test
    public void testGetByPincode() throws Exception {

        when(bo.findByPincode("380015")).thenReturn("sc1");

        mockMvc.perform(get("/servicecenter/find/380015")
                .requestAttr("session", getMockSession()))
                .andExpect(status().isOk());
    }

    @Test
    public void testInsertServiceCenter() throws Exception {

        String json = """
        {
          "scId":"sc1",
          "name":"West Service Center"
        }
        """;

        mockMvc.perform(post("/servicecenter/add")
                .requestAttr("session", getMockSession())
                .contentType(MediaType.APPLICATION_JSON)
                .content(json))
                .andExpect(status().isOk());
    }

    @Test
    public void testUpdateServiceCenter() throws Exception {

        String json = """
        {
          "name":"Updated Center"
        }
        """;

        mockMvc.perform(put("/servicecenter/update/sc1")
                .requestAttr("session", getMockSession())
                .contentType(MediaType.APPLICATION_JSON)
                .content(json))
                .andExpect(status().isOk());
    }

    @Test
    public void testDeleteServiceCenter() throws Exception {

        mockMvc.perform(delete("/servicecenter/delete/sc1")
                .requestAttr("session", getMockSession()))
                .andExpect(status().isOk());
    }
}
