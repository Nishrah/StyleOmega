package com.example.nishrah.styleomega;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;

import com.example.nishrah.styleomega.model.Customer;
import com.example.nishrah.styleomega.model.DatabaseHandler;
import com.example.nishrah.styleomega.model.Item;

public class MainActivity extends Navigation{

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        useAtCreation();

        SharedPreferences sharedPreferences = getSharedPreferences("key", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        if(!sharedPreferences.contains("user")){
            editor.putString("user", "");
            editor.commit();
        }

        DatabaseHandler db  = new DatabaseHandler(this);
        Customer.db = db;
        Item.db=db;
    }

    public void womenCategoryClick(View view){

        Intent intent = new Intent(this, WomenCategoryActivity.class);
        startActivity(intent);
    }

    public void MenCategoryClick(View view){

        Intent intent = new Intent(this, MenCategoryActivity.class);
        startActivity(intent);
    }

    public void KidCategoryClick(View view){

        Intent intent = new Intent(this, KidCategoryActivity.class);
        startActivity(intent);
    }

}
