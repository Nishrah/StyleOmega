package com.example.nishrah.styleomega.model;

import android.database.Cursor;

import java.io.UnsupportedEncodingException;
import java.lang.reflect.Array;
import java.util.*;
import javax.mail.*;
import javax.mail.internet.*;

public class Customer {

    public static DatabaseHandler db;

    public static String register(String email, String firstName, String lastName, String tel, String address, String password){
        String msg = "";

        String checkEmailSQL = "SELECT * FROM customer WHERE email='"+email+"'";
        Cursor c = db.runSELECT(checkEmailSQL);
        if(c.getCount()>0){
            msg = "Email has already been used to create an account!";
            c.close();
        }else{
            String insertCustomerSQL ="INSERT INTO customer(email,firstName,lastName,tel,address,password) VALUES('"+email+"','"+firstName+"','"+lastName+"','"+tel+"','"+address+"','"+password+"')";
            db.runSQL(insertCustomerSQL);
            msg="Sucessfully Registered to StyleOmega";
        }
        return  msg;
    }

    public static ArrayList getAccountInformation(String email){
        ArrayList<String> customer = new ArrayList<>();

        String checkEmailSQL = "SELECT * FROM customer WHERE email='"+email+"'";
        Cursor c = db.runSELECT(checkEmailSQL);
        if(c.getCount()>0){
            c.moveToNext();
            customer.add(c.getString(0));
            customer.add(c.getString(1));
            customer.add(c.getString(2));
            customer.add(c.getString(3));
            customer.add(c.getString(4));
            customer.add(c.getString(5));
            c.close();
        }
        return customer;
    }

    public static String verifyAccount(String email , String password){
        String msg="";

        String verifyAccountSQL ="SELECT * FROM customer WHERE email='"+email+"' AND password='"+password+"'";
        Cursor c = db.runSELECT(verifyAccountSQL);
        if(c.getCount()==1){
            c.moveToNext();
            msg = "Welcome back "+ c.getString(1);
            c.close();
        }else{
            msg = "Invalid Username or Password!";
        }

        return msg;
    }

    public static void updateAccount(String email, String firstName, String lastName, String tel, String address){
        String updateCustomerSQL = "UPDATE customer SET firstName='"+firstName+"', lastName='"+lastName+"', tel='"+tel+"', address='"+address+"' WHERE email='"+email+"'";
        db.runSQL(updateCustomerSQL);
    }

    public static String resetPassword(String email, String currentPassword, String newPassword){
        String msg = "Invalid Username or Password!";
        if(!verifyAccount(email,currentPassword).equals(msg)) {
            String updateCustomerSQL = "UPDATE customer SET password='"+newPassword+"' WHERE email='"+email+"'";
            db.runSQL(updateCustomerSQL);
            msg="Your password has been changed";
        }
        return msg;
    }

    public static String emailCredentials(String email){
        String msg="";
        String verifyEmailSQL = "SELECT * FROM customer WHERE email='"+email+"'";
        Cursor c = db.runSELECT(verifyEmailSQL);
        if(c.getCount()==1) {
            msg="An Email will be sent shortly";
        }else{
            msg="Email does not exist in our records!";
        }
        c.close();
        return  msg;
    }

    public static ArrayList getInquiries(String email){
        ArrayList inquiry = new ArrayList();
        String customerInquiriesSQL = "SELECT * FROM inquiry WHERE email='"+email+"'";
        Cursor c = db.runSELECT(customerInquiriesSQL);
        if(c.getCount()>0){
            for(int i=0;i<c.getCount();i++){
                c.moveToNext();
                InquiryData inquiryData = new InquiryData();
                inquiryData.setItemID(c.getString(1));

                String getItemNameSQL = "SELECT * FROM item WHERE itemID='"+inquiryData.getItemID()+"'";
                Cursor c2 = db.runSELECT(getItemNameSQL);
                if(c2.getCount()>0){
                    c2.moveToNext();
                    inquiryData.setIdentification(c2.getString(1));
                }
                c2.close();

                inquiryData.setInquiry(c.getString(3));
                inquiryData.setResponse(c.getString(4));
                inquiry.add(inquiryData);
            }
            c.close();
        }
        return inquiry;
    }

    public static ArrayList getPurchaseHistory(String email){
        ArrayList purchases = new ArrayList();
        String getPurchaseData="SELECT * FROM purchase WHERE email='"+email+"'";
        Cursor c = db.runSELECT(getPurchaseData);
        if(c.getCount()>0){
            for(int i=0;i<c.getCount();i++) {
                c.moveToNext();
                PurchaseData purchaseData = new PurchaseData();
                purchaseData.setPurchaseID(c.getString(0));
                purchaseData.setDate(c.getString(2));

                ArrayList items = new ArrayList();
                String getPurchaseItems ="SELECT * FROM purchase_item WHERE purchaseID='"+purchaseData.getPurchaseID()+"'";
                Cursor c2 = db.runSELECT(getPurchaseItems);
                if(c2.getCount()>0){
                    for(int j=0;j<c2.getCount();j++) {
                        c2.moveToNext();
                        BagItem bagItem = new BagItem();
                        bagItem.setItemID(c2.getString(1));
                        bagItem.setQuantity(c2.getString(2));
                        bagItem.setTotalPrice(c2.getString(3));
                        ItemData itemData= Item.getItemInformation(bagItem.getItemID());
                        bagItem.setTitle(itemData.getTitle());
                        items.add(bagItem);
                    }
                }
                c2.close();
                purchaseData.setPurchaseItems(items);
                purchases.add(purchaseData);
            }
        }
        c.close();
        return purchases;
    }
}
