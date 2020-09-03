# Module 

## Mobile API Calls Issuing Sequence

Issuing a card for a new user has 3 major steps

 1. Phone Verification
 2. User Creation
 3. Oauth
 4. Agreement & Issue Card

## 1 - Phone Verification

### Start Verification

The first step is to verify the user's primary authentication factor. The examples will be held for Phone because is selected by default.

First we should call the start Phone Verification method

```kotlin
val phoneNumber = PhoneNumber(countryCode, number)
aptoPlatform.startPhoneVerification(phoneNumber) { result ->
	result.either({
		// handle failure
  	}, { verification ->
		if (verification.status == VerificationStatus.PENDING) {
        	// The code has been sent in an SMS to the user
		}
    })
}
```
### Finish Verification

We should ask the user to enter in a screen the SMS that has been sent to the phone number provided

```kotlin
verification.secret = smsCode
aptoPlatform.completeVerification(verification) { result ->
	result.either({
		// handle failure
	}, { verification ->
		if (verification.status == VerificationStatus.PASSED) {
	    	// New user verification has completed, we are ready to create user
            // The following datapoint will be used in user creation
        	val phoneDataPoint = PhoneDataPoint(verification, phoneNumber)
		} else {
			// Show Wrong code error
		}
    })
}
```

## 2 - User Creation

To create a user you need to provide a data point list including at least:
 - PhoneNumber (verified)
 - PersonalName (first name, last name)
 - Address
 - Email
 - Date Of Birth

but you can also include any other data available in the DataPoint.Type enum

```kotlin
val dataPointList = DataPointList()
// create and add all the datapoint that you have/collected from the user (including the 2FA)
dataPointList.add(phoneDataPoint)

AptoPlatform.createUser(dataPointList) { result ->
    result.either({
		// handle failure
	}, { user ->
		// user has been created
    })
}
```

## 3 - Oauth

### Fetch Card Products

Card are issued under specific card products, this method returns all the available card products in your program

```kotlin
aptoPlatformProtocol.fetchCardProducts { result ->
	result.either({
		// failure
	}, { cardProductList ->
		when (cardProductList.size) {
			0 ->  {} // Error
			1 -> {
				val cardProductId = cardProductList[0].id
			}
			2 ->  {} // Show screen to select the card product
		}
    })
}
```

### Apply to card
With the cardProductId collected in the previous step, you should apply to a card.
The API will guide you in the process from now on, for that reason you'll get in the cardApplication the next action
that you have to perform. The list of possible actions is:

- SELECT_BALANCE_STORE
- ISSUE_CARD
- SHOW_DISCLAIMER
- COLLECT_USER_DATA

```kotlin
val cardProduct = CardProduct(cardProductId)
aptoPlatform.applyToCard(cardProduct) { result ->
	result.either({
		// handle failure
	}, { cardApplication ->
		// In this step we have successfully applied to a card.
		// We should check the type of cardApplication.nextAction to know what is the next step
    })
 }
```

By default the next step in the cardApplication would be SELECT_BALANCE_STORE as long as all the required User Datapoints
are set in the User Creation Step. If there is any required Datapoint that is missing, the action type would be COLLECT_USER_DATA


### Oauth

For this step we should start an OauthAttempt, and pass the control to a webView started with the url provided in this step.
When the user finish the oauth, the oauth server must call the Apto OAuth callback endpoint (/v1/oauth/callback) 
following the OAuth2 standards (passing the token and secret, or the error message if any).
Following that action, the oauth status should be checked and if it passed proceed to the next step that is
setting the balance store with the returned oauth token id.
Finally we should check the card application status to see which is the next step.

```kotlin
val allowedBalanceType = cardApplication.nextAction.actionConfiguration.allowedBalanceTypes.first()
AptoPlatform.startOauthAuthentication(allowedBalanceType) { result ->
    result.either({
        // handle failure
    }, { oauthAttempt ->
            val url = oauthAttempt.url
           // Start the oauth attempt in a separate screen
    })
}
```

When there is a redirection to the following URL "apto-sdk://oauth-finish" means that the oauth has ended
and the state of the oauth should be checked, the list of possible states is:
- PASSED
- FAILED

```kotlin
AptoPlatform.verifyOauthAttemptStatus(oauthAttempt) { result ->
    result.either({
        // handle failure
    }, { oauthAttempt ->
        // Verify that the state is PASSED
    })
}
```

After the verification has passed you should call setBalanceStore and check the result
```kotlin
aptoPlatformProtocol.setBalanceStore(cardApplicationId, oauthAttempt.tokenId) { result ->
    result.either({
        // handle failure
    }, { selectBalanceStoreResult ->
           // selectBalanceStoreResult.result should be VALID
    })
}
```

After this, we should update our current cardApplication and check which is the next step defined in the card application.

```kotlin
AptoPlatform.fetchCardApplicationStatus(cardApplication.id) { result ->
  result.either({
		// failure
	}, {
		// check the next step, at this point the result would be SHOW_DISCLAIMER
    })
}
```

## 4 - Agreement & Issue Card

### Accept Disclaimer

To continue the process is mandatory to show the disclaimer to the users and their acceptance. 
When they do this, the application must use the acceptDisclaimer method to mark the disclaimer as accepted.

```kotlin
aptoPlatform.acceptDisclaimer(cardApplication.workflowObjectId, cardApplication.nextAction) { result ->
    result.either({
		// failure
	}, {
		// disclaimer has been accepted
    })
}
```

After this, we should again update our current cardApplication and check which is the next step defined.

```kotlin
AptoPlatform.fetchCardApplicationStatus(cardApplication.id) { result ->
  result.either({
		// failure
	}, {
		// check the next step, at this point the result would be ISSUE_CARD
    })
}
```

### Issue Card

Finally we can issue a card sending the cardApplicationId to the issueCard function and complete the last step.

```kotlin
aptoPlatform.issueCard(cardApplicationId) { result ->
  result.either({
		// failure
	}, { card ->
		// card has been issued
    })
}
