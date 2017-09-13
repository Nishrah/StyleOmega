package com.example.nishrah.styleomega;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;


public class Navigation extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    public static final String EXTRA_CATEGORY= "com.example.styleomega.CATEGORY";
    public static final String EXTRA_TYPE= "com.example.styleomega.TYPE";

    public void useAtCreation(){
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.mSearch) {
            Intent intent = new Intent(getApplicationContext(), SearchActivity.class);
            startActivity(intent);
            //return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_home) {
            Intent intent = new Intent(getApplicationContext(), MainActivity.class);
            startActivity(intent);
        } else if (id == R.id.nav_women) {
            Intent intent = new Intent(getApplicationContext(), WomenCategoryActivity.class);
            startActivity(intent);
        } else if (id == R.id.nav_men) {
            Intent intent = new Intent(getApplicationContext(), MenCategoryActivity.class);
            startActivity(intent);
        } else if (id == R.id.nav_kids) {
            Intent intent = new Intent(getApplicationContext(), KidCategoryActivity.class);
            startActivity(intent);
        } else if (id == R.id.nav_aboutUs) {
            Intent intent = new Intent(getApplicationContext(), AboutUsActivity.class);
            startActivity(intent);
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    public void signInButton(View view){
        SharedPreferences sharedPreferences = getSharedPreferences("key", Context.MODE_PRIVATE);
        String user = sharedPreferences.getString("user",null);
        if(user==""){
            Intent intent = new Intent(getApplicationContext(), SingInActivity.class);
            startActivity(intent);
        }else{
            Intent intent = new Intent(getApplicationContext(), MyAccountActivity.class);
            startActivity(intent);
        }
    }

    public void shoppingBagButton(View view){
        SharedPreferences sharedPreferences = getSharedPreferences("key", Context.MODE_PRIVATE);
        String user = sharedPreferences.getString("user",null);
        if(user==""){
            Intent intent = new Intent(getApplicationContext(), SingInActivity.class);
            startActivity(intent);
            Toast.makeText(getApplicationContext(), "Sign In to view your shopping bag", Toast.LENGTH_SHORT).show();
        }else{
            Intent intent = new Intent(getApplicationContext(), ShoppingBagActivity.class);
            startActivity(intent);
        }
    }

    public void categoryLink(View view){
        String tag = view.getTag().toString();
        String category = tag.split(",")[0];
        String type = tag.split(",")[1];
        Intent intent = new Intent(this,ProductListActivity.class);
        intent.putExtra(EXTRA_CATEGORY,category);
        intent.putExtra(EXTRA_TYPE,type);
        startActivity(intent);
    }
}
