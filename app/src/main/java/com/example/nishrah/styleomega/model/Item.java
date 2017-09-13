package com.example.nishrah.styleomega.model;

import android.database.Cursor;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Iterator;

public class Item {

    public static DatabaseHandler db;

    public static ArrayList getAvailableItems(String category, String type){
        ArrayList items = new ArrayList();

        String getAvailableItemsSQL = "SELECT * FROM item WHERE category='"+category+"' AND type='"+type+"' AND quantity>0";
        Cursor c = db.runSELECT(getAvailableItemsSQL);
        if(c.getCount()>0){
            for(int i=0;i<c.getCount();i++) {
                c.moveToNext();
                ItemData itemData = new ItemData();
                itemData.setItemID(c.getString(0));
                itemData.setTitle(c.getString(1));
                itemData.setPrice(c.getString(5));
                itemData.setImage(c.getString(7));
                items.add(itemData);
            }
        }
        c.close();

        return items;
    }

    public static ItemData getItemInformation(String itemID){
        ItemData itemData = new ItemData();
        itemData.setItemID(itemID);
        String getItemInformationSQL = "SELECT * FROM item WHERE itemID='"+itemID+"'";
        Cursor c = db.runSELECT(getItemInformationSQL);
        if(c.getCount()>0){
            c.moveToNext();
            itemData.setTitle(c.getString(1));
            itemData.setCategory(c.getString(2));
            itemData.setDescription(c.getString(4));
            itemData.setPrice(c.getString(5));
            itemData.setQuantity(c.getString(6));
            itemData.setImage(c.getString(7));
            itemData.setSizes(getItemSizes(itemID));
            itemData.setInquiries(getItemInquiries(itemID));
            itemData.setReviews(getItemReviews(itemID));
        }
        c.close();
        return itemData;
    }

    public static ArrayList getItemSizes(String itemID){
        ArrayList sizes = new ArrayList();
        String getItemSizesSQL = "SELECT * FROM item_size WHERE itemID='"+itemID+"' AND sQuantity>0";
        Cursor c = db.runSELECT(getItemSizesSQL);
        if(c.getCount()>0){
            for(int i=0;i<c.getCount();i++) {
                c.moveToNext();
                sizes.add(c.getString(1));
            }
        }
        c.close();
        return sizes;
    }

    public static ArrayList getItemInquiries(String itemID){
        ArrayList inquiries = new ArrayList();
        String getItemInquiriesSQL="SELECT * FROM inquiry WHERE itemID='"+itemID+"'";
        Cursor c = db.runSELECT(getItemInquiriesSQL);
        if(c.getCount()>0){
            for(int i=0;i<c.getCount();i++) {
                c.moveToNext();

                InquiryData inquiryData = new InquiryData();
                inquiryData.setInquiryID(c.getString(0));
                inquiryData.setItemID(c.getString(1));
                inquiryData.setEmail(c.getString(2));
                inquiryData.setInquiry(c.getString(3));
                inquiryData.setResponse(c.getString(4));

                String customerEmail = c.getString(2);
                String getInquiryIdentificationSQL = "SELECT * FROM customer WHERE email='"+customerEmail+"'";
                Cursor c2 = db.runSELECT(getInquiryIdentificationSQL);
                if(c2.getCount()>0){
                    c2.moveToNext();
                    inquiryData.setIdentification(c2.getString(1));
                }
                c2.close();

                inquiries.add(inquiryData);
            }
        }
        c.close();
        return inquiries;
    }

    public static ArrayList getItemReviews(String itemID){
        ArrayList reviews = new ArrayList();
        String getItemReviewsSQL="SELECT * FROM review WHERE itemID='"+itemID+"'";
        Cursor c = db.runSELECT(getItemReviewsSQL);
        if(c.getCount()>0){
            for(int i=0;i<c.getCount();i++) {
                c.moveToNext();

                ReviewData reviewData = new ReviewData();
                reviewData.setReviewID(c.getString(0));
                reviewData.setItemID(c.getString(1));
                reviewData.setEmail(c.getString(2));
                reviewData.setReviewText(c.getString(3));
                reviewData.setRating(c.getString(4));

                String customerEmail = c.getString(2);
                String getReviewIdentificationSQL = "SELECT * FROM customer WHERE email='"+customerEmail+"'";
                Cursor c2 = db.runSELECT(getReviewIdentificationSQL);
                if(c2.getCount()>0){
                    c2.moveToNext();
                    reviewData.setIdentification(c2.getString(1));
                }
                c2.close();

                reviews.add(reviewData);
            }
        }
        c.close();
        return reviews;

    }

    public static void addInquiry(String itemID, String email, String inquiry){
        String inquiryID="";
        String getInquiryIdSQL = "SELECT substr(inquiryID,2,8)+1 AS IID FROM inquiry ORDER BY substr(inquiryID,2,8)+1 DESC LIMIT 1";
        Cursor c = db.runSELECT(getInquiryIdSQL);
        if(c.getCount()>0){
            c.moveToNext();
            inquiryID = "I"+c.getString(0);
        }else{
            inquiryID = "I10000000";
        }
        c.close();

        DateFormat dateFormat = new SimpleDateFormat("yyyy.MM.dd 'at' HH:mm:ss");
        String inquiryWithDate = inquiry + " " + dateFormat.format(Calendar.getInstance().getTime());

        String insertInquirySQL = "INSERT INTO inquiry(inquiryID, itemID, email, inquiry) VALUES('"+inquiryID+"','"+itemID+"','"+email+"','"+inquiryWithDate+"')";
        db.runSQL(insertInquirySQL);
    }

