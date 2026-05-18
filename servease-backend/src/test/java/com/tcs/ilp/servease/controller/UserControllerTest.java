package com.tcs.ilp.servease.controller;   // ✅ FIXED PACKAGE

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import java.util.Arrays;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

import com.fasterxml.jackson.databind.ObjectMapper;

import com.tcs.ilp.servease.entity.User;
import com.tcs.ilp.servease.entity.UserLogin;
import com.tcs.ilp.servease.repository.UserRepository;
import com.tcs.ilp.servease.repository.UserLoginRepository;
import com.tcs.ilp.servease.util.PasswordUtil;

import com.tcs.ilp.servease.config.SessionManager;
import com.tcs.ilp.servease.config.SessionData;
import com.tcs.ilp.servease.config.Role;

@SuppressWarnings("removal")
@SpringBootTest
@AutoConfigureMockMvc(addFilters = false)
public class UserControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private UserRepository userDAO;

    @MockBean
    private UserLoginRepository loginDAO;

    @MockBean
    private SessionManager sessionManager;   // ✅ CRITICAL

    private User user;
    private UserLogin login;
    private ObjectMapper objectMapper;

    private static final String SESSION_ID = "testSession";

    // ✅ SESSION CREATION
    private SessionData getSession() {
        SessionData session = new SessionData();
        session.setUserId("U101");
        session.setRole(Role.ADMIN);
        return session;
    }

    @BeforeEach
    void setUp() {

        user = new User("U101", "Ayush", "test@mail.com", "9999999999", "ADMIN");

        String salt = PasswordUtil.generateSalt();
        String hash = PasswordUtil.hashPassword("Cust@123", salt);

        login = new UserLogin("U101", hash, salt, true);

        objectMapper = new ObjectMapper();

        // ✅ FINAL FIX (exact match)
        when(sessionManager.getSession(SESSION_ID))
                .thenReturn(getSession());
    }

    // =====================================================
    // ✅ CONTROLLER TESTS
    // =====================================================

    @Test
    void testGetAllUsersAPI() throws Exception {

        when(userDAO.findAll()).thenReturn(Arrays.asList(user));

        mockMvc.perform(get("/user")
                .header("sessionId", SESSION_ID))
                .andExpect(status().isOk());
    }

    @Test
    void testGetUserByIdAPI() throws Exception {

        when(userDAO.findById("U101")).thenReturn(Optional.of(user));

        mockMvc.perform(get("/user/U101")
                .header("sessionId", SESSION_ID))
                .andExpect(status().isOk());
    }

    @Test
    void testDeleteUserAPI() throws Exception {

        doNothing().when(userDAO).deleteById("U101");

        mockMvc.perform(delete("/user/U101")
                .header("sessionId", SESSION_ID))
                .andExpect(status().isOk());
    }

    @Test
    void testResetPasswordAPI() throws Exception {

        when(loginDAO.findById("U101")).thenReturn(Optional.of(login));
        when(loginDAO.save(any(UserLogin.class))).thenReturn(login);

        mockMvc.perform(post("/user/reset")
                .param("userId", "U101")
                .param("newPassword", "NewPass")
                .header("sessionId", SESSION_ID))
                .andExpect(status().isOk());
    }

    @Test
    void testLoginAPI_Admin() throws Exception {

        login.setFirstLogin(false);

        when(loginDAO.findById("U101")).thenReturn(Optional.of(login));
        when(userDAO.findById("U101")).thenReturn(Optional.of(user));

        mockMvc.perform(get("/user/login")
                .param("userId", "U101")
                .param("password", "Cust@123")
        		.header("sessionId", SESSION_ID))
                .andExpect(status().isOk());
    }

    @Test
    void testLoginAPI_Invalid() throws Exception {

        when(loginDAO.findById("U101")).thenReturn(Optional.empty());

        mockMvc.perform(get("/user/login")
                .param("userId", "U101")
                .param("password", "wrong")
                .header("sessionId", SESSION_ID))
                .andExpect(status().isOk());
    }

    @Test
    void testLogoutAPI() throws Exception {

        mockMvc.perform(get("/user/logout")
                .param("userId", "U101")
                .header("sessionId", SESSION_ID))
                .andExpect(status().isOk());
    }
}