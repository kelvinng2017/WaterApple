package com.example.user.waterapple;

public class WaterAppleRate {

    String name;
    String image;

    public WaterAppleRate(){

    }

    public WaterAppleRate(String name, String image) {
        this.name = name;
        this.image = image;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }
}
