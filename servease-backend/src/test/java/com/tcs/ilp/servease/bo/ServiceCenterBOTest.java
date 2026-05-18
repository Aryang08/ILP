package com.tcs.ilp.servease.bo;


import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.tcs.ilp.servease.bo.ServiceCenterBO;
import com.tcs.ilp.servease.dto.ServiceCenterDTO;
import com.tcs.ilp.servease.entity.ServiceCenter;
import com.tcs.ilp.servease.repository.ServiceCenterRepository;

@ExtendWith(MockitoExtension.class)
public class ServiceCenterBOTest {

    @Mock
    private ServiceCenterRepository repository;

    @InjectMocks
    private ServiceCenterBO bo;

    @Test
    public void testGetById() {

        ServiceCenter sc = new ServiceCenter();
        sc.setScId("sc1");
        sc.setName("West Service Center");

        when(repository.findByScId("sc1"))
                .thenReturn(sc);

        ServiceCenter result = bo.getById("sc1");

        assertEquals("West Service Center", result.getName());
    }

    @Test
    public void testFindByPincode() {

        ServiceCenter sc = new ServiceCenter();
        sc.setScId("sc1");
        sc.setPincode("380015");

        when(repository.findByPincode("380015"))
                .thenReturn(sc);

        String result = bo.findByPincode("380015");

        assertEquals("sc1", result);
    }

    @Test
    public void testGetAllServiceCenters() {

        List<ServiceCenter> list = new ArrayList<>();

        ServiceCenter sc1 = new ServiceCenter();
        sc1.setScId("sc1");

        ServiceCenter sc2 = new ServiceCenter();
        sc2.setScId("sc2");

        list.add(sc1);
        list.add(sc2);

        when(repository.findAll())
                .thenReturn(list);

        List<ServiceCenter> result = bo.getAllServiceCenters();

        assertEquals(2, result.size());
    }

    @Test
    public void testInsertServiceCenter() {

        ServiceCenterDTO dto = new ServiceCenterDTO();
        dto.setScId("sc1");
        dto.setName("West Service Center");
        dto.setAddress("Ahmedabad");
        dto.setPincode("380015");
        dto.setPhone("9876543210");

        bo.insertServiceCenter(dto);
    }

    @Test
    public void testUpdateServiceCenter() {

        ServiceCenter sc = new ServiceCenter();
        sc.setScId("sc1");
        sc.setName("Old Center");

        ServiceCenterDTO dto = new ServiceCenterDTO();
        dto.setName("New Center");
        dto.setAddress("SG Highway");
        dto.setPincode("380015");
        dto.setPhone("9876543210");

        when(repository.findByScId("sc1"))
                .thenReturn(sc);

        bo.updateServiceCenter("sc1", dto);

        assertEquals("New Center", sc.getName());
    }

    @Test
    public void testDeleteServiceCenter() {

        ServiceCenter sc = new ServiceCenter();
        sc.setScId("sc1");

        when(repository.findByScId("sc1"))
                .thenReturn(sc);

        bo.deleteServiceCenter("sc1");
    }
}
