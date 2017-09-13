package com.example.nishrah.styleomega;

import android.content.Context;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.TableLayout;
import android.widget.TextView;

import com.example.nishrah.styleomega.model.BagItem;
import com.example.nishrah.styleomega.model.Customer;
import com.example.nishrah.styleomega.model.PurchaseData;

import java.util.ArrayList;
import java.util.Iterator;

public class PurchaseHistoryActivity extends Navigation {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_purchase_history);
        useAtCreation();

        SharedPreferences sharedPreferences = getSharedPreferences("key", Context.MODE_PRIVATE);
        String user = sharedPreferences.getString("user",null);

        ArrayList purchaseHistory = Customer.getPurchaseHistory(user);
        TableLayout items = (TableLayout)findViewById(R.id.tl_itemInformation);

        if(purchaseHistory.size()>0) {
            for (Iterator it = purchaseHistory.iterator(); it.hasNext(); ) {
                PurchaseData purchaseData = (PurchaseData) it.next();
                String purchaseID = purchaseData.getPurchaseID();
                String date = purchaseData.getDate();
                ArrayList purchaseItems = purchaseData.getPurchaseItems();
                if(purchaseItems.size()>0){
                    for (Iterator it2 = purchaseItems.iterator(); it2.hasNext(); ) {
                        BagItem bagItem = (BagItem) it2.next();

                        View tableRow = LayoutInflater.from(this).inflate(R.layout.table_row,null,false);

                        TextView titleSize = (TextView) tableRow.findViewById(R.id.tv_itemV);
                        titleSize.setText(bagItem.getTitle()+"\n("+purchaseID+")");
                        titleSize.setTextSize(10);

                        TextView quantity = (TextView) tableRow.findViewById(R.id.tv_qtyV);
                        quantity.setText(bagItem.getQuantity());
                        quantity.setTextSize(10);

                        TextView price = (TextView) tableRow.findViewById(R.id.tv_priceV);
                        price.setText(bagItem.getTotalPrice());
                        price.setTextSize(10);

                        TextView totalPrice = (TextView) tableRow.findViewById(R.id.tv_totalPriceV);
                        totalPrice.setText(date);
                        totalPrice.setTextSize(9);

                        items.addView(tableRow);

                    }
                }
            }
        }else{
            TextView purchaseHistoryMsg = new TextView(this);
            purchaseHistoryMsg.setText("You have no Purchase History");
            purchaseHistoryMsg.setTextColor(getResources().getColor(R.color.colorPrimaryDark));
            purchaseHistoryMsg.setGravity(Gravity.CENTER);
            purchaseHistoryMsg.setPadding(0,400,0,0);
            items.addView(purchaseHistoryMsg);
        }
    }
}
