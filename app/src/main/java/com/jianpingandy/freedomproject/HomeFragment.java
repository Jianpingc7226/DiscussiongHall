package com.jianpingandy.freedomproject;

import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.GridLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.progressindicator.LinearProgressIndicator;
import com.google.api.Distribution;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import org.w3c.dom.Document;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class HomeFragment extends Fragment {
    Button createPostBtn;
    int id = 1;
    String CurrentUser;

    @Override
    public void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_home, container, false);
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        DocumentReference docRef = db.collection("User").document(user.getEmail());

        docRef.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                DocumentSnapshot document = task.getResult();
                CurrentUser = document.get("username").toString();
            }
        });

        createPostBtn = (Button) view.findViewById(R.id.createPostBtn);
        EditText input =view.findViewById(R.id.input);
        Button check = view.findViewById(R.id.confirm_post_btn);
        TextView post = view.findViewById(R.id.Post);
        TextView userName = view.findViewById(R.id.nameOfUser);
        ImageView reply = view.findViewById(R.id.replyBtn);
        LinearLayout postArea = (LinearLayout) view.findViewById(R.id.alan);
        db.collection("Post")
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if(task.isSuccessful()){
                            for(QueryDocumentSnapshot document : task.getResult()){

                                LinearLayout layout = new LinearLayout(container.getContext());
                                layout.setOrientation(LinearLayout.VERTICAL);
                                layout.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT));

//                                ViewGroup.LayoutParams layoutParams = view.getLayoutParams();
//                                layoutParams.width = 300;
//                                layoutPara
//                                ms.height = android.view.ViewGroup.LayoutParams.WRAP_CONTENT;
                                TextView Question = new TextView(container.getContext());
                                Question.setText(document.get("Title").toString());
                                Question.setTextSize(25);
                                Question.setGravity(Gravity.CENTER);
                                Question.setBackgroundResource(R.drawable.shapeofpostcomment);
//                                Question.setLayoutParams(layoutParams);
                                layout.addView(Question);

                                LinearLayout layout2 = new LinearLayout(container.getContext());
                                layout2.setOrientation(LinearLayout.HORIZONTAL);
                                layout2.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT,LinearLayout.LayoutParams.WRAP_CONTENT));

                                TextView userName = new TextView(container.getContext());
                                userName.setText(document.get("Announcer").toString());
//                                userName.setTextColor(Integer.parseInt("#9505f5"));
                                userName.setTextSize(20);
                                userName.setGravity(Gravity.CENTER);
                                userName.setTypeface(null, Typeface.BOLD);



                                ImageView commentButton = new ImageView(container.getContext());
                                commentButton.setId(id);
                                commentButton.setImageResource(R.drawable.reply_icon);

                                id++;


                                commentButton.setOnClickListener(new View.OnClickListener() {
                                    @Override
                                    public void onClick(View view) {
                                        ImageView button = (ImageView) view;
                                        int id = button.getId();
                                        String trueId = String.valueOf(id);
                                        Intent intent = new Intent(getActivity(), PostHistory.class);
                                        intent.putExtra("postId",trueId);
                                        startActivity(intent);
                                    }
                                });


                                layout2.addView(userName);
                                layout2.addView(commentButton);
                                layout.addView(layout2);
                                postArea.addView(layout);

                            }
                        }
                    }
                });





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

                Map<String,Object> postData = new HashMap<>();
                postData.put("Announcer",CurrentUser);
                postData.put("Title",input.getText().toString());
                postData.put("id",id);
                db.collection("Post").document(String.valueOf(id))
                        .set(postData)
                        .addOnCompleteListener(new OnCompleteListener<Void>() {
                            @Override
                            public void onComplete(@NonNull Task<Void> task) {
                                String ContentInComment = input.getText().toString().toLowerCase();
                                if(ContentInComment.length() == 0){
                                    input.setText("");
                                    Toast.makeText(getContext(), "PLEASE POST YOUR THOUGHTS OR QUESTIONS", Toast.LENGTH_SHORT).show();
                                }else if (ContentInComment.indexOf("fuck")  >= 0 || ContentInComment.indexOf("shit") >= 0 || ContentInComment.indexOf("shut up") >= 0 || ContentInComment.indexOf("bullshit") >= 0 || ContentInComment.indexOf("motherfucker") >= 0 || ContentInComment.indexOf("mother fucker") >= 0 || ContentInComment.indexOf("bitch") >= 0 || ContentInComment.indexOf("bull shit") >= 0 || ContentInComment.indexOf("fat") >= 0 || ContentInComment.indexOf("cunt") >= 0 || ContentInComment.indexOf("hell") >= 0 || ContentInComment.indexOf("ass") >= 0 || ContentInComment.indexOf("dick") >= 0 || ContentInComment.indexOf("pussy") >= 0 || ContentInComment.indexOf("idiot") >= 0 ){
                                    input.setText("");
                                    Toast.makeText(getContext(), "WATCH YOUR LANGUAGE!!!", Toast.LENGTH_SHORT).show();
                                }else{
                                    LinearLayout layout = new LinearLayout(container.getContext());
                                    layout.setOrientation(LinearLayout.VERTICAL);
                                    layout.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT));


                                    TextView Question = new TextView(container.getContext());
                                    Question.setText(input.getText().toString());
                                    Question.setTextSize(25);
                                    Question.setGravity(Gravity.CENTER);
                                    Question.setBackgroundResource(R.drawable.shapeofpostcomment);
    //                                Question.setLayoutParams(layoutParams);
                                    layout.addView(Question);

                                    LinearLayout layout2 = new LinearLayout(container.getContext());
                                    layout2.setOrientation(LinearLayout.HORIZONTAL);
                                    layout2.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT));

                                    TextView userName = new TextView(container.getContext());
                                    userName.setText(CurrentUser);
    //                                userName.setTextColor(Integer.parseInt("#9505f5"));
                                    userName.setTextSize(20);
                                    userName.setGravity(Gravity.CENTER);
                                    userName.setTypeface(null, Typeface.BOLD);

                                    ImageView commentButton = new ImageView(container.getContext());
                                    commentButton.setId(id);
                                    commentButton.setImageResource(R.drawable.reply_icon);
                                    id++;


                                    commentButton.setOnClickListener(new View.OnClickListener() {
                                        @Override
                                        public void onClick(View view) {
                                            ImageView button = (ImageView) view;
                                            int id = button.getId();
                                            String trueId = String.valueOf(id);
                                            Intent intent = new Intent(getActivity(), PostHistory.class);
                                            intent.putExtra("postId", trueId);
                                            startActivity(intent);
                                        }
                                    });

                                    layout2.addView(userName);
                                    layout2.addView(commentButton);
                                    layout.addView(layout2);
                                    postArea.addView(layout);
                                    input.setText("");
                                }
                            }
                        });
            }
        });

        reply.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getContext(), PostHistory.class));
            }
        });

        return view;
    }

}