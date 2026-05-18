package com.tcs.ilp.servease.controller;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import java.util.Optional;
import java.util.Arrays;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.AfterEach;

import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.fasterxml.jackson.databind.ObjectMapper;

import com.tcs.ilp.servease.bo.DiagnosticOptionBO;
import com.tcs.ilp.servease.entity.DiagnosticOption;
import com.tcs.ilp.servease.exception.DiagnosticException;
import com.tcs.ilp.servease.repository.DiagnosticOptionRepository;

import com.tcs.ilp.servease.config.SessionManager;
import com.tcs.ilp.servease.config.SessionData;
import com.tcs.ilp.servease.config.Role;

@SuppressWarnings("removal")
@SpringBootTest
@AutoConfigureMockMvc
@ExtendWith(MockitoExtension.class)
class DiagnosticOptionTest {

    @Mock
    private DiagnosticOptionRepository repo;

    @InjectMocks
    private DiagnosticOptionBO bo;

    // ✅ Controller testing
    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private DiagnosticOptionBO boMock;

    @MockBean
    private SessionManager sessionManager;   // ✅ ADDED

    private DiagnosticOption opt;
    private ObjectMapper mapper;

    private static final String SESSION_ID = "dummy-session";

    // ✅ SESSION HELPER
    private SessionData getSession() {
        SessionData session = new SessionData();
        session.setUserId("U1");
        session.setRole(Role.ADMIN);
        return session;
    }

    // ✅ BEFORE EACH
    @BeforeEach
    void setUp() {
        System.out.println("---- Start Test ----");

        opt = new DiagnosticOption("O1", "Q1", "Yes", "Q2", true);
        mapper = new ObjectMapper();

        // ✅ CRITICAL FIX
        when(sessionManager.getSession(SESSION_ID))
                .thenReturn(getSession());
    }

    @AfterEach
    void tearDown() {
        System.out.println("---- End Test ----");
    }

    // =====================================================
    // ✅ ✅ ✅ BO TEST CASES (UNCHANGED ✅)
    // =====================================================

    @Test
    void testGetOptionById_Success() throws Exception {

        when(repo.findById("O1")).thenReturn(Optional.of(opt));

        DiagnosticOption result = bo.getOptionById("O1");

        assertNotNull(result);
        assertEquals("O1", result.getOptionId());
    }

    @Test
    void testGetOptionById_NotFound() {

        when(repo.findById("O2")).thenReturn(Optional.empty());

        assertThrows(DiagnosticException.class, () -> {
            bo.getOptionById("O2");
        });
    }

    @Test
    void testDeleteOption() {

        doNothing().when(repo).deleteById("O1");

        boolean result = bo.deleteOption("O1");

        assertTrue(result);
    }

    @Test
    void testGetOptionsByQuestionId() {

        List<DiagnosticOption> list =
                Arrays.asList(new DiagnosticOption("O1", "Q1", "Yes", "Q2", true));

        when(repo.findByQuestionId("Q1")).thenReturn(list);

        List<DiagnosticOption> result = bo.getOptionsByQuestionId("Q1");

        assertNotNull(result);
        assertEquals(1, result.size());
    }

    // =====================================================
    // ✅ ✅ ✅ CONTROLLER TEST CASES (FIXED ✅)
    // =====================================================

    @Test
    void testAddOptionAPI() throws Exception {

        when(boMock.addOption(any(DiagnosticOption.class))).thenReturn(true);

        mockMvc.perform(post("/api/diagnostic-options")
                .header("sessionId", SESSION_ID)   // ✅ FIX
                .contentType("application/json")
                .content(mapper.writeValueAsString(opt)))
                .andExpect(status().isOk())
                .andExpect(content().string("true"));
    }

    @Test
    void testGetOptionByIdAPI() throws Exception {

        when(boMock.getOptionById("O1")).thenReturn(opt);

        mockMvc.perform(get("/api/diagnostic-options/O1")
                .header("sessionId", SESSION_ID))   // ✅ FIX
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.optionId").value("O1"));
    }

    @Test
    void testGetOptionsByQuestionIdAPI() throws Exception {

        when(boMock.getOptionsByQuestionId("Q1"))
                .thenReturn(Arrays.asList(opt));

        mockMvc.perform(get("/api/diagnostic-options/question/Q1")
                .header("sessionId", SESSION_ID))   // ✅ FIX
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].questionId").value("Q1"));
    }

    @Test
    void testUpdateOptionAPI() throws Exception {

        when(boMock.updateOption(any(DiagnosticOption.class))).thenReturn(true);

        mockMvc.perform(put("/api/diagnostic-options")
                .header("sessionId", SESSION_ID)   // ✅ FIX
                .contentType("application/json")
                .content(mapper.writeValueAsString(opt)))
                .andExpect(status().isOk())
                .andExpect(content().string("true"));
    }

    @Test
    void testDeleteOptionAPI() throws Exception {

        when(boMock.deleteOption("O1")).thenReturn(true);

        mockMvc.perform(delete("/api/diagnostic-options/O1")
                .header("sessionId", SESSION_ID))   // ✅ FIX
                .andExpect(status().isOk())
                .andExpect(content().string("true"));
    }
}