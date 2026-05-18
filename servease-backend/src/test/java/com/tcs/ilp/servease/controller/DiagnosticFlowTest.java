package com.tcs.ilp.servease.controller;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import java.util.List;
import java.util.Arrays;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;

import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.tcs.ilp.servease.bo.DiagnosticFlowBO;
import com.tcs.ilp.servease.bo.DiagnosticQuestionBO;
import com.tcs.ilp.servease.bo.DiagnosticOptionBO;

import com.tcs.ilp.servease.entity.DiagnosticQuestion;
import com.tcs.ilp.servease.entity.DiagnosticOption;
import com.tcs.ilp.servease.exception.DiagnosticException;

import com.tcs.ilp.servease.config.SessionManager;
import com.tcs.ilp.servease.config.SessionData;
import com.tcs.ilp.servease.config.Role;

@SpringBootTest
@AutoConfigureMockMvc
public class DiagnosticFlowTest {

    @Autowired
    private DiagnosticFlowBO flowBO;

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private DiagnosticQuestionBO questionBO;

    @MockBean
    private DiagnosticOptionBO optionBO;

    @MockBean
    private SessionManager sessionManager;   // ✅ FIX

    private DiagnosticQuestion question;
    private DiagnosticOption option;

    private static final String SESSION_ID = "dummy-session";

    // ✅ SESSION HELPER
    private SessionData getSession() {
        SessionData s = new SessionData();
        s.setUserId("U1");
        s.setRole(Role.ADMIN);
        return s;
    }

    @BeforeEach
    void setUp() {

        System.out.println("---- Start Test ----");

        question = new DiagnosticQuestion(
                "Q1", "AC", "Cooling?", "TAG", false, true);

        option = new DiagnosticOption(
                "O1", "Q1", "Yes", "Q2", true);

        // ✅ CRITICAL FIX
        when(sessionManager.getSession(SESSION_ID))
                .thenReturn(getSession());
    }

    @AfterEach
    void tearDown() {
        System.out.println("---- End Test ----");
    }

    // =========================
    // ✅ BO TESTS (UNCHANGED)
    // =========================

    @Test
    void testStartDiagnosticSuccess() throws Exception {
        when(questionBO.getRootQuestionByAppliance("AC"))
                .thenReturn(question);

        DiagnosticQuestion result = flowBO.startDiagnostic("AC");

        assertNotNull(result);
        assertEquals("Q1", result.getQuestionId());
    }

    @Test
    void testGetOptionsForQuestion() throws Exception {
        when(optionBO.getOptionsByQuestionId("Q1"))
                .thenReturn(Arrays.asList(option));

        assertEquals(1, flowBO.getOptionsForQuestion("Q1").size());
    }

    @Test
    void testGetOptionsEmpty() throws Exception {
        when(optionBO.getOptionsByQuestionId("Q1"))
                .thenReturn(List.of());

        assertTrue(flowBO.getOptionsForQuestion("Q1").isEmpty());
    }

    @Test
    void testMoveToNextQuestionSuccess() throws Exception {

        when(optionBO.getOptionById("O1")).thenReturn(option);

        when(questionBO.getQuestionById("Q2"))
                .thenReturn(new DiagnosticQuestion("Q2", "AC", "Next?", "TAG", false, false));

        DiagnosticQuestion result = flowBO.moveToNextQuestion("O1");

        assertNotNull(result);
        assertEquals("Q2", result.getQuestionId());
    }

    // ✅ MOVE NEXT - NULL OPTION (NEGATIVE)
    @Test
    void testMoveToNextQuestionNullOption() {

        try {
			when(optionBO.getOptionById("O1")).thenReturn(null);
		} catch (DiagnosticException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

        assertThrows(DiagnosticException.class, () -> {
            flowBO.moveToNextQuestion("O1");
        });
    }

    @Test
    void testMoveToNextQuestionEnd() throws Exception {

        option.setNextQuestionId(null);

        when(optionBO.getOptionById("O1")).thenReturn(option);

        assertNull(flowBO.moveToNextQuestion("O1"));
    }

    @Test
    void testIsDiagnosticComplete() {
        question.setTerminal(true);
        assertTrue(flowBO.isDiagnosticComplete(question));
    }

    @Test
    void testIsDiagnosticNotComplete() {
        question.setTerminal(false);
        assertFalse(flowBO.isDiagnosticComplete(question));
    }

    @Test
    void testIsDiagnosticCompleteNull() {
        assertFalse(flowBO.isDiagnosticComplete(null));
    }

    // =========================
    // ✅ CONTROLLER TESTS (FIXED ✅)
    // =========================

    @Test
    void testStartDiagnosticAPI() throws Exception {

        when(questionBO.getRootQuestionByAppliance("AC"))
                .thenReturn(question);

        mockMvc.perform(get("/api/diagnostic-flow/start/AC")
                .header("sessionId", SESSION_ID))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.questionId").value("Q1"));
    }

    @Test
    void testGetOptionsAPI() throws Exception {

        when(optionBO.getOptionsByQuestionId("Q1"))
                .thenReturn(Arrays.asList(option));

        mockMvc.perform(get("/api/diagnostic-flow/options/Q1")
                .header("sessionId", SESSION_ID))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].optionId").value("O1"));
    }

    @Test
    void testMoveNextAPI() throws Exception {

        when(optionBO.getOptionById("O1")).thenReturn(option);

        when(questionBO.getQuestionById("Q2"))
                .thenReturn(new DiagnosticQuestion("Q2", "AC", "Next?", "TAG", false, false));

        mockMvc.perform(get("/api/diagnostic-flow/next/O1")
                .header("sessionId", SESSION_ID))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.questionId").value("Q2"));
    }

    @Test
    void testMoveNextAPIEnd() throws Exception {

        option.setNextQuestionId(null);

        when(optionBO.getOptionById("O1")).thenReturn(option);

        mockMvc.perform(get("/api/diagnostic-flow/next/O1")
                .header("sessionId", SESSION_ID))
                .andExpect(status().isOk());
    }
}