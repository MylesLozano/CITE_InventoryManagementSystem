package com.example.cite_inventorymanagementsystem;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class LoginActivity extends AppCompatActivity {

    private EditText edtUsername, edtPassword;
    private Button btnLogin;
    private UserSessionManager userSessionManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        edtUsername = findViewById(R.id.edtUsername);
        edtPassword = findViewById(R.id.edtPassword);
        btnLogin = findViewById(R.id.btnLogin);

        userSessionManager = new UserSessionManager(this);

        btnLogin.setOnClickListener(v -> {
            String username = edtUsername.getText().toString().trim();
            String password = edtPassword.getText().toString().trim();

            if (username.isEmpty() || password.isEmpty()) {
                Toast.makeText(LoginActivity.this, "Username and Password are required", Toast.LENGTH_SHORT).show();
            } else {
                if (username.equals("admin") && password.equals("admin123")) {
                    userSessionManager.createLoginSession(username, "admin");
                    navigateToDashboard("admin");
                } else if (username.equals("user") && password.equals("user123")) {
                    userSessionManager.createLoginSession(username, "user");
                    navigateToDashboard("user");
                } else {
                    Toast.makeText(LoginActivity.this, "Invalid credentials", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    private void navigateToDashboard(String userType) {
        Intent intent;
        if (userType.equals("admin")) {
            intent = new Intent(LoginActivity.this, AdminDashboardActivity.class);
        } else {
            intent = new Intent(LoginActivity.this, UserDashboardActivity.class);
        }
        startActivity(intent);
        finish();  // To prevent the user from going back to the login screen
    }
}
