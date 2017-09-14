package com.androiddevelopment.mobile_banking;

import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

public class MenuActivity extends AppCompatActivity {

    //declare constants used with shared preferences
    public static final String MY_BALANCE = "My_Balance";
    public static final String CHECKING_KEY = "checking_key";
    public static final String SAVINGS_KEY = "savings_key";
    //declare variables for message, checking and savings balance
    String receivedString;
    public String chkBalance, savBalance;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);

        Bundle extras = getIntent().getExtras();
        //receive welcome msg from MainActivity
            if (extras != null) {
                receivedString = extras.getString("stringReference");
                Toast.makeText(MenuActivity.this, receivedString, Toast.LENGTH_LONG).show();
            }//end if

        //read checking and savings balances from shared preferences file
        getPrefs();

       //declare menu buttons
        Button checking_BT = (Button) findViewById(R.id.checkingButton);
        Button savings_BT = (Button) findViewById(R.id.savingsButton);
        Button transfer_BT = (Button) findViewById(R.id.transferButton);

       //register checking button with Event Listener class, and Event handler method
        checking_BT.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                 //user wants to access checking account
                 Intent checkingIntent = new Intent(MenuActivity.this, TransactionActivity.class);
                 //send only data related to checking account
                 checkingIntent.putExtra("balance", chkBalance); //checking balance
                 checkingIntent.putExtra("key", CHECKING_KEY); //key used to store checking balance
                 checkingIntent.putExtra("title", "Checking Account"); //title for transaction activity
                 //display transaction activity screen
                 startActivity(checkingIntent);

            }
        });//end OnClickListener checking

        //register savings button with Event Listener class, and Event handler method
        savings_BT.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //user wants to access savings account
                Intent savingsIntent = new Intent(MenuActivity.this, TransactionActivity.class);
                //send only data related to savings account
                savingsIntent.putExtra("balance", savBalance); //savings balance
                savingsIntent.putExtra("key", SAVINGS_KEY); //key used to store savings balance
                savingsIntent.putExtra("title", "Savings Account"); //title for transaction activity
                //display transaction activity screen
                startActivity(savingsIntent);

            }
        });//end OnClickListener savings

        //register transfer button with Event Listener class, and Event handler method
        transfer_BT.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //user wants to transfer funds
                Intent transferIntent = new Intent(MenuActivity.this, TransferActivity.class);
                //send both balances
                transferIntent.putExtra("balanceC", chkBalance); //checking balance
                transferIntent.putExtra("balanceS", savBalance); //savings balance
                //display transfer activity screen
                startActivity(transferIntent);

            }
        });//end OnClickListener transfer
    }//end onCreate

    //function to retrieve current balances when program resumes
    protected void onResume() {
        super.onResume();
        getPrefs();

    }//end onResume
    //function to open shared preferences and retrieve current balances
    public void getPrefs() {
        //open shared preferences xml file
        SharedPreferences BalancePref = getSharedPreferences(MenuActivity.MY_BALANCE, MODE_PRIVATE);
        //retrieve checking and savings balances if they are not null
        //or set balances to default value if they are null
        chkBalance = BalancePref.getString(CHECKING_KEY, "5000.00");
        savBalance = BalancePref.getString(SAVINGS_KEY, "7000.00");

    }//end getPrefs
}//end MenuActivity
