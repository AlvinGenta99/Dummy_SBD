package com.example.jfood_android;

import java.util.*;
import java.util.regex.*;
import java.text.Format;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.LocalDate;

public class Invoice
{
    private int id;
    private ArrayList<Food> temp = new ArrayList<>();
    private int totalPrice;
    private int currentUserId;
    private String date;
    private String invoiceStatus;
    private String paymentType;
    private String code;
    private int discount;
    private int deliveryFee;

    public Invoice(int id, ArrayList<Food> temp, int totalPrice, int currentUserId, String date, String invoiceStatus, String paymentType, String code, int discount) {
        this.id = id;
        this.temp = temp;
        this.totalPrice = totalPrice;
        this.currentUserId = currentUserId;
        this.date = date;
        this.invoiceStatus = invoiceStatus;
        this.paymentType= paymentType;
        this.code = code;
        this.discount = discount;
    }

    public Invoice(int id, ArrayList<Food> temp, int totalPrice, int currentUserId, String date, String invoiceStatus, String paymentType, int deliveryFee){
        this.id = id;
        this.temp = temp;
        this.totalPrice = totalPrice;
        this.currentUserId = currentUserId;
        this.date = date;
        this.invoiceStatus = invoiceStatus;
        this.paymentType= paymentType;
        this.deliveryFee = deliveryFee;
    }

    public int getId()
    {
        return id;
    }

    public ArrayList<Food> getFoods()
    {
        return temp;
    }

    public String getDate()
    {
        return date;
    }

    public int getCustomer(){
        return currentUserId;
    }

    public String getPaymentType(){
        return paymentType;
    }

    public String getInvoiceStatus(){
        return invoiceStatus;
    }

    public String getCode(){
        return code;
    }

    public int getDiscount(){
        return discount;
    }

    public int getTotalPrice(){
        return totalPrice;
    }

    public int getDeliveryFee(){
        return deliveryFee;
    }

    public void setId (int id)
    {
        this.id = id;
    }

    public void setFoods (ArrayList<Food> temp)
    {
        this.temp =  temp;
    }

    public void setDate (String date){
        this.date = date.substring(0,9);
    }
    public void setInvoiceStatus(String invoiceStatus)
    {
        this.invoiceStatus = invoiceStatus;
    }

    public void setCustomer(){
        this.currentUserId = currentUserId;
    }

    public void setPaymentType(){
        this.paymentType = paymentType;
    }

    public void setCode(){
        this.code = code;
    }

    public void setDiscount(){
        this.discount = discount;
    }

    public void setTotalPrice(){
        this.totalPrice = totalPrice;
    }

    public void setDeliveryFee(){
        this.deliveryFee = deliveryFee;
    }

}

