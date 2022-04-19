package com.jianpingandy.freedomproject;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.button.MaterialButton;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class LoginPage extends AppCompatActivity {
    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_page);
        mAuth = FirebaseAuth.getInstance();
        MaterialButton loginbtn = (MaterialButton) findViewById(R.id.login);
        MaterialButton createAccountbtn = (MaterialButton) findViewById(R.id.create_account);
//        Login Page
//        username && password == "admin" => Go to MainActivity2
        loginbtn.setOnClickListener(view->{
            loginUser();
        });
        createAccountbtn.setOnClickListener(view->{
            startActivity(new Intent(LoginPage.this, CreateAccount.class));
        });
    }

    private void loginUser(){
        EditText loginusername = (EditText) findViewById(R.id.username);
        EditText loginpassword = (EditText) findViewById(R.id.password);
        String email = loginusername.getText().toString();
        String password = loginpassword.getText().toString();

        if(TextUtils.isEmpty(email)) {
            loginusername.setError("Email cannot be empty");
            loginusername.requestFocus();
        }else if(TextUtils.isEmpty(password)) {
            loginpassword.setError("Password cannot be empty");
            loginpassword.requestFocus();
        } else{
            mAuth.signInWithEmailAndPassword(email,password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                @Override
                public void onComplete(@NonNull Task<AuthResult> task) {
                    if (task.isSuccessful()){
                        Toast.makeText(LoginPage.this, "User Logged in successfully", Toast.LENGTH_SHORT).show();
                        startActivity(new Intent(LoginPage.this,Main_Page.class));
                    }else{
                        Toast.makeText(LoginPage.this, "Log in Error: " + task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                    }
                }
            });
        }
    }
}