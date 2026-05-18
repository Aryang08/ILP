package com.tcs.ilp.servease.bo;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.tcs.ilp.servease.entity.Admin;
import com.tcs.ilp.servease.repository.AdminRepository;

@ExtendWith(MockitoExtension.class)   // ✅ FIX (IMPORTANT)
class AdminBOTest {

    @Mock
    private AdminRepository adminRepository;

    @InjectMocks
    private AdminBO adminBO;

    // ✅ ADD ADMIN
    @Test
    void addAdmin_success() {
        Admin admin = new Admin("a1", "u14");

        when(adminRepository.save(admin)).thenReturn(admin);

        Admin result = adminBO.addAdmin(admin);

        assertEquals("a1", result.getAdminId());
        assertEquals("u14", result.getUserId());
    }

    // ✅ GET ADMIN
    @Test
    void getAdmin_success() {

        Admin admin = new Admin("a1", "u14");

        when(adminRepository.findById("a1"))
                .thenReturn(Optional.of(admin));

        Admin result = adminBO.getAdminById("a1");

        assertNotNull(result);
        assertEquals("a1", result.getAdminId());
    }

    // ✅ DELETE ADMIN
    @Test
    void deleteAdmin_success() {

        doNothing().when(adminRepository).deleteById("a1");

        adminBO.deleteAdmin("a1");

        verify(adminRepository, times(1)).deleteById("a1");
    }

    // ✅ GET ALL
    @Test
    void getAllAdmins_success() {

        Admin admin = new Admin("a1", "u14");

        when(adminRepository.findAll()).thenReturn(List.of(admin));

        List<Admin> list = adminBO.getAllAdmins();

        assertEquals(1, list.size());
        assertEquals("a1", list.get(0).getAdminId());
    }
}