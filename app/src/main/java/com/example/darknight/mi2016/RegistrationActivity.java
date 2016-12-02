package com.example.darknight.mi2016;

import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import org.json.JSONObject;

import okhttp3.MultipartBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class RegistrationActivity extends AppCompatActivity {
    int index = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration);
        final EditText field = (EditText) findViewById(R.id.userInputField);
        final Button button = (Button) findViewById(R.id.submit);
        final String[] fieldNames = {"Name", "Email ID", "Mobile No.", "City", "College", "Year of study"};
        final int lengthList = fieldNames.length;
        field.setText(getIntent().getStringExtra("Name"));
        final String[] userDetails = new String[10];
        button.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                if (index == lengthList) {
                    userDetails[index-1] = field.getText().toString();
                    userDetails[index] = getIntent().getStringExtra("Gender");
                    RegisterMI registerMI = new RegisterMI();
                    registerMI.execute(userDetails);
                } else if (index < lengthList + 1) {
                    userDetails[index - 1] = field.getText().toString();
                    if (index < lengthList) {
                        if (fieldNames[index].equals("Email ID")) {
                            field.setText(getIntent().getStringExtra(fieldNames[index]));
                        } else {
                            field.setText(null);
                            field.setHint(fieldNames[index]);
                        }
                    }
                }
                index = index + 1;
            }
        });
    }
}

class RegisterMI extends AsyncTask<String, Void, Void> {
    @Override
    protected Void doInBackground(String... params) {
        try {
            OkHttpClient client = new OkHttpClient();
            RequestBody requestBody = new MultipartBody.Builder()
                    .setType(MultipartBody.FORM)
                    .addFormDataPart("name", params[0])
                    .addFormDataPart("email", params[1])
                    .addFormDataPart("mobile", params[2])
                    .addFormDataPart("gender", params[6])
                    .addFormDataPart("city", params[3])
                    .addFormDataPart("college", params[4])
                    .build();

            Request request = new Request.Builder()
                    .url("https://moodi.org/api/insert/fbLogin")
                    .method("POST", RequestBody.create(null, new byte[0]))
                    .post(requestBody)
                    .build();

            Response response = client.newCall(request).execute();
            String jsonData = response.body().string();
            JSONObject Jobject = new JSONObject(jsonData);
            Log.e("WWWWWWWWWWWWWWWWW", Jobject.toString());
            return null;
        } catch (Exception e) {
            Log.e("APP_TAG", "STACKTRACE");
            Log.e("APP_TAG", Log.getStackTraceString(e));
            return null;
        }
    }
}
