package com.example.cite_inventorymanagementsystem;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DatabaseHelper extends SQLiteOpenHelper {
    private static final String DATABASE_NAME = "InventorySystem.db";
    private static final int DATABASE_VERSION = 1;

    // Table names
    private static final String TABLE_USERS = "users";
    private static final String TABLE_INVENTORY = "inventory";
    private static final String TABLE_REQUESTS = "requests";

    // Common columns
    private static final String COLUMN_ID = "id";

    // Users table columns
    private static final String COLUMN_USERNAME = "username";
    private static final String COLUMN_PASSWORD = "password";
    private static final String COLUMN_ROLE = "role";

    // Inventory table columns
    protected static final String COLUMN_ITEM_NAME = "item_name";
    protected static final String COLUMN_CATEGORY = "category";
    protected static final String COLUMN_QUANTITY = "quantity";
    protected static final String COLUMN_PRICE = "price";
    private static final String COLUMN_QR_CODE = "qr_code";

    // Requests table columns
    protected static final String COLUMN_USER_ID = "user_id";
    protected static final String COLUMN_ITEM_ID = "item_id";
    protected static final String COLUMN_REQUEST_TYPE = "request_type";
    protected static final String COLUMN_STATUS = "status";
    private static final String COLUMN_TIMESTAMP = "timestamp";

    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        // Create Users table
        db.execSQL("CREATE TABLE " + TABLE_USERS + " (" +
                COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                COLUMN_USERNAME + " TEXT, " +
                COLUMN_PASSWORD + " TEXT, " +
                COLUMN_ROLE + " TEXT)");

        // Create Inventory table
        db.execSQL("CREATE TABLE " + TABLE_INVENTORY + " (" +
                COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                COLUMN_ITEM_NAME + " TEXT, " +
                COLUMN_CATEGORY + " TEXT, " +
                COLUMN_QUANTITY + " INTEGER, " +
                COLUMN_PRICE + " REAL, " +
                COLUMN_QR_CODE + " TEXT)");

        // Create Requests table
        db.execSQL("CREATE TABLE " + TABLE_REQUESTS + " (" +
                COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                COLUMN_USER_ID + " INTEGER, " +
                COLUMN_ITEM_ID + " INTEGER, " +
                COLUMN_REQUEST_TYPE + " TEXT, " +
                COLUMN_STATUS + " TEXT, " +
                COLUMN_TIMESTAMP + " TEXT)");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_USERS);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_INVENTORY);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_REQUESTS);
        onCreate(db);
    }

    // User Authentication
    public boolean authenticateUser(String username, String password, String role) {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM " + TABLE_USERS +
                        " WHERE " + COLUMN_USERNAME + "=? AND " + COLUMN_PASSWORD + "=? AND " + COLUMN_ROLE + "=?",
                new String[]{username, password, role});
        boolean isValid = cursor.moveToFirst();
        cursor.close();
        return isValid;
    }

    // CRUD Operations for Inventory
    public boolean insertItem(String name, String category, int quantity, double price, String qrCode) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COLUMN_ITEM_NAME, name);
        values.put(COLUMN_CATEGORY, category);
        values.put(COLUMN_QUANTITY, quantity);
        values.put(COLUMN_PRICE, price);
        values.put(COLUMN_QR_CODE, qrCode);
        return db.insert(TABLE_INVENTORY, null, values) != -1;
    }

    // Fetch inventory items
    public Cursor getInventory() {
        SQLiteDatabase db = this.getReadableDatabase();
        return db.rawQuery("SELECT * FROM " + TABLE_INVENTORY, null);
    }


    public boolean updateQuantity(int itemId, int newQuantity) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COLUMN_QUANTITY, newQuantity);
        return db.update(TABLE_INVENTORY, values, COLUMN_ID + "=?", new String[]{String.valueOf(itemId)}) > 0;
    }

    // Method to update the status of a request
    public boolean updateRequestStatus(int requestId, String status) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COLUMN_STATUS, status);

        int rowsAffected = db.update(TABLE_REQUESTS, values, COLUMN_ID + " = ?", new String[]{String.valueOf(requestId)});
        return rowsAffected > 0; // Returns true if at least one row was updated
    }

    public boolean updateItem(int itemId, String itemName, String category, int quantity, double price) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COLUMN_ITEM_NAME, itemName);
        values.put(COLUMN_CATEGORY, category);
        values.put(COLUMN_QUANTITY, quantity);
        values.put(COLUMN_PRICE, price);

        int rowsAffected = db.update(TABLE_INVENTORY, values, COLUMN_ITEM_ID + " = ?", new String[]{String.valueOf(itemId)});
        db.close();
        return rowsAffected > 0;
    }

    public boolean deleteItem(int itemId) {
        SQLiteDatabase db = this.getWritableDatabase();
        int rowsDeleted = db.delete(TABLE_INVENTORY, COLUMN_ITEM_ID + " = ?", new String[]{String.valueOf(itemId)});
        db.close();
        return rowsDeleted > 0;
    }

    // CRUD Operations for Requests
    public boolean insertRequest(int userId, int itemId, String requestType) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COLUMN_USER_ID, userId);
        values.put(COLUMN_ITEM_ID, itemId);
        values.put(COLUMN_REQUEST_TYPE, requestType);
        values.put(COLUMN_STATUS, "pending");
        return db.insert(TABLE_REQUESTS, null, values) != -1;
    }

    public Cursor getPendingRequests() {
        SQLiteDatabase db = this.getReadableDatabase();
        return db.rawQuery("SELECT * FROM " + TABLE_REQUESTS + " WHERE " + COLUMN_STATUS + "='pending'", null);
    }
    // Method to search for items in the inventory
    public Cursor searchItems(String query, int limit, int offset) {
        SQLiteDatabase db = this.getReadableDatabase();
        String sql = "SELECT * FROM " + TABLE_INVENTORY +
                " WHERE " + COLUMN_ITEM_NAME + " LIKE ? " +
                "LIMIT ? OFFSET ?";
        return db.rawQuery(sql, new String[]{"%" + query + "%", String.valueOf(limit), String.valueOf(offset)});
    }

    // Add a new item to the inventory
    public boolean addItem(String name, String category, int quantity, double price) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.beginTransaction();
        try {
            ContentValues values = new ContentValues();
            values.put(COLUMN_ITEM_NAME, name);
            values.put(COLUMN_CATEGORY, category);
            values.put(COLUMN_QUANTITY, quantity);
            values.put(COLUMN_PRICE, price);

            // Insert the new item into the inventory table
            long result = db.insert(TABLE_INVENTORY, null, values);
            if (result == -1) {
                return false; // Failed to insert
            }

            db.setTransactionSuccessful();
            return true; // Item added successfully
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        } finally {
            db.endTransaction();
        }
    }
}
