package com.example.cite_inventorymanagementsystem;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    private Button btnManageInventory, btnViewRequests, btnGenerateReports, btnLogout;
    private UserSessionManager userSessionManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Initialize UserSessionManager
        userSessionManager = new UserSessionManager(this);

        // Check if the user is logged in before showing MainActivity
        if (!isUserLoggedIn()) {
            // User is not logged in, redirect to LoginActivity
            Intent intent = new Intent(MainActivity.this, LoginActivity.class);
            startActivity(intent);
            finish(); // Close MainActivity to prevent navigating back to it
            return;
        }

        setContentView(R.layout.activity_main); // Set the layout for this activity

        // Initialize UI elements
        btnManageInventory = findViewById(R.id.btnManageInventory);
        btnViewRequests = findViewById(R.id.btnViewRequests);
        btnGenerateReports = findViewById(R.id.btnGenerateReports);
        btnLogout = findViewById(R.id.btnLogout);

        // Open Manage Inventory screen
        btnManageInventory.setOnClickListener(v -> {
            Intent intent = new Intent(MainActivity.this, ManageInventoryActivity.class);
            startActivity(intent);
        });

        // Open View Requests screen
        btnViewRequests.setOnClickListener(v -> {
            Intent intent = new Intent(MainActivity.this, RequestApprovalActivity.class);
            startActivity(intent);
        });

        // Open Generate Reports screen
        btnGenerateReports.setOnClickListener(v -> {
            Intent intent = new Intent(MainActivity.this, GenerateReportsActivity.class);
            startActivity(intent);
        });

        // Logout functionality
        btnLogout.setOnClickListener(v -> {
            logoutUser();
            Toast.makeText(MainActivity.this, "Logged out successfully", Toast.LENGTH_SHORT).show();
            Intent intent = new Intent(MainActivity.this, LoginActivity.class);
            startActivity(intent);
            finish(); // Close MainActivity after logging out
        });
    }

    // Check if the user is logged in
    private boolean isUserLoggedIn() {
        return userSessionManager.isUserLoggedIn(); // Check login status using UserSessionManager
    }

    // Method to handle user logout
    private void logoutUser() {
        userSessionManager.logoutUser(); // Log out the user and clear session data
    }
}
