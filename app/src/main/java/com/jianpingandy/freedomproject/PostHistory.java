package com.jianpingandy.freedomproject;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.Typeface;
import android.media.Image;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.api.Distribution;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FieldValue;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import org.w3c.dom.Comment;
import org.w3c.dom.Document;
import org.w3c.dom.Text;

import java.util.HashMap;
import java.util.Map;

public class PostHistory extends AppCompatActivity {
    public int commentId=1;
    String CurrentUser;
    String CurrentUserSchool;
    Boolean hasSchool = false;


    @Override
    public void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.posthistory);
        LinearLayout commentArea = findViewById(R.id.commentArea);
        Button homeBtn = findViewById(R.id.homeBtn);
        TextView question = findViewById(R.id.Question);
        Button userComment = findViewById(R.id.confirm_Comment);
        EditText commentContent = findViewById(R.id.comment);
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        Intent intent = getIntent();
        String id = intent.getStringExtra("postId");
        TextView announser = findViewById(R.id.user1);
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        DocumentReference docRef = db.collection("User").document(user.getEmail());



        docRef.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                DocumentSnapshot document = task.getResult();
                CurrentUser = document.get("username").toString();
                if(!document.get("school").equals("")){
                    hasSchool = true;
                    CurrentUserSchool = document.get("school").toString();
                }
            }
        });


        db.collection("Post").document(id).get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                if(task.isSuccessful()){
                    DocumentSnapshot document = task.getResult();
                    if(document.exists()){
                        announser.setText(document.get("Announcer").toString());
                        question.setText(document.get("Title").toString());


                        db.collection("Post").document(id).collection("Comments").get()
                                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                                    @Override
                                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                                        if(task.isSuccessful()){
                                            for(QueryDocumentSnapshot document1: task.getResult()){
                                                LinearLayout layout = new LinearLayout(PostHistory.this);
                                                layout.setOrientation(LinearLayout.VERTICAL);
                                                layout.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT));

                                                TextView userName = new TextView(PostHistory.this);
                                                userName.setBackgroundColor(Color.BLACK);
                                                userName.setText(document1.get("userCommented").toString());
                                                userName.setTextColor(Color.WHITE);
                                                userName.setTextSize(20);
                                                userName.setTypeface(null, Typeface.BOLD);

                                                TextView userComment = new TextView(PostHistory.this);
                                                userComment.setBackgroundColor(Color.WHITE);
                                                userComment.setTextColor(Color.BLACK);
                                                userComment.setTextSize(20);
                                                userComment.setTypeface(null,Typeface.BOLD);
                                                userComment.setText(document1.get("Comment").toString());


                                                LinearLayout likeslayout = new LinearLayout(PostHistory.this);
                                                likeslayout.setOrientation(LinearLayout.HORIZONTAL);
                                                likeslayout.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT));



                                                TextView numberOfLikes = new TextView(PostHistory.this);
                                                numberOfLikes.setText(document1.get("like").toString());
                                                numberOfLikes.setTag(commentId);


                                                ImageView icon = new ImageView(PostHistory.this);
                                                icon.setImageResource(R.drawable.thumb_up_icon);
                                                icon.setId(commentId);

                                                likeslayout.addView(numberOfLikes);
                                                likeslayout.addView(icon);

                                                icon.setOnClickListener(new View.OnClickListener() {
                                                    @Override
                                                    public void onClick(View view) {
                                                        ImageView button = (ImageView) view;
                                                        int number = button.getId();
                                                        LinearLayout parentComment = (LinearLayout) (view.getParent());
                                                        TextView numberLikes = parentComment.findViewWithTag(number);
                                                        db.collection("Post").document(id).collection("Comments").document(String.valueOf(number)).update("like", FieldValue.increment(1));
                                                        db.collection("Post").document(id).collection("Comments").document(String.valueOf(number)).get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                                                            @Override
                                                            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                                                                DocumentSnapshot document2 = task.getResult();
                                                                numberLikes.setText(document2.get("like").toString());
                                                            }
                                                        });
                                                    }
                                                });

                                                layout.addView(userName);
                                                layout.addView(userComment);
