package com.tcs.ilp.servease.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import com.tcs.ilp.servease.bo.CustomerFeedbackBO;
import com.tcs.ilp.servease.dto.CustomerFeedback;
import com.tcs.ilp.servease.config.SessionData;
import com.tcs.ilp.servease.config.Role;
import com.tcs.ilp.servease.util.AuthUtil;

import jakarta.servlet.http.HttpServletRequest;

@RestController
@RequestMapping("/feedbacks")
public class CustomerFeedbackController {

    @Autowired
    private CustomerFeedbackBO customerFeedbackBO;

    // ✅ 1. Get all feedbacks (ADMIN / SUPERVISOR)
    @GetMapping
    public List<CustomerFeedback> getAllFeedbacks(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            HttpServletRequest request) {

        SessionData session = AuthUtil.getSession(request);

        if (session.getRole() != Role.ADMIN &&
            session.getRole() != Role.SUPERVISOR) {
            throw new RuntimeException("Access Denied");
        }

        return customerFeedbackBO.getAllFeedbacks(page, size);
    }

    // ✅ 2. Get feedback by service center
    @GetMapping("/by-service-center")
    public List<CustomerFeedback> getFeedbackByServiceCenter(
            @RequestParam String serviceCenterName,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            HttpServletRequest request) {

        SessionData session = AuthUtil.getSession(request);

        if (session.getRole() != Role.ADMIN &&
            session.getRole() != Role.SUPERVISOR) {
            throw new RuntimeException("Access Denied");
        }

        return customerFeedbackBO.getFeedbackByServiceCenter(
                serviceCenterName, page, size);
    }

    // ✅ 3. Get average rating
    @GetMapping("/average-rating")
    public double getAverageRating(
            @RequestParam String serviceCenterName,
            HttpServletRequest request) {

        SessionData session = AuthUtil.getSession(request);

        if (session.getRole() != Role.ADMIN &&
            session.getRole() != Role.SUPERVISOR) {
            throw new RuntimeException("Access Denied");
        }

        return customerFeedbackBO
                .getAverageRatingByServiceCenter(serviceCenterName);
    }

    // ✅ 4. Sort ASC
    @GetMapping("/sort/rating-asc")
    public List<CustomerFeedback> getFeedbackSortedByRatingAsc(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            HttpServletRequest request) {

        SessionData session = AuthUtil.getSession(request);

        if (session.getRole() != Role.ADMIN &&
            session.getRole() != Role.SUPERVISOR) {
            throw new RuntimeException("Access Denied");
        }

        return customerFeedbackBO
                .getFeedbackSortedByRatingAsc(page, size);
    }

    // ✅ 5. Sort DESC
    @GetMapping("/sort/rating-desc")
    public List<CustomerFeedback> getFeedbackSortedByRatingDesc(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            HttpServletRequest request) {

        SessionData session = AuthUtil.getSession(request);

        if (session.getRole() != Role.ADMIN &&
            session.getRole() != Role.SUPERVISOR) {
            throw new RuntimeException("Access Denied");
        }

        return customerFeedbackBO
                .getFeedbackSortedByRatingDesc(page, size);
    }
}
