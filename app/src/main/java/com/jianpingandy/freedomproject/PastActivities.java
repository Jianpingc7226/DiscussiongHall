package com.jianpingandy.freedomproject;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;

public class PastActivities extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.past_activities);

        Button home1 = (Button) findViewById(R.id.returnHome);

        home1.setOnClickListener(view->{
            startActivity(new Intent(getApplicationContext(),Main_Page.class));
        });
    }
}