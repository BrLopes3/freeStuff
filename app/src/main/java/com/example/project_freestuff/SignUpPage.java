package com.example.project_freestuff;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;


public class SignUpPage extends AppCompatActivity {

    Button btnregister, btnsignin;
    EditText txtname, txtemail, txtpassword, txtconfirmpassword;
    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up_page);

        btnregister = findViewById(R.id.registerbtn);
        btnsignin = findViewById(R.id.signInbtn);
        mAuth = FirebaseAuth.getInstance();

        txtname = findViewById(R.id.usernametxt);
        txtemail = findViewById(R.id.emailtxt);
        txtpassword = findViewById(R.id.passwordtxt);
        txtconfirmpassword = findViewById(R.id.confirmpasswordtxt);

        btnsignin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(SignUpPage.this, LoginPage.class);
                startActivity(intent);
            }
        });

        btnregister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                registerUser();
            }
        });

    }
    private void registerUser() {
        String name = txtname.getText().toString().trim();
        String email = txtemail.getText().toString().trim();
        String password = txtpassword.getText().toString().trim();
        String confirmPassword = txtconfirmpassword.getText().toString().trim();


        if (name.isEmpty() || email.isEmpty() || password.isEmpty() || confirmPassword.isEmpty()) {
            Toast.makeText(this, "All the field should be filled!!", Toast.LENGTH_SHORT).show();
            return;
        }
        if (password.length() < 8) {
            txtpassword.setError("Password should be more than 8 char");
            txtpassword.requestFocus();
            return;
        }
        if (!password.equals(confirmPassword)) {
            txtconfirmpassword.setError("Password and confirmation is not match");
            txtconfirmpassword.requestFocus();
            return;
        }

        if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            txtemail.setError("Please Put the valid Email address");
            txtemail.requestFocus();
            return;
        }

        mAuth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            Toast.makeText(SignUpPage.this,
                                    "Register Success :) ", Toast.LENGTH_SHORT).show();
                            Intent intent = new Intent(SignUpPage.this, LoginPage.class);
                            intent.putExtra("email", email);
                            intent.putExtra("password", password);
                            startActivity(intent);
                            finish();
                        } else {
                            Toast.makeText(SignUpPage.this, "Failed", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }
}