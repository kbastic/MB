package com.androiddevelopment.mobile_banking;

import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.text.DecimalFormat;

public class TransferActivity extends AppCompatActivity {

    //declare constants used with shared preferences
    public static final String MY_BALANCE = "My_Balance";
    public static final String CHECKING_KEY = "checking_key";
    public static final String SAVINGS_KEY = "savings_key";
    //declare variables
    public String receivedBalanceC, receivedBalanceS; //data received from menu activity
    public DecimalFormat currency = new DecimalFormat("$###,##0.00"); //decimal formatting
    TextView cBalanceTV, sBalanceTV;
    public double cBalanceD, sBalanceD, cNewBalance, sNewBalance;
    public double TransferEntered;
    int transferChoice; //spinner index


    SharedPreferences.Editor myEditor;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_transfer);

        //receive checking and savings balance from menu activity
        Bundle extras = getIntent().getExtras();

        if (extras != null) {
            receivedBalanceC = extras.getString("balanceC");
            receivedBalanceS = extras.getString("balanceS");


        }//end if
        //set current balance of checking account
        cBalanceTV = (TextView) findViewById(R.id.cBalanceTextView);
        cBalanceD = Double.parseDouble(String.valueOf(receivedBalanceC));
        cBalanceTV.setText(String.valueOf(currency.format(cBalanceD)));

        //set current balance of savings account
        sBalanceTV = (TextView) findViewById(R.id.sBalanceTextView);
        sBalanceD = Double.parseDouble(String.valueOf(receivedBalanceS));
        sBalanceTV.setText(String.valueOf(currency.format(sBalanceD)));


        //declare transfer input amount, Spinner, and Button
        final EditText TransferET = (EditText) findViewById(R.id.TransferEditText);
        final Spinner TransferS = (Spinner) findViewById(R.id.TransferSpinner);
        Button transferB= (Button) findViewById(R.id.TransferButton);

        //register transfer button with Event Listener class, and Event handler method
        transferB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                //check if transfer amount was entered
                if (!TextUtils.isEmpty(TransferET.getText())) {

                    TransferEntered = Double.parseDouble(String.valueOf(TransferET.getText()));
                    //get index of spinner string array
                    transferChoice = TransferS.getSelectedItemPosition();
                    //choose between two available transfer options
                    switch (transferChoice) {
                     //transfer funds from checking to savings
                     case 0:

                            //check if transfer amount is valid
                             if (cBalanceD >= TransferEntered) {

                                 //withdraw from checking
                                 Withdraw wd = new Withdraw();
                                 wd.setBalance(cBalanceD);
                                 wd.setWithdraw(TransferEntered);
                                 //set new checking balance
                                 cNewBalance = wd.getNewBalance();

                                 cBalanceTV.setText(String.valueOf(currency.format(cNewBalance)));
                                 cBalanceD = cNewBalance;

                                 //deposit to savings
                                 Deposit dp = new Deposit();
                                 dp.setBalance(sBalanceD);
                                 dp.setDeposit(TransferEntered);

                                 //set new savings balance
                                 sNewBalance = dp.getNewBalance();

                                 sBalanceTV.setText(String.valueOf(currency.format(sNewBalance)));
                                 sBalanceD = sNewBalance;

                                 //reset transfer amount
                                 TransferEntered = 0;
                             }//end checking if transfer is valid
                             //transfer amount is not valid
                             else {
                                 //send msg insufficient funds
                                 noFundsMsg();
                             }//end transfer is not valid msg
                         return;

                     //transfer funds from savings  to checking
                     case 1:

                             //check if transfer amount is valid
                             if (sBalanceD >= TransferEntered) {

                                     //withdraw from savings
                                     Withdraw wd = new Withdraw();
                                     wd.setBalance(sBalanceD);
                                     wd.setWithdraw(TransferEntered);
                                     //set new savings balance
                                     sNewBalance = wd.getNewBalance();

                                     sBalanceTV.setText(String.valueOf(currency.format(sNewBalance)));
                                     sBalanceD = sNewBalance;

                                     //deposit to checking
                                     Deposit dp = new Deposit();
                                     dp.setBalance(cBalanceD);
                                     dp.setDeposit(TransferEntered);

                                     //set new checking balance
                                     cNewBalance = dp.getNewBalance();

                                     cBalanceTV.setText(String.valueOf(currency.format(cNewBalance)));
                                     cBalanceD = cNewBalance;

                                     //reset transfer amount
                                     TransferEntered = 0;
                                 }////end checking if transfer is valid
                             //transfer amount is not valid
                             else {

                                 //send msg insufficient funds
                                 noFundsMsg();
                             }//end transfer is not valid msg

                         return;
                 }//end switch transferChoice
                }//end check if transfer amount was entered
                //user didn't enter transfer amount
                else {

                    //send msg no amount entered
                    noAmountMsg();
                }//end transfer amount was not entered msg
            }//end if
        });//end onClickListener TransferButton
    }//end onCreate

    //function to open and edit shared preferences file
    protected void onPause() {

        super.onPause();
        //open shared preferences xml file
        myEditor = getSharedPreferences(MY_BALANCE, MODE_PRIVATE).edit();

        //save new checking and savings balance
        myEditor.putString(CHECKING_KEY, String.valueOf(cBalanceD));
        myEditor.putString(SAVINGS_KEY, String.valueOf(sBalanceD));
        myEditor.apply();

    }//end onPause
    //function to display insufficient funds message
    public  void noFundsMsg() {

        Toast.makeText(TransferActivity.this, "Insufficient funds! Please enter a valid transfer amount and try again!", Toast.LENGTH_LONG).show();
    }//end noFundsMsg
    //function to prompt user for input
    public  void noAmountMsg() {

        Toast.makeText(TransferActivity.this, "Nothing entered! Please enter transfer amount and try again!", Toast.LENGTH_LONG).show();
    }//noAmountMsg

}//end TransferActivity
