package com.tcs.ilp.servease.bo;

import java.util.List;

import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.tcs.ilp.servease.entity.FeedbackUser;
import com.tcs.ilp.servease.exception.FeedbackException;
import com.tcs.ilp.servease.repository.FeedbackRepository;

@Service
public class FeedbackBO {

    private final FeedbackRepository feedbackRepository;

    public FeedbackBO(FeedbackRepository feedbackRepository) {
        this.feedbackRepository = feedbackRepository;
    }

    // ✅ Add feedback
    public FeedbackUser addFeedback(FeedbackUser feedback) throws FeedbackException {

        if (feedback == null) {
            throw new FeedbackException("Feedback cannot be null");
        }

        if (feedback.getFeedbackId() == null ||
            feedback.getFeedbackId().isBlank()) {
            throw new FeedbackException("Feedback ID is mandatory");
        }

        return feedbackRepository.save(feedback);
    }

    // ✅ Get feedback by ID
    public FeedbackUser getFeedbackById(String feedbackId) {
        return feedbackRepository.findById(feedbackId).orElse(null);
    }

    // ✅ Get feedbacks by service ID (paginated)
    public List<FeedbackUser> getFeedbackByServiceId(
            String serviceId, int page, int size) {

        Pageable pageable = PageRequest.of(page, size);

        return feedbackRepository
                .findByServiceIdOrderByCreatedAtDesc(serviceId, pageable)
                .getContent();
    }

    // ✅ Get all feedbacks (paginated)
    public List<FeedbackUser> getAllFeedbacks(int page, int size) {

        Pageable pageable = PageRequest.of(page, size);

        return feedbackRepository
                .findAllByOrderByCreatedAtDesc(pageable)
                .getContent();
    }
}