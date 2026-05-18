package com.tcs.ilp.servease.bo;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.*;

import com.tcs.ilp.servease.bo.BillUserBOImpl;
import com.tcs.ilp.servease.entity.BillUser;
import com.tcs.ilp.servease.exception.BillUserException;
import com.tcs.ilp.servease.repository.BillUserRepository;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
public class BillUserBOTest {

    @Mock
    private BillUserRepository billUserRepository;

    @InjectMocks
    private BillUserBOImpl billUserBO;

    private BillUser billUser;

    @BeforeEach
    void setUp() {
        billUser = new BillUser();
        billUser.setBillUserId("BU1");
        billUser.setAssignmentId("A101");
        billUser.setAmount(new BigDecimal("500"));
        billUser.setBillUserDate(LocalDate.of(2023, 1, 10));
    }

    // ✅ POSITIVE - ADD
    @Test
    void testAddBillUserSuccess() {
        assertDoesNotThrow(() -> billUserBO.addBillUser(billUser));
        verify(billUserRepository).save(billUser);
    }

    // ❌ NEGATIVE - NULL
    @Test
    void testAddBillUserNull() {
        assertThrows(BillUserException.class, () -> {
            billUserBO.addBillUser(null);
        });
    }

    // ❌ NEGATIVE - ZERO AMOUNT
    @Test
    void testAddBillUserZeroAmount() {
        billUser.setAmount(BigDecimal.ZERO);
        assertThrows(BillUserException.class, () -> {
            billUserBO.addBillUser(billUser);
        });
    }

    // ❌ NEGATIVE - NEGATIVE AMOUNT
    @Test
    void testAddBillUserNegativeAmount() {
        billUser.setAmount(new BigDecimal("-10"));
        assertThrows(BillUserException.class, () -> {
            billUserBO.addBillUser(billUser);
        });
    }

    // ✅ BOUNDARY - MIN AMOUNT
    @Test
    void testAddBillUserBoundary() {
        billUser.setAmount(new BigDecimal("1"));
        assertDoesNotThrow(() -> billUserBO.addBillUser(billUser));
    }

    // ✅ POSITIVE - GET BY ID
    @Test
    void testGetByAssignmentIdSuccess() {
        when(billUserRepository.findByAssignmentId("A101"))
                .thenReturn(billUser);

        BillUser result = billUserBO.getBillUserByAssignmentId("A101");

        assertEquals("A101", result.getAssignmentId());
    }

    // ❌ NEGATIVE - NOT FOUND
    @Test
    void testGetByAssignmentIdNotFound() {
        when(billUserRepository.findByAssignmentId("A101"))
                .thenReturn(null);

        assertThrows(BillUserException.class, () -> {
            billUserBO.getBillUserByAssignmentId("A101");
        });
    }

    // ✅ POSITIVE - GET ALL
    @Test
    void testGetAllSuccess() {
        when(billUserRepository.findAll())
                .thenReturn(Arrays.asList(billUser));

        List<BillUser> list = billUserBO.getAllBillUsers();

        assertEquals(1, list.size());
    }

    // ❌ NEGATIVE - EMPTY LIST
    @Test
    void testGetAllEmpty() {
        when(billUserRepository.findAll())
                .thenReturn(Collections.emptyList());

        assertThrows(BillUserException.class, () -> {
            billUserBO.getAllBillUsers();
        });
    }

    // ✅ MOCKITO CHECK
    @Test
    void testMockitoWorking() {
        List<String> list = mock(List.class);
        when(list.size()).thenReturn(3);

        assertEquals(3, list.size());
    }
}
