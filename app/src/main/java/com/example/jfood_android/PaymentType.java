package com.example.jfood_android;

public enum PaymentType
{
    CASH("Cash"), CASHLESS("Cashless");
    private String type;
    private PaymentType(String type){
        this.type = type;
    }


    public String toString(){
        return type;
    }
}

