package com.example.nishrah.styleomega;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.drawable.GradientDrawable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Gravity;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.example.nishrah.styleomega.model.Customer;
import com.example.nishrah.styleomega.model.InquiryData;

import java.util.ArrayList;
import java.util.Iterator;

public class InquiriesActivity extends Navigation {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_inquiries);
        useAtCreation();

        LinearLayout linearLayout = (LinearLayout) findViewById(R.id.ll_inquiries);

        GradientDrawable gradientDrawable=new GradientDrawable();
        gradientDrawable.setStroke(1,getResources().getColor(R.color.colorPrimaryDark));

        SharedPreferences sharedPreferences = getSharedPreferences("key", Context.MODE_PRIVATE);
        String user = sharedPreferences.getString("user",null);

        ArrayList inquiries = Customer.getInquiries(user);

        if(inquiries.size()>0){
            for (Iterator it = inquiries.iterator (); it.hasNext (); ) {
                InquiryData inquiryData = (InquiryData) it.next();
                LinearLayout linearLayout2 = new LinearLayout(this);
                linearLayout2.setOrientation(LinearLayout.VERTICAL);
                linearLayout2.setPadding(0,50,0,20);

                LinearLayout horizontalLine = new LinearLayout(this);
                horizontalLine.setBackgroundDrawable(gradientDrawable);
                horizontalLine.setPadding(0,0,0,10);

                TextView identification = new TextView(this);
                identification.setText(inquiryData.getIdentification());
                identification.setTextColor(getResources().getColor(R.color.colorAccent));

                TextView inquiry = new TextView(this);
                inquiry.setText(inquiryData.getInquiry());
                inquiry.setTextColor(getResources().getColor(R.color.colorPrimary));

                TextView response = new TextView(this);
                if(inquiryData.getResponse()==null){
                    response.setText(" - We will get to you soon");
                }else{
                    response.setText(inquiryData.getResponse());
                }
                response.setTextColor(getResources().getColor(R.color.colorPrimaryDark));
                response.setPadding(30,20,0,0);

                linearLayout2.addView(identification);
                linearLayout2.addView(inquiry);
                linearLayout2.addView(response);

                linearLayout.addView(linearLayout2);
                linearLayout.addView(horizontalLine);
            }
        }else{
            TextView inquiriesMsg = new TextView(this);
            inquiriesMsg.setText("No Inquiries have been made");
            inquiriesMsg.setTextColor(getResources().getColor(R.color.colorPrimaryDark));
            inquiriesMsg.setGravity(Gravity.CENTER);
            inquiriesMsg.setPadding(0,50,0,0);
            linearLayout.addView(inquiriesMsg);
        }

    }
}
