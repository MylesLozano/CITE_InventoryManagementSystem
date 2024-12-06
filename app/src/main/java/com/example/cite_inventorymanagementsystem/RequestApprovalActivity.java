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

public class RequestApprovalActivity extends Fragment {
    private DatabaseHelper dbHelper;
    private ListView requestList;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.request_approval, container, false);

        dbHelper = new DatabaseHelper(getContext());
        requestList = view.findViewById(R.id.requestListView);

        loadRequests();

        return view;
    }

    private void loadRequests() {
        Cursor cursor = dbHelper.getPendingRequests(); // Correct method

        if (cursor != null && cursor.getCount() > 0) {
            String[] from = {DatabaseHelper.COLUMN_USER_ID, DatabaseHelper.COLUMN_ITEM_ID,
                    DatabaseHelper.COLUMN_REQUEST_TYPE, DatabaseHelper.COLUMN_STATUS};
            int[] to = {R.id.tvUserId, R.id.tvItemId, R.id.tvRequestType, R.id.tvStatus};

            SimpleCursorAdapter adapter = new SimpleCursorAdapter(
                    getContext(),
                    R.layout.request_item,
                    cursor,
                    from,
                    to,
                    0
            );

            requestList.setAdapter(adapter);

            // Admin action: Approve/Reject on item click
            requestList.setOnItemClickListener((parent, view, position, id) -> {
                boolean approved = dbHelper.updateRequestStatus((int) id, "approved");
                if (approved) {
                    Toast.makeText(getContext(), "Request approved!", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(getContext(), "Failed to approve request!", Toast.LENGTH_SHORT).show();
                }
                loadRequests();
            });
        } else {
            Toast.makeText(getContext(), "No pending requests", Toast.LENGTH_SHORT).show();
        }
    }
}
