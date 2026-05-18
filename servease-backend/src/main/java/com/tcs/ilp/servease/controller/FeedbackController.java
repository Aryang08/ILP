package com.tcs.ilp.servease.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.tcs.ilp.servease.bo.FeedbackBO;
import com.tcs.ilp.servease.entity.FeedbackUser;
import com.tcs.ilp.servease.exception.FeedbackException;
import com.tcs.ilp.servease.config.SessionData;
import com.tcs.ilp.servease.config.Role;
import com.tcs.ilp.servease.util.AuthUtil;

import jakarta.servlet.http.HttpServletRequest;

@RestController
@RequestMapping("/feedback")
public class FeedbackController {

    private final FeedbackBO feedbackBO;

    public FeedbackController(FeedbackBO feedbackBO) {
        this.feedbackBO = feedbackBO;
    }

    // ✅ ADD FEEDBACK (CUSTOMER ONLY)
    @PostMapping
    public ResponseEntity<?> addFeedback(
            @RequestBody FeedbackUser feedback,
            HttpServletRequest request) {

        SessionData session = AuthUtil.getSession(request);

        if (session.getRole() != Role.CUSTOMER) {
            throw new RuntimeException("Access Denied");
        }

        try {
            FeedbackUser savedFeedback = feedbackBO.addFeedback(feedback);
            return new ResponseEntity<>(savedFeedback, HttpStatus.CREATED);
        } catch (FeedbackException e) {
            return new ResponseEntity<>(e.getMessage(),
                    HttpStatus.BAD_REQUEST);
        }
    }

    // ✅ GET BY ID (ANY LOGGED-IN USER)
    @GetMapping("/{feedbackId}")
    public ResponseEntity<FeedbackUser> getFeedbackById(
            @PathVariable String feedbackId,
            HttpServletRequest request) {

        AuthUtil.getSession(request); // validate only

        FeedbackUser feedback = feedbackBO.getFeedbackById(feedbackId);

        if (feedback == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        return new ResponseEntity<>(feedback, HttpStatus.OK);
    }

    // ✅ GET BY SERVICE ID (ADMIN / SUPERVISOR)
    @GetMapping("/service/{serviceId}")
    public ResponseEntity<List<FeedbackUser>> getFeedbackByServiceId(
            @PathVariable String serviceId,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            HttpServletRequest request) {

        SessionData session = AuthUtil.getSession(request);

        if (session.getRole() != Role.ADMIN &&
            session.getRole() != Role.SUPERVISOR) {
            throw new RuntimeException("Access Denied");
        }

        List<FeedbackUser> feedbackList =
                feedbackBO.getFeedbackByServiceId(serviceId, page, size);

        return new ResponseEntity<>(feedbackList, HttpStatus.OK);
    }

    // ✅ GET ALL FEEDBACK (ADMIN ONLY)
    @GetMapping
    public ResponseEntity<List<FeedbackUser>> getAllFeedbacks(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            HttpServletRequest request) {

        SessionData session = AuthUtil.getSession(request);
        AuthUtil.checkAdmin(session);

        List<FeedbackUser> feedbackList =
                feedbackBO.getAllFeedbacks(page, size);

        return new ResponseEntity<>(feedbackList, HttpStatus.OK);
    }
}