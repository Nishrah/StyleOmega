package com.example.nishrah.styleomega;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.nishrah.styleomega.model.Item;

public class ContactClothStoreActivity extends Navigation{

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contact_cloth_store);
        useAtCreation();

        SharedPreferences sharedPreferences = getSharedPreferences("key", Context.MODE_PRIVATE);
        String itemID = sharedPreferences.getString("itemID",null);
        String title = sharedPreferences.getString("itemTitle",null);

        TextView itemTitle = (TextView)findViewById(R.id.tv_productName);
        itemTitle.setText(title.toUpperCase());

        Button submitButton = (Button)findViewById(R.id.btn_submit);
        submitButton.setTag(itemID);
    }

    public void btnSubmitInquiry_click(View view){
        SharedPreferences sharedPreferences = getSharedPreferences("key", Context.MODE_PRIVATE);
        String userEmail = sharedPreferences.getString("user",null);
        if(userEmail==""){
            Toast.makeText(getApplicationContext(), "Sign In before making an inquiry", Toast.LENGTH_SHORT).show();
            signInButton(view);
        }else{
            Intent intent = new Intent(this,ProductInformationActivity.class);
            String itemID = view.getTag().toString();
            EditText inquiry = (EditText) findViewById(R.id.et_inquiry);
            String inquiryS = inquiry.getText().toString();
            if (inquiryS.trim().equals("")) {
                inquiry.setError("Please Enter Inquiry");
            }else {
                Item.addInquiry(itemID, userEmail, inquiryS);
                Toast.makeText(getApplicationContext(), "Your Inquiry has been posted", Toast.LENGTH_SHORT).show();
                startActivity(intent);
            }
        }
    }
}
