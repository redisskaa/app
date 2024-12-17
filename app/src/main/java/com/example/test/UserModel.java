package com.example.test;

public class UserModel {
    private final int image;
    private final String name;
    private String lastName;
    private final String country;
    private final String city;
    private final String age;

    public UserModel(int image, String name, String lastName, String country, String city, String age) {
        this.image = image;
        this.name = name;
        this.lastName = lastName;
        this.country = country;
        this.city = city;
        this.age = age;
    }

    public int getImage() {
        return image;
    }

    public String getName() {
        return name;
    }

    public String getCountry() {
        return country;
    }

    public String getCity() {
        return city;
    }

    public String getAge() {
        return age;
    }
}