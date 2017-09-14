package com.androiddevelopment.mobile_banking;

import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.text.DecimalFormat;
//TransactionActivity is used for both, checking and savings accounts
public class TransactionActivity extends AppCompatActivity {
    //declare constant of shared preferences file
    public static final String MY_BALANCE = "My_Balance";

    //declare variables
    public String receivedBalance, receivedKey, receivedTitle; //data received from menu activity
    public double BalanceD;
    public double DepositEntered, NewBalance, WithdrawEntered;
    TextView BalanceTV, TitleTV;
    public DecimalFormat currency = new DecimalFormat("$###,##0.00"); //decimal formatting
    SharedPreferences.Editor myEditor;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_transaction);


        //receive balance, key and title from menu activity
        Bundle extras = getIntent().getExtras();

        if (extras != null) {
            receivedBalance = extras.getString("balance");
            receivedKey = extras.getString("key");
            receivedTitle = extras.getString("title");

        }//end if
        //set layout title depending on data that was received to checking or savings account
        TitleTV = (TextView) findViewById(R.id.titleTextView);
        TitleTV.setText(receivedTitle);

        //set current balance of checking or savings account
        BalanceTV = (TextView) findViewById(R.id.BalanceTextView);
        BalanceD = Double.parseDouble(String.valueOf(receivedBalance));
        BalanceTV.setText(String.valueOf(currency.format(BalanceD)));

        //declare deposit button
        Button DepositB = (Button) findViewById(R.id.DepositButton);
        //declare user deposit input amount
        final EditText DepositET = (EditText) findViewById(R.id.DepositEditText);

        //register deposit button with Event Listener class, and Event handler method
        DepositB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //if deposit field is not empty, get deposit amount
                if (!TextUtils.isEmpty(DepositET.getText())) {
                    DepositEntered = Double.parseDouble(String.valueOf(DepositET.getText()));
                    //create deposit object
                    Deposit dp = new Deposit();
                    dp.setBalance(BalanceD);
                    dp.setDeposit(DepositEntered);

                    //calculate new balance
                    NewBalance = dp.getNewBalance();

                    BalanceTV.setText(String.valueOf(currency.format(NewBalance)));
                    BalanceD = NewBalance;
                    //reset user deposit amount to zero
                    DepositEntered = 0;
                }//end if
                //deposit filed is empty, prompt user to enter deposit amount
                else {

                    Toast.makeText(TransactionActivity.this, "Please enter deposit amount and try again!", Toast.LENGTH_LONG).show();
                }//end else
                //clear deposit field
                DepositET.setText(null);
            }
        });//end DepositB OnClick

        //declare withdraw button
        Button WithdrawB = (Button) findViewById(R.id.WithdrawButton);
        //declare user withdraw input amount
        final EditText WithdrawET = (EditText) findViewById(R.id.WithdrawEditText);

        //register withdraw button with Event Listener class, and Event handler method
        WithdrawB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //if withdraw field is not empty, get withdraw amount
                if (!TextUtils.isEmpty(WithdrawET.getText())) {

                    WithdrawEntered = Double.parseDouble(String.valueOf(WithdrawET.getText()));

                    //check if there's enough money to withdraw in the acoount
                    if (BalanceD >= WithdrawEntered) {
                        //create withdraw object
                        Withdraw wd = new Withdraw();
                        wd.setBalance(BalanceD);
                        wd.setWithdraw(WithdrawEntered);

                        //calculate new balance
                        NewBalance = wd.getNewBalance();

                        BalanceTV.setText(String.valueOf(currency.format(NewBalance)));
                        BalanceD = NewBalance;
                        //reset user withdraw amount to zero
                        WithdrawEntered = 0;
                    }//end 2nd if
                    //there's not enough money in the account, prompt user for valid input
                    else
                        Toast.makeText(TransactionActivity.this, "Insufficient funds! Please enter a valid withdraw amount and try again!", Toast.LENGTH_LONG).show();
                }//end 1st if
                //withdraw filed is empty, prompt user to enter withdraw amount
                else {
                    Toast.makeText(TransactionActivity.this, "Nothing entered! Please enter withdraw amount and try again!", Toast.LENGTH_LONG).show();
                }

                //clear withdraw field
                WithdrawET.setText(null);
            }
        });//end Withdraw onClick




    }//end onCreate
    //function to open and edit shared preferences file
    protected void onPause() {

        super.onPause();
        //open shared preferences xml file
        myEditor = getSharedPreferences(MY_BALANCE, MODE_PRIVATE).edit();

        //save new checking or savings balance
        myEditor.putString(receivedKey, String.valueOf(BalanceD));
        myEditor.apply();

    }//end onPause

}//end  TransactionActivity



