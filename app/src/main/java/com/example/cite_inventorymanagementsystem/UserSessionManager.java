package com.example.cite_inventorymanagementsystem;

import android.content.Context;
import android.content.SharedPreferences;

public class UserSessionManager {

    private static final String PREF_NAME = "UserSession";
    private static final String KEY_IS_LOGGED_IN = "isLoggedIn";
    private static final String KEY_USERNAME = "username";
    private static final String KEY_ROLE = "role";

    private SharedPreferences sharedPreferences;
    private SharedPreferences.Editor editor;

    public UserSessionManager(Context context) {
        sharedPreferences = context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE);
        editor = sharedPreferences.edit();
    }

    // Method to check if the user is logged in
    public boolean isUserLoggedIn() {
        return sharedPreferences.getBoolean(KEY_IS_LOGGED_IN, false);
    }

    // Method to create a login session
    public void createLoginSession(String username, String role) {
        editor.putBoolean(KEY_IS_LOGGED_IN, true);
        editor.putString(KEY_USERNAME, username);
        editor.putString(KEY_ROLE, role);
        editor.apply();
    }

    // Method to retrieve the logged-in user's username
    public String getUsername() {
        return sharedPreferences.getString(KEY_USERNAME, null);
    }

    // Method to retrieve the logged-in user's role
    public String getRole() {
        return sharedPreferences.getString(KEY_ROLE, null);
    }

    // Method to handle user logout
    public void logoutUser() {
        editor.clear();
        editor.apply();
    }
}
