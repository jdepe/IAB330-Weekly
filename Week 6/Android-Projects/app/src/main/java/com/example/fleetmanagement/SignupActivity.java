package com.example.fleetmanagement;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class SignupActivity extends AppCompatActivity {
    private EditText nameEditText;
    private EditText emailEditText;
    private EditText phoneEditText;
    private EditText passwordEditText;
    private Button signupButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);

        setViewIds();

        // Set click listener
        signupButton.setOnClickListener(view -> {
            String name = nameEditText.getText().toString();
            String email = emailEditText.getText().toString();
            String password = passwordEditText.getText().toString();
            String phone = phoneEditText.getText().toString();

            int errors = 0;

            if (name.length() < 3 || !name.matches("[a-zA-Z]+")) {
                nameEditText.setError("Names must contain at least 3 characters and only contain alphabetical characters.");
                errors += 1;
            }

            if (!email.matches("[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+")){
                emailEditText.setError("Email is not valid.");
                errors += 1;
            }

            if (!phone.matches("\\d{10}")){
                phoneEditText.setError("Phone number is not valid.");
                errors += 1;
            }

            if (password.length() < 5) {
                passwordEditText.setError("Password is too short.");
                errors += 1;
            }

            if (errors == 0) {
                Toast.makeText(SignupActivity.this, "Signup Successful, please login", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(SignupActivity.this, LoginActivity.class);
                startActivity(intent);
            }
            else {
                Toast.makeText(SignupActivity.this, "Signup failed", Toast.LENGTH_SHORT).show();
            }

        });
    }

    private void setViewIds() {
        nameEditText = findViewById(R.id.nameEditText);
        emailEditText = findViewById(R.id.emailEditText);
        phoneEditText = findViewById(R.id.phoneEditText);
        passwordEditText = findViewById(R.id.passwordEditText);
        signupButton = findViewById(R.id.signupButton);
    }

}