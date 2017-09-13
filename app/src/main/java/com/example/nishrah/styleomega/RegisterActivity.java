package com.example.nishrah.styleomega;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.example.nishrah.styleomega.model.Customer;

public class RegisterActivity extends Navigation{

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        useAtCreation();
    }

    public void btnCreateAccount_click(View view){
        Intent intent = new Intent(this,MyAccountActivity.class);

        EditText email = (EditText)findViewById(R.id.et_email);
        EditText firstName = (EditText)findViewById(R.id.et_firstName);
        EditText lastName = (EditText)findViewById(R.id.et_lastName);
        EditText tel = (EditText)findViewById(R.id.et_tel);
        EditText address = (EditText)findViewById(R.id.et_address);
        EditText password = (EditText)findViewById(R.id.et_password);
        EditText re_password = (EditText)findViewById(R.id.et_rePassword);

        String emailS = email.getText().toString();
        String firstNameS = firstName.getText().toString();
        String lastNameS = lastName.getText().toString();
        String telS = tel.getText().toString();
        String addressS = address.getText().toString();
        String passwordS = password.getText().toString();
        String re_passwordS = re_password.getText().toString();

        if( emailS.trim().equals("")){
            email.setError( "Please Enter Email" );
        }else if(!android.util.Patterns.EMAIL_ADDRESS.matcher(emailS).matches()){
            email.setError( "Invalid Email Format" );
        }else if (firstNameS.trim().equals("")){
            firstName.setError( "Please Enter First Name" );
        }else if (lastNameS.trim().equals("")){
            lastName.setError( "Please Enter Last Name" );
        }else if (addressS.trim().equals("")){
            address.setError( "Please Enter Address" );
        }else if (passwordS.trim().equals("")){
            password.setError( "Please Enter Password" );
        }else if (re_passwordS.trim().equals("")){
            re_password.setError( "Please re-enter Password" );
        }else{

            if(!password.getText().toString().equals(re_password.getText().toString())){
                re_password.setError( "Does not match Password" );
            }else{

                String registerMsg = Customer.register(emailS,firstNameS,lastNameS,telS,addressS,passwordS);
                if(registerMsg.equals("Email has already been used to create an account!")){
                    email.setError(registerMsg);
                }else{
                    SharedPreferences sharedPreferences = getSharedPreferences("key", Context.MODE_PRIVATE);
                    SharedPreferences.Editor editor = sharedPreferences.edit();
                    editor.putString("user", emailS);
                    editor.commit();

                    String emailSubject="Welcome to StyleOmega";
                    String emailMsg = "We are glad that you have regsitered with us.";
                    new SendMailTask(RegisterActivity.this).execute(emailS, firstNameS, emailSubject, emailMsg);

                    CharSequence text = registerMsg;
                    Toast.makeText(getApplicationContext(), text, Toast.LENGTH_SHORT).show();

                    startActivity(intent);
                }
            }
        }

    }
}
