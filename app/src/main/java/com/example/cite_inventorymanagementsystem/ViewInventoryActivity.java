package com.example.cite_inventorymanagementsystem;

import android.database.Cursor;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;
import android.widget.Toast;

import androidx.fragment.app.Fragment;

public class ViewInventoryActivity extends Fragment {
    private DatabaseHelper dbHelper;
    private ListView inventoryList;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.view_inventory, container, false);

        dbHelper = new DatabaseHelper(getContext());
        inventoryList = view.findViewById(R.id.inventoryListView);

        // Load inventory
        loadInventory();

        return view;
    }

    private void loadInventory() {
        Cursor cursor = dbHelper.getInventory();

        if (cursor != null && cursor.getCount() > 0) {
            String[] from = {"item_name", "category", "quantity", "price"};
            int[] to = {R.id.tvItemName, R.id.tvCategory, R.id.tvQuantity, R.id.tvPrice};

            SimpleCursorAdapter adapter = new SimpleCursorAdapter(
                    getContext(),
                    R.layout.inventory_item,
                    cursor,
                    from,
                    to,
                    0
            );

            inventoryList.setAdapter(adapter);
        } else {
            Toast.makeText(getContext(), "No inventory found", Toast.LENGTH_SHORT).show();
        }
    }
}