//                                                layout.addView(numberOfLikes);
//                                                layout.addView(icon);
                                                layout.addView(likeslayout);

                                                commentArea.addView(layout);

                                                commentId++;

                                            }
                                        }
                                    }
                                });

                    }
                }
            }
        });





        userComment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String ContentInComment = commentContent.getText().toString().toLowerCase();
                if(ContentInComment.length() == 0){
                    commentContent.setText("");
                    Toast.makeText(PostHistory.this, "PLEASE POST YOUR THOUGHTS OR QUESTIONS", Toast.LENGTH_SHORT).show();
                }else if (ContentInComment.indexOf("fuck")  >= 0 || ContentInComment.indexOf("shit") >= 0 || ContentInComment.indexOf("shut up") >= 0 || ContentInComment.indexOf("bullshit") >= 0 || ContentInComment.indexOf("motherfucker") >= 0 || ContentInComment.indexOf("mother fucker") >= 0 || ContentInComment.indexOf("bitch") >= 0 || ContentInComment.indexOf("bull shit") >= 0 || ContentInComment.indexOf("fat") >= 0 || ContentInComment.indexOf("cunt") >= 0 || ContentInComment.indexOf("hell") >= 0 || ContentInComment.indexOf("ass") >= 0 || ContentInComment.indexOf("dick") >= 0 || ContentInComment.indexOf("pussy") >= 0 || ContentInComment.indexOf("idiot") >= 0 ) {
                    commentContent.setText("");
                    Toast.makeText(PostHistory.this, "WATCH YOUR LANGUAGE!!!", Toast.LENGTH_SHORT).show();
                } else {


                    Map<String, Object> Comment = new HashMap<>();
                    Comment.put("Comment", commentContent.getText().toString());

                    Comment.put("like", 0);
                    if (hasSchool) {
                        Comment.put("userCommented", CurrentUser + " from " + CurrentUserSchool);
                    } else {
                        Comment.put("userCommented", CurrentUser);
                    }
                    db.collection("Post").document(id).collection("Comments").document(String.valueOf(commentId))
                            .set(Comment)
                            .addOnCompleteListener(new OnCompleteListener<Void>() {
                                @Override
                                public void onComplete(@NonNull Task<Void> task) {


                                    LinearLayout layout = new LinearLayout(PostHistory.this);
                                    layout.setOrientation(LinearLayout.VERTICAL);
                                    layout.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT));

                                    TextView userName = new TextView(PostHistory.this);
                                    userName.setBackgroundColor(Color.BLACK);
                                    userName.setTextColor(Color.WHITE);
                                    userName.setTextSize(20);
                                    userName.setTypeface(null, Typeface.BOLD);
                                    if (hasSchool) {
                                        userName.setText(CurrentUser + " from " + CurrentUserSchool);
                                    } else {
                                        userName.setText(CurrentUser);
                                    }


                                    TextView userComment = new TextView(PostHistory.this);
                                    userComment.setBackgroundColor(Color.WHITE);
                                    userComment.setTextColor(Color.BLACK);
                                    userComment.setTextSize(20);
                                    userComment.setTypeface(null, Typeface.BOLD);
                                    userComment.setText(commentContent.getText().toString());

                                    LinearLayout likeslayout = new LinearLayout(PostHistory.this);
                                    likeslayout.setOrientation(LinearLayout.HORIZONTAL);
                                    likeslayout.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT));


                                    TextView numberOfLikes = new TextView(PostHistory.this);
                                    numberOfLikes.setText("0");
                                    numberOfLikes.setTag(commentId);


                                    ImageView icon = new ImageView(PostHistory.this);
                                    icon.setImageResource(R.drawable.thumb_up_icon);
                                    icon.setId(commentId);

                                    likeslayout.addView(numberOfLikes);
                                    likeslayout.addView(icon);

                                    icon.setOnClickListener(new View.OnClickListener() {
                                        @Override
                                        public void onClick(View view) {
                                            ImageView button = (ImageView) view;
                                            int number = button.getId();
                                            LinearLayout parentComment = (LinearLayout) (view.getParent());
                                            TextView numberLikes = parentComment.findViewWithTag(number);
                                            db.collection("Post").document(id).collection("Comments").document(String.valueOf(number)).update("like", FieldValue.increment(1));
                                            db.collection("Post").document(id).collection("Comments").document(String.valueOf(number)).get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                                                @Override
                                                public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                                                    DocumentSnapshot document2 = task.getResult();
                                                    numberLikes.setText(document2.get("like").toString());
                                                }
                                            });
                                        }
                                    });

                                    layout.addView(userName);
                                    layout.addView(userComment);
                                    layout.addView(likeslayout);

                                    commentArea.addView(layout);

                                    commentId++;
                                    commentContent.setText("");

                                }
                            });
                }
            }
        });

        homeBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getApplicationContext(), Main_Page.class));
            }
        });
    }
}