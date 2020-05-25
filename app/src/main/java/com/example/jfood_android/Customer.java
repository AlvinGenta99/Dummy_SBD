package com.example.jfood_android;
import java.util.*;
import java.util.regex.*;
import java.text.SimpleDateFormat;
public class Customer
{
    /**
     * Variable Customer
     */
    private int id;
    private String name;
    private String email;
    private String password;
    private Calendar joinDate;


    /**
     * Constructor Customer
     * @param id (ID customer)
     * @param name (Nama customer)
     * @param email (Email customer)
     * @param password (Password customer)
     * @param joinDate (Tanggal join customer)
     */
    public Customer(int id, String name, String email, String password, Calendar joinDate){
        this.id = id;
        this.name = name;
        this.email = email;
        this.password = password;
        this.joinDate = joinDate;
    }

    public Customer(int id, String name, String email, String password, int year, int month, int dayOfMonth){
        this.id = id;
        this.name = name;
        this.email = email;
        this.password = password;
        this.joinDate = new GregorianCalendar(year, month-1,dayOfMonth);
    }

    public Customer(int id, String name, String email, String password){
        this.id = id;
        this.name = name;
        this.email = email;
        this.password = password;
        Date input = new Date();
        this.joinDate = Calendar.getInstance();
    }

    /**
     * Mengambil id dari class Customer
     * @return id
     */
    public int getId()
    {
        return id;
    }

    /**
     * Mengambil name dari class Customer
     * @return name
     */
    public String getName()
    {
        return name;
    }

    /**
     * Mengambil email dari class Customer
     * @return email
     */
    public String getEmail()
    {
        return email;
    }

    /**
     * Mengambil password dari class Customer
     * @return password
     */
    public String getPassword()
    {
        return password;
    }

    /**
     * Mengambil joinDate dari class Customer
     * @return joinDate
     */
    public Calendar getJoinDate()
    {
        return joinDate;
    }

    /**
     * Menetapkan value id dari class Customer
     * @param id
     */
    public void setId (int id)
    {
        this.id = id;
    }

    /**
     * Menetapkan value name dari class Customer
     * @param name
     */
    public void setName (String name)
    {
        this.name = name;
    }

    /**
     * Menetapkan value email dari class Customer
     * @param email
     */
    public void setEmail(String email)
    {
        Pattern patternEmail = Pattern.compile("^[\\w!#$%’+/=?`{|~^-]+(?:\\.[\\w!#$%’+/=?`{|}^-]+)*@(?:[a-zA-Z0-9-]+\\.)+[a-zA-Z]{2,6}$");
        Matcher match = patternEmail.matcher(email);
        if(match.find())
        {
            this.email = email;
        }
        else
        {
            this.email = " ";
        }
    }

    /**
     * Menetapkan value password dari class Customer
     * @param password
     */
    public void setPassword (String password)
    {
        Pattern patternPass = Pattern.compile("((?=.*[a-z])(?=.*\\d)(?=.*[A-Z]).{6,})");
        Matcher passMatch = patternPass.matcher(password);
        if(passMatch.find()){
            this.password = password;
        }
        else{
            this.password = " ";
        }
    }

    /**
     * Menetapkan value joinDate dari class Customer
     * @param joinDate
     */
    public void setJoinDate (Calendar joinDate)
    {
        this.joinDate = joinDate;
    }

    public void setJoinDate (int year, int month, int dayOfMonth)
    {
        this.joinDate = new GregorianCalendar(year, month-1,dayOfMonth);
    }

    public String toString(){
        String strDate = "";
        SimpleDateFormat formatter = new SimpleDateFormat("dd MMMM yyyy");
        if (getJoinDate() != null){
            strDate = formatter.format(joinDate.getTime());
        }

        return "\nId: "+getId()+"\nNama: "+getName() +"\nEmail: "+getEmail() +"\nPassword: "+getPassword() +"\nJoin Date : "+ strDate;
    }
}

