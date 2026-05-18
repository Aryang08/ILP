package com.tcs.ilp.servease.bo;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.tcs.ilp.servease.entity.ServiceDiagnostics;
import com.tcs.ilp.servease.repository.ServiceDiagnosticsRepository;

@Service
@Transactional
public class ServiceDiagnosticsBO {

    private final ServiceDiagnosticsRepository repository;

    public ServiceDiagnosticsBO(ServiceDiagnosticsRepository repository) {
        this.repository = repository;
    }

    // ===================== CREATE =====================
    public void addServiceDiagnostics(ServiceDiagnostics diagnostics) {
        if (!repository.existsById(diagnostics.getServiceDiagnosticId())) {
            repository.save(diagnostics);
        }
    }

    // ===================== READ =====================
    @Transactional(readOnly = true)
    public ServiceDiagnostics getServiceDiagnosticsById(String diagnosticId) {
        return repository.findById(diagnosticId).orElse(null);
    }

    @Transactional(readOnly = true)
    public List<ServiceDiagnostics> getServiceDiagnosticsByServiceId(String serviceId) {
        return repository.findByServiceId(serviceId);
    }

    @Transactional(readOnly = true)
    public List<ServiceDiagnostics> getAllServiceDiagnostics() {
        return repository.findAll();
    }

    // ===================== UPDATE =====================
    public void updateServiceDiagnostics(ServiceDiagnostics diagnostics) {
        repository.save(diagnostics);
    }

    // ===================== DELETE =====================
    public void deleteServiceDiagnostics(String diagnosticId) {
        repository.deleteById(diagnosticId);
    }

    // ===================== BACK BUTTON =====================
    @Transactional(readOnly = true)
    public LocalDateTime getAnswerTimestamp(String serviceId, String questionId) {
        return repository.findAnswerTimestamp(serviceId, questionId);
    }

    public void deleteAnswersAfter(String serviceId) {
        repository.deleteByServiceId(serviceId);
    }

    // ===================== SUMMARY =====================
    @Transactional(readOnly = true)
    public List<ServiceDiagnostics> findPositiveAnswers(String serviceId) {
        // Current behavior preserved (same as earlier JDBC fallback)
        return repository.findByServiceId(serviceId);
    }
}