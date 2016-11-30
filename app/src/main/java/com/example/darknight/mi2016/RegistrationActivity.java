package com.example.darknight.mi2016;

import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import okhttp3.MultipartBody;
import okhttp3.RequestBody;

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
                Log.w("dsadasd", Integer.toString(index));
                if (index == lengthList) {
                    userDetails[index-1] = field.getText().toString();
                    for (int i = 0; i < lengthList; i++) {
                        Log.w("Hello", userDetails[i]);
                    }
                    RegisterMI registerMI = new RegisterMI();
                    registerMI.execute();
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
            RequestBody requestBody = new MultipartBody.Builder()
                    .setType(MultipartBody.FORM)
                    .addFormDataPart("name", "a")
                    .addFormDataPart("email", "b")
                    .addFormDataPart("mobile", "c")
                    .addFormDataPart("gender", "c")
                    .addFormDataPart("city", "c")
                    .addFormDataPart("college", "c")
                    .build();

            Log.e("dsdddf", requestBody.toString());
            return null;
        } catch (Exception e) {
            return null;
        }
    }
}
