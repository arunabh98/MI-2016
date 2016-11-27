package com.example.darknight.mi2016;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;

public class RegistrationActivity extends AppCompatActivity {
    int index = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration);
        final EditText field = (EditText) findViewById(R.id.userInputField);
        final Button button = (Button) findViewById(R.id.submit);
        final Spinner fieldDrop = (Spinner) findViewById(R.id.dropList);
        fieldDrop.setVisibility(View.GONE);
        final String[] fieldNames = {"Name", "Email ID", "Mobile No.", "City", "College", "Year of study"};
        field.setText(getIntent().getStringExtra("Name"));
        final String[] userDetails;
        button.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Log.e("JOOOO", field.getText().toString());
                index = index + 1;
                if (fieldNames[index].equals("Name") || fieldNames[index].equals("Email ID")) {
                    field.setText(getIntent().getStringExtra(fieldNames[index]));
                } else if (fieldNames[index].equals("City")) {
                    field.setVisibility(View.GONE);
                    fieldDrop.setVisibility(View.VISIBLE);
                    ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(RegistrationActivity.this,
                            R.array.planets_array, R.layout.registration_dropdown);
                    adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                    fieldDrop.setAdapter(adapter);
                } else {
                    field.setVisibility(View.VISIBLE);
                    fieldDrop.setVisibility(View.GONE);
                    field.setText(null);
                    field.setHint(fieldNames[index]);
                }
            }
        });
    }
}
