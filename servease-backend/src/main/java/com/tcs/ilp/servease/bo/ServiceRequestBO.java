package com.tcs.ilp.servease.bo;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.tcs.ilp.servease.dto.ServiceRequestDTO;
import com.tcs.ilp.servease.repository.ServiceRequestRepository;

@Service
public class ServiceRequestBO {

    @Autowired
    private ServiceRequestRepository serviceRequestRepository;

    /**
     * ✅ Get all service requests (paginated)
     */
    public List<ServiceRequestDTO> getAllServiceRequests(int page, int size) {
        return serviceRequestRepository.getAllServiceRequests(page, size);
    }
}