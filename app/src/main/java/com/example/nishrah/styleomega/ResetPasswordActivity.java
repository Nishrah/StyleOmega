package com.example.nishrah.styleomega;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.example.nishrah.styleomega.model.Customer;

public class ResetPasswordActivity extends Navigation {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reset_password);
        useAtCreation();
    }

    public void btnReset_click(View view){
        Intent intent = new Intent(this,MyAccountActivity.class);

        SharedPreferences sharedPreferences = getSharedPreferences("key", Context.MODE_PRIVATE);
        String user = sharedPreferences.getString("user",null);

        EditText currentPassword = (EditText)findViewById(R.id.et_currentPassword);
        EditText newPassword = (EditText)findViewById(R.id.et_newPassword);
        EditText reNewPassword = (EditText)findViewById(R.id.et_reNewPassword);

        String cPassword = currentPassword.getText().toString();
        String nPassword = newPassword.getText().toString();
        String rPassword = reNewPassword.getText().toString();

        if( cPassword.trim().equals("")){
            currentPassword.setError( "Please Enter Current Password" );
        }else if (nPassword.trim().equals("")) {
            newPassword.setError("Please Enter New Password");
        }else if (rPassword.trim().equals("")) {
            reNewPassword.setError("Please Re-Enter New Password");
        }else{
            if(!nPassword.equals(rPassword)){
                reNewPassword.setError("Does not match New Password");
            }else{
                String resetMsg = Customer.resetPassword(user,cPassword,rPassword);
                if(resetMsg.equals("Invalid Username or Password!")){
                    currentPassword.setText("");
                    currentPassword.setError("Invalid Password!");
                }else{
                    CharSequence text = resetMsg;
                    Toast.makeText(getApplicationContext(), text, Toast.LENGTH_SHORT).show();
                    startActivity(intent);
                }
            }
        }
    }
}
