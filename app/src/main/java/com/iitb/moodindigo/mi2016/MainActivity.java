package com.iitb.moodindigo.mi2016;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
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
import android.util.Base64;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.facebook.login.LoginManager;
import com.iitb.moodindigo.mi2016.ServerConnection.GsonModels;

import java.util.ArrayList;
import java.util.List;

import uk.co.chrisjenx.calligraphy.CalligraphyConfig;
import uk.co.chrisjenx.calligraphy.CalligraphyContextWrapper;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    String miNumberStored;
    int backButtonCount = 0;

    public ContactUsFragment contactUsFragment;
    FaqsFragment faqsFragment;
    MainFragment mainFragment;
    EventPageFragment eventPageFragment;
    MapFragment mapFragment;
    QrCodeFragment qrCodeFragment;
    SharedPreferences prefs;
    DevelopersFragment developersFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        CalligraphyConfig.initDefault(new CalligraphyConfig.Builder()
                .setDefaultFontPath("fonts/ProximaNova-Condensed.otf")
                .setFontAttrId(R.attr.fontPath)
                .build()
        );
        contactUsFragment = new ContactUsFragment();
        developersFragment = new DevelopersFragment();
        prefs = getSharedPreferences("userDetails", MODE_PRIVATE);
        miNumberStored = prefs.getString("MI_NUMBER", null);
        setContentView(R.layout.activity_main);
        String NAME = prefs.getString("NAME", null);
        String EMAIL = prefs.getString("EMAIL", null);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();
        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
        if (miNumberStored == null) {
            navigationView.getMenu().findItem(R.id.nav_logout).setVisible(false);
        }
        View header = navigationView.getHeaderView(0);
        LinearLayout navigationBar = (LinearLayout) header.findViewById(R.id.cover_picture);
        navigationBar.setBackgroundColor(ContextCompat.getColor(getApplicationContext(), R.color.black));
        TextView name = (TextView) header.findViewById(R.id.name);
        TextView email = (TextView) header.findViewById(R.id.email_id);
        String encodedProfileImage = prefs.getString("PROFILE_PIC", "");
        if (!encodedProfileImage.equalsIgnoreCase("")) {
            byte[] b = Base64.decode(encodedProfileImage, Base64.DEFAULT);
            Bitmap profilePic = BitmapFactory.decodeByteArray(b, 0, b.length);
            ImageView profilepic = (ImageView) header.findViewById(R.id.profile_picture);
            profilepic.setImageBitmap(profilePic);
        }
        if (NAME == null) {
            name.setText("Guest User");
        } else {
            name.setText(NAME);
        }

        if (EMAIL == null) {
            email.setText("guest@moodi.org");
        } else {
            email.setText(EMAIL);
        }
        eventPageFragment = new EventPageFragment();
        mainFragment = new MainFragment();
        FragmentManager manager = getSupportFragmentManager();
        FragmentTransaction transaction = manager.beginTransaction();
        transaction.replace(R.id.relativelayout_for_fragment, mainFragment, mainFragment.getTag());
        transaction.commit();

        NotificationEventReceiver.setupAlarm(getApplicationContext());
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (miNumberStored == null) {
            if (drawer.isDrawerOpen(GravityCompat.START)) {
                drawer.closeDrawer(GravityCompat.START);
            } else {
                if (getSupportFragmentManager().findFragmentById(R.id.relativelayout_for_fragment) == mainFragment) {
                    Intent intent = new Intent(MainActivity.this, LoginActivity.class);
                    startActivity(intent);
                    overridePendingTransition(R.anim.pull_in_left, R.anim.push_out_right);
                } else {
                    backButtonCount = 0;
                    if (getSupportFragmentManager().findFragmentById(R.id.relativelayout_for_fragment) instanceof EventPageFragment) {
                        FragmentManager.BackStackEntry fragment = getSupportFragmentManager().getBackStackEntryAt(getSupportFragmentManager().getBackStackEntryCount() - 1);
                        if (fragment.getName().equals("category")) {
                            List<GsonModels.Event> daysList = new ArrayList<>();
                            int day = 0;
                            if (Cache.getDay().get_1()) {
                                daysList = Cache.getDaysList1();
                                day = 1;
                            } else if (Cache.getDay().get_2()) {
                                daysList = Cache.getDaysList2();
                                day = 2;
                            } else if (Cache.getDay().get_3()) {
                                daysList = Cache.getDaysList3();
                                day = 3;
                            } else if (Cache.getDay().get_4()) {
                                daysList = Cache.getDaysList4();
                                day = 4;
                            }
                            CategoryGroupFragment categoryGroupFragment1 = new CategoryGroupFragment(daysList, day);
                            FragmentManager manager = getSupportFragmentManager();
                            FragmentTransaction transaction = manager.beginTransaction();
                            transaction.addToBackStack(null);
                            transaction.setCustomAnimations(R.anim.enter_from_left, R.anim.exit_to_right);
                            transaction.replace(R.id.relativelayout_for_fragment, categoryGroupFragment1, categoryGroupFragment1.getTag());
                            transaction.commit();
                        } else if (fragment.getName().equals("going")) {
                            GoingFragment goingFragment = new GoingFragment();
                            FragmentManager manager = getSupportFragmentManager();
                            FragmentTransaction transaction = manager.beginTransaction();
                            transaction.setCustomAnimations(R.anim.enter_from_left, R.anim.exit_to_right);
                            transaction.addToBackStack(null);
                            transaction.replace(R.id.relativelayout_for_fragment, goingFragment, goingFragment.getTag());
                            transaction.commit();
                        }
                    } else if (getSupportFragmentManager().findFragmentById(R.id.relativelayout_for_fragment) instanceof CategoryGroupFragment) {
                        DaysFragment daysFragment = new DaysFragment();
                        FragmentManager manager = this.getSupportFragmentManager();
                        FragmentTransaction transaction = manager.beginTransaction();
                        transaction.setCustomAnimations(R.anim.enter_from_left, R.anim.exit_to_right);
                        transaction.replace(R.id.relativelayout_for_fragment, daysFragment, daysFragment.getTag());
                        transaction.commit();
                    } else {
                        mainFragment = new MainFragment();
                        FragmentManager manager = this.getSupportFragmentManager();
                        FragmentTransaction transaction = manager.beginTransaction();
                        transaction.setCustomAnimations(R.anim.enter_from_left, R.anim.exit_to_right);
                        transaction.replace(R.id.relativelayout_for_fragment, mainFragment, mainFragment.getTag());
                        transaction.commit();
                    }
                }
            }
        } else {
            if (drawer.isDrawerOpen(GravityCompat.START)) {
                drawer.closeDrawer(GravityCompat.START);
            } else {
                if (getSupportFragmentManager().findFragmentById(R.id.relativelayout_for_fragment) == mainFragment) {
                    if (backButtonCount >= 1) {
                        backButtonCount--;
                        Intent intent = new Intent(Intent.ACTION_MAIN);
                        intent.addCategory(Intent.CATEGORY_HOME);
                        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                        startActivity(intent);
                    } else {
                        Toast.makeText(this, "Press the back button once again to close the application.", Toast.LENGTH_SHORT).show();
                        backButtonCount++;
                    }
                } else {
                    backButtonCount = 0;
                    if (getSupportFragmentManager().findFragmentById(R.id.relativelayout_for_fragment) instanceof EventPageFragment) {
                        FragmentManager.BackStackEntry fragment = getSupportFragmentManager().getBackStackEntryAt(getSupportFragmentManager().getBackStackEntryCount() - 1);
                        if (fragment.getName().equals("category")) {
                            List<GsonModels.Event> daysList = new ArrayList<>();
                            int day = 0;
                            if (Cache.getDay().get_1()) {
                                daysList = Cache.getDaysList1();
                                day = 1;
                            } else if (Cache.getDay().get_2()) {
                                daysList = Cache.getDaysList2();
                                day = 2;
                            } else if (Cache.getDay().get_3()) {
                                daysList = Cache.getDaysList3();
                                day = 3;
                            } else if (Cache.getDay().get_4()) {
                                daysList = Cache.getDaysList4();
                                day = 4;
                            }
                            CategoryGroupFragment categoryGroupFragment1 = new CategoryGroupFragment(daysList, day);
                            FragmentManager manager = getSupportFragmentManager();
                            FragmentTransaction transaction = manager.beginTransaction();
                            transaction.addToBackStack(null);
                            transaction.setCustomAnimations(R.anim.enter_from_left, R.anim.exit_to_right);
                            transaction.replace(R.id.relativelayout_for_fragment, categoryGroupFragment1, categoryGroupFragment1.getTag());
                            transaction.commit();
                        } else if (fragment.getName().equals("going")) {
                            GoingFragment goingFragment = new GoingFragment();
                            FragmentManager manager = getSupportFragmentManager();
                            FragmentTransaction transaction = manager.beginTransaction();
                            transaction.setCustomAnimations(R.anim.enter_from_left, R.anim.exit_to_right);
                            transaction.addToBackStack(null);
                            transaction.replace(R.id.relativelayout_for_fragment, goingFragment, goingFragment.getTag());
                            transaction.commit();
                        }
                    } else if (getSupportFragmentManager().findFragmentById(R.id.relativelayout_for_fragment) instanceof CategoryGroupFragment) {
                        DaysFragment daysFragment = new DaysFragment();
                        FragmentManager manager = this.getSupportFragmentManager();
                        FragmentTransaction transaction = manager.beginTransaction();
                        transaction.setCustomAnimations(R.anim.enter_from_left, R.anim.exit_to_right);
                        transaction.replace(R.id.relativelayout_for_fragment, daysFragment, daysFragment.getTag());
                        transaction.commit();
                    } else {
                        mainFragment = new MainFragment();
                        FragmentManager manager = this.getSupportFragmentManager();
                        FragmentTransaction transaction = manager.beginTransaction();
                        transaction.setCustomAnimations(R.anim.enter_from_left, R.anim.exit_to_right);
                        transaction.replace(R.id.relativelayout_for_fragment, mainFragment, mainFragment.getTag());
                        transaction.commit();
                    }
                }
            }
        }
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.nav_going) {
            openGoing();
        } else if (id == R.id.nav_schedule) {
            openSchedule();
        } else if (id == R.id.nav_faq) {
            openFaq();
        } else if (id == R.id.nav_contact) {
            openContactUs();
        } else if (id == R.id.nav_map) {
            openMap();
        } else if (id == R.id.nav_qr) {
            openQrCode();
        } else if (id == R.id.nav_dev) {
            openDev();
        } else if (id == R.id.nav_logout) {
            logout();
        }


        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    private void openDev() {
        FragmentManager manager = getSupportFragmentManager();
        FragmentTransaction transaction = manager.beginTransaction();
        transaction.addToBackStack(null);
        transaction.replace(R.id.relativelayout_for_fragment, developersFragment, developersFragment.getTag());
        transaction.commit();
    }


    public void openContactUs() {
        FragmentManager manager = getSupportFragmentManager();
        FragmentTransaction transaction = manager.beginTransaction();
        transaction.addToBackStack(null);
        transaction.replace(R.id.relativelayout_for_fragment, contactUsFragment, contactUsFragment.getTag());
        transaction.commit();
    }

    public void openContactUsFromFragment() {
        FragmentManager manager = getSupportFragmentManager();
        FragmentTransaction transaction = manager.beginTransaction();
        transaction.addToBackStack(null);
        transaction.setCustomAnimations(R.anim.enter_from_right, R.anim.exit_to_left);
        transaction.replace(R.id.relativelayout_for_fragment, contactUsFragment, contactUsFragment.getTag());
        transaction.commit();
    }

    public void call(View v) {
        contactUsFragment.call(v);
    }

    public void mail(View v) {
        contactUsFragment.mail(v);
    }

    public void callDev(View v) {
        developersFragment.call(v);
    }

    public void mailDev(View v) {
        developersFragment.mail(v);
    }

    @Override
    protected void attachBaseContext(Context newBase) {
        super.attachBaseContext(CalligraphyContextWrapper.wrap(newBase));
    }

    public void openGoing() {
        GoingFragment goingFragment = new GoingFragment();
        FragmentManager manager = getSupportFragmentManager();
        FragmentTransaction transaction = manager.beginTransaction();
        transaction.replace(R.id.relativelayout_for_fragment, goingFragment, goingFragment.getTag());
        transaction.commit();
    }

    public void openSchedule() {
        DaysFragment daysFragment = new DaysFragment();
        FragmentManager manager = getSupportFragmentManager();
        FragmentTransaction transaction = manager.beginTransaction();
        transaction.addToBackStack(null);
        transaction.replace(R.id.relativelayout_for_fragment, daysFragment, daysFragment.getTag());
        transaction.commit();
    }

    public void openFaq() {
        faqsFragment = new FaqsFragment();
        FragmentManager manager = getSupportFragmentManager();
        FragmentTransaction transaction = manager.beginTransaction();
        transaction.addToBackStack(null);
        transaction.replace(R.id.relativelayout_for_fragment, faqsFragment, faqsFragment.getTag());
        transaction.commit();
    }

    public void openMap() {
        mapFragment = new MapFragment();
        FragmentManager manager = getSupportFragmentManager();
        FragmentTransaction transaction = manager.beginTransaction();
        transaction.addToBackStack(null);
        transaction.replace(R.id.relativelayout_for_fragment, mapFragment, mapFragment.getTag());
        transaction.commit();
    }

    private void openQrCode() {
        qrCodeFragment = new QrCodeFragment();
        FragmentManager manager = getSupportFragmentManager();
        FragmentTransaction transaction = manager.beginTransaction();
        transaction.addToBackStack(null);
        transaction.replace(R.id.relativelayout_for_fragment, qrCodeFragment, qrCodeFragment.getTag());
        transaction.commit();
    }

    public void logout() {
        new AlertDialog.Builder(this)
                .setTitle("Logout")
                .setMessage("Are you sure you want to Logout?")
                .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        LoginManager.getInstance().logOut();
                        SharedPreferences.Editor editor = prefs.edit();
                        editor.clear();
                        editor.apply();
                        Intent intent = new Intent(MainActivity.this, LoginActivity.class);
                        startActivity(intent);
                        overridePendingTransition(R.anim.pull_in_left, R.anim.push_out_right);
                        finish();
                    }

                })
                .setNegativeButton("No", null)
                .show();
    }

    public String getMiNumber() {
        return this.miNumberStored;
    }

    public void openDevelopersFromFragment() {
        FragmentManager manager = getSupportFragmentManager();
        FragmentTransaction transaction = manager.beginTransaction();
        transaction.addToBackStack(null);
        transaction.setCustomAnimations(R.anim.enter_from_right, R.anim.exit_to_left);
        transaction.replace(R.id.relativelayout_for_fragment, developersFragment, developersFragment.getTag());
        transaction.commit();
    }
}
