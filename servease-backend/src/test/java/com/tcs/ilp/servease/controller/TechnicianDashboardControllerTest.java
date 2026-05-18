package com.tcs.ilp.servease.controller;

import com.tcs.ilp.servease.bo.TechnicianDashboardBO;
import com.tcs.ilp.servease.dto.TechnicianDashboardDTO;
import com.tcs.ilp.servease.entity.SparePart;
import com.tcs.ilp.servease.entity.StatusENUM;
import com.tcs.ilp.servease.config.SessionData;
import com.tcs.ilp.servease.config.Role;

import jakarta.servlet.http.HttpServletRequest;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class TechnicianDashboardControllerTest {

    @Mock
    private TechnicianDashboardBO service;

    @InjectMocks
    private TechnicianDashboardController controller;

    //  SESSION + REQUEST MOCK
    private HttpServletRequest getRequest() {
        HttpServletRequest request = mock(HttpServletRequest.class);

        SessionData session = new SessionData();
        session.setRole(Role.TECHNICIAN);
        session.setUserId("T1");

        when(request.getAttribute("session")).thenReturn(session);

        return request;
    }

    //  1 Dashboard
    @Test
    void testGetDashboard() {

        when(service.getDashboard("T1"))
                .thenReturn(List.of(
                        new TechnicianDashboardDTO(
                                "T1", "S1", "AC", "Issue", "ASSIGNED",
                                "John", "Address", "time", "desc")));

        List<TechnicianDashboardDTO> result =
                controller.getDashboard("T1", getRequest());

        assertEquals(1, result.size());
    }

    //  2 Assigned
    @Test
    void testAssigned() {

        List<Object[]> list = new ArrayList<>();
        list.add(new Object[]{"A1", "S1"});

        when(service.getAssigned("T1")).thenReturn(list);

        List<Object[]> result =
                controller.assigned("T1", getRequest());

        assertEquals(1, result.size());
    }

    //  3 Completed
    @Test
    void testCompleted() {

        List<Object[]> list = new ArrayList<>();
        list.add(new Object[]{"A1", "S1"});

        when(service.getCompleted("T1")).thenReturn(list);

        List<Object[]> result =
                controller.completed("T1", getRequest());

        assertEquals(1, result.size());
    }

    //  4 History
    @Test
    void testHistory() {

        List<Object[]> list = new ArrayList<>();
        list.add(new Object[]{"H1", "S1"});

        when(service.getHistory("T1")).thenReturn(list);

        List<Object[]> result =
                controller.history("T1", getRequest());

        assertEquals(1, result.size());
    }

    //  5 Requests
    @Test
    void testRequests() {

        List<Object[]> list = new ArrayList<>();
        list.add(new Object[]{"S1", "AC"});

        when(service.getRequests("T1")).thenReturn(list);

        List<Object[]> result =
                controller.requests("T1", getRequest());

        assertEquals(1, result.size());
    }

    //  6 Initiate
    @Test
    void testInitiate() {

        String response = controller.initiate("A1", getRequest());

        assertEquals("Technician is on the way", response);
        verify(service).updateStatus("A1", StatusENUM.ON_THE_WAY);
    }

    //  7 Start
    @Test
    void testStart() {

        String response = controller.start("A1", getRequest());

        assertEquals("Job started", response);
        verify(service).updateStatus("A1", StatusENUM.IN_PROGRESS);
    }

    //  8 Complete
    @Test
    void testComplete() {

        String response =
                controller.complete("A1", "S1", "Fixed", getRequest());

        assertEquals("Job completed and waiting for feedback", response);
        verify(service).completeService("A1", "S1", "Fixed");
    }

    //  9 Spare Parts
    @Test
    void testSpareParts() {

        when(service.getSparePartsByCategory("AC"))
                .thenReturn(List.of(new SparePart(1, "Fan", "AC", 5, 100f)));

        List<SparePart> result =
                controller.getSpareParts("AC", getRequest());

        assertEquals(1, result.size());
    }

    // 10 History by appliance
    @Test
    void testHistoryByAppliance() {

        List<Object[]> list = new ArrayList<>();
        list.add(new Object[]{"H1", "S1"});

        when(service.getHistoryByAppliance("AP1")).thenReturn(list);

        List<Object[]> result =
                controller.getHistoryByAppliance("AP1", getRequest());

        assertEquals(1, result.size());
    }
}