# Apto SDK for Android

Welcome to the Apto Android SDK. This SDK gives access to the Apto's mobile API, designed to be used from a mobile app. Using this SDK there's no need to integrate the API itself, all the API endpoints are exposed as simple to use methods, and the data returned by the API is properly encapsulated and easy to access.

With this SDK, you'll be able to onboard new users, issue cards, obtain card activity information and manage the card (set pin, freeze / unfreeze, etc.)

For more information, see the [Apto developer portal](https://aptopayments.com/developer).

## Requirements

    * Android Version - API Level 21 (Android 5.0)
    * Kotlin - 1.3.71
    * Gradle - 3.6.1

### Required permisions

    * <uses-permission android:name="android.permission.INTERNET" />
    * <uses-permission android:name="android.permission.ACCESS_NETWORK_STATE" />

### Installation (Using [Gradle](https://gradle.org))

1. In your `build.gradle` file, add the following dependency:

    ```
    
    implementation 'com.aptopayments.sdk:core:2.9.2'
    
    ```

2. Run `./gradlew build`.

## Using the SDK

To run the SDK you must first register a project in order to get a `API KEY`. Please contact Apto to create a project for you. Then, initialise the SDK by passing the public api key:

```kotlin
AptoPlatform.initializeWithApiKey(application, "API KEY")
```

This will initialise the SDK to operate in production mode. If you want to use it in sandbox mode, an additional parameter can be sent during initialization:

```kotlin
AptoPlatform.initializeWithApiKey(application, "API KEY", AptoSdkEnvironment.SBX)

```

## User session token

In order to authenticate a user and retrieve a user session token, the Apto mobile API provides mechanisms to sign up or sign in. Both mechanisms are based on verifying the user primary credentials, which can be user's phone or email, depending on the configuration of your project. Please contact us to set up the proper primary credential for your users.

Typical steps to obtain a user token involve:

1. Verify user's primary credential. Once verified, the verification contains data showing if the credential belongs to an existing user or to a new user.

2.1) If the credential belongs to an existing user, verify user's secondary credential.
2.2) Obtain a user session token by using the login method on the SDK. That method receives the two previous verifications. The user token will be stored and handled by the SDK.

3.1) If the credential doesn't belong to any existing user, create a new user with the verified credential and obtain a user token. The user token will be stored and handled by the SDK.

### Set user token

Use this method to specify a user session token (obtained using the Apto B2B API) to be used by the SDK. This is optional, if a session token is not provided, the SDK will verify the user to obtain one.

```swift
AptoPlatform.setUserToken("user_token")
```

## Verifications

### Start a new verification

To start a new verification, you can use the following SDK methods:

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

### Restart a verification

Verifications can be restarted, by using the following SDK method:

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

### Complete a verification

To complete a new verification, you can use the following SDK method:

```kotlin
verification.secret = pin // pin entered by the user
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

## Users management

### Creating a new user

Once the primary credential has been verified, you can use the following SDK method to create a new user:

```kotlin
val primaryCredential = PhoneNumber(countryCode, phoneNumber)
primaryCredential.verification = verification // The verification obtained before.

AptoPlatform.createUser(DataPointList().add(primaryCredential)) {
  it.either({ error ->
    // Do something with the error
  },
  { user ->
    // The user created. It contains the user id and the user session token.
  })
}
```

The `createUser` method also accepts an optional `custodianUid` parameter:

```kotlin
val custodianUid = "custodian_uid"
AptoPlatform.createUser(DataPointList().add(primaryCredential), custodianUid) {
  ...
}
```

this parameter can be used to send Apto the id of the user in your platform so future notifications (webhooks) can contain that information, making easier matching the event with the user in your platform.

### Login with an existing user

Once the primary and secondary credentials have been verified, you can use the following SDK method to obtain a user token for an existing user:

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

### Update user info

To update user's info, use the following SDK method:

```kotlin
val userData = DataPointList()
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