    public static String addToBag(String itemID, String email, String quantity, String size){
        String msg="";
        ItemData itemData = getItemInformation(itemID);
        String checkBagItemSQL ="SELECT * FROM shopping_cart WHERE email='"+email+"' AND itemID='"+itemID+"'";
        Cursor c = db.runSELECT(checkBagItemSQL);
        if(c.getCount()>0){
            msg=itemData.getTitle()+" is already in your shopping bag";
        }else{
            String initialSize="";
            if(itemData.getSizes().size()==0){
                initialSize="0";
            }else{
                initialSize = size;
            }
            double totalPrice = Double.parseDouble(itemData.getPrice())*Integer.parseInt(quantity);
            String addToBagSQL ="INSERT INTO shopping_cart(email,itemID,size,qty,totalPrice) VALUES('"+email+"','"+itemID+"','"+initialSize+"','"+quantity+"',"+totalPrice+")";
            db.runSQL(addToBagSQL);
            msg=itemData.getTitle()+" was added to your shopping Bag";
        }
        c.close();
        return msg;
    }

    public static ArrayList getBagItems(String email){
        ArrayList bag = new ArrayList();
        String getBagItemsSQL ="SELECT * FROM shopping_cart WHERE email='"+email+"'";
        Cursor c = db.runSELECT(getBagItemsSQL);
        if(c.getCount()>0){
            for(int i=0;i<c.getCount();i++){
                c.moveToNext();
                BagItem bagItem = new BagItem();
                ItemData itemData = getItemInformation(c.getString(1));
                bagItem.setItemID(c.getString(1));
                bagItem.setTitle(itemData.getTitle());
                bagItem.setSize(c.getString(2));
                bagItem.setQuantity(c.getString(3));
                bagItem.setPrice(itemData.getPrice());
                bagItem.setTotalPrice(c.getString(4));
                bagItem.setImage(itemData.getImage());
                bag.add(bagItem);
            }
        }
        c.close();
        return bag;
    }

    public static void addReview(String itemID, String email, String review,String rating){
        String reviewID="";
        String getReviewIdSQL = "SELECT substr(reviewID,2,8)+1 AS RID FROM review ORDER BY substr(reviewID,2,8)+1 DESC LIMIT 1";
        Cursor c = db.runSELECT(getReviewIdSQL);
        if(c.getCount()>0){
            c.moveToNext();
            reviewID = "R"+c.getString(0);
        }else{
            reviewID = "R10000000";
        }
        c.close();

        DateFormat dateFormat = new SimpleDateFormat("yyyy.MM.dd 'at' HH:mm:ss");
        String reviewWithDate = review + " " + dateFormat.format(Calendar.getInstance().getTime());

        String insertReviewSQL = "INSERT INTO review(reviewID, itemID, email, reviewText, rating) VALUES('"+reviewID+"','"+itemID+"','"+email+"','"+reviewWithDate+"','"+rating+"')";
        db.runSQL(insertReviewSQL);
    }

    public static void editBagItem(String itemID, String email,String quantity, String size){

        ItemData itemData = getItemInformation(itemID);
        String initialSize = "";
        if (itemData.getSizes().size()==0) {
            initialSize = "0";
        } else {
            initialSize = size;
        }

        double totalPrice = Double.parseDouble(itemData.getPrice())*Integer.parseInt(quantity);
        String editBagItemSQL="UPDATE shopping_cart SET size='"+initialSize+"', qty='"+quantity+"', totalPrice='"+totalPrice+"' WHERE email='"+email+"' AND itemID='"+itemID+"'";
        db.runSQL(editBagItemSQL);
    }

    public static void removeBagItem(String itemID, String email){
        String removeBagItemSQL="DELETE FROM shopping_cart WHERE email='"+email+"' AND itemID='"+itemID+"'";
        db.runSQL(removeBagItemSQL);
    }

    public static String processPurchase(String email){

        String purchaseID="";
        String getReviewIdSQL = "SELECT substr(purchaseID,2,8)+1 AS PID FROM purchase ORDER BY substr(purchaseID,2,8)+1 DESC LIMIT 1";
        Cursor c = db.runSELECT(getReviewIdSQL);
        if(c.getCount()>0){
            c.moveToNext();
            purchaseID = "P"+c.getString(0);
        }else{
            purchaseID = "P10000000";
        }
        c.close();

        String grandTotal="";
        String getGrandTotalSQL = "SELECT SUM(totalPrice) AS subTotal FROM shopping_cart WHERE email='"+email+"'";
        Cursor c2 = db.runSELECT(getGrandTotalSQL);
        if(c2.getCount()>0){
            c2.moveToNext();
            grandTotal=c2.getString(0);
        }
        c2.close();

        DateFormat dateFormat = new SimpleDateFormat("yyyy.MM.dd 'at' HH:mm:ss");
        String date = dateFormat.format(Calendar.getInstance().getTime());

        String insertPurchaseSQL = "INSERT INTO purchase(purchaseID,email,date,totalPrice) VALUES('"+purchaseID+"','"+email+"','"+date+"','"+grandTotal+"')";
        db.runSQL(insertPurchaseSQL);
        addPurchaseItems(email,purchaseID);
        String deleteShoppingCartSQL="DELETE FROM shopping_cart WHERE email='"+email+"'";
        db.runSQL(deleteShoppingCartSQL);

        return purchaseID;
    }

