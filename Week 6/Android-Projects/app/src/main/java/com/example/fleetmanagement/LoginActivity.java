package com.example.fleetmanagement;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.fleetmanagement.Utils.SharedPrefManager;
public class LoginActivity extends AppCompatActivity {
    private EditText emailEditText;
    private EditText passwordEditText;
    private Button loginButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        // Linking the views
        setViewIds();

        // Setting Click Listeners
        loginButton.setOnClickListener(view -> {
            String email = emailEditText.getText().toString();
            String password = passwordEditText.getText().toString();

            if (isValidCredentials(email, password)) {
                Toast.makeText(LoginActivity.this, "Login Successful", Toast.LENGTH_SHORT).show();

                SharedPrefManager.setLoginState(true);

                if (email.equals("ad@ad.com")){
                    SharedPrefManager.setAdmin(true);
                }
                else {
                    SharedPrefManager.setAdmin(false);
                }

                Intent intent = new Intent(this, VehicleListActivity.class);
                startActivity(intent);

            } else {
                Toast.makeText(LoginActivity.this, "Invalid Credentials", Toast.LENGTH_SHORT).show();
                emailEditText.setError("Invalid email");
                passwordEditText.setError("Invalid password");
            }
        });
    }

    private void setViewIds() {
        emailEditText = findViewById(R.id.emailEditText);
        passwordEditText = findViewById(R.id.passwordEditText);
        loginButton = findViewById(R.id.loginButton);
    }

    private boolean isValidCredentials(String email, String password) {
        // Perform validation logic here
        // Return true if credentials are valid, false otherwise
        return (email.equals("ex@ex.com") && password.equals("ex123")) ||  (email.equals("ad@ad.com") && password.equals("ad123"));
    }
}