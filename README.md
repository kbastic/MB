# MB
Mobile Banking (MB) is an android app for simple bank transactions

## Current Implementation
Mobile banking (MB) app allows a customer to log in to his account, check his current checking and savings balance, deposit or withdraw money from both accounts and transfer funds from one account to another. 
  
Customer must provide a valid username and password to log into his accounts. Upon successful login, the customer is redirected to the menu screen where he can choose to proceed to Checking Account, Savings Account or Transfer Funds screens. Checking and Savings screens enable the user to see his current checking or savings balance and deposit or withdraw money from his checking or savings account. Transfer screen enables the user to choose between transferring money from checking to a savings account or vice versa. 

Each screen uses a java class to handle the programming logic. Java classes include all necessary conditions to check the validity of transactions. For example, the user can’t withdraw more money than it’s available in the account.

Mobile Banking app keeps track of customer’s balance by utilizing Android Shared Preferences file. Each time the user makes a transaction, shared preferences file is updated. When the user quits the application and logs in again, his previous balances are retrieved from shared preferences file.

Mobile banking app security is implemented by requiring a customer to provide valid username and password. After three unsuccessful logins, the application shuts down. 

Mobile Banking app is created with Android Studio to run on all Android phones with at least Android 4.03 (ice cream sandwich) installed.

## Screenshots

## Pseudocode


### MainActivity Class

```
function onCreate():
SET Counter TO 3 attempts
  WHEN User CLICKS Login button CALL onClick()
     function OnClick():
     GET Username and Password
                  IF Username is EQUAL to 1 AND Password is EQUAL to 1 THEN
		          CALL MenuActivity class
		          SEND welcome message
                  ENDIF
		  ELSE IF counter is GREATER OR EQUAL to 1 THEN
		          DECREMENT counter BY 1
		          DISPLAY access denied message and SHOW counter remaining
		  END ELSEIF
                  ELSE counter ran out THEN
		          DISPLAY closing app message
	                      CLOSE application
	     END ELSE
      END onClick()
END onCreate()
```


### MenuActivity Class

```

function onCreate():
IF message FROM MainActivity was sent THEN
         RECEIVE message  
         DISPLAY message
ENDIF
CALL getPref() to GET balances
     WHEN User CLICKS Checking button CALL onClick()
	    function OnClick():
	             CALL TransactionActivity()
		 SEND checking balance from shared preferences file
		 SEND checking key from shared preferences file
		 SEND checking account title
	    END onClick
     WHEN User CLICKS Savings button CALL onClick()
	    function OnClick():
	             CALL TransactionActivity()
		 SEND savings balance from shared preferences file
		 SEND savings key from shared preferences file
		 SEND savings account title
	    END onClick
    WHEN User CLICKS Transfer button CALL onClick()
	    function OnClick():
	             CALL TransactionActivity()
		 SEND checking balance from shared preferences file
		 SEND savings balance from shared preferences file
	    END onClick
END onCreate

function onResume():
    CALL getPref()
END onResume


function getPrefs(): 
    OPEN shared preferences xml file
    GET checking balance
    GET savings balance
END getPref

```

### TransactionActivity Class

```

function onCreate():
IF balances FROM MenuActivity was sent THEN
         RECEIVE balance, key and title
ENDIF

DISPLAY layout title
DISPLAY balance

WHEN User CLICKS Deposit button CALL onClick()
	    function OnClick():
	       IF User input IS NOT empty THEN
              GET deposit amount from User deposit input field
              CREATE Deposit object
			  SET balance
			  SET deposit amount
			  GET new balance
			  REPLACE current balance with new balance
			  DISPLAY new balance
			  RESET deposit amount to 0
           ENDIF
		   
		   ELSE
			  DISPLAY message prompting user for input
			END ELSE
	     CLEAR User deposit input field
	    END onClick
		
WHEN User CLICKS Withdraw button CALL onClick()
	    function OnClick():
		   IF User input IS NOT empty THEN
		      GET withdraw amount from User withdraw input field
			  IF balance is GREATER or EQUAL to withdraw amount THEN 
                                             CREATE Withdraw object
			         SET balance
			         SET withdraw amount
			         GET new balance
			  
			         REPLACE current balance with new balance
			         DISPLAY new balance
			  
			         RESET withdraw amount to 0
			   ENDIF
			   ELSE 
			         DISPLAY insufficient funds to user
		               END ELSE
			
		    ELSE
		         DISPLAY message prompting user for input
		    END ELSE
	     CLEAR User withdraw input field
	    END onClick

END onCreate

function onPause():

    OPEN shared preferences xml file for editing
    SAVE new checking or savings balance
	
END onPause
TransferActivity Class

function onCreate():
IF balances FROM MenuActivity was sent THEN
         RECEIVE checking and savings balance
ENDIF
DISPLAY checking balance
DISPLAY savings balance
WHEN User CLICKS Transfer button CALL onClick()
	    function OnClick():
		  IF User input IS NOT empty THEN
		             GET transfer amount from User input field
			 GET selected transfer choice
			 SWITCH between transfer choices
			   CASE transfer from checking to savings
			       IF checking balance is GREATER or EQUAL to transfer amount THEN 
                                                CREATE Withdraw object
			            SET checking balance
			            SET withdraw amount
			            GET new checking balance
				REPLACE current checking balance with new checking balance
			            DISPLAY new checking balance
				CREATE Deposit object
			            SET savings balance
			            SET deposit amount
			            GET new savings balance
				REPLACE current savings balance with new savings balance
			            DISPLAY new savings balance
			            RESET transfer amount to 0
			        ENDIF
			        ELSE 
			            CALL noFundsMsg()
		                    END ELSE
		
	                                CLEAR User input field
			   END CASE
			   
			   CASE transfer from savings to checking
			        IF savings balance is GREATER or EQUAL to transfer amount THEN 
                                                   CREATE Withdraw object
			               SET savings balance
			               SET withdraw amount
			               GET new savings balance
				   REPLACE current savings balance with new savings balance
			               DISPLAY new savings balance
				   CREATE Deposit object
			               SET checking balance
			               SET deposit amount
			               GET new checking balance
				   REPLACE current checking balance with new checking balance
			               DISPLAY new checking balance
			               RESET transfer amount to 0
			        ENDIF
			        ELSE 
			               CALL noFundsMsg();
		                     END ELSE
		
	                                CLEAR User input field
			   END CASE
			   
			  END SWITCH
		  ENDIF
		  ELSE
			  CALL noAmountMsg()
		  END ELSE
		END onClick
END onCreate


function noFundsMsg():
    DISPLAY Insufficient funds message 
END noFundsMsg

function noAmountMsg():
    DISPLAY message prompting user for input
END noAmountMsg

function onPause():
            OPEN shared preferences xml file for editing
	SAVE new checking balance
	SAVE new savings balance
END onPause

```

### Deposit Class

```

function setBalance():
       SET balance for calculation
END setBalance

function setDeposit():
       SET deposit amount for calculation
END setDeposit

function getNewBalance():
       ADD deposit amount to balance
       RETURN new balance
END getNewBalance

```

### Withdraw Class

```

function setBalance():
       SET balance for calculation
END setBalance

function setWithdraw():
       SET withdraw amount for calculation
END setDeposit

function getNewBalance():
       SUBTRACT withdraw amount from balance
       RETURN new balance
END getNewBalance

```



