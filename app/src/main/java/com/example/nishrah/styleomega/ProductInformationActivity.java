package com.example.nishrah.styleomega;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.drawable.GradientDrawable;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RatingBar;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.nishrah.styleomega.model.InquiryData;
import com.example.nishrah.styleomega.model.Item;
import com.example.nishrah.styleomega.model.ItemData;
import com.example.nishrah.styleomega.model.ReviewData;

import java.util.ArrayList;
import java.util.Iterator;

public class ProductInformationActivity extends Navigation {

    ArrayList sizeA;
    int stockQuantity;
    String quantitySpinnerItem;
    String sizeSpinnerItem;
    ArrayList quantity = new ArrayList();
    String itemID;
    String email;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product_information);
        useAtCreation();

        SharedPreferences sharedPreferences = getSharedPreferences("key", Context.MODE_PRIVATE);
        itemID = sharedPreferences.getString("itemID",null);
        email = sharedPreferences.getString("user",null);

        ItemData itemdata = Item.getItemInformation(itemID);

        this.setTitle(itemdata.getCategory().toUpperCase());

        TextView title = (TextView) findViewById(R.id.tv_heading);
        title.setText(itemdata.getTitle().toUpperCase());
        title.setTextColor(getResources().getColor(R.color.colorAccent));
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString("itemTitle", itemdata.getTitle());
        editor.commit();
        Button shareButton = (Button) findViewById(R.id.btn_share);
        shareButton.setTag(itemdata.getTitle());

        ImageView image = (ImageView)findViewById(R.id.iv_itemImage);
        int id = this.getResources().getIdentifier(itemdata.getImage(), "drawable", "com.example.nishrah.styleomega");
        image.setImageResource(id);

        TextView price = (TextView) findViewById(R.id.tv_itemPrice);
        price.setText(itemdata.getPrice()+" LKR");
        price.setTextColor(getResources().getColor(R.color.colorPrimaryDark));

        TextView quantity = (TextView)findViewById(R.id.tv_itemQuantity);
        quantity.setText(itemdata.getQuantity()+" IN STOCK");
        quantity.setTextColor(getResources().getColor(R.color.colorAccent));
        stockQuantity=Integer.parseInt(itemdata.getQuantity());

        TextView description = (TextView) findViewById(R.id.tv_itemDescription);
        description.setText(itemdata.getDescription());
        description.setTextColor(getResources().getColor(R.color.colorPrimaryDark));

        sizeA = itemdata.getSizes();
        if(sizeA.size()>0){
            String sizeS = "AVAILABLE SIZES: ";
            for (Iterator it = sizeA.iterator (); it.hasNext (); ) {
                sizeS = sizeS + (String)it.next() + " ";
            }

            TextView size = (TextView) findViewById(R.id.tv_itemSize);
            size.setText(sizeS);
            size.setTextColor(getResources().getColor(R.color.colorPrimaryDark));
        }

        ArrayList reviewA = itemdata.getReviews();

        if(reviewA.size()>0){

            LinearLayout reviews = (LinearLayout)findViewById(R.id.ll_itemReviews);

            GradientDrawable gradientDrawable=new GradientDrawable();
            gradientDrawable.setStroke(1,getResources().getColor(R.color.colorPrimaryDark));
            LinearLayout horizontalLine = new LinearLayout(this);
            horizontalLine.setBackgroundDrawable(gradientDrawable);
            horizontalLine.setPadding(0,0,0,10);

            reviews.addView(horizontalLine);

            TextView reviewHeading = new TextView(this);
            reviewHeading.setText("REVIEWS");
            reviewHeading.setGravity(Gravity.CENTER);
            reviewHeading.setPadding(0,16,0,10);

            reviews.addView(reviewHeading);

            for (Iterator it = reviewA.iterator (); it.hasNext (); ) {
                ReviewData reviewData = (ReviewData) it.next();
                LinearLayout linearLayoutR2 = new LinearLayout(this);
                linearLayoutR2.setOrientation(LinearLayout.VERTICAL);
                linearLayoutR2.setPadding(0,50,0,20);

                TextView identificationR = new TextView(this);
                identificationR.setText(reviewData.getIdentification());
                identificationR.setTextColor(getResources().getColor(R.color.colorAccent));

                TextView review = new TextView(this);
                review.setText(reviewData.getReviewText());
                review.setTextColor(getResources().getColor(R.color.colorPrimary));

                int ratingI = Integer.parseInt(reviewData.getRating());
                RatingBar rating = new RatingBar(this,null,android.R.attr.ratingBarStyleSmall);
                rating.setRating(ratingI);
                rating.setPadding(10,10,0,0);
                rating.isIndicator();
                rating.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT));

                linearLayoutR2.addView(identificationR);
                linearLayoutR2.addView(review);
                linearLayoutR2.addView(rating);

                reviews.addView(linearLayoutR2);
            }
        }

        ArrayList inquiriesA = itemdata.getInquiries();

        if(inquiriesA.size()>0){

            LinearLayout inquiries = (LinearLayout)findViewById(R.id.ll_itemInquiries);

            GradientDrawable gradientDrawable=new GradientDrawable();
            gradientDrawable.setStroke(1,getResources().getColor(R.color.colorPrimaryDark));
            LinearLayout horizontalLine = new LinearLayout(this);
            horizontalLine.setBackgroundDrawable(gradientDrawable);
            horizontalLine.setPadding(0,0,0,10);

            inquiries.addView(horizontalLine);

            TextView inquiryHeading = new TextView(this);
            inquiryHeading.setText(R.string.inquiry_heading);
            inquiryHeading.setGravity(Gravity.CENTER);
            inquiryHeading.setPadding(0,16,0,10);

            inquiries.addView(inquiryHeading);

            for (Iterator it = inquiriesA.iterator (); it.hasNext (); ) {
                InquiryData inquiryData = (InquiryData) it.next();
                LinearLayout linearLayout2 = new LinearLayout(this);
                linearLayout2.setOrientation(LinearLayout.VERTICAL);
                linearLayout2.setPadding(0,50,0,20);

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

                inquiries.addView(linearLayout2);
            }
        }

    }

    public void contactStyleOmegaLink(View view){
        Intent intent = new Intent(this,ContactClothStoreActivity.class);
        startActivity(intent);
    }

    public void share(View view){
        String itemTitle = view.getTag().toString();
        Intent intent = new Intent(android.content.Intent.ACTION_SEND);
        intent.setType("text/plain");
        String shareBodyText = "Visit StyleOmega and check out the "+itemTitle;
        intent.putExtra(android.content.Intent.EXTRA_SUBJECT, "Visit StyleOmega");
        intent.putExtra(android.content.Intent.EXTRA_TEXT, shareBodyText);
        startActivity(Intent.createChooser(intent, "SHARE"));
    }

    public void addToBagLink(View view){

        if(email==""){
            Toast.makeText(getApplicationContext(), "Sign In to maintain a shopping bag", Toast.LENGTH_SHORT).show();
            signInButton(view);
        }else{

            final Dialog dialog = new Dialog(this);
            dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
            dialog.setContentView(R.layout.bag_spinner_content);
            dialog.setCancelable(true);

            for(int i=1;i<=stockQuantity-1;i++){
                quantity.add(i);
            }
            final Spinner quantitySpinner = (Spinner) dialog.findViewById(R.id.spn_quantity);
            ArrayAdapter<String> quantityArrayAdapter = new ArrayAdapter<String>(this,android.R.layout.simple_spinner_item, quantity);
            quantityArrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            quantitySpinner.setAdapter(quantityArrayAdapter);
            quantitySpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

                @Override
                public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                    quantitySpinnerItem = quantity.get(position).toString();
                }

                @Override
                public void onNothingSelected(AdapterView<?> parent) { }
            });

            final Spinner sizeSpinner = (Spinner) dialog.findViewById(R.id.spn_size);
            ArrayAdapter<String> sizeArrayAdapter;
            if(sizeA==null){
                ArrayList sizeNull = new ArrayList();
                sizeNull.add("Free Size");
                sizeArrayAdapter = new ArrayAdapter<String>(this,android.R.layout.simple_spinner_item, sizeNull);
            }else{
                sizeArrayAdapter = new ArrayAdapter<String>(this,android.R.layout.simple_spinner_item, sizeA);
            }
            sizeArrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            sizeSpinner.setAdapter(sizeArrayAdapter);
            sizeSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

                @Override
                public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                    sizeSpinnerItem = sizeA.get(position).toString();
                }

                @Override
                public void onNothingSelected(AdapterView<?> parent) { }
            });

            Button btnConfirm = (Button) dialog.findViewById(R.id.btn_confirm);
            btnConfirm.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    dialog.dismiss();
                    String msg = Item.addToBag(itemID,email,quantitySpinnerItem,sizeSpinnerItem);
                    CharSequence msgC = msg;
                    Toast.makeText(getApplicationContext(), msgC, Toast.LENGTH_SHORT).show();
                }
            });
            dialog.show();
        }
    }

    public void addReviewLink(View view){
        Intent intent = new Intent(this,ReviewsActivity.class);
        startActivity(intent);
    }
}
