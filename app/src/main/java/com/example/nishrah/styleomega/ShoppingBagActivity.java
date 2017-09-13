package com.example.nishrah.styleomega;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.nishrah.styleomega.model.BagItem;
import com.example.nishrah.styleomega.model.Item;
import com.example.nishrah.styleomega.model.ItemData;

import java.util.ArrayList;
import java.util.Iterator;

public class ShoppingBagActivity extends Navigation {

    String user;
    String quantitySpinnerItem;
    String sizeSpinnerItem;
    ArrayList quantityA;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shopping_bag);
        useAtCreation();

        SharedPreferences sharedPreferences = getSharedPreferences("key", Context.MODE_PRIVATE);
        user = sharedPreferences.getString("user",null);

        ArrayList bag = Item.getBagItems(user);
        LinearLayout linearLayoutMain = (LinearLayout)findViewById(R.id.ll_items);

        if(bag.size()>0){
            double grandTotal=0.0;
            for (Iterator it = bag.iterator (); it.hasNext (); ) {
                BagItem bagItem = (BagItem) it.next();

                LinearLayout itemLayout = new LinearLayout(this);
                itemLayout.setOrientation(LinearLayout.VERTICAL);
                LinearLayout.LayoutParams itemLayoutParams = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
                itemLayoutParams.setMargins(40,25,40,50);
                itemLayout.setLayoutParams(itemLayoutParams);
                itemLayout.setBackgroundColor(-1);
                itemLayout.setGravity(Gravity.CENTER_HORIZONTAL);

                LinearLayout itemInfo = new LinearLayout(this);
                itemInfo.setOrientation(LinearLayout.HORIZONTAL);
                itemInfo.setPadding(20,20,0,50);

                LinearLayout itemData = new LinearLayout(this);
                itemData.setOrientation(LinearLayout.VERTICAL);
                itemData.setPadding(90,0,0,0);

                LinearLayout itemAction = new LinearLayout(this);
                itemAction.setOrientation(LinearLayout.HORIZONTAL);

                ImageView itemImage = new ImageView(this);
                int id = this.getResources().getIdentifier(bagItem.getImage(), "drawable", "com.example.nishrah.styleomega");
                itemImage.setImageResource(id);
                itemImage.setLayoutParams(new LinearLayout.LayoutParams(200, 300));
                itemImage.setScaleType(ImageView.ScaleType.FIT_XY);
                itemImage.setTag(bagItem.getItemID());
                itemImage.setOnClickListener(new View.OnClickListener(){
                    @Override
                    public void onClick(View v) {
                        itemLink(v);
                    }
                });
                itemInfo.addView(itemImage);

                TextView itemTitle= new TextView(this);
                itemTitle.setText(bagItem.getTitle().toUpperCase());
                itemTitle.setTextColor(getResources().getColor(R.color.colorPrimary));
                itemData.addView(itemTitle);

                TextView itemID= new TextView(this);
                itemID.setText("Ref. "+bagItem.getItemID());
                itemData.addView(itemID);

                TextView itemDetails = new TextView(this);
                if(bagItem.getSize().equals("0")){
                    itemDetails.setText(bagItem.getPrice()+" LKR");
                }else{
                    itemDetails.setText(bagItem.getSize()+" | "+bagItem.getPrice()+" LKR");
                }
                itemDetails.setTextColor(getResources().getColor(R.color.colorPrimaryDark));
                itemData.addView(itemDetails);

                TextView itemQty= new TextView(this);
                itemQty.setText("Qty. "+bagItem.getQuantity());
                itemQty.setTextColor(getResources().getColor(R.color.colorPrimaryDark));
                itemData.addView(itemQty);

                Button editItem = new Button(this);
                editItem.setText("EDIT");
                editItem.setTextColor(getResources().getColor(R.color.colorPrimaryDark));
                editItem.setBackgroundColor(-1);
                editItem.setPadding(50,0,70,0);
                editItem.setTag(bagItem.getItemID()+","+bagItem.getQuantity()+","+bagItem.getSize());
                editItem.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        editItemClick(v);
                    }
                });
                itemAction.addView(editItem);

                TextView totalPrice = new TextView(this);
                totalPrice.setText(bagItem.getTotalPrice()+" LKR");
                totalPrice.setTextColor(getResources().getColor(R.color.colorPrimaryDark));
                totalPrice.setBackgroundColor(-1);
                totalPrice.setPadding(100,0,200,0);
                itemAction.addView(totalPrice);

                ImageView removeItem = new ImageView(this);
                removeItem.setImageResource(R.drawable.remove_item);
                removeItem.setLayoutParams(new LinearLayout.LayoutParams(90, 130));
                removeItem.setScaleType(ImageView.ScaleType.FIT_XY);
                removeItem.setTag(bagItem.getItemID());
                removeItem.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        removeItemClick(v);
                    }
                });
                itemAction.addView(removeItem);

                itemInfo.addView(itemData);
                itemLayout.addView(itemInfo);
                itemLayout.addView(itemAction);

                linearLayoutMain.addView(itemLayout);

                grandTotal+= Double.parseDouble(bagItem.getTotalPrice());
            }

            TextView grandTotalL = new TextView(this);
            grandTotalL.setText("GRAND TOTAL :  "+grandTotal+" LKR");
            grandTotalL.setTextColor(-1);
            grandTotalL.setPadding(50,50,0,40);
            grandTotalL.setBackgroundColor(getResources().getColor(R.color.colorPrimaryDark));
            LinearLayout.LayoutParams layoutParams =
                    new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
            layoutParams.gravity = Gravity.CENTER_HORIZONTAL;
            linearLayoutMain.addView(grandTotalL, layoutParams);

            Button checkout = new Button(this);
            checkout.setText("CHECKOUT");
            checkout.setTextColor(-1);
            checkout.setBackgroundColor(getResources().getColor(R.color.colorPrimary));
            checkout.setGravity(Gravity.CENTER);
            checkout.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    btnCheckout_click();
                }
            });
            linearLayoutMain.addView(checkout);

        }else{
            TextView shoppingBagMsg = new TextView(this);
            shoppingBagMsg.setText("Your Bag in empty");
            shoppingBagMsg.setTextColor(getResources().getColor(R.color.colorPrimaryDark));
            shoppingBagMsg.setGravity(Gravity.CENTER);
            shoppingBagMsg.setPadding(0,600,0,0);
            linearLayoutMain.addView(shoppingBagMsg);
        }
    }

    public void removeItemClick(View view){
        Intent intent = new Intent(this,ShoppingBagActivity.class);
        String itemID = view.getTag().toString();
        Item.removeBagItem(itemID,user);
        startActivity(intent);
    }

    public void editItemClick(View view){
        final Intent intent = new Intent(this,ShoppingBagActivity.class);
        String[] tag = view.getTag().toString().split(",");
        final String itemID = tag[0];
        ItemData itemData = Item.getItemInformation(itemID);
        int stockQuantity=Integer.parseInt(itemData.getQuantity());
        final ArrayList sizes =itemData.getSizes();

        final Dialog dialog = new Dialog(this);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.bag_spinner_content);
        dialog.setCancelable(true);

        quantityA = new ArrayList();
        for(int i=1;i<=stockQuantity-1;i++){
            quantityA.add(i);
        }
        final Spinner quantitySpinner = (Spinner) dialog.findViewById(R.id.spn_quantity);
        ArrayAdapter<String> quantityArrayAdapter = new ArrayAdapter<String>(this,android.R.layout.simple_spinner_item, quantityA);
        quantityArrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        quantitySpinner.setAdapter(quantityArrayAdapter);
        quantitySpinner.setSelection(quantityA.indexOf(Integer.parseInt(tag[1])));
        quantitySpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                quantitySpinnerItem = quantityA.get(position).toString();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) { }
        });

        final Spinner sizeSpinner = (Spinner) dialog.findViewById(R.id.spn_size);
        ArrayAdapter<String> sizeArrayAdapter;
        if(sizes==null){
            ArrayList sizeNull = new ArrayList();
            sizeNull.add("Free Size");
            sizeArrayAdapter = new ArrayAdapter<String>(this,android.R.layout.simple_spinner_item, sizeNull);
        }else{
            sizeArrayAdapter = new ArrayAdapter<String>(this,android.R.layout.simple_spinner_item, sizes);
        }
        sizeArrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        sizeSpinner.setAdapter(sizeArrayAdapter);
        sizeSpinner.setSelection(sizeArrayAdapter.getPosition(tag[2]));
        sizeSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                sizeSpinnerItem = sizes.get(position).toString();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) { }
        });

        Button btnConfirm = (Button) dialog.findViewById(R.id.btn_confirm);
        btnConfirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
                Item.editBagItem(itemID,user,quantitySpinnerItem,sizeSpinnerItem);
                startActivity(intent);
            }
        });
        dialog.show();
    }

    public void btnCheckout_click(){
        Intent intent = new Intent(this,ConfirmPurchaseActivity.class);
        startActivity(intent);
    }

    public void itemLink(View view){
        Intent intent = new Intent(this, ProductInformationActivity.class);
        String itemID = view.getTag().toString();
        SharedPreferences sharedPreferences = getSharedPreferences("key", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString("itemID", itemID);
        editor.commit();
        startActivity(intent);
    }
}
