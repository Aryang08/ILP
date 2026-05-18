package com.tcs.ilp.servease.controller;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import java.util.Arrays;
import java.util.Optional;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.AfterEach;

import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.fasterxml.jackson.databind.ObjectMapper;

import com.tcs.ilp.servease.bo.DiagnosticQuestionBO;
import com.tcs.ilp.servease.entity.DiagnosticQuestion;
import com.tcs.ilp.servease.exception.DiagnosticException;
import com.tcs.ilp.servease.repository.DiagnosticQuestionRepository;

import com.tcs.ilp.servease.config.SessionManager;
import com.tcs.ilp.servease.config.SessionData;
import com.tcs.ilp.servease.config.Role;

@SuppressWarnings("removal")
@SpringBootTest
@AutoConfigureMockMvc
@ExtendWith(MockitoExtension.class)
class DiagnosticQuestionTest {

    @Mock
    private DiagnosticQuestionRepository repo;

    @InjectMocks
    private DiagnosticQuestionBO bo;

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private DiagnosticQuestionBO boMock;

    @MockBean
    private SessionManager sessionManager;   // ✅ FIX

    private DiagnosticQuestion q;
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

        q = new DiagnosticQuestion("Q1", "AC", "Cooling?", "TAG", false, true);
        mapper = new ObjectMapper();

        // ✅ CRITICAL FIX
        when(sessionManager.getSession(SESSION_ID))
                .thenReturn(getSession());
    }

    // ✅ AFTER EACH
    @AfterEach
    void tearDown() {
        System.out.println("---- End Test ----");
    }

    // =====================================================
    // ✅ ✅ ✅ BO TESTS (UNCHANGED ✅)
    // =====================================================

    @Test
    void testGetQuestionById_Success() throws Exception {

        when(repo.findById("Q1")).thenReturn(Optional.of(q));

        DiagnosticQuestion result = bo.getQuestionById("Q1");

        assertNotNull(result);
        assertEquals("Q1", result.getQuestionId());
    }

    @Test
    void testGetQuestionById_NotFound() {

        when(repo.findById("Q2")).thenReturn(Optional.empty());

        assertThrows(DiagnosticException.class, () -> {
            bo.getQuestionById("Q2");
        });
    }

    @Test
    void testAddQuestion_Invalid() {

        DiagnosticQuestion empty = new DiagnosticQuestion();

        assertThrows(DiagnosticException.class, () -> {
            bo.addQuestion(empty);
        });
    }

    @Test
    void testAddQuestion_Success() throws Exception {

        when(repo.save(q)).thenReturn(q);

        assertTrue(bo.addQuestion(q));
    }

    @Test
    void testGetQuestionById_EmptyString() {

        when(repo.findById("")).thenReturn(Optional.empty());

        assertThrows(DiagnosticException.class, () -> {
            bo.getQuestionById("");
        });
    }

    @Test
    void testGetQuestionsByAppliance() {

        List<DiagnosticQuestion> list = Arrays.asList(q);

        when(repo.findByApplianceType("AC")).thenReturn(list);

        assertEquals(1, bo.getQuestionsByAppliance("AC").size());
    }

    // =====================================================
    // ✅ ✅ ✅ CONTROLLER TESTS (FIXED ✅)
    // =====================================================

    @Test
    void testAddQuestionAPI() throws Exception {

        when(boMock.addQuestion(any(DiagnosticQuestion.class))).thenReturn(true);

        mockMvc.perform(post("/api/diagnostic-questions")
                .header("sessionId", SESSION_ID)
                .contentType("application/json")
                .content(mapper.writeValueAsString(q)))
                .andExpect(status().isOk())
                .andExpect(content().string("true"));
    }

    @Test
    void testGetQuestionByIdAPI() throws Exception {

        when(boMock.getQuestionById("Q1")).thenReturn(q);

        mockMvc.perform(get("/api/diagnostic-questions/Q1")
                .header("sessionId", SESSION_ID))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.questionId").value("Q1"));
    }

    @Test
    void testGetQuestionsByApplianceAPI() throws Exception {

        when(boMock.getQuestionsByAppliance("AC"))
                .thenReturn(Arrays.asList(q));

        mockMvc.perform(get("/api/diagnostic-questions/appliance/AC")
                .header("sessionId", SESSION_ID))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].applianceType").value("AC"));
    }

    @Test
    void testGetRootQuestionAPI() throws Exception {

        when(boMock.getRootQuestionByAppliance("AC")).thenReturn(q);

        mockMvc.perform(get("/api/diagnostic-questions/appliance/AC/root")
                .header("sessionId", SESSION_ID))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.questionId").value("Q1"));
    }

    @Test
    void testUpdateQuestionAPI() throws Exception {

        when(boMock.updateQuestion(any(DiagnosticQuestion.class))).thenReturn(true);

        mockMvc.perform(put("/api/diagnostic-questions")
                .header("sessionId", SESSION_ID)
                .contentType("application/json")
                .content(mapper.writeValueAsString(q)))
                .andExpect(status().isOk())
                .andExpect(content().string("true"));
    }

    @Test
    void testDeleteQuestionAPI() throws Exception {

        when(boMock.deleteQuestion("Q1")).thenReturn(true);

        mockMvc.perform(delete("/api/diagnostic-questions/Q1")
                .header("sessionId", SESSION_ID))
                .andExpect(status().isOk())
                .andExpect(content().string("true"));
    }
}