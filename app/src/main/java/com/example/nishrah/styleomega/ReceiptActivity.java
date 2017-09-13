package com.example.nishrah.styleomega;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TableLayout;
import android.widget.TextView;

import com.example.nishrah.styleomega.model.BagItem;
import com.example.nishrah.styleomega.model.Item;
import com.example.nishrah.styleomega.model.PurchaseData;

import java.util.ArrayList;
import java.util.Iterator;

public class ReceiptActivity extends Navigation {

    String purchaseID;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_receipt);
        useAtCreation();

        SharedPreferences sharedPreferences = getSharedPreferences("key", Context.MODE_PRIVATE);
        purchaseID = sharedPreferences.getString("purchaseID",null);

        PurchaseData purchaseData = Item.getPurchaseInformation(purchaseID);

        TextView purchaseID = (TextView)findViewById(R.id.tv_purchaseID);
        purchaseID.append(purchaseData.getPurchaseID());

        TextView customer = (TextView)findViewById(R.id.tv_customer);
        customer.append(purchaseData.getCustomerName());

        TextView date = (TextView)findViewById(R.id.tv_date);
        date.append(purchaseData.getDate());

        TextView grandTotal = (TextView)findViewById(R.id.tv_totalValue);
        grandTotal.setText(purchaseData.getTotalPrice()+".00 LKR");

        ArrayList purchasedItems = purchaseData.getPurchaseItems();
        TableLayout items = (TableLayout)findViewById(R.id.tl_itemInformation);

        if(purchasedItems.size()>0){
            for (Iterator it = purchasedItems.iterator (); it.hasNext (); ) {
                BagItem bagItem = (BagItem) it.next();
                View tableRow = LayoutInflater.from(this).inflate(R.layout.table_row,null,false);

                TextView titleSize = (TextView) tableRow.findViewById(R.id.tv_itemV);
                titleSize.setText(bagItem.getTitle());

                TextView quantity = (TextView) tableRow.findViewById(R.id.tv_qtyV);
                quantity.setText(bagItem.getQuantity());

                TextView price = (TextView) tableRow.findViewById(R.id.tv_priceV);
                price.setText(bagItem.getPrice());

                TextView totalPrice = (TextView) tableRow.findViewById(R.id.tv_totalPriceV);
                totalPrice.setText(bagItem.getTotalPrice()+" LKR");

                items.addView(tableRow);
            }
        }
    }

    public void btnOk_click(View view){
        Intent intent = new Intent(this,MainActivity.class);
        startActivity(intent);
    }
}
