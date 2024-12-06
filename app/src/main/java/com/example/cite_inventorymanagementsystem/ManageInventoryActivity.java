package com.example.cite_inventorymanagementsystem;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;

public class ManageInventoryActivity extends AppCompatActivity {

    private DatabaseHelper dbHelper;
    private EditText edtItemName, edtCategory, edtQuantity, edtPrice, edtItemId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_manage_inventory);

        dbHelper = new DatabaseHelper(this);

        // Initialize UI elements
        edtItemId = findViewById(R.id.edtItemId);  // Added Item ID field for updating or deleting
        edtItemName = findViewById(R.id.edtItemName);
        edtCategory = findViewById(R.id.edtCategory);
        edtQuantity = findViewById(R.id.edtQuantity);
        edtPrice = findViewById(R.id.edtPrice);

        Button btnAddItem = findViewById(R.id.btnAddItem);
        Button btnViewItems = findViewById(R.id.btnViewItems);
        Button btnUpdateItem = findViewById(R.id.btnUpdateItem);  // New button to update item
        Button btnDeleteItem = findViewById(R.id.btnDeleteItem);  // New button to delete item

        // Add new inventory item
        btnAddItem.setOnClickListener(v -> {
            String itemName = edtItemName.getText().toString().trim();
            String category = edtCategory.getText().toString().trim();
            String quantityStr = edtQuantity.getText().toString().trim();
            String priceStr = edtPrice.getText().toString().trim();

            if (itemName.isEmpty() || category.isEmpty() || quantityStr.isEmpty() || priceStr.isEmpty()) {
                Toast.makeText(this, "All fields must be filled", Toast.LENGTH_SHORT).show();
                return;
            }

            int quantity = Integer.parseInt(quantityStr);
            double price = Double.parseDouble(priceStr);

            boolean isSuccess = dbHelper.insertItem(itemName, category, quantity, price, "QR1234");
            if (isSuccess) {
                Toast.makeText(this, "Item added successfully", Toast.LENGTH_SHORT).show();
                clearFields();
            } else {
                Toast.makeText(this, "Failed to add item", Toast.LENGTH_SHORT).show();
            }
        });

        // View all inventory items
        btnViewItems.setOnClickListener(v -> {
            Intent intent = new Intent(ManageInventoryActivity.this, ViewInventoryActivity.class);
            startActivity(intent);
        });

        // Update inventory item
        btnUpdateItem.setOnClickListener(v -> {
            String itemIdStr = edtItemId.getText().toString().trim();
            String itemName = edtItemName.getText().toString().trim();
            String category = edtCategory.getText().toString().trim();
            String quantityStr = edtQuantity.getText().toString().trim();
            String priceStr = edtPrice.getText().toString().trim();

            if (itemIdStr.isEmpty() || itemName.isEmpty() || category.isEmpty() || quantityStr.isEmpty() || priceStr.isEmpty()) {
                Toast.makeText(this, "All fields must be filled", Toast.LENGTH_SHORT).show();
                return;
            }

            int itemId = Integer.parseInt(itemIdStr);
            int quantity = Integer.parseInt(quantityStr);
            double price = Double.parseDouble(priceStr);

            boolean isSuccess = dbHelper.updateItem(itemId, itemName, category, quantity, price);
            if (isSuccess) {
                Toast.makeText(this, "Item updated successfully", Toast.LENGTH_SHORT).show();
                clearFields();
            } else {
                Toast.makeText(this, "Failed to update item", Toast.LENGTH_SHORT).show();
            }
        });

        // Delete inventory item
        btnDeleteItem.setOnClickListener(v -> {
            String itemIdStr = edtItemId.getText().toString().trim();
            if (itemIdStr.isEmpty()) {
                Toast.makeText(this, "Item ID is required", Toast.LENGTH_SHORT).show();
                return;
            }

            int itemId = Integer.parseInt(itemIdStr);
            boolean isSuccess = dbHelper.deleteItem(itemId);
            if (isSuccess) {
                Toast.makeText(this, "Item deleted successfully", Toast.LENGTH_SHORT).show();
                clearFields();
            } else {
                Toast.makeText(this, "Failed to delete item", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void clearFields() {
        edtItemId.setText("");
        edtItemName.setText("");
        edtCategory.setText("");
        edtQuantity.setText("");
        edtPrice.setText("");
    }
}
