package com.jianpingandy.freedomproject;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import com.google.android.material.button.MaterialButton;

public class DiscussingHall extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_discussing_hall);

        MaterialButton returnbtn = (MaterialButton) findViewById(R.id.homepagebutton);

        returnbtn.setOnClickListener(view->{
            startActivity(new Intent(getApplicationContext(), Main_Page.class));
        });
    }
}