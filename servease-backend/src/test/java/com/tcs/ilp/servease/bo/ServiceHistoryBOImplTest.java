package com.tcs.ilp.servease.bo;

import com.tcs.ilp.servease.bo.ServiceHistoryBOImpl;
import com.tcs.ilp.servease.entity.ServiceHistory;
import com.tcs.ilp.servease.exception.ServiceHistoryException;
import com.tcs.ilp.servease.repository.ServiceHistoryRepository;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;
import java.util.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class ServiceHistoryBOImplTest {

    @Mock
    private ServiceHistoryRepository repo;

    @InjectMocks
    private ServiceHistoryBOImpl bo;

    private ServiceHistory history;

    @BeforeEach
    void setup() {
        history = new ServiceHistory();
        history.setHistoryId("h1");
        history.setServiceId("s1");
        history.setTechnicianId("t1");
        history.setReopenDate(LocalDateTime.now());
    }

    // ✅ TEST ADD SUCCESS
    @Test
    void testAddServiceHistory_success() throws Exception {
        bo.addServiceHistory(history);
        verify(repo, times(1)).save(history);
    }

    // ✅ TEST NULL HISTORY
    @Test
    void testAddServiceHistory_null() {
        Exception ex = assertThrows(ServiceHistoryException.class,
                () -> bo.addServiceHistory(null));

        assertEquals("History cannot be null", ex.getMessage());
    }

    // ✅ TEST INVALID DATE
    @Test
    void testAddServiceHistory_invalidDate() {
        history.setReopenDate(LocalDateTime.now().minusYears(2));

        Exception ex = assertThrows(ServiceHistoryException.class,
                () -> bo.addServiceHistory(history));

        assertEquals("Invalid reopen date", ex.getMessage());
    }

    // ✅ TEST GET BY ID SUCCESS
    @Test
    void testGetHistoryById_success() throws Exception {

        Object[] mockData = {
                "h1", "s1", "t1",
                "Tech1", "AC", "b1",
                new java.math.BigDecimal("2000"),
                java.sql.Timestamp.valueOf(LocalDateTime.now())
        };

        when(repo.getHistoryByIdRaw("h1"))
                .thenReturn(Collections.singletonList(mockData)); // ✅ FIX

        var result = bo.getHistoryById("h1");

        assertEquals("h1", result.getHistoryId());
        assertEquals("s1", result.getServiceId());
    }

    // ✅ TEST GET BY ID NOT FOUND
    @Test
    void testGetHistoryById_notFound() {

        when(repo.getHistoryByIdRaw("h1"))
                .thenReturn(Collections.emptyList());

        Exception ex = assertThrows(ServiceHistoryException.class,
                () -> bo.getHistoryById("h1"));

        assertTrue(ex.getMessage().contains("Not found"));
    }

    // ✅ TEST GET ALL SUCCESS
    @Test
    void testGetAllHistory_success() throws Exception {

        Object[] mockData = {
                "h1", "s1", "t1",
                "Tech1", "AC", "b1",
                new java.math.BigDecimal("2000"),
                java.sql.Timestamp.valueOf(LocalDateTime.now())
        };

        when(repo.getAllHistoryRaw())
                .thenReturn(Collections.singletonList(mockData)); // ✅ FIX

        var result = bo.getAllHistory();

        assertEquals(1, result.size());
    }

    // ✅ TEST UPDATE SUCCESS
    @Test
    void testUpdateServiceHistory_success() throws Exception {

        when(repo.findById("h1"))
                .thenReturn(Optional.of(history));

        bo.updateServiceHistory("h1", history);

        verify(repo).save(any(ServiceHistory.class));
    }

    // ✅ TEST UPDATE NOT FOUND
    @Test
    void testUpdateServiceHistory_notFound() {

        when(repo.findById("h1"))
                .thenReturn(Optional.empty());

        Exception ex = assertThrows(ServiceHistoryException.class,
                () -> bo.updateServiceHistory("h1", history));

        assertEquals("Not found", ex.getMessage());
    }
}