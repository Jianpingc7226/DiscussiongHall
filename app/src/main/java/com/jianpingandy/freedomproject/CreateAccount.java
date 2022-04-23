package com.jianpingandy.freedomproject;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.nfc.Tag;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.button.MaterialButton;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;

public class CreateAccount extends AppCompatActivity {
    FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_account);

        MaterialButton returnHome = (MaterialButton) findViewById(R.id.returnHome);
        MaterialButton login = (MaterialButton) findViewById(R.id.makeaccount);
        mAuth = FirebaseAuth.getInstance();
        login.setOnClickListener(view -> {
            createUser();
        });


        returnHome.setOnClickListener(view ->{
                startActivity(new Intent(CreateAccount.this, LoginPage.class));
        });
    }

    private void createUser(){
        EditText newUsername = findViewById(R.id.newUsername);
        EditText newPassword = findViewById(R.id.newPassword);
        String email = newUsername.getText().toString();
        String password = newPassword.getText().toString();
        FirebaseFirestore db = FirebaseFirestore.getInstance();



        if(TextUtils.isEmpty(email)) {
            newUsername.setError("Email cannot be empty");
            newUsername.requestFocus();
        }else if(TextUtils.isEmpty(password)) {
            newPassword.setError("Password cannot be empty");
            newPassword.requestFocus();
        } else {
            mAuth.createUserWithEmailAndPassword(email,password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                @Override
                public void onComplete(@NonNull Task<AuthResult> task) {
                    if(task.isSuccessful()){
                        Map<String, Object> user = new HashMap<>();
                        user.put("email",email);
                        user.put("password",password);
                        user.put("username","visitor");
                        user.put("school","");
                        Toast.makeText(CreateAccount.this, "User registered successfully", Toast.LENGTH_SHORT).show();
                        db.collection("User").document(email).set(user);
                        startActivity(new Intent(CreateAccount.this,Main_Page.class));
                    } else {
                        Toast.makeText(CreateAccount.this, "Registration Error: " + task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                    }
                }
            });
        }
    }
}