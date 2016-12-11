package com.example.darknight.mi2016;


import android.animation.ObjectAnimator;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
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
import com.facebook.login.LoginResult;
import com.facebook.login.widget.LoginButton;
import com.google.android.gms.appindexing.Action;
import com.google.android.gms.appindexing.AppIndex;
import com.google.android.gms.appindexing.Thing;
import com.google.android.gms.common.api.GoogleApiClient;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.regex.Pattern;

import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class LoginActivity extends AppCompatActivity {
    /**
     * ATTENTION: This was auto-generated to implement the App Indexing API.
     * See https://g.co/AppIndexing/AndroidStudio for more information.
     */
    private GoogleApiClient client;
    private Button mi_login_button;
    private LoginButton fb_login_button;
    private Button reg_later_button;
    private EditText mi_no;
    private Button submit_button;
    private JSONObject Jobject;
    private CallbackManager callbackManager;
    private ProgressBar pb;
    private String storeUserDetails = "userDetails";
    String miNumberStored;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        SharedPreferences prefs = getSharedPreferences(storeUserDetails, MODE_PRIVATE);
        miNumberStored = prefs.getString("MI_NUMBER", null);
        if (miNumberStored != null) {
            Intent intent = new Intent(LoginActivity.this, MainActivity.class);
            intent.putExtra("NAME", prefs.getString("NAME", null));
            intent.putExtra("EMAIL", prefs.getString("EMAIL", null));
            intent.putExtra("MI_NUMBER", prefs.getString("MI_NUMBER", null));
            intent.putExtra("CONTACT", prefs.getString("CONTACT", null));
            intent.putExtra("GENDER", prefs.getString("GENDER", null));
            intent.putExtra("CITY", prefs.getString("CITY", null));
            intent.putExtra("COLLEGE", prefs.getString("COLLEGE", null));
            intent.putExtra("FB_ID", prefs.getString("fb_id", null));
            startActivity(intent);
        }

        FacebookSdk.sdkInitialize(getApplicationContext());
        callbackManager = CallbackManager.Factory.create();
        setContentView(R.layout.activity_login);

        mi_login_button = (Button) findViewById(R.id.mi_login_button);
        fb_login_button = (LoginButton) findViewById(R.id.fb_login_button);
        reg_later_button = (Button) findViewById(R.id.register_later);
        mi_no = (EditText) findViewById(R.id.mi_number_input);
        submit_button = (Button) findViewById(R.id.login_submit);
        pb = (ProgressBar) findViewById(R.id.progress);
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
                                    new getDetails_fb().execute(jsonObject.getString("id"));
                                } catch (Exception e) {
                                    Log.e("LoginActivity", "Error in parsing JSON");
                                }
                            }
                        });
                Bundle parameters = new Bundle();
                parameters.putString("fields", "id,name,email,gender");
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
                ObjectAnimator translationFbLogin = ObjectAnimator.ofFloat(mi_login_button, "translationX", -1000);
                translationFbLogin.setInterpolator(new DecelerateInterpolator());
                translationFbLogin.setDuration(300);
                translationFbLogin.start();

                ObjectAnimator translationMiLogin = ObjectAnimator.ofFloat(fb_login_button, "translationX", -1000);
                translationMiLogin.setInterpolator(new DecelerateInterpolator());
                translationMiLogin.setDuration(300);
                translationMiLogin.start();

                mi_no.setVisibility(View.VISIBLE);
                ObjectAnimator translationMiNumber = ObjectAnimator.ofFloat(mi_no, "translationX", 1000, 0);
                translationMiNumber.setInterpolator(new DecelerateInterpolator());
                translationMiNumber.setDuration(300);
                translationMiNumber.start();

                submit_button.setVisibility(View.VISIBLE);
                ObjectAnimator translationSubmitButton = ObjectAnimator.ofFloat(submit_button, "translationX", 1000, 0);
                translationSubmitButton.setInterpolator(new DecelerateInterpolator());
                translationSubmitButton.setDuration(300);
                translationSubmitButton.start();
                mi_login_button.setVisibility(View.GONE);
                fb_login_button.setVisibility(View.GONE);
            }
        });

        submit_button.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                submit_button.setEnabled(false);
                String mi_no_text = mi_no.getText().toString().toUpperCase();
                Pattern pattern = Pattern.compile("MI-[A-Z]{3}-[0-9]{3,4}");

                if (!pattern.matcher(mi_no_text).matches()) {
                    Toast.makeText(LoginActivity.this, "Not a Valid MI Number!", Toast.LENGTH_SHORT).show();
                } else {
                    pb.setVisibility(View.VISIBLE);
                    mi_no.setEnabled(false);
                    submit_button.setEnabled(false);
                    reg_later_button.setEnabled(false);
                    new getDetails().execute(mi_no_text);
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

        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
        client = new GoogleApiClient.Builder(this).addApi(AppIndex.API).build();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        callbackManager.onActivityResult(requestCode, resultCode, data);
    }

    public void startMainActivity() {
        Intent intent = new Intent(LoginActivity.this, MainActivity.class);
        try {
            intent.putExtra("NAME", Jobject.getString("NAME"));
            intent.putExtra("EMAIL", Jobject.getString("EMAIL"));
            intent.putExtra("MI_NUMBER", Jobject.getString("MI_NUMBER"));
            intent.putExtra("CONTACT", Jobject.getString("CONTACT"));
            intent.putExtra("GENDER", Jobject.getString("GENDER"));
            intent.putExtra("CITY", Jobject.getString("CITY"));
            intent.putExtra("COLLEGE", Jobject.getString("COLLEGE"));
            intent.putExtra("FB_ID", Jobject.getString("fb_id"));
            SharedPreferences.Editor editor = getSharedPreferences(storeUserDetails, MODE_PRIVATE).edit();
            editor.putString("NAME", Jobject.getString("NAME"));
            editor.putString("EMAIL", Jobject.getString("EMAIL"));
            editor.putString("MI_NUMBER", Jobject.getString("MI_NUMBER"));
            editor.putString("CONTACT", Jobject.getString("CONTACT"));
            editor.putString("GENDER", Jobject.getString("GENDER"));
            editor.putString("CITY", Jobject.getString("CITY"));
            editor.putString("COLLEGE", Jobject.getString("COLLEGE"));
            editor.putString("FB_ID", Jobject.getString("fb_id"));
            editor.apply();
        } catch (JSONException e) {
            e.printStackTrace();
        }
        startActivity(intent);
        overridePendingTransition(R.anim.pull_in_right, R.anim.push_out_left);
    }

    public void startMainActivity_fb() {
        Intent intent = new Intent(LoginActivity.this, MainActivity.class);
        try {
            if (Jobject.getString("mi_number") != null) {
                intent.putExtra("NAME", Jobject.getString("name"));
                intent.putExtra("EMAIL", Jobject.getString("email"));
                intent.putExtra("MI_NUMBER", Jobject.getString("mi_number"));
                intent.putExtra("CONTACT", Jobject.getString("contact"));
                intent.putExtra("GENDER", Jobject.getString("gender"));
                intent.putExtra("CITY", Jobject.getString("city"));
                intent.putExtra("COLLEGE", Jobject.getString("college"));
                intent.putExtra("FB_ID", Jobject.getString("fb_id"));
                SharedPreferences.Editor editor = getSharedPreferences(storeUserDetails, MODE_PRIVATE).edit();
                editor.putString("NAME", Jobject.getString("name"));
                editor.putString("EMAIL", Jobject.getString("email"));
                editor.putString("MI_NUMBER", Jobject.getString("mi_number"));
                editor.putString("CONTACT", Jobject.getString("contact"));
                editor.putString("GENDER", Jobject.getString("gender"));
                editor.putString("CITY", Jobject.getString("city"));
                editor.putString("COLLEGE", Jobject.getString("college"));
                editor.putString("FB_ID", Jobject.getString("fb_id"));
                editor.apply();
            }
            else {
                Toast.makeText(LoginActivity.this, "Not successful", Toast.LENGTH_SHORT).show();
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        startActivity(intent);
        overridePendingTransition(R.anim.pull_in_right, R.anim.push_out_left);
    }

    @Override
    public void onBackPressed() {
        if (mi_login_button.getVisibility() == View.GONE) {
            mi_login_button.setVisibility(View.VISIBLE);
            ObjectAnimator translationMiLogin = ObjectAnimator.ofFloat(fb_login_button, "translationX", 0);
            translationMiLogin.setInterpolator(new DecelerateInterpolator());
            translationMiLogin.setDuration(300);
            translationMiLogin.start();

            fb_login_button.setVisibility(View.VISIBLE);
            ObjectAnimator translationFbLogin = ObjectAnimator.ofFloat(mi_login_button, "translationX", 0);
            translationFbLogin.setInterpolator(new DecelerateInterpolator());
            translationFbLogin.setDuration(300);
            translationFbLogin.start();

            ObjectAnimator translationMiNumber = ObjectAnimator.ofFloat(mi_no, "translationX", 2000);
            translationMiNumber.setInterpolator(new DecelerateInterpolator());
            translationMiNumber.setDuration(300);
            translationMiNumber.start();

            ObjectAnimator translationSubmitButton = ObjectAnimator.ofFloat(submit_button, "translationX", 2000);
            translationSubmitButton.setInterpolator(new DecelerateInterpolator());
            translationSubmitButton.setDuration(300);
            translationSubmitButton.start();
            mi_no.setVisibility(View.GONE);
            submit_button.setVisibility(View.GONE);
        } else {
            super.onBackPressed();
        }
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
            LoginActivity.this.startMainActivity();
        }

    }

    private class getDetails_fb extends AsyncTask<String, Void, Void> {

        @Override
        protected Void doInBackground(String... params) {
            try {
                OkHttpClient client = new OkHttpClient();
                String json = "{\"fb_id\":\"" + params[0] + "\"}";
                MediaType JSON = MediaType.parse("application/json; charset=utf-8");
                RequestBody body = RequestBody.create(JSON, json);
                Request request = new Request.Builder()
                        .url("http://moodi.org/api/insert/fbLogin")
                        .post(body)
                        .build();
                Log.d("body", json);
                Log.d("fb request", request.toString());
                Response response = client.newCall(request).execute();
                String jsonData = response.body().string();
                Log.d("hurray", jsonData);
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
            LoginActivity.this.startMainActivity_fb();
        }

    }


}