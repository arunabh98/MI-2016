package com.example.darknight.mi2016;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class RegistrationActivity extends AppCompatActivity {
    int index = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration);
        final EditText field = (EditText) findViewById(R.id.userInputField);
        final Button button = (Button) findViewById(R.id.submit);
        final String[] fieldNames = {"Name", "Email ID", "Mobile No.", "City", "College", "Year of study"};
        field.setText(getIntent().getStringExtra("Name"));
        final String[] userDetails;
        button.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                index = index + 1;
                if (index >= 6) {
                    ;
                } else {
                    if (fieldNames[index].equals("Name") || fieldNames[index].equals("Email ID")) {
                        field.setText(getIntent().getStringExtra(fieldNames[index]));
                    } else {
                        field.setText(null);
                        field.setHint(fieldNames[index]);
                    }
                }
            }
        });
    }
}
