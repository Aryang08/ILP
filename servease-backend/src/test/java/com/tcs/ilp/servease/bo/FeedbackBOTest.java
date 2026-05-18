package com.tcs.ilp.servease.bo;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;

import com.tcs.ilp.servease.entity.FeedbackUser;
import com.tcs.ilp.servease.exception.FeedbackException;
import com.tcs.ilp.servease.repository.FeedbackRepository;

@ExtendWith(MockitoExtension.class)   // ✅ FIX
class FeedbackBOTest {

    @Mock
    private FeedbackRepository repository;

    @InjectMocks
    private FeedbackBO feedbackBO;

    // ✅ ADD FEEDBACK
    @Test
    void testAddFeedback_success() throws Exception {

        FeedbackUser feedback = new FeedbackUser();
        feedback.setFeedbackId("f1");
        feedback.setServiceId("s1");

        when(repository.save(feedback)).thenReturn(feedback);

        FeedbackUser result = feedbackBO.addFeedback(feedback);

        assertNotNull(result);
        verify(repository, times(1)).save(feedback);
    }

    // ✅ VALIDATION TEST
    @Test
    void testAddFeedback_null() {

        Exception ex = assertThrows(FeedbackException.class, () -> {
            feedbackBO.addFeedback(null);
        });

        assertEquals("Feedback cannot be null", ex.getMessage());
    }

    // ✅ GET BY ID
    @Test
    void testGetFeedbackById() {

        FeedbackUser feedback = new FeedbackUser();
        feedback.setFeedbackId("f1");
        feedback.setServiceId("s1");

        when(repository.findById("f1"))
                .thenReturn(Optional.of(feedback));

        FeedbackUser result = feedbackBO.getFeedbackById("f1");

        assertNotNull(result);
        assertEquals("f1", result.getFeedbackId());
    }

    // ✅ GET BY SERVICE ID (PAGINATION)
    @Test
    void testGetFeedbackByServiceId() {

        FeedbackUser feedback = new FeedbackUser();
        feedback.setFeedbackId("f1");
        feedback.setServiceId("s1");

        Page<FeedbackUser> page =
                new PageImpl<>(Arrays.asList(feedback));

        when(repository.findByServiceIdOrderByCreatedAtDesc(
                eq("s1"), any(Pageable.class)))
                .thenReturn(page);

        List<FeedbackUser> list =
                feedbackBO.getFeedbackByServiceId("s1", 0, 10);

        assertEquals(1, list.size());
    }

    // ✅ GET ALL (PAGINATION)
    @Test
    void testGetAllFeedbacks() {

        FeedbackUser feedback = new FeedbackUser();
        feedback.setFeedbackId("f1");
        feedback.setServiceId("s1");

        Page<FeedbackUser> page =
                new PageImpl<>(Arrays.asList(feedback));

        when(repository.findAllByOrderByCreatedAtDesc(any(Pageable.class)))
                .thenReturn(page);

        List<FeedbackUser> list =
                feedbackBO.getAllFeedbacks(0, 10);

        assertEquals(1, list.size());
    }
}