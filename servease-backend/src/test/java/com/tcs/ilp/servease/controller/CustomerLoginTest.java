package com.tcs.ilp.servease.controller;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import java.util.List;
import java.util.Arrays;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;

import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.fasterxml.jackson.databind.ObjectMapper;

import com.tcs.ilp.servease.bo.CustomerBO;
import com.tcs.ilp.servease.entity.Customer; 
import com.tcs.ilp.servease.entity.UserLogin;
import com.tcs.ilp.servease.repository.CustomerRepository;
import com.tcs.ilp.servease.repository.UserLoginRepository;

import com.tcs.ilp.servease.config.SessionManager;
import com.tcs.ilp.servease.config.SessionData;
import com.tcs.ilp.servease.config.Role;
import java.util.Optional;

@SpringBootTest
@AutoConfigureMockMvc
public class CustomerLoginTest {

    @Autowired
    private CustomerBO service;

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private CustomerRepository dao;

    @MockBean
    private UserLoginRepository loginDAO;

    @MockBean
    private SessionManager sessionManager;   // ✅ FIX

    private Customer customer; 
    private ObjectMapper objectMapper;

    private static final String SESSION_ID = "dummy-session";

    // ✅ SESSION HELPER
    private SessionData getSession() {
        SessionData session = new SessionData();
        session.setUserId("U101");
        session.setRole(Role.ADMIN);
        return session;
    }

    @BeforeEach
    void setUp() {

        customer = new Customer("C101", "U101", "Ahmedabad", "380001");

        objectMapper = new ObjectMapper();

        // ✅ CRITICAL FIX
        when(sessionManager.getSession(SESSION_ID))
                .thenReturn(getSession());
    }

    @AfterEach
    void tearDown() {}

    // =========================
    // ✅ BO TEST CASES (UNCHANGED)
    // =========================

    @Test
    void testInsertCustomerSuccess() {
        service.insertCustomer(customer);
        verify(loginDAO).save(any(UserLogin.class));
        verify(dao).save(customer);
    }

    @Test
    void testInsertCustomerNull() {
        assertThrows(Exception.class, () -> service.insertCustomer(null));
    }

    @Test
    void testGetAllCustomers() {
        when(dao.findAll()).thenReturn(Arrays.asList(customer));
        assertEquals(1, service.getAllCustomers().size());
    }

    @Test
    void testGetAllCustomersEmpty() {
        when(dao.findAll()).thenReturn(List.of());
        assertTrue(service.getAllCustomers().isEmpty());
    }

    @Test
    void testGetCustomerByIdFound() {
        when(dao.findById("C101")).thenReturn(java.util.Optional.of(customer));
        assertNotNull(service.getCustomerById("C101"));
    }

    @Test
    void testGetCustomerByIdNotFound() {
        when(dao.findById("C101")).thenReturn(java.util.Optional.empty());
        assertNull(service.getCustomerById("C101"));
    }

    @Test
    void testUpdateCustomer() {
        service.updateCustomer(customer);
        verify(dao).save(customer);
    }

    @Test
    void testDeleteCustomer() {
        service.deleteCustomer("C101");
        verify(dao).deleteById("C101");
    }

    @Test
    void testLoginSuccess() {
        when(dao.findByUserId("U101")).thenReturn(java.util.Optional.of(customer));
        assertEquals("Customer Dashboard", service.login("U101", "380001"));
    }

    @Test
    void testLoginInvalidPincode() {
        when(dao.findByUserId("U101")).thenReturn(java.util.Optional.of(customer));
        assertEquals("Invalid Credentials", service.login("U101", "000000"));
    }

    @Test
    void testLoginUserNotFound() {
        when(dao.findByUserId("U101")).thenReturn(java.util.Optional.empty());
        assertEquals("Invalid Credentials", service.login("U101", "380001"));
    }

    @Test
    void testResetPincodeSuccess() {
        when(dao.findByUserId("U101")).thenReturn(java.util.Optional.of(customer));
        assertEquals("Pincode Updated", service.resetPincode("U101", "123456"));
    }

    @Test
    void testResetPincodeUserNotFound() {

        // ✅ FIXED
        when(dao.findByUserId("U101")).thenReturn(Optional.empty());

        assertEquals("User Not Found", service.resetPincode("U101", "123456"));
    }

    // =========================
    // ✅ CONTROLLER TEST CASES (FIXED ✅)
    // =========================

    @Test
    void testCreateCustomerAPI() throws Exception {

        mockMvc.perform(post("/customers")
                .header("sessionId", SESSION_ID)
                .contentType("application/json")
                .content(objectMapper.writeValueAsString(customer)))
                .andExpect(status().isOk());
    }

    @Test
    void testGetAllCustomersAPI() throws Exception {

        when(dao.findAll()).thenReturn(Arrays.asList(customer));

        mockMvc.perform(get("/customers")
                .header("sessionId", SESSION_ID))
                .andExpect(status().isOk());
    }

    @Test
    void testGetAllCustomersAPIEmpty() throws Exception {

        when(dao.findAll()).thenReturn(List.of());

        mockMvc.perform(get("/customers")
                .header("sessionId", SESSION_ID))
                .andExpect(status().isOk());
    }

    @Test
    void testGetCustomerByIdAPI() throws Exception {

        when(dao.findById("C101")).thenReturn(java.util.Optional.of(customer));

        mockMvc.perform(get("/customers/C101")
                .header("sessionId", SESSION_ID))
                .andExpect(status().isOk());
    }

    @Test
    void testDeleteCustomerAPI() throws Exception {

        mockMvc.perform(delete("/customers/C101")
                .header("sessionId", SESSION_ID))
                .andExpect(status().isOk());
    }

    @Test
    void testUpdateCustomerAPI() throws Exception {

        mockMvc.perform(put("/customers")
                .header("sessionId", SESSION_ID)
                .contentType("application/json")
                .content(objectMapper.writeValueAsString(customer)))
                .andExpect(status().isOk());
    }
}