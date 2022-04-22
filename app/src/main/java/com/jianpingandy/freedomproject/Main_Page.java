package com.jianpingandy.freedomproject;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;

import com.google.android.material.button.MaterialButton;

public class Main_Page extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_page);

        MaterialButton profilebtn = (MaterialButton) findViewById(R.id.profile);
        MaterialButton pastActivity = (MaterialButton) findViewById(R.id.personalActivity);
        MaterialButton discussingHallButton = (MaterialButton) findViewById(R.id.discussionHall);

        profilebtn.setOnClickListener(view->{
                startActivity(new Intent(getApplicationContext(), UserProfile.class));
        });

        pastActivity.setOnClickListener(view->{
                startActivity(new Intent(getApplicationContext(),PastActivities.class));
        });

        discussingHallButton.setOnClickListener(view->{
            startActivity(new Intent(getApplicationContext(),DiscussingHall.class));
        });
    }
}