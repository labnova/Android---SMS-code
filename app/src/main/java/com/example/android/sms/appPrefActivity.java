package com.example.android.sms;

import android.os.Bundle;
import android.os.PersistableBundle;
import android.preference.PreferenceActivity;


public class appPrefActivity extends PreferenceActivity {

    @Override
    public void onCreate(Bundle savedInstanceState, PersistableBundle persistentState) {
        super.onCreate(savedInstanceState, persistentState);

        addPreferencesFromResource(R.xml.preference);
    }
}
