package com.tcs.ilp.servease.bo;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.tcs.ilp.servease.dto.ServiceCenterDTO;
import com.tcs.ilp.servease.entity.ServiceCenter;
import com.tcs.ilp.servease.exception.ServiceCenterException;
import com.tcs.ilp.servease.repository.ServiceCenterRepository;

@Service
@Transactional
public class ServiceCenterBO {

    @Autowired
    private ServiceCenterRepository repository;

    // POST - Add
    public void insertServiceCenter(ServiceCenterDTO dto) {

        ServiceCenter sc = new ServiceCenter();

        sc.setScId(dto.getScId());
        sc.setName(dto.getName());
        sc.setAddress(dto.getAddress());
        sc.setPincode(dto.getPincode());
        sc.setPhone(dto.getPhone());

        repository.save(sc);
    }

    // GET - By Pincode
    public String findByPincode(String pincode) {

        ServiceCenter sc =
                repository.findByPincode(pincode);

        if (sc != null) {
            return sc.getScId();
        }

        throw new ServiceCenterException(
                "No Service Center Found");
    }

    // GET - By ID
    public ServiceCenter getById(String scId) {

        ServiceCenter sc =
                repository.findByScId(scId);

        if (sc != null) {
            return sc;
        }

        throw new ServiceCenterException(
                "Service Center Not Found");
    }

    // GET - All
    public List<ServiceCenter> getAllServiceCenters() {

        return repository.findAll();
    }

    // PUT - Update
    public void updateServiceCenter(
            String scId,
            ServiceCenterDTO dto) {

        ServiceCenter sc =
                repository.findByScId(scId);

        if (sc == null) {
            throw new ServiceCenterException(
                    "Service Center Not Found");
        }

        sc.setName(dto.getName());
        sc.setAddress(dto.getAddress());
        sc.setPincode(dto.getPincode());
        sc.setPhone(dto.getPhone());

        repository.save(sc);
    }

    // DELETE
    public void deleteServiceCenter(
            String scId) {

        ServiceCenter sc =
                repository.findByScId(scId);

        if (sc == null) {
            throw new ServiceCenterException(
                    "Service Center Not Found");
        }

        repository.delete(sc);
    }
}