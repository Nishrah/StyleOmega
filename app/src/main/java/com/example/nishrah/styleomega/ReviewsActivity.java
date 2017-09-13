package com.example.nishrah.styleomega;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.nishrah.styleomega.model.Item;

public class ReviewsActivity extends Navigation{

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reviews);
        useAtCreation();

        SharedPreferences sharedPreferences = getSharedPreferences("key", Context.MODE_PRIVATE);
        String itemID = sharedPreferences.getString("itemID",null);
        String title = sharedPreferences.getString("itemTitle",null);

        TextView itemTitle = (TextView)findViewById(R.id.tv_productName);
        itemTitle.setText(title.toUpperCase());

        Button submitButton = (Button)findViewById(R.id.btn_submit);
        submitButton.setTag(itemID);
    }

    public void btnSubmitReview(View view){
        SharedPreferences sharedPreferences = getSharedPreferences("key", Context.MODE_PRIVATE);
        String userEmail = sharedPreferences.getString("user",null);
        if(userEmail==""){
            Toast.makeText(getApplicationContext(), "Sign In before adding a review", Toast.LENGTH_SHORT).show();
            signInButton(view);
        }else {
            Intent intent = new Intent(this, ProductInformationActivity.class);
            String itemID = view.getTag().toString();

            EditText review = (EditText) findViewById(R.id.et_review);
            String reviewS = review.getText().toString();

            RatingBar rating = (RatingBar) findViewById(R.id.rb_rating);
            String ratingS = String.valueOf(rating.getRating());

            if (reviewS.trim().equals("")) {
                review.setError("Please Enter Review");
            }else{
                Item.addReview(itemID,userEmail,reviewS,ratingS);
                Toast.makeText(getApplicationContext(), "Your Review has been posted", Toast.LENGTH_SHORT).show();
                startActivity(intent);
            }

        }
    }
}
