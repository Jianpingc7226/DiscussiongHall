package com.jianpingandy.freedomproject;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;

import com.google.android.material.button.MaterialButton;

public class UserProfile extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        MaterialButton homeButton = (MaterialButton) findViewById(R.id.z1);

        homeButton.setOnClickListener(view -> {
            startActivity(new Intent(getApplicationContext(), Main_Page.class));
        });
    }
}