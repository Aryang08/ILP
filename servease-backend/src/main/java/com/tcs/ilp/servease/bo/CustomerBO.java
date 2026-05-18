package com.tcs.ilp.servease.bo;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.tcs.ilp.servease.entity.Customer;
import com.tcs.ilp.servease.entity.UserLogin;
import com.tcs.ilp.servease.repository.CustomerRepository;
import com.tcs.ilp.servease.repository.UserLoginRepository;

@Service
public class CustomerBO {

    @Autowired
    private CustomerRepository dao;

    @Autowired
    private UserLoginRepository loginDAO;

    // ✅ CREATE
    public void insertCustomer(Customer c) {

        if (c == null || c.getUserId() == null) {
            throw new RuntimeException("Customer or UserId cannot be null");
        }

        // ✅ create login
        UserLogin login = new UserLogin();
        login.setUserId(c.getUserId());
        login.setPasswordHash("Cust@123");
        login.setFirstLogin(false);

        loginDAO.save(login);

        dao.save(c);
    }

    // ✅ GET ALL
    public List<Customer> getAllCustomers() {
        return dao.findAll();
    }

    // ✅ GET BY ID
    public Customer getCustomerById(String id) {

        if (id == null) {
            throw new RuntimeException("Customer ID cannot be null");
        }

        return dao.findById(id).orElse(null);
    }

    // ✅ UPDATE
    public void updateCustomer(Customer c) {

        if (c == null || c.getCustomerId() == null) {
            throw new RuntimeException("Customer or ID cannot be null");
        }

        dao.save(c);
    }

    // ✅ DELETE
    public void deleteCustomer(String id) {

        if (id == null) {
            throw new RuntimeException("Customer ID cannot be null");
        }

        dao.deleteById(id);
    }

    // ✅ LOGIN
    public String login(String userId, String pincode) {

        if (userId == null || pincode == null) {
            return "Invalid Credentials";
        }

        Optional<Customer> optionalCustomer = dao.findByUserId(userId);

        if (optionalCustomer.isPresent()) {
            Customer c = optionalCustomer.get();

            if (pincode.equals(c.getPincode())) {
                return "Customer Dashboard";
            }
        }

        return "Invalid Credentials";
    }

    // ✅ RESET PINCODE
    public String resetPincode(String userId, String newPincode) {

        if (userId == null || newPincode == null) {
            return "Invalid Request";
        }

        Optional<Customer> optionalCustomer = dao.findByUserId(userId);

        if (optionalCustomer.isPresent()) {
            Customer c = optionalCustomer.get();

            c.setPincode(newPincode);
            dao.save(c);

            return "Pincode Updated";
        }

        return "User Not Found";
    }
}
