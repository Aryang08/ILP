package com.tcs.ilp.servease.bo;

import java.util.*;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.tcs.ilp.servease.dto.ApplianceDTO;
import com.tcs.ilp.servease.entity.Appliance;
import com.tcs.ilp.servease.entity.EntityStatus;
import com.tcs.ilp.servease.entity.ServiceStatus;
import com.tcs.ilp.servease.entity.AssignmentStatus;
import com.tcs.ilp.servease.exception.ApplianceExceptions.*;
import com.tcs.ilp.servease.repository.ApplianceRepository;

@Service
public class ApplianceBO {

    @Autowired
    private ApplianceRepository applianceRepository;

    // ✅ ADD
    @Transactional
    public void addAppliance(ApplianceDTO dto) {

        if (dto.getCustomerId() == null || dto.getCustomerId().isEmpty()) {
            throw new CustomerNotFoundException("Customer not found!");
        }

        if (dto.getApplianceId() == null || dto.getApplianceId().isBlank()) {
            dto.setApplianceId("APP-" + java.util.UUID.randomUUID().toString().substring(0, 8).toUpperCase());
        }

        if (applianceRepository.existsById(dto.getApplianceId())) {
            throw new DuplicateApplianceException("Already exists!");
        }

        Appliance a = convertToEntity(dto);
        a.setStatus(EntityStatus.ACTIVE);
        validateAppliance(a);

        applianceRepository.save(a);
    }

    // ✅ DELETE
    @Transactional
    public void deleteAppliance(String id) {
        if (!applianceRepository.existsById(id)) {
            throw new CustomerNotFoundException("Not found");
        }
        applianceRepository.softDeleteAppliance(id, EntityStatus.HIDDEN.name());
        applianceRepository.hideServicesByApplianceId(id, ServiceStatus.HIDDEN.name());
        applianceRepository.hideAssignmentsByApplianceId(id, AssignmentStatus.HIDDEN.name());
        applianceRepository.hideServiceHistoryByApplianceId(id, EntityStatus.HIDDEN.name());
    }

    // ✅ GET BY ID
    @Transactional(readOnly = true)
    public ApplianceDTO getApplianceById(String id) {
        return applianceRepository.findById(id)
                .map(this::convertToDTO)
                .orElseThrow(() -> new CustomerNotFoundException("Not found"));
    }

    // ✅ GET ALL (THIS WAS FAILING)
    @Transactional(readOnly = true)
    public List<ApplianceDTO> getAllAppliances() {

        List<Appliance> list = applianceRepository.findByStatusNot(EntityStatus.HIDDEN);

        return list.stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    // ✅ STATS
    @Transactional(readOnly = true)
    public int getTotalAppliances() {
        return applianceRepository.findAll().size();
    }

    @Transactional(readOnly = true)
    public long getInWarrantyCount() {
        return applianceRepository.findAll().stream()
                .filter(a -> "IN-WARRANTY".equalsIgnoreCase(a.getWarrantyStatus()))
                .count();
    }

    @Transactional(readOnly = true)
    public long getExpiredWarrantyCount() {
        return applianceRepository.findAll().stream()
                .filter(a -> "EXPIRED".equalsIgnoreCase(a.getWarrantyStatus()))
                .count();
    }

    @Transactional(readOnly = true)
    public Map<String, Long> getAppliancesByModel() {
        return applianceRepository.findAll().stream()
                .collect(Collectors.groupingBy(
                        Appliance::getModelNo,
                        Collectors.counting()
                ));
    }

    @Transactional(readOnly = true)
    public Map<String, Long> getAppliancesPerCustomer() {
        return applianceRepository.findAll().stream()
                .collect(Collectors.groupingBy(
                        Appliance::getCustomerId,
                        Collectors.counting()
                ));
    }

    // ✅ Helper Methods

    private Appliance convertToEntity(ApplianceDTO dto) {
        return new Appliance(
                dto.getApplianceId(),
                dto.getCustomerId(),
                dto.getSerialNo(),
                dto.getName(),
                dto.getModelNo(),
                dto.getPurchaseDate(),
                dto.getWarrantyStatus(),
                dto.getInstallationDate()
        );
    }

    private ApplianceDTO convertToDTO(Appliance a) {
        ApplianceDTO d = new ApplianceDTO();

        d.setApplianceId(a.getApplianceId());
        d.setCustomerId(a.getCustomerId());
        d.setSerialNo(a.getSerialNo());
        d.setName(a.getName());
        d.setModelNo(a.getModelNo());
        d.setPurchaseDate(a.getPurchaseDate());
        d.setWarrantyStatus(a.getWarrantyStatus());
        d.setInstallationDate(a.getInstallationDate());

        return d;
    }

    // ✅ ✅ ✅ FIXED NULL SAFE VALIDATION (MAIN FIX)
    private void validateAppliance(Appliance a) {

        if (a.getPurchaseDate() == null || a.getInstallationDate() == null) {
            throw new InvalidDateException("Dates cannot be null");
        }

        if (a.getInstallationDate().isBefore(a.getPurchaseDate())) {
            throw new InvalidDateException("Invalid dates");
        }
    }
}
