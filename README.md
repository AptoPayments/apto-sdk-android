# Apto Mobile Android SDK

Welcome to the Apto Mobile Android SDK. This SDK provides access to Apto's mobile API, and is designed to be used for mobile apps. When using this SDK, there is no need for a separate API integration. All the API endpoints are exposed as simple-to-use SDK methods, and the data returned by the API is already encapsulated in the SDK and is easily accessible.

With this SDK, you can:

* Onboard new users
* Issue new cards
* Obtain card activity information
* Manage cards (set pin, lock / unlock, etc.)

This document provides an overview of how to:

* [Install the SDK](#install-the-sdk)
* [Initialize the SDK](#initialize-the-sdk)
* [Authenticate a User](#authenticate-a-user)
* [Verify a User](#verify-a-user)
* [Manage Users](#manage-users)
* [Manage Card Programs](#manage-card-programs)
* [Manage Cards](#manage-cards)
* [Manage Funding Sources](#manage-funding-sources)
* [Set Notification Preferences](#set-notification-preferences)
* [Manage Transactions](#manage-transactions)

For more information, see the [Apto developer portal](https://developer.aptopayments.com).

To contribute to the SDK development, see [Contributions & Development](#contributions-Development)

## Requirements

* Android SDK - API Level 23 (Android 6.0)
* Kotlin version 1.3.72
* Gradle version 4.0.0

**Note:** The SDK is built using Kotlin, but is fully interoperable with Java. Code adjustments may be needed, if used within a Java project.

### Register a Project and Get API key

You must register a project in the [Apto Developer Portal](https://developer.aptopayments.com) to run the SDK. 

In the developer portal, retrieve your `API KEY`. Please contact Apto to create a project for you.

### Required Permissions

Your app must require the following permissions, in order to use the SDK.

```
    <uses-permission android:name="android.permission.INTERNET" />
    <uses-permission android:name="android.permission.ACCESS_NETWORK_STATE" />
```


## Install the SDK 

We suggest using [Gradle](https://gradle.org) to install the SDK:

1. In your project's `build.gradle` file, add the Apto repository:

	```
	allprojects {
		repositories {
		
			...
			
			maven { url "https://dl.bintray.com/apto/maven" }
			
			...
		}
	}
```

2. In your app's `build.gradle`, add the following dependency:

	```
    dependencies {
	    ...
    
    	implementation 'com.aptopayments.sdk:mobile:3.0.0'
    
    	...
    }
```


## Initialize the SDK

The SDK must be initialized using the `onCreate` method of your Application class, and requires your `API KEY`: 

* **One-step initialization:** Load your `API KEY` in the `onCreate` method of your Application class.
* **Two-step initialization:** Initialize the SDK, and set up the "API_KEY" prior to interacting with the SDK methods.


### One-step Initialization

In the `onCreate` method of your Application class, invoke `AptoPlatform.initializeWithApiKey` and pass in the `API_KEY` from the [Apto Developer Portal](https://developer.aptopayments.com). This fully initializes the SDK for your application.

```kotlin
AptoPlatform.initializeWithApiKey(application, "API_KEY", AptoSdkEnvironment.SBX)
```

**Note:** The last parameter is optional and indicates the deployment environment. The default deployment environment is Production. This example uses the Sandbox environment (`AptoSdkEnvironment.SBX`). 

### Two-step Initialization

If you want to defer setting your `API_KEY`, use the two-step initialization process:

1. In the `onCreate` method of your Application class, invoke `AptoPlatform.initialize` initialize the SDK for your application.

	```kotlin
AptoPlatform.initialize(application)
```

2. Prior to your app's first interaction with the SDK, ensure you set your `API_KEY`. This fully initializes the SDK for your application.

	```kotlin
AptoPlatform.setApiKey("API_KEY", AptoSdkEnvironment.SBX)
```
**Note:** The last parameter is optional and indicates the deployment environment. The default deployment environment is Production. This example uses the Sandbox environment (`AptoSdkEnvironment.SBX`).


## Authenticate a User

The SDK enables users to sign up or sign in to the Apto mobile API. Authentication requires the user to verify their primary credentials. Depending on your project's configuration, the user credentials can be a phone number or email address.

Please contact us to set up proper primary credentials for your users.

### Retrieve a User Token

A user session token is required for authentication. The standard steps for obtaining a user token are:

1. **Verify the user's primary credential.**

2. **Determine if the the user is a new or existing user.** Once the primary credential is verified, the API response will contain data indicating if the credentials belong to an existing user or a new user.

	**If the credentials belong to an existing user:**
	
	1. Verify the user's secondary credential.
	2. Using the SDK's login method to obtain a user session token. The login method consumes the two previous verifications, and the SDK automatically stores and manages the user token.

	**If the credentials don't belong to an existing user:**
	
	- Create a new user with the verified credentials and obtain a user token. The SDK automatically stores and manages the user token.

### Set User Token

If you are using the Apto B2B API and have an existing user session token, you can set the user token with:

```kotlin
AptoPlatform.setUserToken("user_token")
```

**Note:** This is optional. If a session token is not provided, the SDK will obtain one by verifying the user.


## Verify a User

There are three methods for verifying a user:

- [Start a New Verification](#start-a-new-verification) - Use this to initialize the verification process.
- [Restart a Verification](#restart-a-verification) - Use this to conduct subsequent verifications, should the initial verification fail.
- [Complete a Verification](#complete-a-verification) - Use this to completion the verifiation process.

### Start a New Verification

Depending on your project's configuration for your users, use one of the following SDK methods to start a new verification:

- [Phone Verification](#phone-verification): `AptoPlatform.startPhoneVerification` 
- [Email Verification](#email-verification): `AptoPlatform.startEmailVerification`


#### Phone Verification

To initialize a user verification with a phone number, use:

```kotlin
AptoPlatform.startPhoneVerification(phoneNumber) {
  it.either({ error ->
    // Do something with the error
  },
  { verification ->
    // The verification started and the user received an SMS with a single use code.
  })
}
```

- When the method succeeds, the user will receive a single use code via SMS. You can add additional verification handling within the `verification` response.
- When the method fails, error handling can be included within the `error` response.

#### Email Verification

To initialize a user verification with an email address, use:

```kotlin
AptoPlatform.startEmailVerification(email) {
  it.either({ error ->
    // Do something with the error
  },
  { verification ->
    // The verification started and the user received an email with a single use code.
  })
}
```

- When the method succeeds, the user will receive a single use code via email. You can add additional verification handling within the `verification` response.
- When the method fails, error handling can be included within the `error` response.

### Restart a Verification

If you previously initialized a verification, you can restart a verification by passing in the verification object to the `AptoPlatform.restartVerification` method:

```kotlin
AptoPlatform.restartVerification(verification) {
  it.either({ error ->
    // Do something with the error
  },
  { verification ->
    // The verification started and the user received a new secret via email or phone.
  })
}
```

- When the method succeeds, the user will receive a single use code via email or SMS (depending on your project's configuration for your users). You can add additional verification handling within the `verification` response.
- When the method fails, error handling can be included within the `error` response.


### Complete a Verification

To complete a verification, the user will need to enter their pin number.

1. Set the `secret` property on the `verification` object to the user's pin number.

	```kotlin
verification.secret = pin // pin entered by the user
```

2. Complete the verification by passing the `verification` object into the `AptoPlatform.completeVerification` method.

	```kotlin
AptoPlatform.completeVerification(verification) {
  it.either({ error ->
    // Do something with the error
  },
  { verification ->
    if (verification.status == .passed) {
      // The verification succeeded. If it belongs to an existing user, it will contain a non null `secondaryCredential`.
    }
    else {
      // The verification failed: the secret is invalid.
    }
  })
}
```

- When the method succeeds:
	- If the `status` property is set to `.passed`, the verification is successful. If the verification belongs to an existing user, the response will contain a non-null `secondaryCredential` value.
	- Otherwise, the verification failed due to an invalid `secret` value. You can add error handling for the user to re-enter their pin.
- When the method fails, error handling can be included within the `error` response.

## Manage Users

The SDK enables you to:

- [Create a New User](#create-a-new-user)
- [Login with an Existing User](#login-with-an-existing-user)
- [Update a User's Info](#update-a-user-s-info)
- [Close a User Session](#close-a-user-session)

### Create a New User

Once a user's primary credential is verified, you can create a new user using the `AptoPlatform.createUser` method.

1. Create a primary credential object and set its `verification` object.

	This example, creates a `PhoneNumber` object for project configurations that use a phone number as the primary credential.

	```kotlin
val primaryCredential = PhoneNumber(countryCode, phoneNumber)
primaryCredential.verification = verification // The verification obtained before.
```

2. Create a new user, by passing in the `primaryCredential` into `DataPointList().add`.

	```kotlin
AptoPlatform.createUser(DataPointList().add(primaryCredential)) {
  it.either({ error ->
    // Do something with the error
  },
  { user ->
    // The user created. It contains the user id and the user session token.
  })
}
```

	- When the method succeeds, the `user` object contains the user ID and user session token.
	- When the method fails, error handling can be included within the `error` response.

	**Note:** The `createUser` method can accept an optional `custodianUid` parameter. Use this parameter to send Apto the user ID, so future webhook notifications can include this information. This simplifies the process of matching the user with the notification event.

	```kotlin
val custodianUid = "custodian_uid"
AptoPlatform.createUser(DataPointList().add(primaryCredential), custodianUid) {
  ...
}
```

### Login with an Existing User

Once the primary and secondary credentials are verified, you log the user in with the `AptoPlatform.loginUserWith` method and obtain a user token for the existing user.

```kotlin
AptoPlatform.loginUserWith(listOf(phoneVerification, dobVerification)) {
  it.either({error ->
    // Do something with the error
  ),
  { user ->
    // The user logged in. The user variable contains the user id and the user session token.
  })
}
```
	- When the method succeeds, the `user` object contains the user ID and user session token.
	- When the method fails, error handling can be included within the `error` response.

### Update a User's Info

You can update the user's info, with the `AptoPlatform.updateUserInfo` method:

1. Create a `DataPointList` object:

	```kotlin
val userData = DataPointList()
```

2. Pass the `userData` into the `AptoPlatform.updateUserInfo` method:

	```kotlin
// Add to userPII the datapoints that you want to update
AptoPlatform.updateUserInfo(userData) {
  it.either({ error ->
    // Do something with the error
  ),
  { user ->
    // Update successful.
  })
}
```

	- When the method succeeds, the update is successful and the `user` object contains the updated user information.
	- When the method fails, error handling can be included within the `error` response.

### Close a User Session

To close the current user's session, use the `logout` SDK method:

```kotlin
AptoPlatform.logout()
```

## Manage Card Programs

The SDK enables you to manage card programs. This document explains how to use the SDK to:

* [Get Available Card Programs](#get-available-card-programs)
* [Get Card Program Details](#get-card-program-details)

### Get Available Card Programs

To retrieve a list of all the available card programs, use the `AptoPlatform.fetchCardProducts` method:

```kotlin
AptoPlatform.fetchCardProducts {
  it.either({ error ->
    // Do something with the error
  ),
  { cardProducts ->
    // cardProducts is a list of CardProductSummary objects.
  })
}
```

- When the method succeeds, a `cardProducts` object is returned with a list of `CardProductSummary` objects, containing details for each card product.
- When the method fails, error handling can be included within the `error` response.

### Get Card Program Details

To retrieve a specific card program, pass a card product ID into the `AptoPlatform.fetchCardProduct` method:

```kotlin
AptoPlatform.fetchCardProduct(cardProductId) {
  it.either({ error ->
    // Do something with the error
  ),
  { cardProduct ->
    // cardProduct is a CardProduct object.
  })
}
```

- When the method succeeds, a `cardProduct` object is returned containing details for the card product.
- When the method fails, error handling can be included within the `error` response.

## Manage Cards

The SDK enables you to manage cards. This document explains how to use the SDK to:

* [Get Cards](#get-cards)
* [Issue a Card](#issue-a-card)
* [Activate a Physical Card](#activate-a-physical-card)
* [Change a Card PIN](#change-a-card-pin)
* [Lock / unloack a Card](#lock-unlock-a-card)
* [View Monthly Spending Stats](#view-monthly-spending-stats)

### Get Cards

To retrieve a list of cards for the current user, use the `AptoPlatform.fetchCards` method:

```kotlin
AptoPlatform.fetchCards {
  it.either({ error ->
    // Do something with the error
  ),
  { cards ->
    // cards contains an array of Card objects
  })
}
```

- When the method succeeds, a `cards` object is returned, containing a list of `Card` objects.
- When the method fails, error handling can be included within the `error` response.

### Issue a Card

To issue a card for the current user, use the `AptoPlatform.issueCard` method:

1. Generate an `OAuthCredential` object.

	```kotlin
val credential = OAuthCredential(oauthToken = "token", refreshToken = "refresh_token")
```

2. Retrieve a list of all the card products using the `AptoPlatform.fetchCardProducts` method.

	```kotlin
AptoPlatform.fetchCardProducts {
  it.either({ error ->
      // Do something with the error
  },
  { cardProducts ->
		...
  })
}
```

3. with the returned `cardProducts` value, pass the card product you want to issue into the `AptoPlatform.issueCard` method.

	If you have more than one card product, you may need to enable the user to select a specific card product. This example demonstrates how to issue a card on the first card product.
	
	**Note:** To retrieve details about the card product, use the `fetchCardProduct(cardProductId = "", forceRefresh = false) {}` method.

	```kotlin
    AptoPlatform.issueCard(cardProductId = cardProducts[0].id, credential = credential) {
      it.either({ error ->
        // Do something with the error
      },
      { card ->
        // card contains the issued Card object
      })
    }
```
	
	- When the method succeeds, a `card` object is returned containing a the issued card details.
	- When the method fails, error handling can be included within the `error` response.
	

#### Issue a Card with Additional Info

The `issueCard` method also accepts two optional parameters:

- `additionalFields` - Use this parameter to send additional data that may be required for card issuance, but may not have been captured during the user creation process. For a list of allowed fields and values contact us.

- `initialFundingSourceId` - Use this parameter to specify the wallet ID connected to the issued card.
	
	**Note:** This only applies to debit card programs. For additional information regarding this parameter, contact us.

```kotlin
val additionalFields = mapOf<String, Any>("field1" to "value1", "field2" to 2)
val initialFundingSourceId = "initial_funding_source_id"
AptoPlatform.issueCard(cardProductId = cardProducts[0].id, credential = credential,
                       additionalFields = additionalFields, initialFundingSourceId = initialFundingSourceId) {
  ...
}
```

### Activate a Physical Card

Physical cards need to be activated using the provided activation code sent to the cardholder.

To activate the physical card, use the `AptoPlatform.activatePhysicalCard` method:

```kotlin
AptoPlatform.activatePhysicalCard(cardId, code) {
  it.either({ error ->
    // Do something with the error
  ),
  { result ->
    // Result contains information about the activation process and, if it failed, the reason why it failed.
  })
}
```

- When the method succeeds, a `result` object is returned containing information about the activation process. If the activation fails, the `result` object will contain the reason for the failure.
- When the method fails, error handling can be included within the `error` response.


### Change a Card PIN

To set or change the card pin, use the `AptoPlatform.changeCardPin` method:

```kotlin
AptoPlatform.changeCardPin(cardId, pin) {
  it.either({ error ->
    // Do something with the error
  ),
  { card ->
    // Operation successful
  })
}
```

- When the method succeeds, a `card` object is returned containing information about the updated card.
- When the method fails, error handling can be included within the `error` response.


### Lock / Unlock a Card

Cards can be locked or unlocked at any time. Transactions using a locked card will be rejected by the merchant's POS.

#### Lock a Card

To lock a card, pass the account ID into the `AptoPlatform.lockCard` method:

```kotlin
AptoPlatform.lockCard(accountId) {
  it.either({ error ->
    // Do something with the error
  ),
  { card ->
    // Operation successful
  })
}
```

- When the method succeeds, a `card` object is returned containing information about the locked card.
- When the method fails, error handling can be included within the `error` response.

#### Unlock a Card

To unlock a card, pass the account ID into the `AptoPlatform.unlockCard` method:

```kotlin
AptoPlatform.unlockCard(accountId) {
  it.either({ error ->
    // Do something with the error
  ),
  { card ->
    // Operation successful
  })
}
```

- When the method succeeds, a `card` object is returned containing information about the unlocked card.
- When the method fails, error handling can be included within the `error` response.

### View Monthly Spending Stats

The SDK enables you to view the monthly spending for cards, classified by category.

To retrieve information about the monthly spending for a card, pass the card ID, spending month, and spending year into the `AptoPlatform.cardMonthlySpending` method.

```kotlin
AptoPlatform.cardMonthlySpending(cardId, month, year) {
  it.either({ error ->
    // Do something with the error
  ),
  { monthlySpending ->
    // monthlySpending contains the stats of the given month; spendings classified by transaction category and difference with the previous month.
  })
}
```

- When the method succeeds, a `monthlySpending` object is returned containing the stats for the provided month. The spending is classified by transaction category, and difference from the previous month.
- When the method fails, error handling can be included within the `error` response.

## Manage Funding Sources

The SDK enables you to:

* [Get a Card's Default Funding Source](#get-a-card-s-default-funding-source)
* [List a Card's Available Funding Sources](#list-a-card-s-available-funding-sources)
* [Connect a Funding Source to a Card](#connect-a-funding-source-to-a-card)

### Get a Card's Default Funding Source

To retrieve a card's default funding source, pass the card ID into the `AptoPlatform.fetchCardFundingSource` method.

```kotlin
AptoPlatform.fetchCardFundingSource(cardId) {
  it.either({ error ->
    // Do something with the error
  ),
  { fundingSource ->
    // fundingSource is a FundingSource object.
  })
}
```

- When the method succeeds, a `fundingSource` object is returned containing information about the funding source.
- When the method fails, error handling can be included within the `error` response.

### List a Card's Available Funding Sources

Apto cards may have multiple funding sources. To retrieve a list of available funding sources for a card, pass the card ID into the `AptoPlatform.fetchCardFundingSources` method.

```kotlin
AptoPlatform.fetchCardFundingSources(cardId) {
  it.either({ error ->
    // Do something with the error
  ),
  { fundingSources ->
    // fundingSources contain a list of FundingSource objects.
  })
}
```

- When the method succeeds, a `fundingSources` object is returned containing a list of `FundingSource` objects.
- When the method fails, error handling can be included within the `error` response.

### Connect a Funding Source to a Card

To connect a funding source to a card, pass the card ID and funding source ID into the `AptoPlatform.setCardFundingSource` method.

```kotlin
AptoPlatform.setCardFundingSource(cardId, fundingSourceId) {
  it.either({ error ->
    // Do something with the error
  ),
  { fundingSource ->
    // Operation successful.
  })
}
```

- When the method succeeds, a `fundingSource` object is returned containing information for the funding source.
- When the method fails, error handling can be included within the `error` response.


## Set Notification Preferences

Push notifications for users are available for several events (transactions, card status changes, etc.). The SDK can enable the users to decide how they receive some of these notifications.

This document demonstrates how to:

* [Get Notification Preferences](#get-notification-preferences)
* [Update Notification Preferences](#update-notification-preferences)

### Get Notification Preferences

To retrieve the current user's notification preferences, use the `AptoPlatform.fetchNotificationPreferences` method.

```kotlin
AptoPlatform.fetchNotificationPreferences {
  it.either({ error ->
    // Do something with the error
  ),
  { notificationPreferences ->
    // notificationPreferences contains all the information regarding the current user preferences.
  })
}
```

- When the method succeeds, a `notificationPreferences` object is returned containing the information for current user's notification preferences.
- When the method fails, error handling can be included within the `error` response.

### Update Notification Preferences

To update the current user's notification preferences:

1. Create a `NotificationPreferences` object.

	```kotlin
val preferences = NotificationPreferences()
```

2. Pass the preferences object into the `AptoPlatform.updateNotificationPreferences` method.

	```kotlin
// Set the user preferences in `preferences`
AptoPlatform.updateNotificationPreferences(preferences) {
  it.either({ error ->
    // Do something with the error
  ),
  { notificationPreferences ->
    // Operation successful
  })
}
```

	- When the method succeeds, a `notificationPreferences` object is returned containing the information for current user's notification preferences.
	- When the method fails, error handling can be included within the `error` response.

## Manage Transactions

To retrieve a list of transactions, pass the following into the `AptoPlatform.fetchCardTransactions` method:

* `cardId` - The card ID
* `filters` - Filters the transactions by type.
* `forceRefresh` - Boolean value indicating if transactions should be retrieved from the Apto Mobile API or the local cached transaction list. If true, transactions are retrieved from the Apto Mobile API.

```kotlin
AptoPlatform.fetchCardTransactions(cardId, filters, forceRefresh) {
  it.either({ error ->
    // Do something with the error
  ),
  { transactions ->
    // transactions contains a list of Transaction objects.
  })
}
```

- When the method succeeds, a `transactions` object is returned containing a list of `Transaction` objects.
- When the method fails, error handling can be included within the `error` response.


## Contributions & Development

We look forward to receiving your feedback, including new feature requests, bug fixes and documentation improvements.

If you would like to help: 

1. Refer to the [issues](https://github.com/AptoPayments/apto-sdk-android/issues) section of the repository first, to ensure your feature or bug doesn't already exist (The request may be ongoing, or newly finished task).
2. If your request is not in the [issues](https://github.com/AptoPayments/apto-sdk-android/issues) section, please feel free to [create one](https://github.com/AptoPayments/apto-sdk-android/issues/new). We'll get back to you as soon as possible.

If you want to help improve the SDK by adding a new feature or bug fix, we'd be happy to receive [pull requests](https://github.com/AptoPayments/apto-sdk-android/compare)!
