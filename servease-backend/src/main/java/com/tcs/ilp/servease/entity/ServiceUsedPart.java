package com.tcs.ilp.servease.entity;

import jakarta.persistence.*;

@Entity
@Table(name = "service_used_parts", schema = "dev")
public class ServiceUsedPart {

    @Id
    @Column(name = "id")
    private String id;

    @Column(name = "service_id")
    private String serviceId;

    @Column(name = "sno")
    private Integer sparePartSno;


    public ServiceUsedPart() {}

    public ServiceUsedPart(String id, String serviceId, Integer sparePartSno) {
        this.id = id;
        this.serviceId = serviceId;
        this.sparePartSno = sparePartSno;
    }

    public String getId() { return id; }
    public void setId(String id) { this.id = id; }

    public String getServiceId() { return serviceId; }
    public void setServiceId(String serviceId) { this.serviceId = serviceId; }

    public Integer getSparePartSno() { return sparePartSno; }
    public void setSparePartSno(Integer sparePartSno) { this.sparePartSno = sparePartSno; }

}