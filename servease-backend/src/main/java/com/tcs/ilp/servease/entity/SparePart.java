package com.tcs.ilp.servease.entity;

import jakarta.persistence.*;

@Entity
@Table(name = "checklistspareparts", schema = "dev")
public class SparePart {

    @Id
    @Column(name = "sno")
    private int sno;

    @Column(name = "sparepart")
    private String sparepart;

    @Column(name = "category")
    private String category;

    @Column(name = "count")
    private int count;

    @Column(name = "price")
    private float price;

    public SparePart() {}

    public SparePart(int sno, String sparepart, String category, int count, float price) {
        this.sno = sno;
        this.sparepart = sparepart;
        this.category = category;
        this.count = count;
        this.price = price;
    }

    public int getSno() { return sno; }
    public void setSno(int sno) { this.sno = sno; }

    public String getSparepart() { return sparepart; }
    public void setSparepart(String sparepart) { this.sparepart = sparepart; }

    public String getCategory() { return category; }
    public void setCategory(String category) { this.category = category; }

    public int getCount() { return count; }
    public void setCount(int count) { this.count = count; }

    public float getPrice() { return price; }
    public void setPrice(float price) { this.price = price; }
}