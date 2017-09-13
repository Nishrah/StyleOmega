package com.example.nishrah.styleomega.model;

import java.util.ArrayList;

public class ItemData {
    private String itemID;
    private String title;
    private String category;
    private String type;
    private String description;
    private String price;
    private String quantity;
    private String image;
    private ArrayList sizes;
    private ArrayList inquiries;
    private ArrayList reviews;


    public String getItemID() {
        return itemID;
    }

    public void setItemID(String itemID) {
        this.itemID = itemID;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getQuantity() {
        return quantity;
    }

    public void setQuantity(String quantity) {
        this.quantity = quantity;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public ArrayList getSizes() {
        return sizes;
    }

    public void setSizes(ArrayList sizes) {
        this.sizes = sizes;
    }

    public ArrayList getInquiries() {
        return inquiries;
    }

    public void setInquiries(ArrayList inquiries) {
        this.inquiries = inquiries;
    }

    public ArrayList getReviews() {
        return reviews;
    }

    public void setReviews(ArrayList reviews) {
        this.reviews = reviews;
    }
}
