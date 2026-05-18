package com.tcs.ilp.servease.bo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

import com.tcs.ilp.servease.dto.SupervisorDashboardDTO;
import com.tcs.ilp.servease.dto.TechnicianAvailabilityDTO;
import com.tcs.ilp.servease.dto.TodayScheduleDTO;

import com.tcs.ilp.servease.repository.AssignmentRepository;
import com.tcs.ilp.servease.repository.ServiceMainRepository;
import com.tcs.ilp.servease.repository.TechnicianRepository;

@Service
public class SupervisorDashboardBO {

    @Autowired
    private ServiceMainRepository serviceMainRepository;

    @Autowired
    private AssignmentRepository assignmentRepository;

    @Autowired
    private TechnicianRepository technicianRepository;

    public SupervisorDashboardDTO getDashboard(
            String supervisorId,
            int techPage,
            int techSize,
            int schedPage,
            int schedSize
    ) {

        SupervisorDashboardDTO dto = new SupervisorDashboardDTO();

        // =========================
        //  SERVICE METRICS
        // =========================
        dto.setTotalOpen(serviceMainRepository.countByStatus("OPEN"));
        dto.setPendingAssignment(serviceMainRepository.countUnassigned());
        dto.setDelayed(serviceMainRepository.countDelayed());

        // =========================
        //  ASSIGNMENT METRICS
        // =========================
        dto.setInProgress(assignmentRepository.countByStatus("IN_PROGRESS"));

        // =========================
        //  TECHNICIAN AVAILABILITY (PAGINATED)
        // =========================
        List<Object[]> techResults = technicianRepository
                .getTechnicianAvailabilityRawPaginated(
                        supervisorId,
                        techSize,
                        techPage * techSize   // offset
                );

        List<TechnicianAvailabilityDTO> techList = new ArrayList<>();

        for (Object[] row : techResults) {

            TechnicianAvailabilityDTO t = new TechnicianAvailabilityDTO();

            t.setTechnicianId((String) row[0]);
            t.setTechnicianName((String) row[1]);

            int active = ((Number) row[2]).intValue();
            t.setActiveAssignments(active);
            t.setAvailable(active == 0);

            techList.add(t);
        }

        dto.setTechnicianAvailability(techList);

        // =========================
        //  TODAY SCHEDULE (PAGINATED)
        // =========================
        List<Object[]> schedResults = assignmentRepository
                .getTodayScheduleRawPaginated(
                        supervisorId,
                        schedSize,
                        schedPage * schedSize
                );

        List<TodayScheduleDTO> schedList = new ArrayList<>();

        for (Object[] row : schedResults) {

            TodayScheduleDTO s = new TodayScheduleDTO();

            s.setServiceId((String) row[0]);
            s.setTechnicianId((String) row[1]);
            s.setScheduledDate(((java.sql.Date) row[2]).toLocalDate());
            s.setStatus((String) row[3]);

            schedList.add(s);
        }

        dto.setTodaysSchedule(schedList);

        return dto;
    }
}
