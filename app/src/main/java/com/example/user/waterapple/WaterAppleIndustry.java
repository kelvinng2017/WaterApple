package com.example.user.waterapple;

public class WaterAppleIndustry {
    String address;
    String name;
    String phone;
    String product;

    public WaterAppleIndustry(){

    }

    public WaterAppleIndustry(String address, String name, String phone, String product) {
        this.address = address;
        this.name = name;
        this.phone = phone;
        this.product = product;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getProduct() {
        return product;
    }

    public void setProduct(String product) {
        this.product = product;
    }
}
