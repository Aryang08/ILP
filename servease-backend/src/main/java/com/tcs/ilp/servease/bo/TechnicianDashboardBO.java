package com.tcs.ilp.servease.bo;

import com.tcs.ilp.servease.dto.TechnicianDashboardDTO;
import com.tcs.ilp.servease.entity.ServiceUsedPart;
import com.tcs.ilp.servease.entity.SparePart;
import com.tcs.ilp.servease.entity.StatusENUM;
import com.tcs.ilp.servease.repository.ServiceUsedPartRepository;
import com.tcs.ilp.servease.repository.SparePartRepository;
import com.tcs.ilp.servease.repository.TechnicianDashboardRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;
import java.util.stream.Collectors;

@Service
public class TechnicianDashboardBO {

    private final TechnicianDashboardRepository repo;

    @Autowired
    private SparePartRepository sparePartRepo;

    @Autowired
    private ServiceUsedPartRepository usedPartRepo;

    public TechnicianDashboardBO(TechnicianDashboardRepository repo) {
        this.repo = repo;
    }

    // ✅ DASHBOARD
    @Transactional(readOnly = true)
    public List<TechnicianDashboardDTO> getDashboard(String techId) {
        return repo.getDashboard(techId).stream()
                .map(row -> new TechnicianDashboardDTO(
                        (String) row[0],
                        (String) row[1],
                        (String) row[2],
                        (String) row[3],
                        (String) row[4],
                        (String) row[5],
                        (String) row[6],
                        row[7] != null ? row[7].toString() : null,
                        (String) row[8]
                ))
                .collect(Collectors.toList());
    }

    // ✅ ASSIGNED
    public List<Object[]> getAssigned(String techId) {
        return repo.getAssignedJobs(techId);
    }

    // ✅ COMPLETED
    public List<Object[]> getCompleted(String techId) {
        return repo.getCompletedJobs(techId);
    }

    // ✅ TECHNICIAN HISTORY
    public List<Object[]> getHistory(String techId) {
        return repo.getServiceHistory(techId);
    }

    // ✅ SERVICE REQUESTS
    public List<Object[]> getRequests(String techId) {
        return repo.getServiceRequests(techId);
    }

    // ✅ STATUS UPDATE (GENERIC)
    @Transactional
    public void updateStatus(String assignmentId, StatusENUM status) {
        repo.updateStatus(assignmentId, status.name());
    }

    // ✅ COMPLETE SERVICE
    @Transactional
    public void completeService(String assignmentId, String serviceId, String description) {

        // update status
        repo.updateStatus(assignmentId, StatusENUM.COMPLETED.name());

        // insert completion
        repo.addCompletion(
                assignmentId,
                serviceId,
                description
        );

        // next stage
        repo.updateStatus(assignmentId, StatusENUM.WAITING_FOR_FEEDBACK.name());
    }

    // ✅ SPARE PARTS BY CATEGORY
    public List<SparePart> getSparePartsByCategory(String category) {
        return sparePartRepo.findByCategory(category);
    }

    // ✅ SAVE USED PARTS (NO UUID NEEDED)
//    @Transactional
//    public void saveUsedParts(String serviceId, List<Integer> partSnos) {
//
//        for (Integer sno : partSnos) {
//            ServiceUsedPart part = new ServiceUsedPart();
//            part.setServiceId(serviceId);
//            part.setId(sno);
//
//            usedPartRepo.save(part);
//        }
//    }

    // ✅ APPLIANCE HISTORY (NEW)
    public List<Object[]> getHistoryByAppliance(String applianceId) {
        return repo.getHistoryByAppliance(applianceId);
    }
}