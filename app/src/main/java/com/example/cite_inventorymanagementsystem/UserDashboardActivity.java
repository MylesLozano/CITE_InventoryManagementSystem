package com.example.cite_inventorymanagementsystem;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import androidx.appcompat.app.AppCompatActivity;

public class UserDashboardActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_dashboard);

        Button btnSearchItems = findViewById(R.id.btnSearchItems);
        Button btnRequestItem = findViewById(R.id.btnRequestItem);

        // Navigate to Search Items
        btnSearchItems.setOnClickListener(v -> {
            Intent intent = new Intent(this, SearchItemActivity.class);
            startActivity(intent);
        });

        // Navigate to Request Item
        btnRequestItem.setOnClickListener(v -> {
            Intent intent = new Intent(this, RequestApprovalActivity.class);
            startActivity(intent);
        });
    }
}
