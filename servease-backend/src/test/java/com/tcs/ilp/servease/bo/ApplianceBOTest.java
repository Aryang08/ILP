package com.tcs.ilp.servease.bo;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import java.time.LocalDate;
import java.util.*;

import com.tcs.ilp.servease.bo.ApplianceBO;
import com.tcs.ilp.servease.dto.ApplianceDTO;
import com.tcs.ilp.servease.entity.Appliance;
import com.tcs.ilp.servease.exception.ApplianceExceptions.*;
import com.tcs.ilp.servease.repository.ApplianceRepository;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
public class ApplianceBOTest {

    @Mock
    private ApplianceRepository applianceRepository;

    @InjectMocks
    private ApplianceBO applianceBO;

    private Appliance appliance;
    private ApplianceDTO dto;

    @BeforeEach
    void setUp() {
        appliance = new Appliance(
                "A1", "C1", "S123", "TV", "M101",
                LocalDate.of(2023, 1, 1),
                "IN-WARRANTY",
                LocalDate.of(2023, 1, 5)
        );

        dto = new ApplianceDTO();
        dto.setApplianceId("A1");
        dto.setCustomerId("C1");
        dto.setSerialNo("S123");
        dto.setName("TV");
        dto.setModelNo("M101");
        dto.setPurchaseDate(LocalDate.of(2023, 1, 1));
        dto.setWarrantyStatus("IN-WARRANTY");
        dto.setInstallationDate(LocalDate.of(2023, 1, 5));
    }

    // =======================================
    // ✅ ADD APPLIANCE
    // =======================================

    // ✅ Positive
    @Test
    void testAddApplianceSuccess() {
        when(applianceRepository.existsById("A1")).thenReturn(false);

        assertDoesNotThrow(() -> applianceBO.addAppliance(dto));

        verify(applianceRepository).save(any(Appliance.class));
    }

    // ❌ Negative - Duplicate
    @Test
    void testAddApplianceDuplicate() {
        when(applianceRepository.existsById("A1")).thenReturn(true);

        assertThrows(DuplicateApplianceException.class,
                () -> applianceBO.addAppliance(dto));
    }

    // ❌ Negative - No customer
    @Test
    void testAddApplianceNoCustomer() {
        dto.setCustomerId(null);

        assertThrows(CustomerNotFoundException.class,
                () -> applianceBO.addAppliance(dto));
    }

    // ✅ Boundary - minimum valid date
    @Test
    void testAddApplianceBoundaryDates() {
        when(applianceRepository.existsById("A1")).thenReturn(false);

        dto.setPurchaseDate(LocalDate.of(2023,1,1));
        dto.setInstallationDate(LocalDate.of(2023,1,1));

        assertDoesNotThrow(() -> applianceBO.addAppliance(dto));
    }

    // =======================================
    // ✅ GET BY ID
    // =======================================

    @Test
    void testGetByIdSuccess() {
        when(applianceRepository.findById("A1"))
                .thenReturn(Optional.of(appliance));

        ApplianceDTO result = applianceBO.getApplianceById("A1");

        assertEquals("A1", result.getApplianceId());
    }

    @Test
    void testGetByIdNotFound() {
        when(applianceRepository.findById("A1"))
                .thenReturn(Optional.empty());

        assertThrows(CustomerNotFoundException.class,
                () -> applianceBO.getApplianceById("A1"));
    }

    // =======================================
    // ✅ GET ALL
    // =======================================

    @Test
    void testGetAllSuccess() {
        when(applianceRepository.findAll())
                .thenReturn(Arrays.asList(appliance));

        List<ApplianceDTO> list = applianceBO.getAllAppliances();

        assertEquals(1, list.size());
    }

    @Test
    void testGetAllEmpty() {
        when(applianceRepository.findAll())
                .thenReturn(Collections.emptyList());

        List<ApplianceDTO> list = applianceBO.getAllAppliances();

        assertTrue(list.isEmpty());
    }

    // =======================================
    // ✅ DELETE
    // =======================================

    @Test
    void testDeleteSuccess() {
        when(applianceRepository.existsById("A1")).thenReturn(true);

        applianceBO.deleteAppliance("A1");

        verify(applianceRepository).deleteById("A1");
    }

    @Test
    void testDeleteNotFound() {
        when(applianceRepository.existsById("A1")).thenReturn(false);

        assertThrows(CustomerNotFoundException.class,
                () -> applianceBO.deleteAppliance("A1"));
    }

    // =======================================
    // ✅ STATS
    // =======================================

    @Test
    void testTotalCount() {
        when(applianceRepository.findAll())
                .thenReturn(Arrays.asList(appliance));

        int count = applianceBO.getTotalAppliances();

        assertEquals(1, count);
    }

    @Test
    void testInWarrantyCount() {
        when(applianceRepository.findAll())
                .thenReturn(Arrays.asList(appliance));

        long count = applianceBO.getInWarrantyCount();

        assertEquals(1, count);
    }

    // ✅ Mockito sanity test
    @Test
    void testMockitoWorking() {
        List<String> list = mock(List.class);
        when(list.size()).thenReturn(2);

        assertEquals(2, list.size());
    }
}