### Close user session

To close the current user's session, use the following SDK method:

```kotlin
AptoPlatform.logout()
```

## Card programs management

### Get available card programs

To get a list of all the available card programs that can be used to issue cards, you can use the following SDK method:

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

### Get card program details

To get the details of a specific card program, you can use the following SDK method:

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

## Cards management

### Get cards

To retrieve the user cards, you can use the following SDK method:

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

### Issue a card

To issue a card, you can use the following SDK methods:

```kotlin
val credential = OAuthCredential(oauthToken = "token", refreshToken = "refresh_token")
AptoPlatform.fetchCardProducts {
  it.either({ error ->
      // Do something with the error
  },
  { cardProducts ->
    // Depending on the card program setup you might need to allow the user to select the appropriate card product
    // instead of using the first one. You can use the fetchCardProduct(cardProductId = "", forceRefresh = false) {}
    // method to get more details about the card product.
    AptoPlatform.issueCard(cardProductId = cardProducts[0].id, credential = credential) {
      it.either({ error ->
        // Do something with the error
      },
      { card ->
        // card contains the issued Card object
      })
    }
  })
}
```

The `issueCard` method also accepts an optional `additionalFields` parameter that can be used to send Apto additional data required to card issuance that is not captured during the user creation process. For a list of allowed fields and values contact us. You can also use `initialFundingSourceId` to specify the id of the wallet that will be connected to the card when issued. Only applies to debit card programs. For additional information regarding this parameter, contact us.

```kotlin
val additionalFields = mapOf<String, Any>("field1" to "value1", "field2" to 2)
val initialFundingSourceId = "initial_funding_source_id"
AptoPlatform.issueCard(cardProductId = cardProducts[0].id, credential = credential,
                       additionalFields = additionalFields, initialFundingSourceId = initialFundingSourceId) {
  ...
}
```

### Physical card activation

Physical cards need to be activated by providing an activation code that is sent to the cardholder. In order to activate the physical card, you can use this SDK method:

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

### Change card PIN

In order to set the card PIN, you can use the following SDK method:

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

### Freeze / unfreeze card

Cards can be freezed and unfreezed at any moment. Transactions of a freezed card will be rejected in the merchant's POS. To freeze / unfreeze cards, you can ue the following SDK methods:

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

## Funding Sources management

Apto cards can be connected to different funding sources. You can obtain a list of the available funding sources for the current user, and connect one of them to a user's card.

### Get a list of the available funding sources

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

### Get the funding source connected to a card

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

### Connect a funding source to a card

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

## Monthly spending stats

To obtain information about the monthly spendings of a given card, classified by Category, you can use the following SDK method:

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

date represents the month of the spending stats.

## Notification preferences

Users can be notified via push notifications regarding several events (transactions, card status changes, etc.). The SDK offers some functions that allow the users to decide how they receive these notifications.

### Obtain the current user notification preferences

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

### Update the current user notification preferences

```kotlin
val preferences = NotificationPreferences()
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

## Transactions management

To get a list of transactions, you can use the following SDK method:

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

The filters parameter allows you to filter the type of transactions that are returned by the SDK.

The forceRefresh parameter controls whenever the SDK returns the local cached transaction list of whenever it asks for the transaction list through the Apto mobile API.

## Contributing & Development

We're looking forward to receive your feedback including new feature requests, bug fixes and documentation improvements. If you waht to help us, please take a look at the [issues](https://github.com/AptoPayments/apto-sdk-android/issues) section in the repository first; maybe someone else had the same idea and it's an ongoing or even better a finished task! If what you want to share with us is not in the issues section, please [create one](https://github.com/AptoPayments/apto-sdk-android/issues/new) and we'll get back to you as soon as possible.

And, if you want to help us improve our SDK by adding a new feature or fixing a bug, we'll be glad to see your [pull requests!](https://github.com/AptoPayments/apto-sdk-android/compare)
