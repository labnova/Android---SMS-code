package com.example.android.sms;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.parse.LogInCallback;
import com.parse.ParseException;
import com.parse.ParseUser;
import com.parse.SignUpCallback;


public class ParseLoginSignup extends ActionBarActivity implements View.OnClickListener {

    Button loginButton;
    Button signup;
    String txtUsername;
    String txtPassword;

    EditText username;
    EditText password;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_parse_login_signup);


        username = (EditText) findViewById(R.id.edUsername);
        password = (EditText) findViewById(R.id.edPassword);

        loginButton = (Button) findViewById(R.id.login);
        signup = (Button) findViewById(R.id.signup);

        loginButton.setOnClickListener(this);
        signup.setOnClickListener(this);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_parse_login_signup, menu);
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

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.login:
               txtUsername = username.getText().toString();
               txtPassword = password.getText().toString();

                ParseUser.logInInBackground(txtUsername, txtPassword, new LogInCallback() {
                    @Override
                    public void done(ParseUser parseUser, ParseException e) {
                        if(parseUser != null) {
                            //l'utente è loggato
                            Intent intent = new Intent(ParseLoginSignup.this, MainActivity.class);
                            startActivity(intent);
                            finish();
                        } else {
                            Toast.makeText(getApplicationContext(),
                                    "questo username non esiste", Toast.LENGTH_SHORT).show();
                        }
                    }
                });

            break;

            case R.id.signup:
                txtUsername = username.getText().toString();
                txtPassword = password.getText().toString();

                if(txtUsername.equals("") || txtPassword.equals("")) {
                    Toast.makeText(getApplicationContext(),
                            "Prego completare il signup", Toast.LENGTH_SHORT).show();
                } else {
                    ParseUser user = new ParseUser();
                    user.setUsername(txtUsername);
                    user.setPassword(txtPassword);
                    user.signUpInBackground(new SignUpCallback() {
                        @Override
                        public void done(ParseException e) {
                            if(e == null) {
                                Toast.makeText(getApplicationContext(),
                                        "LOGGATO!", Toast.LENGTH_SHORT).show();
                            } else {
                                Toast.makeText(getApplicationContext(),
                                        "SIGNUP ERROR!", Toast.LENGTH_SHORT).show();
                            }
                        }
                    });
                }
                break;

            default: return;
        }
    }
}
