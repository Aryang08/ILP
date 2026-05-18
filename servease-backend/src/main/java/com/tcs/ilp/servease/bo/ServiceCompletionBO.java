package com.tcs.ilp.servease.bo;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.tcs.ilp.servease.entity.ServiceCompletion;
import com.tcs.ilp.servease.exception
        .ServiceCompletionExceptionMain.ServiceCompletionNotFoundException;
import com.tcs.ilp.servease.repository.ServiceCompletionRepository;

@Service
@Transactional
public class ServiceCompletionBO {

    private final ServiceCompletionRepository repository;

    public ServiceCompletionBO(ServiceCompletionRepository repository) {
        this.repository = repository;
    }

    // ✅ ADD
    public void addServiceCompletion(ServiceCompletion completion) {
        repository.save(completion);
    }

    // ✅ GET BY ID (NULL SAFE ✅)
    public ServiceCompletion getCompletionById(String id)
            throws ServiceCompletionNotFoundException {

        if (id == null) {
            throw new ServiceCompletionNotFoundException(
                    "ServiceCompletion ID cannot be null");
        }

        return repository.findById(id)
                .orElseThrow(() ->
                        new ServiceCompletionNotFoundException(
                                "ServiceCompletion not found: " + id));
    }

    // ✅ GET BY SERVICE ID
    public List<ServiceCompletion> getCompletionsByServiceId(String serviceId) {
        return repository.findByServiceId(serviceId);
    }

    // ✅ UPDATE (NULL + EXISTS CHECK ✅)
    public void updateServiceCompletion(ServiceCompletion completion)
            throws ServiceCompletionNotFoundException {

        if (completion == null || completion.getCompletionId() == null) {
            throw new ServiceCompletionNotFoundException(
                    "ServiceCompletion or ID cannot be null");
        }

        if (!repository.existsById(completion.getCompletionId())) {
            throw new ServiceCompletionNotFoundException(
                    "ServiceCompletion not found: " +
                            completion.getCompletionId());
        }

        repository.save(completion);
    }

    // ✅ DELETE (NULL + EXISTS CHECK ✅)
    public void deleteServiceCompletion(String completionId)
            throws ServiceCompletionNotFoundException {

        if (completionId == null) {
            throw new ServiceCompletionNotFoundException(
                    "ServiceCompletion ID cannot be null");
        }

        if (!repository.existsById(completionId)) {
            throw new ServiceCompletionNotFoundException(
                    "ServiceCompletion not found: " + completionId);
        }

        repository.deleteById(completionId);
    }
}
