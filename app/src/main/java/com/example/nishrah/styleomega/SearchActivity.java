package com.example.nishrah.styleomega;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.SearchView;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.nishrah.styleomega.model.Item;
import com.example.nishrah.styleomega.model.ItemData;

import java.util.ArrayList;
import java.util.Iterator;

public class SearchActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);
        final EditText searchKey = (EditText)findViewById(R.id.et_search);
        searchKey.setOnEditorActionListener(new EditText.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if ( (actionId == EditorInfo.IME_ACTION_DONE) || ((event.getKeyCode() == KeyEvent.KEYCODE_ENTER) && (event.getAction() == KeyEvent.ACTION_DOWN ))){
                    search(searchKey);
                    return true;
                }
                else{
                    return false;
                }
            }
        });
    }

    public void btnCancel_onClick(View view){
        Intent intent = new Intent(this,MainActivity.class);
        startActivity(intent);
    }

    public void search(EditText searchKey){
        String key = searchKey.getText().toString();
        ArrayList items = Item.search(key);
        LinearLayout linearLayoutMain = (LinearLayout)findViewById(R.id.ll_searchItems);
        linearLayoutMain.removeAllViews();

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
            inquiriesMsg.setText("No Search Product");
            inquiriesMsg.setTextColor(getResources().getColor(R.color.colorPrimaryDark));
            inquiriesMsg.setGravity(Gravity.CENTER);
            inquiriesMsg.setPadding(0,100,0,0);
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
