package com.androiddevelopment.mobile_banking;

/**Deposit class
 * Receives the current balance and deposit amount
 * Adds deposit amount to balance
 * Returns new balance
 * Created by Kat on 11/22/2016.
 */

public class Deposit {

    private double balance;
    private double dpValue;
    //set current balance
    public void setBalance(double b) {

        balance = b;

    }//end setBalance

    //set deposit value entered by user
    public void setDeposit (double dp) {

        dpValue = dp;

    }//end setDeposit
    //calculate and return new balance
    public double getNewBalance() {

        return balance + dpValue;

    }// end getNewBalance
}//end Deposit
