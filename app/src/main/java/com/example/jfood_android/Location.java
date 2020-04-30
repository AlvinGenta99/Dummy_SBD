package com.example.jfood_android;

public class Location {

    /**
     * Variabel Location
     */
    private String province;
    private String description;
    private String city;

    /**
     * Constructor Location
     * @param province (Provinsi)
     * @param description (Deskripsi)
     * @param city (Kota)
     */
    public Location(String province, String description, String city){
        this.province = province;
        this.description = description;
        this.city = city;
    }

}
