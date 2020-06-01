/**
 * class Food digunakan untuk memberi info tentang Makanan yang dijual.
 * Masing-masing komponen memiliki fungsi get (untuk mengambil value yang ada) dan set (untuk memperbarui value dari komponen).
 * seller tidak dapat diubah dari class Food secara langsung.
 * @author Alvin Genta Pratama
 * @version 1.1.27.20
 */
package com.example.jfood_android;

import com.example.jfood_android.Seller;
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

    /**
     * Mengambil id dari class Food
     * @return id
     */
    public int getId()
    {
        return id;
    }

    /**
     * Mengambil name dari class Food
     * @return name
     */
    public String getName()
    {
        return name;
    }

    /**
     * Mengambil seller dari class Food
     * @return seller
     */
    public Seller getSeller(){
        return seller;
    }

    /**
     * Mengambil price dari class Food
     * @return price
     */
    public int getPrice()
    {
        return price;
    }

    /**
     * Mengambil category dari class Food
     * @return category
     */
    public String getCategory()
    {
        return category;
    }

    /**
     * Menetapkan value id dari class Food
     * @param id
     */
    public void setId (int id)
    {
        this.id = id;
    }

    /**
     * Menetapkan value name dari class Food
     * @param name
     */
    public void setName(String name)
    {
        this.name = name;
    }

    /**
     * Menetapkan value seller dari class Food
     * @param seller = Detail Seller dari Makanan
     */
    public void setSeller(Seller seller)
    {
        this.seller = seller;
    }

    /**
     * Menetapkan value price dari class Food
     * @param price = Harga makanan
     */
    public void setPrice (int price)
    {
        this.price = price;
    }

    /**
     * Menetapkan value category dari class Food
     * @param category = Kategori Food berdasarkan Enum FoodCategory
     */
    public void setCategory(String category)
    {
        this.category = category;
    }
}