    public static void addPurchaseItems(String email, String purchaseID){
        ArrayList bag = Item.getBagItems(email);

        if(bag.size()>0){
            for (Iterator it = bag.iterator (); it.hasNext (); ) {
                BagItem bagItem = (BagItem) it.next();

                if(!bagItem.getSize().equals("0")){
                    String updateSizeQtySQL = "UPDATE item_size SET sQuantity=sQuantity-'"+bagItem.getQuantity()+"' WHERE itemID='"+bagItem.getImage()+"' AND size='"+bagItem.getSize()+"'";
                    db.runSQL(updateSizeQtySQL);
                }

                String updateQtySQL = "UPDATE item SET quantity= quantity-'"+bagItem.getQuantity()+"' WHERE itemID='"+bagItem.getItemID()+"'";
                db.runSQL(updateQtySQL);

                String addPurchaseItemSQL="INSERT INTO purchase_item(purchaseID,itemID,qty,price) VALUES('"+purchaseID+"','"+bagItem.getItemID()+"','"+bagItem.getQuantity()+"','"+bagItem.getTotalPrice()+"')";
                db.runSQL(addPurchaseItemSQL);
            }
        }
    }

    public static PurchaseData getPurchaseInformation(String purchaseID){
        PurchaseData purchaseData = new PurchaseData();
        String getPurchaseInformationSQL = "SELECT * FROM purchase WHERE purchaseID='"+purchaseID+"'";
        Cursor c = db.runSELECT(getPurchaseInformationSQL);
        if(c.getCount()>0){
            c.moveToNext();
            purchaseData.setPurchaseID(c.getString(0));
            String email=c.getString(1);
            purchaseData.setEmail(email);
            ArrayList customer = Customer.getAccountInformation(email);
            purchaseData.setCustomerName(customer.get(1).toString()+" "+customer.get(2).toString());
            purchaseData.setDate(c.getString(2));
            purchaseData.setTotalPrice(c.getString(3));
            ArrayList purchaseItems = new ArrayList();

            String getPurchaseItemsSQL="SELECT * FROM purchase_item WHERE purchaseID='"+purchaseID+"'";
            Cursor c2 = db.runSELECT(getPurchaseItemsSQL);
            if(c2.getCount()>0){
                for(int i=0;i<c2.getCount();i++) {
                    c2.moveToNext();
                    BagItem bagItem = new BagItem();
                    bagItem.setItemID(c2.getString(1));
                    bagItem.setQuantity(c2.getString(2));
                    bagItem.setTotalPrice(c2.getString(3));
                    ItemData itemData = getItemInformation(bagItem.getItemID());
                    bagItem.setPrice(itemData.getPrice());
                    bagItem.setTitle(itemData.getTitle());
                    purchaseItems.add(bagItem);
                }
                purchaseData.setPurchaseItems(purchaseItems);
            }
            c2.close();
        }
        c.close();
        return purchaseData;
    }

    public static ArrayList search(String searchKey){
        String[] keys = searchKey.split(" ");
        ArrayList searchItems = new ArrayList();

        ArrayList<String> searchItemIDs = new ArrayList<String>();
        for(String key : keys){
            String searchQuerySQL = "SELECT * FROM item WHERE title LIKE '%"+key+"%' OR title LIKE '%"+key+"' OR title LIKE '"+key+"%' OR " +
                    "category LIKE '%"+key+"%' OR category LIKE '%"+key+"' OR category LIKE '"+key+"%' OR " +
                    "type LIKE '%"+key+"%' OR type LIKE '%"+key+"' OR type LIKE '"+key+"%' OR " +
                    "description LIKE '%"+key+"%' OR description LIKE '%"+key+"' OR description LIKE '"+key+"%'";

            Cursor c = db.runSELECT(searchQuerySQL);
            if (c.getCount()>0){
                for(int i=0;i<c.getCount();i++) {
                    c.moveToNext();
                    String itemID = c.getString(0);
                    if (!searchItemIDs.contains(itemID)) {
                        searchItemIDs.add(itemID);
                        ItemData itemData = new ItemData();
                        itemData.setItemID(c.getString(0));
                        itemData.setTitle(c.getString(1));
                        itemData.setPrice(c.getString(5));
                        itemData.setImage(c.getString(7));
                        searchItems.add(itemData);
                    }
                }
            }
            c.close();
        }

        return searchItems;
    }

}
