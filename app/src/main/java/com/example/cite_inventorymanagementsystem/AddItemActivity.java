package com.example.cite_inventorymanagementsystem;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.fragment.app.Fragment;

public class AddItemActivity extends Fragment {
    private DatabaseHelper dbHelper;
    private EditText etItemName, etCategory, etQuantity, etPrice;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.add_item, container, false);

        dbHelper = new DatabaseHelper(getContext());
        etItemName = view.findViewById(R.id.etItemName);
        etCategory = view.findViewById(R.id.etCategory);
        etQuantity = view.findViewById(R.id.etQuantity);
        etPrice = view.findViewById(R.id.etPrice);
        Button btnAddItem = view.findViewById(R.id.btnAddItem);

        btnAddItem.setOnClickListener(v -> {
            String name = etItemName.getText().toString().trim();
            String category = etCategory.getText().toString().trim();
            String quantityStr = etQuantity.getText().toString().trim();
            String priceStr = etPrice.getText().toString().trim();

            // Edge case checks
            if (name.isEmpty() || category.isEmpty() || quantityStr.isEmpty() || priceStr.isEmpty()) {
                Toast.makeText(getContext(), "All fields are required", Toast.LENGTH_SHORT).show();
                return;
            }

            try {
                int quantity = Integer.parseInt(quantityStr);
                double price = Double.parseDouble(priceStr);

                boolean success = dbHelper.addItem(name, category, quantity, price);
                if (success) {
                    Toast.makeText(getContext(), "Item added successfully", Toast.LENGTH_SHORT).show();
                    etItemName.setText("");
                    etCategory.setText("");
                    etQuantity.setText("");
                    etPrice.setText("");
                } else {
                    Toast.makeText(getContext(), "Failed to add item", Toast.LENGTH_SHORT).show();
                }
            } catch (NumberFormatException e) {
                Toast.makeText(getContext(), "Quantity and price must be numeric", Toast.LENGTH_SHORT).show();
            }
        });

        return view;
    }
}
