package com.tcs.ilp.servease.bo;

import java.util.List;

import org.springframework.stereotype.Service;

import com.tcs.ilp.servease.entity.Admin;
import com.tcs.ilp.servease.exception.InvalidAdminException;
import com.tcs.ilp.servease.repository.AdminRepository;

@Service
public class AdminBO {

    private final AdminRepository adminRepository;

    public AdminBO(AdminRepository adminRepository) {
        this.adminRepository = adminRepository;
    }

    // CREATE
    public Admin addAdmin(Admin admin) {

        if (admin == null) {
            throw new InvalidAdminException("Admin object cannot be null");
        }

        if (admin.getAdminId() == null || admin.getAdminId().isBlank()) {
            throw new InvalidAdminException("Admin ID is mandatory");
        }

        return adminRepository.save(admin); // JPA handles insert/update
    }

    // READ BY ID
    public Admin getAdminById(String adminId) {
        return adminRepository.findById(adminId).orElse(null);
    }

    // DELETE
    public void deleteAdmin(String adminId) {
        adminRepository.deleteById(adminId);
    }

    // READ ALL
    public List<Admin> getAllAdmins() {
        return adminRepository.findAll();
    }
}