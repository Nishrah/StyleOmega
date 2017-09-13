package com.example.nishrah.styleomega.model;

import java.util.ArrayList;

public class PurchaseData {

    private String purchaseID;
    private String email;
    private String customerName;
    private String date;
    private String totalPrice;
    private ArrayList purchaseItems = new ArrayList();

    public String getPurchaseID() {
        return purchaseID;
    }

    public void setPurchaseID(String purchaseID) {
        this.purchaseID = purchaseID;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getCustomerName() {
        return customerName;
    }

    public void setCustomerName(String customerName) {
        this.customerName = customerName;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(String totalPrice) {
        this.totalPrice = totalPrice;
    }

    public ArrayList getPurchaseItems() {
        return purchaseItems;
    }

    public void setPurchaseItems(ArrayList purchaseItems) {
        this.purchaseItems = purchaseItems;
    }
}
