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

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import com.tcs.ilp.servease.bo.FeedbackBO;
import com.tcs.ilp.servease.config.Role;
import com.tcs.ilp.servease.config.SessionData;
import com.tcs.ilp.servease.entity.FeedbackUser;
import com.tcs.ilp.servease.exception.FeedbackException;

@ExtendWith(MockitoExtension.class)   // ✅ FIX
class FeedbackControllerTest {

    @Mock
    private FeedbackBO feedbackBO;

    @Mock
    private HttpServletRequest request;

    @InjectMocks
    private FeedbackController feedbackController;

    @BeforeEach
    void setup() {

        // ✅ Default session (CUSTOMER for addFeedback)
        SessionData session = new SessionData();
        session.setUserId("cust1");
        session.setRole(Role.CUSTOMER);

        when(request.getAttribute("session")).thenReturn(session);
    }

    // ✅ ADD FEEDBACK SUCCESS
    @Test
    void addFeedback_success() throws Exception {

        FeedbackUser feedback = new FeedbackUser();
        feedback.setFeedbackId("f1");
        feedback.setServiceId("s1");

        when(feedbackBO.addFeedback(feedback)).thenReturn(feedback);

        ResponseEntity<?> response =
                feedbackController.addFeedback(feedback, request);

        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        assertNotNull(response.getBody());
    }

    // ✅ ADD FEEDBACK BAD REQUEST
    @Test
    void addFeedback_badRequest() throws Exception {

        FeedbackUser feedback = new FeedbackUser();

        when(feedbackBO.addFeedback(feedback))
                .thenThrow(new FeedbackException("Error"));

        ResponseEntity<?> response =
                feedbackController.addFeedback(feedback, request);

        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        assertEquals("Error", response.getBody());
    }

    // ✅ GET BY ID SUCCESS
    @Test
    void getFeedbackById_success() {

        FeedbackUser feedback = new FeedbackUser();
        feedback.setFeedbackId("f1");

        when(feedbackBO.getFeedbackById("f1")).thenReturn(feedback);

        ResponseEntity<FeedbackUser> response =
                feedbackController.getFeedbackById("f1", request);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals("f1", response.getBody().getFeedbackId());
    }

    // ✅ GET BY ID NOT FOUND
    @Test
    void getFeedbackById_notFound() {

        when(feedbackBO.getFeedbackById("f1")).thenReturn(null);

        ResponseEntity<FeedbackUser> response =
                feedbackController.getFeedbackById("f1", request);

        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
        assertNull(response.getBody());
    }

    // ✅ GET BY SERVICE ID (ADMIN OR SUPERVISOR)
    @Test
    void getFeedbackByServiceId() {

        // ✅ Change role for this test
        SessionData adminSession = new SessionData();
        adminSession.setRole(Role.ADMIN);

        when(request.getAttribute("session")).thenReturn(adminSession);

        FeedbackUser feedback = new FeedbackUser();
        feedback.setFeedbackId("f1");

        when(feedbackBO.getFeedbackByServiceId("s1", 0, 10))
                .thenReturn(List.of(feedback));

        ResponseEntity<List<FeedbackUser>> response =
                feedbackController.getFeedbackByServiceId("s1", 0, 10, request);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(1, response.getBody().size());
    }

    // ✅ GET ALL (ADMIN ONLY)
    @Test
    void getAllFeedbacks() {

        SessionData adminSession = new SessionData();
        adminSession.setRole(Role.ADMIN);

        when(request.getAttribute("session")).thenReturn(adminSession);

        FeedbackUser feedback = new FeedbackUser();
        feedback.setFeedbackId("f1");

        when(feedbackBO.getAllFeedbacks(0, 10))
                .thenReturn(List.of(feedback));

        ResponseEntity<List<FeedbackUser>> response =
                feedbackController.getAllFeedbacks(0, 10, request);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(1, response.getBody().size());
    }
}