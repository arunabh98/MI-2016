package com.example.darknight.mi2016;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    String miNumberStored;
    int backButtonCount;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        SharedPreferences prefs = getSharedPreferences("userDetails", MODE_PRIVATE);
        miNumberStored = prefs.getString("MI_NUMBER", null);
        setContentView(R.layout.activity_main);
        Intent intent = getIntent();
        String NAME = intent.getStringExtra("NAME");
        String EMAIL = intent.getStringExtra("EMAIL");

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
        View header = navigationView.getHeaderView(0);
        LinearLayout navigationBar = (LinearLayout) header.findViewById(R.id.cover_picture);
        navigationBar.setBackgroundColor(ContextCompat.getColor(getApplicationContext(), R.color.black));
        TextView name = (TextView) header.findViewById(R.id.name);
        TextView email = (TextView) header.findViewById(R.id.email_id);
        if (getIntent().hasExtra("PROFILE_PIC")) {
            Bitmap profilePic = BitmapFactory.decodeByteArray(
                    getIntent().getByteArrayExtra("PROFILE_PIC"), 0, getIntent().getByteArrayExtra("PROFILE_PIC").length);
            ImageView profilepic = (ImageView) header.findViewById(R.id.profile_picture);
            profilepic.setImageBitmap(profilePic);
        }
        name.setText(NAME);
        email.setText(EMAIL);
    }

    @Override
    public void onBackPressed() {
        if (miNumberStored == null) {
            DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
            if (drawer.isDrawerOpen(GravityCompat.START)) {
                drawer.closeDrawer(GravityCompat.START);
            } else {
                super.onBackPressed();
                overridePendingTransition(R.anim.pull_in_left, R.anim.push_out_right);
            }
        } else {
            DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
            if (drawer.isDrawerOpen(GravityCompat.START)) {
                drawer.closeDrawer(GravityCompat.START);
            } else {
                if (getSupportFragmentManager().getBackStackEntryCount() == 0) {
                    if (backButtonCount >= 1) {
                        Intent intent = new Intent(Intent.ACTION_MAIN);
                        intent.addCategory(Intent.CATEGORY_HOME);
                        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                        startActivity(intent);
                    } else {
                        Toast.makeText(this, "Press the back button once again to close the application.", Toast.LENGTH_SHORT).show();
                        backButtonCount++;
                    }
                } else {
                    getSupportFragmentManager().popBackStack();
                }
            }
        }
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        int id = item.getItemId();
        FragmentManager manager = getSupportFragmentManager();

        if (id == R.id.nav_events) {

        } else if (id == R.id.nav_going) {

        } else if (id == R.id.nav_schedule) {

        } else if (id == R.id.nav_faq) {
            FaqsFragment faqsFragment = new FaqsFragment();
            FragmentTransaction transaction = manager.beginTransaction();
            transaction.addToBackStack(null);
            transaction.add(R.id.relativelayout_for_fragment, faqsFragment, faqsFragment.getTag());
            transaction.commit();
        } else if (id == R.id.nav_contact) {

        } else if (id == R.id.nav_map) {
//            Intent intent = new Intent(MainActivity.this, MapsActivity.class);
//            startActivity(intent);
            MapFragment MapFragment = new MapFragment();
            FragmentTransaction transaction = manager.beginTransaction();
            transaction.addToBackStack(null);
            transaction.add(R.id.relativelayout_for_fragment, MapFragment, MapFragment.getTag());
            transaction.commit();
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
}
