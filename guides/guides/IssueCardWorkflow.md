# Module 

# Android Mobile SDK - Issue Card Workflow

Once a user is created, you can issue a card for the user.

The steps to issue a card are:

1. [Get Card Programs](#user-content-get-card-programs)
2. [Apply for a Card](#user-content-apply-for-a-card)
3. [Get the Next Application Step](#user-content-get-the-next-application-step)
	* [Accept the Disclaimer](#user-content-accept-the-disclaimer) 
5. [Issue the Card](#user-content-issue-the-card)

## Get Card Programs

Each card must be issued under a card program. 

To retrieve a list of all the available card programs, use the `fetchCardProducts` method:

```kotlin
AptoPlatform.fetchCardProducts {
	it.either(
		{ error ->
			// Do something with the error
		}, { cardProducts ->
			// cardProducts is a list of CardProduct objects.
			when (c cardProducts.size) {
				0 -> {} // Error
				1 -> {
					val cardProductId = cardProductList[0].id 
				}
				2 -> {
					// Show screen to select the card program
				}
			}
		}
	)
}
```

**Note:** Card Programs used to be called Card Products, so you may see the SDK methods reflect the term *products*. These method names may change in the future to match the correct term *programs*.

* When the method succeeds, a list of `CardProductSummary` objects are returned, containing details for each card program. Save the card program's ID you want to use to apply for a new card.
* When the method fails, error handling can be included within the `error` response.

## Apply for a Card

Use the card program's ID from the previous step to apply for a card.

To apply for a card:

1. Create a `CardProduct` object passing in the `cardProductId`:

```kotlin
val cardProduct = CardProduct(cardProductId)
```

2. Pass `cardProduct` into the `applyToCard` SDK method:

```kotlin
AptoPlatform.applyToCard(cardProduct) { result ->
	result.either({
		// handle failure  
	}, { cardApplication ->
		// In this step we have successfully applied for a card.
		// Check the cardApplication.nextAction property to identify the next step of the application
    })
 }
```

* When the method succeeds, a `CardApplication` object is returned, containing details for the card application. Save the `cardApplication` object for the remaining steps of the application.
* When the method fails, error handling can be included within the `error` response.

## Get the Next Application Step

Once you have a card application initiated, check for the next application step in the `nextAction` property.

```kotlin
if (cardApplication.nextAction == "SHOW_DISCLAIMER") {
	// Show the disclaimer
}
```

The available values for the `nextAction` property are:

Value|Description
---|---
`COLLECT_USER_DATA`|If any required user information is missing, the next action value will be `COLLECT_USER_DATA`.
`SELECT_BALANCE_STORE`|For Enterprise programs, if using an OAuth application process and all required user information was submitted during user creation, this is the default step. See [Set the Balance Store using OAuth (Enterprise Only)](#user-content-set-the-balance-store-using-oauth--enterprise-only-) to process this step.
`SHOW_DISCLAIMER`|For both Instant Issuance and Enterprise programs, if all required user information was submitted during user creation, this is the default step prior to issuing a card. See [Accept the Disclaimer](#user-content-accept-the-disclaimer) to process this step.
`ISSUE_CARD`|This indicates the application is complete and a card can be issued for the user. See [Issue the Card](#user-content-issue-the-card) to issue a card for the user.

**Note:** After each application step, ensure you update the current card application to see if the card can be issued (`ISSUE_CARD`), or if there are additional steps in the application process.

### Set the Balance Store using OAuth (Enterprise Only)

To set the balance store using OAuth:

1. Get the allowed balance type from the `nextAction`'s `actionConfiguration.allowedBalanceTypes.first()` property.

```kotlin
val allowedBalanceType = cardApplication.nextAction.actionConfiguration.allowedBalanceTypes.first()
```

2. Pass the allowed balance type into the `startOauthAuthentication` method:

```kotlin
AptoPlatform.startOauthAuthentication(allowedBalanceType) { result ->
    result.either({
        // handle failure
    }, { oauthAttempt ->
            val url = oauthAttempt.url
           // Start the oauth attempt in a separate screen
    })
}
```

* When the method succeeds, an OAuth attempt object will be returned. Save its `url` property for the next OAuth process. 
* When the method fails, error handling can be included within the `error` response.

3. Load the OAuth attempt URL in a web view. 

**Note:** Your OAuth server must call the Apto OAuth callback endpoint `/v1/oauth/callback` adhering to OAuth2 standards, including passing in the token and secret, or the error message (if any).

4. Once the user completes the OAuth process, the user will be redirected to the `apto-sdk://oauth-finish` URL. Use this to check for the OAuth status. 

```kotlin
AptoPlatform.verifyOauthAttemptStatus(oauthAttempt) { result ->
    result.either({
        // handle failure
    }, { oauthAttempt ->
        // Verify that the state is PASSED
        if (oauthAttempt.state == "PASSED") {
        	// The OAuth attempt is successful
        } else {
        	// The OAuth attempt failed
        }
    })
}
```

The available values for the OAuth `state` are `PASSED` or `FAILED`:

* If the OAuth `state` is `PASSED`, proceed to the next step to set the balance store using the returned OAuth token ID.
* If the OAuth `state` is `FAILED`, the user must restart the OAuth process to retrieve an OAuth token ID.

5. Once the OAuth completes successfully, pass the card application ID and OAuth token ID into the `setBalanceStore` method:

```kotlin
aptoPlatformProtocol.setBalanceStore(cardApplication.id, oauthAttempt.tokenId) { result ->
    result.either({
        // handle failure
    }, { selectBalanceStoreResult ->
           // selectBalanceStoreResult.result should be VALID
    })
}
```

* When the method succeeds, a select balance store result object is returned. Ensure the `result` property is `VALID` before proceeding to the next step.
* When the method fails, error handling can be included within the `error` response.

6. Check the application status by passing the card application ID into the `fetchCardApplicationStatus` method:

```kotlin
AptoPlatform.fetchCardApplicationStatus(cardApplication.id) { result ->
  result.either({
		// failure
	}, {
		// Check the next step. After the disclaimer the result should be ISSUE_CARD
		if (cardApplication.nextAction == "ISSUE_CARD") {
			// Show the disclaimer
		}
    })
}
```

* When the method succeeds, check the application's `nextAction` property. The value should be `SHOW_DISCLAIMER` to indicate the next step in the application is to display the disclaimer. See [Accept the Disclaimer](#user-content-accept-the-disclaimer) to process this step.
* When the method fails, error handling can be included within the `error` response.

### Accept the Disclaimer

The user must accept the disclaimer to complete their card application. To process the user's disclaimer acceptance:

1. Display the disclaimer to the user and ask for their acceptance.

2. Once the user confirms they accept the disclaimer, pass the applications's `workflowObjectId` and `nextAction` properties into the `acceptDisclaimer` method to mark the disclaimer as accepted:

```kotlin
AptoPlatform.acceptDisclaimer(cardApplication.workflowObjectId, cardApplication.nextAction) { result ->
    result.either({
		// failure
	}, {
		// disclaimer has been accepted
    })
}
```

* When the method succeeds, the disclaimer has been successfully processed.
* When the method fails, error handling can be included within the `error` response.

3. Check the application status by passing the card application ID into the `fetchCardApplicationStatus` method:

```kotlin
AptoPlatform.fetchCardApplicationStatus(cardApplication.id) { result ->
  result.either({
		// failure
	}, {
		// Check the next step. After the disclaimer the result should be ISSUE_CARD
		if (cardApplication.nextAction == "ISSUE_CARD") {
			// Show the disclaimer
		}
    })
}
```

* When the method succeeds, check the application's `nextAction` property. The value should be `ISSUE_CARD` to indicate the application is approved so you may issue a card to the user.
* When the method fails, error handling can be included within the `error` response.

## Issue the Card

To issue a card to the user, pass in the card's approved application ID into the `issueCard` method.

```kotlin
AptoPlatform.issueCard(cardApplication.id) { result ->
  result.either({
		// failure
	}, { card ->
		// card has been issued
    })
}

* When the method succeeds, the card has been issued.
* When the method fails, error handling can be included within the `error` response.