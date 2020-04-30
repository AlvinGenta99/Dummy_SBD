package com.example.jfood_android;

public class Food
{
    /**
     * Variable Food
     */
    private int id;
    private String name;
    private int price;
    private String category;
    private Seller seller;

    /**
     * Constructor Food
     * @param id (ID Makanan)
     * @param name (Nama Makanan)
     * @param price (Harga)
     * @parm category (kategori makanan)
     * @param seller (Penjual), di referensikan dari class Seller
     */
    public Food(int id, String name, int price, String category, Seller seller){
        this.id = id;
        this.name = name;
        this.price = price;
        this.category = category;
        this.seller = seller;
    }
}
