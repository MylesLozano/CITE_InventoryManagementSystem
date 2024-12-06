package com.example.cite_inventorymanagementsystem;

import android.database.Cursor;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import androidx.fragment.app.Fragment;

public class GenerateReportsActivity extends Fragment {

    private DatabaseHelper dbHelper;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.generate_reports, container, false);

        dbHelper = new DatabaseHelper(getContext());
        Button generateReportButton = view.findViewById(R.id.btnGenerateReport);

        generateReportButton.setOnClickListener(v -> {
            String report = generateInventoryReport();
            if (report != null && !report.isEmpty()) {
                Toast.makeText(getContext(), "Report generated:\n" + report, Toast.LENGTH_LONG).show();
            } else {
                Toast.makeText(getContext(), "No data available for the report", Toast.LENGTH_SHORT).show();
            }
        });

        return view;
    }

    /**
     * Generates a simple inventory report using the DatabaseHelper.
     */
    private String generateInventoryReport() {
        StringBuilder report = new StringBuilder();
        Cursor cursor = dbHelper.getInventory(); // Fetch inventory data
        if (cursor != null) {
            while (cursor.moveToNext()) {
                String itemName = cursor.getString(cursor.getColumnIndex(DatabaseHelper.COLUMN_ITEM_NAME));
                String category = cursor.getString(cursor.getColumnIndex(DatabaseHelper.COLUMN_CATEGORY));
                int quantity = cursor.getInt(cursor.getColumnIndex(DatabaseHelper.COLUMN_QUANTITY));
                double price = cursor.getDouble(cursor.getColumnIndex(DatabaseHelper.COLUMN_PRICE));
                report.append("Item: ").append(itemName)
                        .append(", Category: ").append(category)
                        .append(", Quantity: ").append(quantity)
                        .append(", Price: $").append(price)
                        .append("\n");
            }
            cursor.close();
        }
        return report.toString();
    }
}
