package com.example.nishrah.styleomega;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.drawable.GradientDrawable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

import com.example.nishrah.styleomega.model.BagItem;
import com.example.nishrah.styleomega.model.Item;
import com.example.nishrah.styleomega.model.PurchaseData;

import java.util.ArrayList;
import java.util.Iterator;

public class ConfirmPurchaseActivity extends Navigation {

    String user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_confirm_purchase);
        useAtCreation();

        SharedPreferences sharedPreferences = getSharedPreferences("key", Context.MODE_PRIVATE);
        user = sharedPreferences.getString("user",null);

        GradientDrawable gradientDrawable=new GradientDrawable();
        gradientDrawable.setStroke(1,getResources().getColor(R.color.colorPrimaryDark));
        LinearLayout horizontalLine = (LinearLayout)findViewById(R.id.ll_horizontalLine);
        horizontalLine.setBackgroundDrawable(gradientDrawable);
        horizontalLine.setPadding(0,0,0,5);

        ArrayList bag = Item.getBagItems(user);
        TableLayout items = (TableLayout)findViewById(R.id.tl_itemInformation);

        if(bag.size()>0){
            double grandTotal=0.0;
            for (Iterator it = bag.iterator (); it.hasNext (); ) {
                BagItem bagItem = (BagItem) it.next();
                View tableRow = LayoutInflater.from(this).inflate(R.layout.table_row,null,false);

                TextView titleSize = (TextView) tableRow.findViewById(R.id.tv_itemV);
                if(bagItem.getSize().equals("0")){
                    titleSize.setText(bagItem.getTitle());
                }else {
                    titleSize.setText(bagItem.getTitle()+"\n Size: "+bagItem.getSize());
                }

                TextView quantity = (TextView) tableRow.findViewById(R.id.tv_qtyV);
                quantity.setText(bagItem.getQuantity());

                TextView price = (TextView) tableRow.findViewById(R.id.tv_priceV);
                price.setText(bagItem.getPrice());

                TextView totalPrice = (TextView) tableRow.findViewById(R.id.tv_totalPriceV);
                totalPrice.setText(bagItem.getTotalPrice()+" LKR");

                items.addView(tableRow);
                grandTotal+=Double.parseDouble(bagItem.getTotalPrice());
            }
            
            TextView grandTotalTV = (TextView)findViewById(R.id.tv_totalValue);
            grandTotalTV.setText((""+grandTotal+" LKR"));
        }

    }

    public void btnConfirm_click(View view){

        EditText shippingAddress = (EditText)findViewById(R.id.et_shippingAddress);
        EditText cardNumber = (EditText)findViewById(R.id.et_cardNumber);
        EditText securityCode = (EditText)findViewById(R.id.et_securityCode);
        EditText expiryDate = (EditText)findViewById(R.id.et_expirationDate);

        String shippingAddressS = shippingAddress.getText().toString();
        String cardNumberS = cardNumber.getText().toString();
        String securityCodeS = securityCode.getText().toString();
        String expiryDateS = expiryDate.getText().toString();

        if( shippingAddressS.trim().equals("")){
            shippingAddress.setError( "Please Enter Shipping Address" );
        }else if(cardNumberS.trim().equals("")){
            cardNumber.setError( "Please Enter Card Number" );
        }else if(!(cardNumberS.trim().length()==16)){
            cardNumber.setError( "Invalid Card Number" );
        }else if(securityCodeS.trim().equals("")){
            securityCode.setError( "Please Enter Security Code" );
        }else if(!(securityCodeS.trim().length()==3)){
            securityCode.setError( "Invalid Security Code" );
        }else if(expiryDateS.trim().equals("")){
            expiryDate.setError( "Please Select Expiry Date" );
        }else{
            Intent intent = new Intent(this,ReceiptActivity.class);
            String purchaseID = Item.processPurchase(user);
            PurchaseData purchaseData = Item.getPurchaseInformation(purchaseID);
            String emailSubject="Order Receipt - "+purchaseData.getPurchaseID();
            String emailMsg= "Your purchase made on "+purchaseData.getDate()+" for a total of "+purchaseData.getTotalPrice()+".00 LKR was sucessfull.<br>The items will be delivered to you in 15-16 working days.";
            String firstName = purchaseData.getCustomerName();
            String email = purchaseData.getEmail();
            new SendMailTask(ConfirmPurchaseActivity.this).execute(email, firstName, emailSubject, emailMsg);

            SharedPreferences sharedPreferences = getSharedPreferences("key", Context.MODE_PRIVATE);
            SharedPreferences.Editor editor = sharedPreferences.edit();
            editor.putString("purchaseID", purchaseID);
            editor.commit();
            startActivity(intent);
        }

    }
}
