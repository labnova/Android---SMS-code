package com.example.android.sms;


import android.app.Application;

import com.parse.Parse;
import com.parse.ParseACL;
import com.parse.ParseUser;

public class ParseApp extends Application {

    @Override
    public void onCreate() {
        super.onCreate();

        Parse.initialize(this, "pdzoAVWFSUZIhYcNTQY3n2s3JOlpEFQA42Ev1ato", "XHgY24fXixiMm1LVIdmOhdgfM3LKezmyz3ztqlZV");


        ParseUser.enableAutomaticUser();

        ParseACL defaulACL = new ParseACL();
        defaulACL.setPublicReadAccess(true);

        ParseACL.setDefaultACL(defaulACL, true);


    }





}
