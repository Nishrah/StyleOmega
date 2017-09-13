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

public class SingInActivity extends Navigation{

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sing_in);
        useAtCreation();
    }

    public void createAccountButton(View view){

        Intent intent = new Intent(this, RegisterActivity.class);
        startActivity(intent);
    }

    public void forgotPasswordLink(View view){
        Intent intent = new Intent(this, ForgotPasswordActivity.class);
        startActivity(intent);
    }

    public void btnSignIn_click(View view){
        Intent intent = new Intent(this,MyAccountActivity.class);

        EditText email = (EditText)findViewById(R.id.et_email);
        EditText password = (EditText)findViewById(R.id.et_password);

        String emailS = email.getText().toString();
        String passwordS = password.getText().toString();

        if( emailS.trim().equals("")){
            email.setError( "Please Enter Email" );
        }else if (passwordS.trim().equals("")) {
            password.setError("Please Enter Password");
        }else{
            String signInMsg = Customer.verifyAccount(emailS,passwordS);
            if(signInMsg.equals("Invalid Username or Password!")){
                password.setText("");
                password.setError(signInMsg);
            }else{
                SharedPreferences sharedPreferences = getSharedPreferences("key", Context.MODE_PRIVATE);
                SharedPreferences.Editor editor = sharedPreferences.edit();
                editor.putString("user", emailS);
                editor.commit();

                CharSequence text = signInMsg;
                Toast.makeText(getApplicationContext(), text, Toast.LENGTH_SHORT).show();

                startActivity(intent);
            }
        }
    }
}
