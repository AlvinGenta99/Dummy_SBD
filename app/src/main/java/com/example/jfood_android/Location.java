package com.example.jfood_android;

public class Location
{
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

    /**
     * Mengambil province dari class Location
     * @return province
     */
    public String getProvince(){
        return province;
    }

    /**
     * Mengambil description dari class Location
     * @return description
     */
    public String getDescription(){
        return description;
    }

    /**
     * Mengambil city dari class Location
     * @return city
     */
    public String getCity(){
        return city;
    }

    /**
     * Menetapkan value province dari class Location
     * @param province
     */
    public void setProvince(String province){
        this.province = province;
    }

    /**
     * Menetapkan value description dari class Location
     * @param description
     */
    public void setDescription(String description){
        this.description = description;
    }

    /**
     * Menetapkan value city dari class Location
     * @param city
     */
    public void setCity(String city){
        this.city = city;
    }

    public String toSting(){
        return "\nProvince: "+getProvince()+"\nCity: "+getCity() +"\nDescription: "+getDescription();
    }
}

