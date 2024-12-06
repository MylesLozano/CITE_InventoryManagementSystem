package com.example.cite_inventorymanagementsystem;

import android.database.Cursor;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

public class SearchItemActivity extends Fragment {
    private DatabaseHelper dbHelper;
    private EditText etSearchQuery;
    private ListView searchResults;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.search_item, container, false);

        dbHelper = new DatabaseHelper(getContext());
        etSearchQuery = view.findViewById(R.id.etSearchQuery);
        searchResults = view.findViewById(R.id.searchResults);
        Button btnSearch = view.findViewById(R.id.btnSearch);

        btnSearch.setOnClickListener(v -> {
            String query = etSearchQuery.getText().toString().trim();
            if (query.isEmpty()) {
                Toast.makeText(getContext(), "Please enter a search query", Toast.LENGTH_SHORT).show();
                return;
            }

            Cursor cursor = dbHelper.searchItems(query, 10, 0); // Updated method
            if (cursor != null && cursor.getCount() > 0) {
                SimpleCursorAdapter adapter = getSimpleCursorAdapter(cursor);
                searchResults.setAdapter(adapter);
            } else {
                Toast.makeText(getContext(), "No items match your search", Toast.LENGTH_SHORT).show();
            }
        });

        return view;
    }

    private @NonNull SimpleCursorAdapter getSimpleCursorAdapter(Cursor cursor) {
        String[] from = {DatabaseHelper.COLUMN_ITEM_NAME, DatabaseHelper.COLUMN_CATEGORY,
                DatabaseHelper.COLUMN_QUANTITY, DatabaseHelper.COLUMN_PRICE};
        int[] to = {R.id.tvItemName, R.id.tvCategory, R.id.tvQuantity, R.id.tvPrice};

        return new SimpleCursorAdapter(
                getContext(),
                R.layout.inventory_item,
                cursor,
                from,
                to,
                0
        );
    }
}
