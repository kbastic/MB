package com.androiddevelopment.mobile_banking;

/**Withdraw class
 * Receives the current balance and withdraw amount
 * Subtracts withdraw amount from balance
 * Returns new balance
 * Created by Kat on 11/22/2016.
 */

public class Withdraw {

    private double balance;
    private double wdValue;

    //set current balance
    public void setBalance(double b) {

        balance = b;

    }//end setBalance
    //set withdraw value entered by user
    public void setWithdraw (double wd) {


        wdValue = wd;
    }//end setWithdraw
    //calculate and return new balance
    public double getNewBalance() {

        return balance - wdValue;


    }//end getNewBalance
}//end Withdraw
