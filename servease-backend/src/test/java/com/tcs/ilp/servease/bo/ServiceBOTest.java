package com.tcs.ilp.servease.bo;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.tcs.ilp.servease.entity.ServiceMain;
import com.tcs.ilp.servease.entity.Customer;
import com.tcs.ilp.servease.repository.ServiceMainRepository;
import com.tcs.ilp.servease.repository.CustomerRepository;
import com.tcs.ilp.servease.repository.ServiceCenterRepository;

@ExtendWith(MockitoExtension.class)
class ServiceBOTest {

    @Mock
    private ServiceMainRepository repository;

    @Mock
    private CustomerRepository customerRepository;   // ✅ ADD

    @Mock
    private ServiceCenterRepository serviceCenterRepository; // ✅ ADD

    @InjectMocks
    private ServiceMainBO serviceMainBO;

    // ✅ ADD SERVICE
    @Test
    void testAddService() {

        ServiceMain s = new ServiceMain();
        s.setServiceId("S1");
        s.setStatus("pending");
        s.setCustomerId("C1");

        // ✅ MOCK CUSTOMER
        Customer customer = new Customer();
        customer.setCustomerId("C1");
        customer.setPincode("123456");

        when(customerRepository.findByCustomerId("C1"))
                .thenReturn(Optional.of(customer));

        // ✅ MOCK SERVICE CENTER
        when(serviceCenterRepository.findServiceCenterIdByPincode("123456"))
                .thenReturn("SC1");

        when(repository.save(s)).thenReturn(s);

        ServiceMain result = serviceMainBO.addService(s);

        assertNotNull(result);
        assertEquals("SC1", result.getServiceCenterId()); // ✅ important check
        verify(repository).save(s);
    }

    // ✅ VALIDATION
    @Test
    void testAddService_null() {

        Exception ex = assertThrows(RuntimeException.class, () -> {
            serviceMainBO.addService(null);
        });

        assertEquals("Service object cannot be null", ex.getMessage());
    }

    // ✅ GET BY ID
    @Test
    void testGetServiceById() {

        ServiceMain s = new ServiceMain();
        s.setServiceId("S1");

        when(repository.findById("S1"))
                .thenReturn(Optional.of(s));

        ServiceMain result = serviceMainBO.getServiceById("S1");

        assertEquals("S1", result.getServiceId());
    }

    // ✅ UPDATE STATUS
    @Test
    void testUpdateServiceStatus() {

        ServiceMain s = new ServiceMain();
        s.setServiceId("S1");
        s.setStatus("pending");

        when(repository.findById("S1"))
                .thenReturn(Optional.of(s));

        when(repository.save(any(ServiceMain.class))).thenReturn(s);

        serviceMainBO.updateServiceStatus("S1", "completed");

        assertEquals("completed", s.getStatus());
        verify(repository).save(s);
    }

    // ✅ DELETE
    @Test
    void testDeleteService() {

        when(repository.existsById("S1")).thenReturn(true);

        serviceMainBO.deleteService("S1");

        verify(repository).deleteById("S1");
    }

    // ✅ GET ALL
    @Test
    void testGetAllServices() {

        ServiceMain s = new ServiceMain();
        s.setServiceId("S1");

        when(repository.findAll()).thenReturn(Arrays.asList(s));

        List<ServiceMain> list = serviceMainBO.getAllServices();

        assertEquals(1, list.size());
    }
}