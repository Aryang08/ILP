package com.tcs.ilp.servease.dto;

public class SparePartDTO {

    private String sparepart;
    private String category;
    private float price;

    public SparePartDTO() {}

    public SparePartDTO(String sparepart, String category, float price) {
        this.sparepart = sparepart;
        this.category = category;
        this.price = price;
    }

    public String getSparepart() {
        return sparepart;
    }

    public void setSparepart(String sparepart) {
        this.sparepart = sparepart;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public float getPrice() {
        return price;
    }

    public void setPrice(float price) {
        this.price = price;
    }
}