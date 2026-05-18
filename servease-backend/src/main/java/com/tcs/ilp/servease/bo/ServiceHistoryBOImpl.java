package com.tcs.ilp.servease.bo;

import com.tcs.ilp.servease.dto.ServiceHistoryDTO;
import com.tcs.ilp.servease.entity.ServiceHistory;
import com.tcs.ilp.servease.exception.ServiceHistoryException;
import com.tcs.ilp.servease.repository.ServiceHistoryRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;
import java.math.BigDecimal;

@Service
public class ServiceHistoryBOImpl {

    @Autowired
    private ServiceHistoryRepository repo;

    @Transactional
    public void addServiceHistory(ServiceHistory history)
            throws ServiceHistoryException {

        if (history == null)
            throw new ServiceHistoryException("History cannot be null");

        if (history.getReopenDate() == null)
            throw new ServiceHistoryException("Reopen date required");

        if (history.getReopenDate()
                .isBefore(LocalDateTime.now().minusYears(1))) {
            throw new ServiceHistoryException("Invalid reopen date");
        }

        repo.save(history);
    }

    // ✅ ✅ FIXED Mapping method (IMPORTANT CHANGE)
    private ServiceHistoryDTO map(Object[] r) {

        LocalDateTime reopenDate = null;

        // ✅ Handle Timestamp → LocalDateTime conversion
        if (r[7] instanceof java.sql.Timestamp) {
            reopenDate = ((java.sql.Timestamp) r[7]).toLocalDateTime();
        } else if (r[7] instanceof LocalDateTime) {
            reopenDate = (LocalDateTime) r[7];
        }

        return new ServiceHistoryDTO(
                (String) r[0],
                (String) r[1],
                (String) r[2],
                (String) r[3],
                (String) r[4],
                (String) r[5],
                (BigDecimal) r[6],
                reopenDate
        );
    }

    @Transactional(readOnly = true)
    public ServiceHistoryDTO getHistoryById(String id)
            throws ServiceHistoryException {

        List<Object[]> rows = repo.getHistoryByIdRaw(id);

        if (rows == null || rows.isEmpty()) {
            throw new ServiceHistoryException("Not found: " + id);
        }

        return map(rows.get(0)); // ✅ take first record
    }

    @Transactional(readOnly = true)
    public List<ServiceHistoryDTO> getHistoryByServiceId(String serviceId)
            throws ServiceHistoryException {

        List<Object[]> rows = repo.getHistoryByServiceIdRaw(serviceId);

        if (rows.isEmpty())
            throw new ServiceHistoryException("No data");

        return rows.stream()
                .map(this::map)
                .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public List<ServiceHistoryDTO> getAllHistory()
            throws ServiceHistoryException {

        List<Object[]> rows = repo.getAllHistoryRaw();

        if (rows.isEmpty())
            throw new ServiceHistoryException("No data");

        return rows.stream()
                .map(this::map)
                .collect(Collectors.toList());
    }

    @Transactional
    public void updateServiceHistory(String id, ServiceHistory history)
            throws ServiceHistoryException {

        ServiceHistory existing = repo.findById(id)
                .orElseThrow(() ->
                        new ServiceHistoryException("Not found"));

        existing.setServiceId(history.getServiceId());
        existing.setTechnicianId(history.getTechnicianId());
        existing.setReopenDate(history.getReopenDate());

        repo.save(existing);
    }
}