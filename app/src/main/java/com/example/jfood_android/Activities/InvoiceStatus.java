package com.example.jfood_android;

public enum InvoiceStatus
{
    ONGOING("Ongoing"), FINISHED("Finished"), CANCELLED("Cancelled");

    private String status;
    private InvoiceStatus(String status){
        this.status = status;
    }
    public String toString(){
        return status;
    }
}