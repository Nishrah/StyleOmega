package com.example.nishrah.styleomega;

import android.app.ActionBar;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.nishrah.styleomega.model.InquiryData;
import com.example.nishrah.styleomega.model.Item;
import com.example.nishrah.styleomega.model.ItemData;

import java.util.ArrayList;
import java.util.Iterator;

import static android.R.attr.id;

public class ProductListActivity extends Navigation{

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product_list);
        useAtCreation();

        Intent intent = getIntent();
        String category = intent.getStringExtra(Navigation.EXTRA_CATEGORY);
        String type = intent.getStringExtra(Navigation.EXTRA_TYPE);
        this.setTitle(category.toUpperCase());

        ArrayList items = Item.getAvailableItems(category,type);

        LinearLayout linearLayoutMain = (LinearLayout)findViewById(R.id.ll_items);

        if(items.size()>0){
            for (Iterator it = items.iterator (); it.hasNext (); ) {
                ItemData itemData = (ItemData) it.next();

                LinearLayout itemLayout = new LinearLayout(this);
                itemLayout.setOrientation(LinearLayout.HORIZONTAL);
                itemLayout.setPadding(0,50,0,0);
                itemLayout.setTag(itemData.getItemID());
                itemLayout.setOnClickListener(new View.OnClickListener(){
                    @Override
                    public void onClick(View v) {
                        itemLink(v);
                    }
                });

                ImageView itemImage = new ImageView(this);
                int id = this.getResources().getIdentifier(itemData.getImage(), "drawable", "com.example.nishrah.styleomega");
                itemImage.setImageResource(id);
                itemImage.setLayoutParams(new LinearLayout.LayoutParams(400, 500));
                itemImage.setScaleType(ImageView.ScaleType.FIT_XY);

                LinearLayout itemInfo = new LinearLayout(this);
                itemInfo.setOrientation(LinearLayout.VERTICAL);
                itemInfo.setPadding(50,0,0,0);

                TextView itemTitle = new TextView(this);
                itemTitle.setText(itemData.getTitle().toUpperCase());
                itemTitle.setTextColor(getResources().getColor(R.color.colorAccent));

                TextView itemPrice = new TextView(this);
                itemPrice.setText(itemData.getPrice()+" LKR");
                itemPrice.setTextColor(getResources().getColor(R.color.colorPrimaryDark));

                itemInfo.addView(itemTitle);
                itemInfo.addView(itemPrice);

                itemLayout.addView(itemImage);
                itemLayout.addView(itemInfo);

                linearLayoutMain.addView(itemLayout);
            }
        }else{
            TextView inquiriesMsg = new TextView(this);
            inquiriesMsg.setText("No items have been recorded");
            inquiriesMsg.setTextColor(getResources().getColor(R.color.colorPrimaryDark));
            inquiriesMsg.setGravity(Gravity.CENTER);
            inquiriesMsg.setPadding(0,50,0,0);
            linearLayoutMain.addView(inquiriesMsg);
        }

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
