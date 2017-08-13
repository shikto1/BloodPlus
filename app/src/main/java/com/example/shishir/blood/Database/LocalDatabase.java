package com.example.shishir.blood.Database;

import android.content.Context;
import android.content.SharedPreferences;

/**
 * Created by Shishir on 7/14/2017.
 */

public class LocalDatabase {
    SharedPreferences sharedPreferences;
    SharedPreferences.Editor editor;
    private int admin;

    public LocalDatabase(Context context) {
        sharedPreferences = context.getSharedPreferences("MY_PREFER", Context.MODE_PRIVATE);
        editor = sharedPreferences.edit();
    }


    public String getUserName() {
        return sharedPreferences.getString("userName", "");
    }

    public void setUserName(String name) {
        editor.putString("userName", name).commit();
    }

    public void setUserBloodGroup(String bloodGroup) {
        editor.putString("userBlood", bloodGroup).commit();
    }

    public String getUserBloodGroup() {
        return sharedPreferences.getString("userBlood", "");
    }


    public void setSelectedBloodGrop(String selectedBlood) {
        editor.putString("selectedBlood", selectedBlood);
        editor.commit();
    }

    public void setSelectedLocation(String selectedLocation) {
        editor.putString("selectedLocation", selectedLocation).commit();
    }

    public String getSelectedBloodGrop() {
        return sharedPreferences.getString("selectedBlood", "");
    }

    public String getSelectedLocation() {
        return sharedPreferences.getString("selectedLocation", "");
    }

    public void setLoggedIn(boolean log) {
        editor.putBoolean("loggedIn", log).commit();
    }

    public boolean getLoggedIn() {
        return sharedPreferences.getBoolean("loggedIn", false);
    }

    public void setAdmin(int admin) {
        editor.putInt("admin", admin).commit();
    }

    public boolean getAdmin() {
        int i = sharedPreferences.getInt("admin", 0);
        return i == 1;
    }
}
