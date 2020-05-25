package com.example.jfood_android;

/**
 * class Seller digunakan untuk memberi info tentang penjual
 * Masing-masing komponen memiliki fungsi get (untuk mengambil value yang ada) dan set (untuk memperbarui value dari komponen).
 * location tidak dpapat diubah dari class Seller secara langsung.
 * @author Alvin Genta Pratama
 * @version 1.1.27.20
 */

public class Seller
{
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
     * @param id (ID penjual)
     * @param name (Nama penjual)
     * @param email (E-mail User)
     * @param phoneNUmber (No. Telp penjual)
     * @param location (Lokasi penjual)
     */
    public Seller(int id, String name, String email, String phoneNumber, Location location){
        this.id = id;
        this.name = name;
        this.email = email;
        this.phoneNumber = phoneNumber;
        this.location = location;
    }

    /**
     * Mengambil id dari class Seller
     * @return id
     */
    public int getId()
    {
        return id;
    }

    /**
     * Mengambil name dari class Seller
     * @return name
     */
    public String getName()
    {
        return name;
    }

    /**
     * Mengambil email dari class Seller
     * @return email*/
    public String getEmail()
    {
        return email;
    }

    /**
     * Mengambil phoneNumber (nomor telpon) dari class Seller
     * @return phoneNmber
     */
    public String getPhoneNumber()
    {
        return phoneNumber;
    }

    /**
     * Mengambil location dari class Seller
     * @return location
     */
    public Location getLocation(){
        return location;
    }

    /**
     * Menetapkan value id dari class Seller
     * @param id
     */
    public void setId (int id)
    {
        this.id = id;
    }

    /**
     * Menetapkan value name dari class Seller
     * @param name
     */
    public void setName (String name)
    {
        this.name = name;
    }

    /**
     * Menetapkan value email dari class Seller
     * @param email
     */
    public void setEmail (String email)
    {
        this.email = email;
    }

    /**
     * Menetapkan value phoneNumber (nomor Telpon) dari class Seller
     * @param phoneNumber
     */
    public void setPhoneNumber(String phoneNumber)
    {
        this.phoneNumber = phoneNumber;
    }

    /**
     * Menetapkan value lcation dari class Seller, yang di refernsi dari class Location
     * @param location
     */
    public void setLocation(Location location){
        this.location = location;
    }

    public String toString(){
        return "\nId: "+getId()+"\nNama: "+getName() +"\nPhone Number: "+getPhoneNumber() +"\nLocation: "+getLocation();
    }
}
