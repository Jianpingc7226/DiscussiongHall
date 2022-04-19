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
import com.google.firebase.database.FirebaseDatabase;

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

        if(TextUtils.isEmpty(email)) {
            newUsername.setError("Email cannot be empty");
            newUsername.requestFocus();
        }else if(TextUtils.isEmpty(password)) {
            newPassword.setError("Password cannot be empty");
            newPassword.requestFocus();
        }

        mAuth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information
                            User user = new User("visitor", email);
                            FirebaseDatabase.getInstance().getReference("users").child(FirebaseAuth.getInstance().getCurrentUser().getUid()).setValue(user).addOnCompleteListener(new OnCompleteListener<Void>() {
                                @Override
                                public void onComplete(@NonNull Task<Void> task) {
                                    showMainActivity();
                                }
                            });
                        } else {
                            // If sign in fails, display a message to the user.
                            Toast.makeText(CreateAccount.this, "Authentication failed.",Toast.LENGTH_LONG).show();

                        }
                    }
                });
    }


    public void showMainActivity(){
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
        finish();
    }
}