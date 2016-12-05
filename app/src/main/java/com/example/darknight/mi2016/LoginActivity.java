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
    Button login_button;
    Button reg_later_button;
    EditText mi_no;
    Button submit_button;
    JSONObject Jobject;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        login_button = (Button) findViewById(R.id.mi_number);
        reg_later_button = (Button) findViewById(R.id.register_later);
        mi_no = (EditText) findViewById(R.id.mi_number_input);
        submit_button = (Button) findViewById(R.id.login_submit);

        login_button.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                login_button.setVisibility(View.GONE);
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


    public void startMainActivity(){
        Intent intent = new Intent(LoginActivity.this, MainActivity.class);
        try {
            intent.putExtra("NAME", Jobject.getString("NAME"));
        } catch (JSONException e) {
            e.printStackTrace();
        }
        try {
            intent.putExtra("EMAIL", Jobject.getString("EMAIL"));
        } catch (JSONException e) {
            e.printStackTrace();
        }
        try {
            intent.putExtra("MI_NUMBER", Jobject.getString("MI_NUMBER"));
        } catch (JSONException e) {
            e.printStackTrace();
        }
        try {
            intent.putExtra("CONTACT", Jobject.getString("CONTACT"));
        } catch (JSONException e) {
            e.printStackTrace();
        }
        try {
            intent.putExtra("GENDER", Jobject.getString("GENDER"));
        } catch (JSONException e) {
            e.printStackTrace();
        }
        try {
            intent.putExtra("CITY", Jobject.getString("CITY"));
        } catch (JSONException e) {
            e.printStackTrace();
        }
        try {
            intent.putExtra("COLLEGE", Jobject.getString("COLLEGE"));
        } catch (JSONException e) {
            e.printStackTrace();
        }
        startActivity(intent);
        overridePendingTransition(R.anim.pull_in_right, R.anim.push_out_left);
    }

    @Override
    public void onBackPressed() {
        if (login_button.getVisibility() == View.GONE) {
            login_button.setVisibility(View.VISIBLE);
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