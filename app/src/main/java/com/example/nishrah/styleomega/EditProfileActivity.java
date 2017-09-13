package com.example.nishrah.styleomega;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.example.nishrah.styleomega.model.Customer;

import java.util.ArrayList;

public class EditProfileActivity extends Navigation{

    EditText firstName;
    EditText lastName;
    EditText tel;
    EditText address;

    SharedPreferences sharedPreferences;
    String user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_profile);
        useAtCreation();

        firstName = (EditText)findViewById(R.id.et_firstName);
        lastName = (EditText)findViewById(R.id.et_lastName);
        tel = (EditText)findViewById(R.id.et_tel);
        address = (EditText)findViewById(R.id.et_address);

        sharedPreferences = getSharedPreferences("key", Context.MODE_PRIVATE);
        user = sharedPreferences.getString("user",null);
        ArrayList<String> customer = Customer.getAccountInformation(user);

        firstName.setText(customer.get(1));
        lastName.setText(customer.get(2));
        tel.setText(customer.get(3));
        address.setText(customer.get(4));
    }

    public void btnApplyChanges_click(View view){
        Intent intent = new Intent(this,MyAccountActivity.class);

        String firstNameS = firstName.getText().toString();
        String lastNameS = lastName.getText().toString();
        String telS = tel.getText().toString();
        String addressS = address.getText().toString();

        if (firstNameS.trim().equals("")){
            firstName.setError( "Please Enter First Name" );
        }else if (lastNameS.trim().equals("")){
            lastName.setError( "Please Enter Last Name" );
        }else if (addressS.trim().equals("")) {
            address.setError("Please Enter Address");
        }else{
            Customer.updateAccount(user,firstNameS,lastNameS,telS,addressS);
            Toast.makeText(getApplicationContext(), "Your profile has been updated", Toast.LENGTH_SHORT).show();
            startActivity(intent);
        }

    }
}
