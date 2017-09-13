package com.example.nishrah.styleomega;

import android.content.Intent;
import android.support.v4.media.session.PlaybackStateCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.example.nishrah.styleomega.model.Customer;

import java.util.ArrayList;

public class ForgotPasswordActivity extends Navigation{

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forgot_password);
        useAtCreation();
    }

    public void btnSubmit_click(View view){
        Intent intent = new Intent(this,SingInActivity.class);

        EditText email = (EditText) findViewById(R.id.et_email);
        String emailS = email.getText().toString();

        if(emailS.trim().equals("")){
            email.setError("Please Enter Email Address");
        }else if(!android.util.Patterns.EMAIL_ADDRESS.matcher(emailS).matches()){
            email.setError( "Invalid Email Format" );
        }else{
            String forgotPasswordMsg = Customer.emailCredentials(emailS);
            if(forgotPasswordMsg.equals("Email does not exist in our records!")){
                email.setError("Email does not exist in our records!");
            }else{
                ArrayList customer = Customer.getAccountInformation(emailS);
                String firstName =(String) customer.get(1);
                String password = (String)customer.get(5);
                String emailSubject="StyleOmega - Forgot Password";
                String emailMsg= "These are your login details. If you want to reset your password, login to your account and do so in your account settings.<br><br> Username: "+emailS+" <br> Password: "+password;
                new SendMailTask(ForgotPasswordActivity.this).execute(emailS, firstName, emailSubject, emailMsg);

                CharSequence text = forgotPasswordMsg;
                Toast.makeText(getApplicationContext(), text, Toast.LENGTH_LONG).show();
                startActivity(intent);
            }
        }
    }
}
