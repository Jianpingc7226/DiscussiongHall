package com.jianpingandy.freedomproject;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.button.MaterialButton;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class CreateAccount extends AppCompatActivity {
    FirebaseAuth mAuth;
    EditText newUsername = findViewById(R.id.newUsername);
    EditText newPassword = findViewById(R.id.newPassword);
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
        String email = newUsername.getText().toString();
        String password = newPassword.getText().toString();

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
                        Toast.makeText(CreateAccount.this, "User registered successfully", Toast.LENGTH_SHORT).show();
                        startActivity(new Intent(CreateAccount.this,Main_Page.class));
                    } else {
                        Toast.makeText(CreateAccount.this, "Registration Error: " + task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                    }
                }
            });
        }
    }
}