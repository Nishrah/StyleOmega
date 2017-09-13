package com.example.nishrah.styleomega;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.example.nishrah.styleomega.model.Customer;

import java.util.ArrayList;

public class MyAccountActivity extends Navigation{

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_account);
        useAtCreation();

        SharedPreferences sharedPreferences = getSharedPreferences("key", Context.MODE_PRIVATE);
        String user = sharedPreferences.getString("user",null);
        ArrayList<String> customer = Customer.getAccountInformation(user);

        TextView name = (TextView)findViewById(R.id.tv_name);
        TextView email = (TextView)findViewById(R.id.tv_email);
        TextView tel = (TextView)findViewById(R.id.tv_tel);
        TextView address = (TextView)findViewById(R.id.tv_address);

        String nameS = customer.get(1) +" "+ customer.get(2);
        name.setText(nameS);
        email.setText(customer.get(0));
        tel.setText(customer.get(3));
        address.setText(customer.get(4));

    }

    public void editProfileLink(View view){

        Intent intent = new Intent(this, EditProfileActivity.class);
        startActivity(intent);
    }

    public void resetPasswordLink(View view){

        Intent intent = new Intent(this, ResetPasswordActivity.class);
        startActivity(intent);
    }

    public void viewInquiriesLink(View view){
        Intent intent = new Intent(this, InquiriesActivity.class);
        startActivity(intent);
    }

    public void purchaseHistoryLink(View view){
        Intent intent = new Intent(this, PurchaseHistoryActivity.class);
        startActivity(intent);
    }

    public void btnSignOut_click(View view){
        SharedPreferences sharedPreferences = getSharedPreferences("key", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString("user", "");
        editor.commit();

        Intent intent = new Intent(this, SingInActivity.class);
        startActivity(intent);
    }
}
