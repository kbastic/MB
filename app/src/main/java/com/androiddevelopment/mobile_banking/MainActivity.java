package com.androiddevelopment.mobile_banking;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Toast;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;



public class MainActivity extends AppCompatActivity {
    //declare vars
    String UserName, Password;
    int loginCntr = 3;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //display welcome msg when app starts
        Toast.makeText(MainActivity.this, "Welcome User. Please enter your Username and Password.", Toast.LENGTH_LONG).show();

        //reference Button, User Name and Password
        Button myButton = (Button) findViewById(R.id.loginButton);
        final EditText myUserName = (EditText) findViewById(R.id.usernameEditText);
        final EditText myPassword = (EditText) findViewById(R.id.passwordEditText);

        //register button with Event Listener class, and Event handler method
        myButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                //get user input
                UserName = myUserName.getText().toString();
                Password = myPassword.getText().toString();


                //check how many times unregistered user tried to log in with wrong credentials

                //check if username and password are correct
                if (UserName.equals("1") && Password.equals("1")) {
                    //user is registered, display menu layout and registered user msg

                    Intent myIntent = new Intent(MainActivity.this, MenuActivity.class);
                    myIntent.putExtra("stringReference", "Access Granted!");
                    //display menu activity screen
                    startActivity(myIntent);

                }//end if
                //check login attempts counter
                else if (loginCntr != 1) {
                    //unregistered user, display unregistered user msg and decrease login counter
                    loginCntr = loginCntr - 1;

                    Toast.makeText(MainActivity.this, "Access Denied! Please try again.You have " + loginCntr + " attempt(s) remaining", Toast.LENGTH_LONG).show();
                }//end else if
                else {
                    //3 login attempts are up, close app
                    Toast.makeText(MainActivity.this, "Access Denied! Closing app!", Toast.LENGTH_LONG).show();
                    finish();
                }//end else

            }//end onClick
        });//end setOnClickListener
    }//end onCreate
}//end MainActivity
