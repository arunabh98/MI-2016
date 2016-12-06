package com.example.darknight.mi2016;


import android.content.Intent;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
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

import okhttp3.OkHttpClient;
import okhttp3.Request;
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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        FacebookSdk.sdkInitialize(getApplicationContext());
        callbackManager = CallbackManager.Factory.create();
        setContentView(R.layout.activity_login);

        mi_login_button = (Button) findViewById(R.id.mi_login_button);
        fb_login_button = (LoginButton) findViewById(R.id.fb_login_button);
        reg_later_button = (Button) findViewById(R.id.register_later);
        mi_no = (EditText) findViewById(R.id.mi_number_input);
        submit_button = (Button) findViewById(R.id.login_submit);

        AppEventsLogger.activateApp(this);
        fb_login_button.setReadPermissions("email");

        fb_login_button.registerCallback(callbackManager, new FacebookCallback<LoginResult>() {
            @Override
            public void onSuccess(LoginResult loginResult) {
                GraphRequest request = GraphRequest.newMeRequest(
                        AccessToken.getCurrentAccessToken(),
                        new GraphRequest.GraphJSONObjectCallback() {
                            @Override
                            public void onCompleted(JSONObject jsonObject, GraphResponse response) {
                                try {
                                    Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                                    intent.putExtra("NAME", jsonObject.getString("name"));
                                    intent.putExtra("EMAIL", jsonObject.getString("email"));
                                    intent.putExtra("GENDER", jsonObject.getString("gender"));
                                    intent.putExtra("FB_ID", jsonObject.getString("id"));
                                    startActivity(intent);
                                    overridePendingTransition(R.anim.pull_in_right, R.anim.push_out_left);
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
                mi_login_button.setVisibility(View.GONE);
                fb_login_button.setVisibility(View.GONE);
                mi_no.setVisibility(View.VISIBLE);
                submit_button.setVisibility(View.VISIBLE);
            }
        });

        submit_button.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {

                String mi_no_text = mi_no.getText().toString().toUpperCase();
                Pattern pattern = Pattern.compile("MI-[A-Z]{3}-[0-9]{3,4}");

                if (!pattern.matcher(mi_no_text).matches()) {
                    Toast.makeText(LoginActivity.this, "Not a Valid MI Number!", Toast.LENGTH_SHORT).show();
                }
                else {
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

    public void startMainActivity(){
        Intent intent = new Intent(LoginActivity.this, MainActivity.class);
        try {
            intent.putExtra("NAME", Jobject.getString("NAME"));
            intent.putExtra("EMAIL", Jobject.getString("EMAIL"));
            intent.putExtra("MI_NUMBER", Jobject.getString("MI_NUMBER"));
            intent.putExtra("CONTACT", Jobject.getString("CONTACT"));
            intent.putExtra("GENDER", Jobject.getString("GENDER"));
            intent.putExtra("CITY", Jobject.getString("CITY"));
            intent.putExtra("COLLEGE", Jobject.getString("COLLEGE"));
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
            fb_login_button.setVisibility(View.VISIBLE);
            mi_no.setVisibility(View.GONE);
            submit_button.setVisibility(View.GONE);
        } else {
            super.onBackPressed();
        }
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
                        .url("http://cradmin.moodi.org/parti-det/"+params[0])
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
            LoginActivity.this.startMainActivity();
        }

    }

}