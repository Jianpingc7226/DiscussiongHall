package com.jianpingandy.freedomproject;

import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

public class HomeFragment extends Fragment {
    Button createPostBtn;

    @Override
    public void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_home, container, false);

        createPostBtn = (Button) view.findViewById(R.id.createPostBtn);
        EditText input = (EditText) view.findViewById(R.id.input);
        ImageView check = view.findViewById(R.id.check_mark);
        TextView post = view.findViewById(R.id.Post);
        ImageView reply = view.findViewById(R.id.replyBtn);

        createPostBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                input.setVisibility(View.VISIBLE);
                check.setVisibility(View.VISIBLE);
            }
        });

        check.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                post.setText(input.getText().toString());
                post.setVisibility(View.VISIBLE);
                reply.setVisibility(View.VISIBLE);
                input.setText("");
            }
        });

        reply.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getContext(),PostHistory.class));
            }
        });

        return view;
    }

}