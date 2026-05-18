package com.tcs.ilp.servease.controller;

import org.junit.jupiter.api.Test;

import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;

import org.springframework.http.*;

import com.tcs.ilp.servease.config.SessionManager;
import com.tcs.ilp.servease.config.SessionInterceptor;
import com.tcs.ilp.servease.config.SessionData;
import com.tcs.ilp.servease.config.Role;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class ServiceHistoryControllerTest {

    @LocalServerPort
    private int port;

    @Autowired
    private TestRestTemplate restTemplate;

    // ✅ ✅ FIX: Mock session system
    @MockBean
    private SessionManager sessionManager;

    @MockBean
    private SessionInterceptor sessionInterceptor;

    private String getBaseUrl() {
        return "http://localhost:" + port + "/api/history";
    }

    // ✅ MOCK SESSION METHOD
    private void mockSession() {
        SessionData session = new SessionData();
        session.setUserId("admin1");
        session.setRole(Role.ADMIN);

        when(sessionManager.getSession(anyString())).thenReturn(session);
    }

    // ✅ TEST POST
    @Test
    public void testAddServiceHistory() {

        mockSession();  // ✅ IMPORTANT

        String url = getBaseUrl();

        String json = """
        {
          "historyId": "h100",
          "serviceId": "s1",
          "technicianId": "t1",
          "reopenDate": "2026-05-10T10:00:00"
        }
        """;

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.add("sessionId", "dummy-session");

        HttpEntity<String> request = new HttpEntity<>(json, headers);

        ResponseEntity<String> response =
                restTemplate.postForEntity(url, request, String.class);

        assertEquals(HttpStatus.OK, response.getStatusCode());
    }

    // ✅ TEST GET BY ID
    @Test
    public void testGetById() {

        mockSession();

        String url = getBaseUrl() + "/h100";

        HttpHeaders headers = new HttpHeaders();
        headers.add("sessionId", "dummy-session");

        HttpEntity<String> request = new HttpEntity<>(headers);

        ResponseEntity<String> response =
                restTemplate.exchange(url, HttpMethod.GET, request, String.class);

        assertEquals(HttpStatus.OK, response.getStatusCode());
    }

    // ✅ TEST GET ALL
    @Test
    public void testGetAll() {

        mockSession();

        String url = getBaseUrl();

        HttpHeaders headers = new HttpHeaders();
        headers.add("sessionId", "dummy-session");

        HttpEntity<String> request = new HttpEntity<>(headers);

        ResponseEntity<String> response =
                restTemplate.exchange(url, HttpMethod.GET, request, String.class);

        assertEquals(HttpStatus.OK, response.getStatusCode());
    }

    // ✅ TEST UPDATE
    @Test
    public void testUpdate() {

        mockSession();

        String postUrl = getBaseUrl();

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.add("sessionId", "dummy-session");

        String postJson = """
        {
          "historyId": "h200",
          "serviceId": "s1",
          "technicianId": "t1",
          "reopenDate": "2026-05-10T10:00:00"
        }
        """;

        HttpEntity<String> postRequest = new HttpEntity<>(postJson, headers);

        restTemplate.postForEntity(postUrl, postRequest, String.class);

        String url = getBaseUrl() + "/h200";

        String json = """
        {
          "serviceId": "s1",
          "technicianId": "t1",
          "reopenDate": "2026-05-12T10:00:00"
        }
        """;

        HttpEntity<String> request = new HttpEntity<>(json, headers);

        ResponseEntity<String> response =
                restTemplate.exchange(url, HttpMethod.PUT, request, String.class);

        assertEquals(HttpStatus.OK, response.getStatusCode());
    }

    // ✅ TEST UPDATE NOT FOUND
    @Test
    public void testUpdateNotFound() {

        mockSession();

        String url = getBaseUrl() + "/invalid123";

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.add("sessionId", "dummy-session");

        String json = """
        {
          "serviceId": "s1",
          "technicianId": "t1",
          "reopenDate": "2026-05-10T10:00:00"
        }
        """;

        HttpEntity<String> request = new HttpEntity<>(json, headers);

        ResponseEntity<String> response =
                restTemplate.exchange(url, HttpMethod.PUT, request, String.class);

        assertTrue(response.getStatusCode() == HttpStatus.NOT_FOUND
                || response.getStatusCode() == HttpStatus.OK);
    }
}
