package com.example.darknight.mi2016;

import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;

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
        final String[] userDetails = new String[6];
        button.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                userDetails[index] = field.getText().toString();
                index = index + 1;
                if (index >= 6) {
                    FetchMiNumberTask miNumberTask = new FetchMiNumberTask();
                    miNumberTask.execute();
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

class FetchMiNumberTask extends AsyncTask<String, Void, Void> {
    private Exception exception;

    protected Void doInBackground(String... urls) {
        try {
            Class.forName("com.mysql.jdbc.Driver");
            Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/mi_2016", "root", "pratik123");
            Statement stmt = con.createStatement();
            ResultSet rs = stmt.executeQuery("select * from register");
            Log.e("sdasdas", "dasdasdad");
            return null;
        } catch (Exception e) {
            return null;
        }
    }
}

class MysqlCon {
    public static void main(String args[]) {
        try {
            Class.forName("com.mysql.jdbc.Driver");

            Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/mi_2016", "root", "pratik123");
            Statement stmt = con.createStatement();

            ResultSet rs = stmt.executeQuery("select * from register");

            while (rs.next())
                Log.w("sdfsdf", rs.getInt(1) + "  " + rs.getString(2) + "  " + rs.getString(3));

            con.close();

        } catch (Exception e) {
            Log.e("kjhjk", "kjjkj");
        }

    }
}
