package com.tcs.ilp.servease.bo;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import java.util.*;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.tcs.ilp.servease.controller.ServiceCompletionController;
import com.tcs.ilp.servease.entity.ServiceCompletion;
import com.tcs.ilp.servease.exception.ServiceCompletionExceptionMain.ServiceCompletionNotFoundException;
import com.tcs.ilp.servease.repository.ServiceCompletionRepository;
import com.tcs.ilp.servease.config.SessionData;
import com.tcs.ilp.servease.config.Role;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.*;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

@ExtendWith(MockitoExtension.class)
class ServiceCompletionBOTest {

    private static final String COMPLETION_ID = "co1";
    private static final String SERVICE_ID = "s1";
    private static final String BASE_URL = "/api/service-completions";

    // ✅ BO
    @Mock
    private ServiceCompletionRepository repository;

    @InjectMocks
    private ServiceCompletionBO bo;

    // ✅ CONTROLLER
    @Mock
    private ServiceCompletionBO service;

    @InjectMocks
    private ServiceCompletionController controller;

    private MockMvc mockMvc;
    private ObjectMapper objectMapper = new ObjectMapper();

    // ✅ SESSION MOCK
    private SessionData getMockSession() {
        SessionData session = new SessionData();
        session.setUserId("admin1");
        session.setRole(Role.ADMIN);
        return session;
    }

    private ServiceCompletion getCompletion() {
        ServiceCompletion c = new ServiceCompletion();
        c.setCompletionId(COMPLETION_ID);
        c.setServiceId(SERVICE_ID);
        return c;
    }

    private void initMockMvc() {
        mockMvc = MockMvcBuilders.standaloneSetup(controller).build();
    }

    // ==================== BO TESTS ====================

    @Test
    void testAddServiceCompletion_Positive() {
        ServiceCompletion c = getCompletion();

        bo.addServiceCompletion(c);

        verify(repository).save(c);
    }

    @Test
    void testGetCompletionById_Positive()throws Exception{

        ServiceCompletion c = getCompletion();

        when(repository.findById(COMPLETION_ID))
                .thenReturn(Optional.of(c));

        ServiceCompletion result = bo.getCompletionById(COMPLETION_ID);

        assertNotNull(result);
        assertEquals(COMPLETION_ID, result.getCompletionId());
    }

    @Test
    void testGetCompletionById_NotFound() {

        when(repository.findById(COMPLETION_ID))
                .thenReturn(Optional.empty());

        assertThrows(ServiceCompletionNotFoundException.class,
                () -> bo.getCompletionById(COMPLETION_ID));
    }

    @Test
    void testGetByServiceId_Positive() {

        when(repository.findByServiceId(SERVICE_ID))
                .thenReturn(Arrays.asList(getCompletion()));

        assertEquals(1, bo.getCompletionsByServiceId(SERVICE_ID).size());
    }

    @Test
    void testUpdate_Positive() throws Exception{

        ServiceCompletion c = getCompletion();

        when(repository.existsById(COMPLETION_ID)).thenReturn(true);

        bo.updateServiceCompletion(c);

        verify(repository).save(c);
    }

    @Test
    void testDelete_Positive() throws Exception{

        when(repository.existsById(COMPLETION_ID)).thenReturn(true);

        bo.deleteServiceCompletion(COMPLETION_ID);

        verify(repository).deleteById(COMPLETION_ID);
    }

    // ==================== CONTROLLER TESTS ====================

    @Test
    void testController_Add_Positive() throws Exception {

        initMockMvc();

        ServiceCompletion c = getCompletion();

        mockMvc.perform(post(BASE_URL)
                        .requestAttr("session", getMockSession())   // ✅ FIX
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(c)))
                .andExpect(status().isOk())
                .andExpect(content().string("Created"));

        verify(service).addServiceCompletion(any(ServiceCompletion.class));
    }

    @Test
    void testController_GetById_Positive() throws Exception {

        initMockMvc();

        when(service.getCompletionById(COMPLETION_ID))
                .thenReturn(getCompletion());

        mockMvc.perform(get(BASE_URL + "/" + COMPLETION_ID)
                        .requestAttr("session", getMockSession()))   // ✅ FIX
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.completionId").value(COMPLETION_ID));
    }

    @Test
    void testController_GetByServiceId() throws Exception {

        initMockMvc();

        when(service.getCompletionsByServiceId(SERVICE_ID))
                .thenReturn(Arrays.asList(getCompletion()));

        mockMvc.perform(get(BASE_URL + "/service/" + SERVICE_ID)
                        .requestAttr("session", getMockSession()))   // ✅ FIX
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()").value(1));
    }

    @Test
    void testController_Delete_Positive() throws Exception {

        initMockMvc();

        mockMvc.perform(delete(BASE_URL + "/" + COMPLETION_ID)
                        .requestAttr("session", getMockSession()))   // ✅ FIX
                .andExpect(status().isOk())
                .andExpect(content().string("Deleted"));

        verify(service).deleteServiceCompletion(COMPLETION_ID);
    }
}