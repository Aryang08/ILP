package com.tcs.ilp.servease.bo;

import java.time.LocalDate;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import com.tcs.ilp.servease.entity.ServiceMain;
import com.tcs.ilp.servease.entity.ServiceDiagnostics;
import com.tcs.ilp.servease.entity.Customer;

import com.tcs.ilp.servease.repository.ServiceMainRepository;
import com.tcs.ilp.servease.repository.ServiceDiagnosticsRepository;
import com.tcs.ilp.servease.repository.ServiceCenterRepository;
import com.tcs.ilp.servease.repository.CustomerRepository;

import com.tcs.ilp.servease.dto.RequestServiceDTO;
import com.tcs.ilp.servease.dto.ServiceDiagnosticsDTO;

@Service
public class ServiceMainBO {

    @Autowired
    private ServiceMainRepository serviceMainRepository;

    @Autowired
    private ServiceDiagnosticsRepository serviceDiagnosticsRepository;

    @Autowired
    private ServiceCenterRepository serviceCenterRepository;

    @Autowired
    private CustomerRepository customerRepository;

    // =========================
    // CREATE SERVICE
    // =========================
    @Transactional
    public ServiceMain addService(ServiceMain s) {

        if (s == null) {
            throw new RuntimeException("Service object cannot be null");
        }

        if (s.getServiceId() == null || s.getServiceId().isBlank()) {
            throw new RuntimeException("Service ID is mandatory");
        }

        if (s.getCustomerId() == null || s.getCustomerId().isBlank()) {
            throw new RuntimeException("Customer ID is mandatory");
        }

        // ✅ SAFE OPTIONAL HANDLING
        Customer customer = customerRepository
                .findByCustomerId(s.getCustomerId())
                .orElse(null);

        if (customer != null) {

            String pincode = customer.getPincode();

            if (pincode != null) {
                String scId = serviceCenterRepository
                        .findServiceCenterIdByPincode(pincode);

                if (scId != null) {
                    s.setServiceCenterId(scId);
                }
            }
        }

        return serviceMainRepository.save(s);
    }

    // =========================
    // READ BY ID
    // =========================
    public ServiceMain getServiceById(String serviceId) {

        return serviceMainRepository.findById(serviceId)
                .orElseThrow(() ->
                        new RuntimeException("Service not found: " + serviceId));
    }

    // =========================
    // UPDATE STATUS
    // =========================
    @Transactional
    public ServiceMain updateServiceStatus(String serviceId, String status) {

        if (status == null || status.isBlank()) {
            throw new RuntimeException("Status cannot be empty");
        }

        ServiceMain s = getServiceById(serviceId);
        s.setStatus(status);

        return serviceMainRepository.save(s);
    }

    // =========================
    // DELETE
    // =========================
    @Transactional
    public void deleteService(String serviceId) {

        if (serviceId == null || !serviceMainRepository.existsById(serviceId)) {
            throw new RuntimeException("Service not found");
        }

        serviceMainRepository.deleteById(serviceId);
    }

    // =========================
    // READ ALL
    // =========================
    public List<ServiceMain> getAllServices() {
        return serviceMainRepository.findAll();
    }

    // =========================
    // PAGINATION
    // =========================
    public Page<ServiceMain> getAllServicesPaginated(int page, int size) {

        Pageable pageable = PageRequest.of(page, size);
        return serviceMainRepository.findAll(pageable);
    }

    // =========================
    // REQUEST SERVICE
    // =========================
    @Transactional
    public void requestService(RequestServiceDTO dto) {

        if (dto == null) {
            throw new RuntimeException("Request cannot be null");
        }

        if (dto.getServiceId() == null || dto.getServiceId().isBlank()) {
            throw new RuntimeException("Service ID is required");
        }

        ServiceMain serviceMain = new ServiceMain();

        serviceMain.setServiceId(dto.getServiceId());
        serviceMain.setCustomerId(dto.getCustomerId());
        serviceMain.setApplianceId(dto.getApplianceId());

        serviceMain.setPreferredDate(
                LocalDate.parse(dto.getPreferredDate())
        );

        serviceMain.setIssueDate(LocalDate.now());
        serviceMain.setStatus("OPEN");
        serviceMain.setCustomComplaint(dto.getCustomComplaint());

        // ✅ SAFE OPTIONAL HANDLING
        Customer customer = customerRepository
                .findByCustomerId(dto.getCustomerId())
                .orElse(null);

        if (customer != null) {

            String pincode = customer.getPincode();

            if (pincode != null) {
                String scId =
                        serviceCenterRepository.findServiceCenterIdByPincode(pincode);

                if (scId != null) {
                    serviceMain.setServiceCenterId(scId);
                }
            }
        }

        serviceMainRepository.save(serviceMain);

        // =========================
        // SAVE DIAGNOSTICS
        // =========================
        if (dto.getDiagnostics() != null) {

            for (ServiceDiagnosticsDTO diagnosticDTO : dto.getDiagnostics()) {

                ServiceDiagnostics diagnostics = new ServiceDiagnostics();

                diagnostics.setServiceDiagnosticId(
                        diagnosticDTO.getDiagnosticId()
                );

                diagnostics.setServiceId(dto.getServiceId());
                diagnostics.setQuestionId(diagnosticDTO.getQuestionId());
                diagnostics.setSelectedOptionId(diagnosticDTO.getSelectedOptionId());

                serviceDiagnosticsRepository.save(diagnostics);
            }
        }
    }
}