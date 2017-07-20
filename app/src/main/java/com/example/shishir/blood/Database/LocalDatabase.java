package com.example.shishir.blood.Database;

import android.content.Context;
import android.content.SharedPreferences;

/**
 * Created by Shishir on 7/14/2017.
 */

public class LocalDatabase {
    SharedPreferences sharedPreferences;
    SharedPreferences.Editor editor;

    public LocalDatabase(Context context) {
        sharedPreferences = context.getSharedPreferences("MY_PREFER", Context.MODE_PRIVATE);
        editor = sharedPreferences.edit();
    }

    public boolean isPhoneNumberMatched(String recoveryNumber) {
        if (sharedPreferences.getString("recN", "").equals(recoveryNumber))
            return true;
        else
            return false;
    }

    public String getPassword() {
        return sharedPreferences.getString("password", "");
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
}
