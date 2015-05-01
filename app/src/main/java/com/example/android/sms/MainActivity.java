package com.example.android.sms;

import android.app.Activity;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.telephony.SmsManager;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.parse.ParseAnalytics;
import com.parse.ParseAnonymousUtils;
import com.parse.ParseInstallation;
import com.parse.ParseUser;
import com.parse.PushService;


public class MainActivity extends ActionBarActivity {
    String SENT = "SMS_SENT";
    String DELIVERED = "SMS_DELIVERED";
    PendingIntent sentPI, deliveredPI;
    BroadcastReceiver smsSentReceiver, smsDeliveredReceiver;
    IntentFilter intentFilter;

    private BroadcastReceiver intentReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            //visualizzare l'SMS ricevuto nella TextView
            TextView SMSes = (TextView) findViewById(R.id.textView1);
            SMSes.setText(intent.getExtras().getString("sms"));
        }
    };


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ParseAnalytics.trackAppOpenedInBackground(getIntent());

        PushService.setDefaultPushCallback(this, MainActivity.class);
        ParseInstallation.getCurrentInstallation().saveInBackground();

        if(ParseAnonymousUtils.isLinked(ParseUser.getCurrentUser())) {
            Intent intent = new Intent(this, ParseLoginSignup.class);
            startActivity(intent);
            finish();

        } else {

            ParseUser currentUser = ParseUser.getCurrentUser();
            if(currentUser == null) {
                Intent intent = new Intent(this, ParseLoginSignup.class);
                startActivity(intent);
                finish();
            }

        }

        sentPI = PendingIntent.getBroadcast(this,0, new Intent(SENT), 0);
        deliveredPI = PendingIntent.getBroadcast(this,0, new Intent(DELIVERED), 0);

        //intent filter programmatico per filtrare l'SMS ricevuto
        intentFilter = new IntentFilter();
        intentFilter.addAction("SMS_RECEIVED_ACTION");


    }

    @Override
    protected void onResume() {
        super.onResume();

        //registrare il receiver
        registerReceiver(intentReceiver, intentFilter);

        //creare il BroadcastReceiver quando l'SMS è stato spedito
        smsSentReceiver = new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {
                switch(getResultCode()) {
                    case Activity.RESULT_OK:
                        Toast.makeText(getBaseContext(), "SMS sent", Toast.LENGTH_SHORT).show();
                    break;
                    case SmsManager.RESULT_ERROR_GENERIC_FAILURE:
                        Toast.makeText(getBaseContext(), "Generic Failure", Toast.LENGTH_SHORT).show();
                    break;
                    case SmsManager.RESULT_ERROR_NO_SERVICE:
                        Toast.makeText(getBaseContext(), "No service", Toast.LENGTH_SHORT).show();
                    break;
                    case SmsManager.RESULT_ERROR_NULL_PDU:
                        Toast.makeText(getBaseContext(), "Null PDU", Toast.LENGTH_SHORT).show();
                    break;
                    case SmsManager.RESULT_ERROR_RADIO_OFF:
                        Toast.makeText(getBaseContext(), "Radio Off", Toast.LENGTH_SHORT).show();
                        break;
                }
            }
        };

        //creare il BroadcastReceiver quando l'SMS è consegnato
        smsDeliveredReceiver = new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {
                switch(getResultCode()) {
                    case Activity.RESULT_OK:
                        Toast.makeText(getBaseContext(), "SMS consegnato", Toast.LENGTH_SHORT ).show();
                    break;
                    case Activity.RESULT_CANCELED:
                        Toast.makeText(getBaseContext(), "SMS not delivered", Toast.LENGTH_SHORT).show();
                    break;

                }
            }
        };

        //registrare i due BroadcastReceivers
        registerReceiver(smsDeliveredReceiver, new IntentFilter(DELIVERED));
        registerReceiver(smsSentReceiver, new IntentFilter(SENT));
    }


    public void onClickLoad(View view) {
        Intent i = new Intent("com.example.android.sms.appPrefActivity");
        startActivity(i);
    }

    @Override
    protected void onPause() {
        super.onPause();

        //registrare il receiver
        registerReceiver(intentReceiver, intentFilter);

        //de-registrare i due BroadcastReceivers
        unregisterReceiver(smsSentReceiver);
        unregisterReceiver(smsDeliveredReceiver);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();

        //deregistrare il receiver
      //  unregisterReceiver(intentReceiver);
    }

    public void onClick(View v) {
        sendSMS("5556", "Ciao a tutti dal primo emulatore!");
    }


    private void sendSMS(String phoneNumber, String message) {
        SmsManager sms = SmsManager.getDefault();
        sms.sendTextMessage(phoneNumber, null, message, null, null);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
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
