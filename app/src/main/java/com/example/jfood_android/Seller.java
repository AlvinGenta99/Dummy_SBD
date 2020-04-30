package com.example.jfood_android;

public class Seller {
    /**
     * Variabel Seller
     */
    private int id;
    private String name;
    private String email;
    private String phoneNumber;
    private Location location;

    /**
     * Constructor Seller
     *
     * @param id          (ID penjual)
     * @param name        (Nama penjual)
     * @param email       (E-mail User)
     * @param phoneNumber (No. Telp penjual)
     * @param location    (Lokasi penjual)
     */
    public Seller(int id, String name, String email, String phoneNumber, Location location) {
        this.id = id;
        this.name = name;
        this.email = email;
        this.phoneNumber = phoneNumber;
        this.location = location;
    }
}