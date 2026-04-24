package com.example.tatwa10;

import android.content.Context;
import android.content.SharedPreferences;

public class SharedPrefManager {

    private static SharedPreferences prefs;

    public static void init(Context context) {
        prefs = context.getSharedPreferences("hospital_app", Context.MODE_PRIVATE);
    }

    public static void savePatientId(int id) {
        prefs.edit().putInt("patient_id", id).apply();
    }

    public static int getPatientId() {
        return prefs.getInt("patient_id", -1);
    }
}