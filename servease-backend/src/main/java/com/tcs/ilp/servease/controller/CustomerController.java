package com.tcs.ilp.servease.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import com.tcs.ilp.servease.bo.CustomerBO;
import com.tcs.ilp.servease.entity.Customer;
import com.tcs.ilp.servease.config.SessionData;
import com.tcs.ilp.servease.util.AuthUtil;

import jakarta.servlet.http.HttpServletRequest;

@RestController
@RequestMapping("/customers")
public class CustomerController {

    @Autowired
    private CustomerBO service;

    // ✅ CREATE (PUBLIC - NO SESSION)
    @PostMapping
    public String createCustomer(@RequestBody Customer c) {
        service.insertCustomer(c);
        return "✅ Customer Created Successfully";
    }

    // ✅ GET ALL (ADMIN ONLY)
    @GetMapping
    public List<Customer> getAllCustomers(HttpServletRequest request) {

        try {
            SessionData session = AuthUtil.getSession(request);
            AuthUtil.checkAdmin(session);

            return service.getAllCustomers();

        } catch (RuntimeException e) {
            throw new RuntimeException("Access Denied - ADMIN only");
        }
    }
    // ✅ GET BY ID (ANY LOGGED-IN USER)
    @GetMapping("/{customerId}")
    public Customer getCustomerById(@PathVariable String customerId,
                                    HttpServletRequest request) {

        AuthUtil.getSession(request); // only validate session
        return service.getCustomerById(customerId);
    }

    // ✅ UPDATE (ANY LOGGED-IN USER)
    @PutMapping
    public String updateCustomer(@RequestBody Customer c,
                                 HttpServletRequest request) {

        AuthUtil.getSession(request);
        service.updateCustomer(c);

        return "✅ Customer Updated Successfully";
    }

    // ✅ DELETE (ADMIN ONLY)
    @DeleteMapping("/{customerId}")
    public String deleteCustomer(@PathVariable String customerId,
                                 HttpServletRequest request) {

        SessionData session = AuthUtil.getSession(request);
        AuthUtil.checkAdmin(session);

        service.deleteCustomer(customerId);
        return "✅ Customer Deleted Successfully";
    }
}
