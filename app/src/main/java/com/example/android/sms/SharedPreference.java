package com.example.android.sms;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;


public class SharedPreference extends ActionBarActivity {

    private static final String MY_DB = "my_db";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shared_preference);

        SharedPreferences sP = getSharedPreferences(MY_DB, Context.MODE_PRIVATE);
        SharedPreferences.Editor e = sP.edit();
        e.putString("strKey","Ciao World!");
        e.putBoolean("boolKey", true);
        e.commit();
        String stringKey = sP.getString("strKey", "error");
        boolean boolValue = sP.getBoolean("boolKey", false);
        Log.i("LOG TAG", "String value " + stringKey);
        Log.i("LOG TAG", "Boolean value " +boolValue);

        boolean hasVisite = sP.getBoolean("hasVisite", false);
        if(!hasVisite) {
            //mostra splashActivity di benvenuto

            e.putBoolean("hasVisite", true);
            e.commit();
        }

        long lastTimeUpdate = sP.getLong("lastUpdateKey", 0L);
        long timeElapsed = System.currentTimeMillis() - lastTimeUpdate;
        final long UPDATE_FREQ = 1000 * 60 * 60 * 24;

        if(timeElapsed > UPDATE_FREQ) {
            //CAMBIA OPERAZIONE
        }

       e.putLong("lastUpdateKey", System.currentTimeMillis());
        e.commit();

    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_shared_preference, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
