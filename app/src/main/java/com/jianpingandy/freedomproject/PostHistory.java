package com.jianpingandy.freedomproject;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.media.Image;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

public class PostHistory extends AppCompatActivity {

    @Override
    public void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.posthistory);

        ImageView userComment = findViewById(R.id.confirm);
        EditText commentContent = findViewById(R.id.comment);
        TextView newComment = findViewById(R.id.newCommentContent);

        userComment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                newComment.setText(commentContent.getText().toString());
                commentContent.setText("");
            }
        });

    }
}