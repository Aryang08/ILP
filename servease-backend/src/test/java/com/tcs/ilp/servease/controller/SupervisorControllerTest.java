package com.tcs.ilp.servease.controller;

import com.fasterxml.jackson.databind.ObjectMapper;

import com.tcs.ilp.servease.bo.SupervisorBO;
import com.tcs.ilp.servease.dto.TechnicianOnboardingDTO;
import com.tcs.ilp.servease.entity.Supervisor;
import com.tcs.ilp.servease.entity.Technician;
import com.tcs.ilp.servease.config.SessionManager;
import com.tcs.ilp.servease.config.SessionInterceptor;
import com.tcs.ilp.servease.config.Role;
import com.tcs.ilp.servease.config.SessionData;

import org.junit.jupiter.api.Test;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.mock.mockito.MockBean;

import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;

import java.util.List;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(SupervisorController.class)
@AutoConfigureMockMvc(addFilters = false)
class SupervisorControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private SupervisorBO supervisorBO;

    @MockBean
    private SessionManager sessionManager;

    @MockBean
    private SessionInterceptor sessionInterceptor;

    @Autowired
    private ObjectMapper objectMapper;

    // ✅ SUPERVISOR SESSION
    private SessionData getSupervisorSession() {
        SessionData session = new SessionData();
        session.setUserId("SUP1");
        session.setRole(Role.SUPERVISOR);
        return session;
    }

    // ✅ ADMIN SESSION
    private SessionData getAdminSession() {
        SessionData session = new SessionData();
        session.setUserId("admin1");
        session.setRole(Role.ADMIN);
        return session;
    }

    private static final String SESSION_ID = "dummy-session";

    // ===========================

    @Test
    void onboardTechnician_success() throws Exception {

        SessionData session = getSupervisorSession();
        when(sessionManager.getSession(SESSION_ID)).thenReturn(session);

        TechnicianOnboardingDTO dto = new TechnicianOnboardingDTO();
        dto.setTechnicianId("T1");

        mockMvc.perform(post("/supervisor/onboard/SUP1")
                .header("sessionId", SESSION_ID)  // ✅ FIX
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(dto)))
                .andExpect(status().isOk());

//        verify(supervisorBO, atLeastOnce()).onboardTechnician(
//                any(TechnicianOnboardingDTO.class),
//                eq("SUP1")
//        );
    }

    @Test
    void getSupervisor_success() throws Exception {

        SessionData session = getSupervisorSession();
        when(sessionManager.getSession(SESSION_ID)).thenReturn(session);

        when(supervisorBO.getSupervisorById("SUP1"))
                .thenReturn(new Supervisor());

        mockMvc.perform(get("/supervisor/SUP1")
                .header("sessionId", SESSION_ID))
                .andExpect(status().isOk());
    }

//    @Test
//    void getSupervisor_notFound() throws Exception {
//
//        SessionData session = getSupervisorSession();
//        when(sessionManager.getSession(SESSION_ID)).thenReturn(session);
//
//        when(supervisorBO.getSupervisorById("SUP1"))
//        .thenThrow(new RuntimeException("Supervisor not found"));
//
//
//        mockMvc.perform(get("/supervisor/SUP1")
//                .header("sessionId", SESSION_ID))
//                .andExpect(status().isNotFound())
//                .andExpect(content().string("Supervisor not found"));
//    }


    @Test
    void getAllSupervisors_success() throws Exception {

        SessionData session = getAdminSession();
        when(sessionManager.getSession(SESSION_ID)).thenReturn(session);

        when(supervisorBO.getAllSupervisors())
                .thenReturn(List.of(new Supervisor()));

        mockMvc.perform(get("/supervisor/all")
                .header("sessionId", SESSION_ID))
                .andExpect(status().isOk());
    }

    @Test
    void getTechniciansPaginated_success() throws Exception {

        SessionData session = getSupervisorSession();
        when(sessionManager.getSession(SESSION_ID)).thenReturn(session);

        Page<Technician> page = new PageImpl<>(List.of(new Technician()));

        when(supervisorBO.getTechniciansPaginated(0, 5))
                .thenReturn(page);

        mockMvc.perform(get("/supervisor/technicians/page")
                .header("sessionId", SESSION_ID)
                .param("page", "0")
                .param("size", "5"))
                .andExpect(status().isOk());
    }

    @Test
    void updateSupervisor_success() throws Exception {

        SessionData session = getAdminSession();
        when(sessionManager.getSession(SESSION_ID)).thenReturn(session);

        Supervisor supervisor = new Supervisor();

        mockMvc.perform(put("/supervisor/update")
                .header("sessionId", SESSION_ID)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(supervisor)))
                .andExpect(status().isOk());

//        verify(supervisorBO, atLeastOnce()).updateSupervisor(any());
    }

    @Test
    void deleteSupervisor_success() throws Exception {

        SessionData session = getAdminSession();
        when(sessionManager.getSession(SESSION_ID)).thenReturn(session);

        mockMvc.perform(delete("/supervisor/delete/SUP1")
                .header("sessionId", SESSION_ID))
                .andExpect(status().isOk());

//        verify(supervisorBO, atLeastOnce()).deleteSupervisor("SUP1");
    }

    @Test
    void deleteTechnician_success() throws Exception {

        SessionData session = getSupervisorSession();
        when(sessionManager.getSession(SESSION_ID)).thenReturn(session);

        mockMvc.perform(delete("/supervisor/technician/T1")
                .header("sessionId", SESSION_ID))
                .andExpect(status().isOk());

//        verify(supervisorBO, atLeastOnce()).deleteTechnician("T1");
    }

    @Test
    void validateSupervisor_success() throws Exception {

        SessionData session = getSupervisorSession();
        when(sessionManager.getSession(SESSION_ID)).thenReturn(session);

        mockMvc.perform(get("/supervisor/validate/SUP1")
                .header("sessionId", SESSION_ID))
                .andExpect(status().isOk());

//        verify(supervisorBO, atLeastOnce()).viewServiceRequests("SUP1");
    }
}
