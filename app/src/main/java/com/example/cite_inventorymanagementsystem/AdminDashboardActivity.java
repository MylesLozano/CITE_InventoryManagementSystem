package com.example.cite_inventorymanagementsystem;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import androidx.appcompat.app.AppCompatActivity;

public class AdminDashboardActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_dashboard);

        Button btnManageInventory = findViewById(R.id.btnManageInventory);
        Button btnViewRequests = findViewById(R.id.btnViewRequests);

        // Navigate to Inventory Management
        btnManageInventory.setOnClickListener(v -> {
            Intent intent = new Intent(this, ManageInventoryActivity.class);
            startActivity(intent);
        });

        // Navigate to View Requests
        btnViewRequests.setOnClickListener(v -> {
            Intent intent = new Intent(this, RequestApprovalActivity.class);
            startActivity(intent);
        });
    }
}
