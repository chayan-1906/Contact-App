package com.example.contact30thoct;

public class Contact {

    private int id;
    public String name;
    public String company;
    public String title;
    public String phoneNumber;
    public String emailId;

    public Contact() {

    }

    public Contact(String name, String phoneNumber, String emailId) {
        this.name = name;
        this.phoneNumber = phoneNumber;
        this.emailId = emailId;
    }

    public Contact(String name, String company, String title, String phoneNumber, String emailId) {
        this.name = name;
        this.company = company;
        this.title = title;
        this.phoneNumber = phoneNumber;
        this.emailId = emailId;
    }

    public Contact(int id, String name, String company, String title, String phoneNumber, String emailId) {
        this.id = id;
        this.name = name;
        this.company = company;
        this.title = title;
        this.phoneNumber = phoneNumber;
        this.emailId = emailId;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getCompany() {
        return company;
    }

    public void setCompany(String company) {
        this.company = company;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getEmailId() {
        return emailId;
    }

    public void setEmailId(String emailId) {
        this.emailId = emailId;
    }

}