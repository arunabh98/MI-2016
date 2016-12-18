package com.iitb.moodindigo.mi2016;


import android.animation.ObjectAnimator;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.PorterDuff;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.view.animation.DecelerateInterpolator;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.facebook.AccessToken;
import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.FacebookSdk;
import com.facebook.GraphRequest;
import com.facebook.GraphResponse;
import com.facebook.appevents.AppEventsLogger;
import com.facebook.login.LoginManager;
import com.facebook.login.LoginResult;
import com.facebook.login.widget.LoginButton;
import com.google.android.gms.appindexing.Action;
import com.google.android.gms.appindexing.AppIndex;
import com.google.android.gms.appindexing.Thing;
import com.google.android.gms.common.api.GoogleApiClient;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.util.regex.Pattern;

import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
import uk.co.chrisjenx.calligraphy.CalligraphyConfig;
import uk.co.chrisjenx.calligraphy.CalligraphyContextWrapper;

public class LoginActivity extends AppCompatActivity {
    String miNumberStored;
    Bitmap profilePic;
    /**
     * ATTENTION: This was auto-generated to implement the App Indexing API.
     * See https://g.co/AppIndexing/AndroidStudio for more information.
     */
    private GoogleApiClient client;
    private Button mi_login_button;
    private LoginButton fb_login_button;
    private Button reg_later_button;
    private Button reg_now_button;
    private EditText mi_no;
    private Button submit_button;
    private JSONObject Jobject;
    private CallbackManager callbackManager;
    private ProgressBar pb;
    private String storeUserDetails = "userDetails";
    private String mi_no_text;
    private String contact_no;
    private int index = 0;
    private String[] userDetailsList = {"NAME", "EMAIL", "MI_NUMBER", "CONTACT", "GENDER", "CITY", "COLLEGE"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setTheme(R.style.AppTheme);
        super.onCreate(savedInstanceState);
        ActionBar actionBar = getSupportActionBar();
        actionBar.hide();
        CalligraphyConfig.initDefault(new CalligraphyConfig.Builder()
                .setDefaultFontPath("fonts/ProximaNova-Condensed.otf")
                .setFontAttrId(R.attr.fontPath)
                .build()
        );

        SharedPreferences prefs = getSharedPreferences(storeUserDetails, MODE_PRIVATE);
        miNumberStored = prefs.getString("MI_NUMBER", null);
        if (miNumberStored != null) {
            Intent intent = new Intent(LoginActivity.this, MainActivity.class);
            for (int userDetail = 0; userDetail < userDetailsList.length; userDetail++) {
                intent.putExtra(userDetailsList[userDetail], prefs.getString(userDetailsList[userDetail], null));
            }
            String propic = prefs.getString("PROFILE_PIC", "");
            if (!propic.equalsIgnoreCase("")) {
                byte[] b = Base64.decode(propic, Base64.DEFAULT);
                Bitmap profilePic = BitmapFactory.decodeByteArray(b, 0, b.length);
                ByteArrayOutputStream _bs = new ByteArrayOutputStream();
                profilePic.compress(Bitmap.CompressFormat.PNG, 100, _bs);
                intent.putExtra("PROFILE_PIC", _bs.toByteArray());
            }
            intent.putExtra("FB_ID", prefs.getString("fb_id", null));
            startActivity(intent);
        }
        FacebookSdk.sdkInitialize(getApplicationContext());
        callbackManager = CallbackManager.Factory.create();
        setContentView(R.layout.activity_login);

        mi_login_button = (Button) findViewById(R.id.mi_login_button);
        fb_login_button = (LoginButton) findViewById(R.id.fb_login_button);
        reg_later_button = (Button) findViewById(R.id.register_later);
        reg_now_button = (Button) findViewById(R.id.register_now);
        mi_no = (EditText) findViewById(R.id.mi_number_input);
        mi_login_button.setVisibility(View.VISIBLE);
        fb_login_button.setVisibility(View.VISIBLE);
        mi_no.setVisibility(View.GONE);
        submit_button = (Button) findViewById(R.id.login_submit);
        pb = (ProgressBar) findViewById(R.id.progress);
        pb.getIndeterminateDrawable().setColorFilter(getResources().getColor(R.color.green), PorterDuff.Mode.MULTIPLY);
        pb.setScaleY(0.7f);
        pb.setScaleX(0.7f);
        pb.setVisibility(View.GONE);
        AppEventsLogger.activateApp(this);
        fb_login_button.setReadPermissions("email");

        fb_login_button.registerCallback(callbackManager, new FacebookCallback<LoginResult>() {
            @Override
            public void onSuccess(LoginResult loginResult) {
                mi_login_button.setEnabled(false);
                fb_login_button.setEnabled(false);
                reg_later_button.setEnabled(false);
                GraphRequest request = GraphRequest.newMeRequest(
                        AccessToken.getCurrentAccessToken(),
                        new GraphRequest.GraphJSONObjectCallback() {
                            @Override
                            public void onCompleted(JSONObject jsonObject, GraphResponse response) {
                                try {
                                    String profilePicUrl = jsonObject.getJSONObject("picture").getJSONObject("data").getString("url");
                                    pb.setVisibility(View.VISIBLE);
                                    new getDetails_fb().execute(jsonObject.getString("id"), profilePicUrl);
                                } catch (Exception e) {
                                    e.printStackTrace();
                                }
                            }
                        });
                Bundle parameters = new Bundle();
                parameters.putString("fields", "id,picture");
                request.setParameters(parameters);
                request.executeAsync();

            }

            @Override
            public void onCancel() {
                // App code
            }

            @Override
            public void onError(FacebookException exception) {
                // App code
            }
        });

        mi_login_button.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                animateElement(mi_login_button, 300, -1000);

                animateElement(fb_login_button, 300, -1000);

                mi_no.setVisibility(View.VISIBLE);
                animateElement(mi_no, 300, 1000, 0);

                submit_button.setVisibility(View.VISIBLE);
                animateElement(submit_button, 300, 1000, 0);
                mi_login_button.setVisibility(View.GONE);
                fb_login_button.setVisibility(View.GONE);
                mi_no.setHint("MI No. (mi-abc-123)");
            }
        });

        submit_button.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                if (index == 0) {

                    mi_no_text = mi_no.getText().toString().toUpperCase();
                    Pattern pattern = Pattern.compile("MI-[A-Z]{3}-[0-9]{3,4}");

                    if (!pattern.matcher(mi_no_text).matches()) {
                        Toast.makeText(LoginActivity.this, "Not a Valid MI Number!", Toast.LENGTH_SHORT).show();
                    } else {
                        animateElement(mi_no, 300, -1000);
                        mi_no.setText(null);
                        mi_no.setHint("Contact No.");
                        animateElement(mi_no, 300, 1000, 0);
                        index = index + 1;
                    }
                } else {

                    contact_no = mi_no.getText().toString();
                    Pattern pattern = Pattern.compile("[0-9]{10}");

                    if (!pattern.matcher(contact_no).matches()) {
                        Toast.makeText(LoginActivity.this, "Not a Valid Contact Number!", Toast.LENGTH_SHORT).show();
                    } else {
                        index = index + 1;
                        pb.setVisibility(View.VISIBLE);
                        new getDetails().execute(mi_no_text, contact_no);
                    }
                }
            }
        });

        reg_later_button.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                startActivity(intent);
                overridePendingTransition(R.anim.pull_in_right, R.anim.push_out_left);
            }
        });

        reg_now_button.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Uri webpage = Uri.parse("https://moodi.org/#/registration");
                Intent intent = new Intent(Intent.ACTION_VIEW, webpage);
                if (intent.resolveActivity(getPackageManager()) != null) {
                    startActivity(intent);
                }
            }
        });
        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
        client = new GoogleApiClient.Builder(this).addApi(AppIndex.API).build();
    }

    public Bitmap getBitmapFromURL(String src) {
        try {
            java.net.URL url = new java.net.URL(src);
            HttpURLConnection connection = (HttpURLConnection) url
                    .openConnection();
            connection.setDoInput(true);
            connection.connect();
            InputStream input = connection.getInputStream();
            Bitmap myBitmap = BitmapFactory.decodeStream(input);
            return myBitmap;
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        callbackManager.onActivityResult(requestCode, resultCode, data);
    }

    public void startMainActivity() {
        Intent intent = new Intent(LoginActivity.this, MainActivity.class);
        SharedPreferences.Editor editor = getSharedPreferences(storeUserDetails, MODE_PRIVATE).edit();
        try {
            if (Jobject != null) {
                for (int userDetail = 0; userDetail < userDetailsList.length; userDetail++) {
                    intent.putExtra(userDetailsList[userDetail], Jobject.getString(userDetailsList[userDetail]));
                    editor.putString(userDetailsList[userDetail], Jobject.getString(userDetailsList[userDetail]));
                }
                intent.putExtra("FB_ID", Jobject.getString("fb_id"));
                editor.putString("FB_ID", Jobject.getString("fb_id"));
                editor.apply();
                mi_no.setEnabled(false);
                submit_button.setEnabled(false);
                reg_later_button.setEnabled(false);
                startActivity(intent);
                overridePendingTransition(R.anim.pull_in_right, R.anim.push_out_left);
            } else {
                Toast.makeText(LoginActivity.this, "Invalid Credentials", Toast.LENGTH_SHORT).show();
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    public void startMainActivity_fb() {
        Intent intent = new Intent(LoginActivity.this, MainActivity.class);
        SharedPreferences.Editor editor = getSharedPreferences(storeUserDetails, MODE_PRIVATE).edit();
        try {
            if (Jobject.getString("message").equals("not_registered")) {
                LoginManager.getInstance().logOut();
                Toast.makeText(LoginActivity.this, "Looks like you have not registered for Mood Indigo. Click Register Now to register.", Toast.LENGTH_SHORT).show();
            } else if (Jobject != null) {
                Log.e("dffdsf", Jobject.getString("MI_NUMBER"));
                for (int userDetail = 0; userDetail < userDetailsList.length; userDetail++) {
                    intent.putExtra(userDetailsList[userDetail], Jobject.getString(userDetailsList[userDetail].toLowerCase()));
                    editor.putString(userDetailsList[userDetail], Jobject.getString(userDetailsList[userDetail].toLowerCase()));
                }
                intent.putExtra("FB_ID", Jobject.getString("fb_id"));
                ByteArrayOutputStream _bs = new ByteArrayOutputStream();
                profilePic.compress(Bitmap.CompressFormat.PNG, 100, _bs);
                intent.putExtra("PROFILE_PIC", _bs.toByteArray());
                String encodedImage = Base64.encodeToString(_bs.toByteArray(), Base64.DEFAULT);
                editor.putString("PROFILE_PIC", encodedImage);
                editor.putString("FB_ID", Jobject.getString("fb_id"));
                editor.apply();
                startActivity(intent);
                overridePendingTransition(R.anim.pull_in_right, R.anim.push_out_left);
            } else {
                LoginManager.getInstance().logOut();
                Toast.makeText(LoginActivity.this, "Not successful", Toast.LENGTH_SHORT).show();
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onBackPressed() {
        if (mi_no.getHint() != null) {
            if (mi_no.getHint().equals("Contact No.")) {
                index = 0;
                animateElement(mi_no, 300, 0, 1000);
                mi_no.setText(mi_no_text);
                mi_no.setHint("MI No. (mi-abc-123)");
                animateElement(mi_no, 300, -2000, 0);
            } else if (mi_login_button.getVisibility() == View.GONE) {
                mi_login_button.setVisibility(View.VISIBLE);
                animateElement(fb_login_button, 300, 0);

                fb_login_button.setVisibility(View.VISIBLE);
                animateElement(mi_login_button, 300, 0);

                animateElement(mi_no, 300, 2000);

                animateElement(submit_button, 300, 2000);
                mi_no.setVisibility(View.GONE);
                submit_button.setVisibility(View.GONE);
            } else {
                Intent a = new Intent(Intent.ACTION_MAIN);
                a.addCategory(Intent.CATEGORY_HOME);
                a.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(a);
            }
        } else {
            Intent a = new Intent(Intent.ACTION_MAIN);
            a.addCategory(Intent.CATEGORY_HOME);
            a.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(a);
        }
    }

    void animateElement(View element, int duration, int start_pos, int final_pos) {
        ObjectAnimator translateElement = ObjectAnimator.ofFloat(element, "translationX", start_pos, final_pos);
        translateElement.setInterpolator(new DecelerateInterpolator());
        translateElement.setDuration(duration);
        translateElement.start();
    }

    void animateElement(View element, int duration, int pos) {
        ObjectAnimator translateElement = ObjectAnimator.ofFloat(element, "translationX", pos);
        translateElement.setInterpolator(new DecelerateInterpolator());
        translateElement.setDuration(duration);
        translateElement.start();
    }

    @Override
    public void onResume() {
        super.onResume();
        mi_login_button.setEnabled(true);
        fb_login_button.setEnabled(true);
        reg_later_button.setEnabled(true);
        submit_button.setEnabled(true);
        mi_no.setEnabled(true);
    }

    /**
     * ATTENTION: This was auto-generated to implement the App Indexing API.
     * See https://g.co/AppIndexing/AndroidStudio for more information.
     */
    public Action getIndexApiAction() {
        Thing object = new Thing.Builder()
                .setName("Login Page") // TODO: Define a title for the content shown.
                // TODO: Make sure this auto-generated URL is correct.
                .setUrl(Uri.parse("http://[ENTER-YOUR-URL-HERE]"))
                .build();
        return new Action.Builder(Action.TYPE_VIEW)
                .setObject(object)
                .setActionStatus(Action.STATUS_TYPE_COMPLETED)
                .build();
    }

    @Override
    public void onStart() {
        super.onStart();

        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
        client.connect();
        AppIndex.AppIndexApi.start(client, getIndexApiAction());
    }

    @Override
    public void onStop() {
        super.onStop();

        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
        AppIndex.AppIndexApi.end(client, getIndexApiAction());
        client.disconnect();
    }

    private class getDetails extends AsyncTask<String, Void, Void> {

        @Override
        protected Void doInBackground(String... params) {
            try {
                OkHttpClient client = new OkHttpClient();
                Request request = new Request.Builder()
                        .url("http://cradmin.moodi.org/parti-det/" + params[0])
                        .build();
                Response response = client.newCall(request).execute();
                String jsonData = response.body().string();
                JSONObject tempjsonobject = new JSONObject(jsonData);
                if (tempjsonobject.getString("CONTACT").equals(params[1])) {
                    Jobject = tempjsonobject;
                }
            } catch (Exception e) {
                Log.e("APP_TAG", "STACKTRACE");
                Log.e("APP_TAG", Log.getStackTraceString(e));
            }

            return null;
        }

        @Override
        protected void onPostExecute(Void result) {
            super.onPostExecute(result);
            pb.setVisibility(View.GONE);
            LoginActivity.this.startMainActivity();
        }
    }

    private class getDetails_fb extends AsyncTask<String, Void, Void> {

        @Override
        protected Void doInBackground(String... params) {
            try {
                profilePic = getBitmapFromURL(params[1]);
                OkHttpClient client = new OkHttpClient();
                String json = "{\"fb_id\":\"" + params[0] + "\"}";
                MediaType JSON = MediaType.parse("application/json; charset=utf-8");
                RequestBody body = RequestBody.create(JSON, json);
                Request request = new Request.Builder()
                        .url("http://moodi.org/api/insert/fbLogin")
                        .post(body)
                        .build();
                Response response = client.newCall(request).execute();
                String jsonData = response.body().string();
                Jobject = new JSONObject(jsonData);
            } catch (Exception e) {
                Log.e("APP_TAG", "STACKTRACE");
                Log.e("APP_TAG", Log.getStackTraceString(e));
            }

            return null;
        }

        @Override
        protected void onPostExecute(Void result) {
            super.onPostExecute(result);
            pb.setVisibility(View.GONE);
            LoginActivity.this.startMainActivity_fb();
        }
    }

    @Override
    protected void attachBaseContext(Context newBase) {
        super.attachBaseContext(CalligraphyContextWrapper.wrap(newBase));
    }
}
