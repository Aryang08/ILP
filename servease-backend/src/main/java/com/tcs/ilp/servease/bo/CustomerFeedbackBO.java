package com.tcs.ilp.servease.bo;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.tcs.ilp.servease.dto.CustomerFeedback;
import com.tcs.ilp.servease.repository.CustomerFeedbackRepository;

@Service
public class CustomerFeedbackBO {

    @Autowired
    private CustomerFeedbackRepository customerFeedbackRepository;

    // ✅ Get all feedbacks (paginated)
    public List<CustomerFeedback> getAllFeedbacks(int page, int size) {
        return customerFeedbackRepository.getAllFeedbacks(page, size);
    }

    // ✅ Get feedbacks by service center (paginated)
    public List<CustomerFeedback> getFeedbackByServiceCenter(
            String serviceCenterName, int page, int size) {

        return customerFeedbackRepository.getFeedbackByServiceCenter(
            serviceCenterName, page, size
        );
    }

    // ✅ Get average rating (no pagination)
    public double getAverageRatingByServiceCenter(String serviceCenterName) {
        return customerFeedbackRepository
                .getAverageRatingByServiceCenter(serviceCenterName);
    }

    // ✅ Get feedbacks sorted by rating ASC (paginated)
    public List<CustomerFeedback> getFeedbackSortedByRatingAsc(int page, int size) {
        return customerFeedbackRepository.getSortedByRatingAsc(page, size);
    }

    // ✅ Get feedbacks sorted by rating DESC (paginated)
    public List<CustomerFeedback> getFeedbackSortedByRatingDesc(int page, int size) {
        return customerFeedbackRepository.getSortedByRatingDesc(page, size);
    }
}