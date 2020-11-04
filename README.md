# Module

## Apto Android Mobile SDK

Welcome to the Apto Android Mobile SDK. This SDK provides access to Apto's Mobile API, and is designed to be used for mobile apps. When using this SDK, there is no need for a separate API integration. All the API endpoints are exposed as simple-to-use SDK methods, and the data returned by the API is already encapsulated in the SDK and is easily accessible.

**Note:** This SDK can be used in conjunction with our [UI SDK](https://github.com/AptoPayments/apto-ui-sdk-android). However, if you would like to control your UI/UX, we recommend using the standalone Mobile SDK and implementing your own UI/UX.

With this SDK, you can:

* [Onboard new users](#user-content-create-a-new-user)
* [Issue new cards](#user-content-issue-a-card)
* [Obtain card activity information](#user-content-view-monthly-spending-stats)
* Manage cards ([set pin](#user-content-change-a-card-pin), [lock / unlock](#user-content-lock--unlock-a-card), etc.)

**Note:** The Apto Mobile API has a request rate limit of 1 request per 30 seconds for the verification and login endpoints.

This document provides an overview of how to:

* [Install the SDK](#user-content-install-the-sdk)
* [Initialize the SDK](#user-content-initialize-the-sdk)
* [Verify a User Credential to Obtain a User Token](#user-content-verify-a-user-credential-to-obtain-a-user-token)
* [Authenticate a User](#user-content-authenticate-a-user)
* [Manage Users](#user-content-manage-users)
* [Manage Card Programs](#user-content-manage-card-programs)
* [Manage Cards](#user-content-manage-cards)
* [View Card Transactions and Spending](#user-content-view-card-transactions-and-spending)
* [Manage Card Payment Sources / Load Funds](#user-content-manage-card-payment-sources--load-funds)
* [Manage Card Funding Sources for On-demand Debits](#user-content-manage-card-funding-sources-for-on-demand-debits-enterprise-only) (Enterprise Only)
* [Set Notification Preferences](#user-content-set-notification-preferences)

**Note:** To enable your cardholders to view PCI Data, you will also need to implement our [PCI SDK](https://github.com/AptoPayments/apto-pci-sdk-android). 

For more information, see the [Apto Developer Guides](http://docs.aptopayments.com) or the [Apto API Docs](https://docs.aptopayments.com/api/MobileAPI.html).

To contribute to the SDK development, see [Contributions & Development](#user-content-contributions--development)

## Requirements

* Android SDK, minimum API Level 23 (Marshmallow)
* Kotlin, minimum version 1.4.0
* Gradle, minimum version 4.0.1

**Note:** The SDK is built using Kotlin, but is fully interoperable with Java. Code adjustments may be needed, if used within a Java project.

The following Android permissions need to be added to the `AndroidManifest.xml` of your app:

* `<uses-permission android:name="android.permission.INTERNET" />`
* `<uses-permission android:name="android.permission.ACCESS_NETWORK_STATE" />`

### Get the Mobile API key

A Mobile API Key is required to run the SDK. To retrieve your Mobile API Key:

1. Register for an account or login into the [Apto Developer Portal](https://developer.aptopayments.com). 

	**Note:** In order to register or login to your [Apto Developer Portal](https://developer.aptopayments.com) account, you will need to download a 2FA app such as the [Google Authenticator App](https://play.google.com/store/apps/details?id=com.google.android.apps.authenticator2) for your mobile device.

2. 	Your account has different Mobile API Keys: Sandbox and Production. Ensure you choose the correct environment from the dropdown located in the lower left of the page. 
	
	![Mobile API Key](readme_images/environment.jpg)

3. Select **Developers** from the left menu. Your **Mobile API Key** is listed on this page.

	![Mobile API Key](readme_images/devPortal_mobileApiKey.jpg)

	**Note:** `MOBILE_API_KEY` is used throughout this document to represent your Mobile API key. Ensure you replace `MOBILE_API_KEY` with the Mobile API Key in your account.


## Install the SDK 

We suggest using [Gradle](https://gradle.org) to install the SDK:

1. In your project's `build.gradle` file, add the Apto repository:

```
	allprojects {
		repositories {
		
			...
			
		    jcenter()
		    maven { url 'https://dl.bintray.com/apto/maven' }
		    maven { url "https://jitpack.io" }
			
			...
		}
	}
```

2. In your app's `build.gradle`, add the following compile options:

```
android {
	
	...
	
	compileOptions {
		sourceCompatibility = 1.8
		targetCompatibility = 1.8
	}
		
	...
	
}
```

**Note:** This is required if your minimum Android SDK version is below Level 26 (Oreo).

3. In your app's `build.gradle`, also add the following dependency:

```
    dependencies {
    	...
    
		implementation 'com.aptopayments.sdk:mobile:3.2.2'
    
    	...
    }
```

**Note:** The other files in the repo are not required for installation or initialization. Those files are available so you can:

* See the SDK code.
* Modify and recompile the code, to create a custom SDK experience.

## Initialize the SDK

The SDK must be initialized using the `onCreate` method of your Application class, and requires your `MOBILE_API_KEY`: 

* **One-step initialization:** Load your `MOBILE_API_KEY` in the `onCreate` method of your Application class.
* **Two-step initialization:** Initialize the SDK, and set up the `MOBILE_API_KEY` prior to interacting with the SDK methods.

### One-step Initialization

In the `onCreate` method of your Application class, invoke `initializeWithApiKey` and pass in the `MOBILE_API_KEY` from the [Apto Developer Portal](https://developer.aptopayments.com). This fully initializes the SDK for your application.

```kotlin
AptoUiSdk.initializeWithApiKey(application, "MOBILE_API_KEY")
```

The default deployment environment is the Production environment (`AptoSdkEnvironment.PRD`). If you are deploying using the Sandbox environment, you must set the optional environment parameter to `AptoSdkEnvironment.SBX`:

```kotlin
AptoUiSdk.initializeWithApiKey(application, "MOBILE_API_KEY", AptoSdkEnvironment.SBX)
```

### Two-step Initialization

If you want to defer setting your `MOBILE_API_KEY`, use the two-step initialization process:

1. In the `onCreate` method of your Application class, invoke `AptoPlatform.initialize` initialize the SDK for your application.

```kotlin
AptoPlatform.initialize(application)
```

2. Prior to your app's first interaction with the SDK, ensure you set your `MOBILE_API_KEY`. This fully initializes the SDK for your application.

	The default deployment environment is the Production environment (`AptoSdkEnvironment.PRD`). If you are deploying using the Sandbox environment, you must set the optional environment parameter to `AptoSdkEnvironment.SBX`:

```kotlin
AptoUiSdk.setApiKey("MOBILE_API_KEY", AptoSdkEnvironment.SBX)
```

## Verify a User Credential to Obtain a User Token

The SDK enables users to sign up or sign in to the Apto Mobile API. 

A user's credentials must be verified prior to authenticating a user. A successful verification will return a user token, which is used during the authentication process. Depending on your project's configuration, the user's credentials can be:

* **Primary credential**: The user's phone number or email address. An [Instant Issuance Program](http://docs.aptopayments.com/docs/instant-issuance-programs) is the default program, which uses a phone number as the primary credential. Only Enterprise Programs can use an email address as the primary credential, and this must be configured by Apto Payments. Please [contact us](mailto:developers@aptopayments.com) if you would like to use a different primary credential.

	**Note:** The primary credential must be capable of receiving a secret One-Time Passcode (OTP) code which is used to verify the user's Primary credential. For example, the phone number must be able to receive an SMS message, and the email address must be able to receive an email sent by Apto Payments.

* **Secondary credential**: The user's date of birth.

There are three main methods for the [User Verification Process](#user-content-user-verification-process):

* [Start a New Verification](#user-content-start-a-new-verification) - Use this to initialize the verification process.
* [Complete a Verification](#user-content-complete-a-verification) - Use this to finish the verification process.
* [Restart a Primary Verification](#user-content-restart-a-primary-verification) - This only applies to primary (phone / email) verifications. Use this to conduct subsequent verifications, should the initial verification fail.

### User Verification Process

1. **Start verification for the user's primary credential.** See [Start a New Verification](#user-content-start-a-new-verification) for more information.

2. **Complete verification for the user's primary credential.** See [Complete a Verification](#user-content-complete-a-verification) for more information.

3. **Determine if the user is a new or existing user.** Once the user's primary credential is verified, the API response will contain data indicating if the credential belongs to an existing user or a new user.

	**If the credentials belong to an existing user:**
	
	1. Verify the user's secondary credential. See [Complete a Verification](#user-content-complete-a-verification) for more information.
	2. Use the SDK's login method to obtain a user session token. The login method consumes the two previous verifications, and the SDK automatically stores and manages the user token. See [Login with an Existing User](#user-content-login-with-an-existing-user) for more information.

	**If the credentials don't belong to an existing user:**
	
	* Create a new user with the verified credentials and obtain a user token. The SDK automatically stores and manages the user token.

### Start a New Verification

Depending on your project's configuration for your users, use one of the following SDK methods to start a new verification. [Instant Issuance Programs](http://docs.aptopayments.com/docs/instant-issuance-programs) can only use the phone verification.

* [Phone Verification](#user-content-phone-verification): `AptoPlatform.startPhoneVerification` 
* [Email Verification](#user-content-email-verification): `AptoPlatform.startEmailVerification` (Enterprise Programs only)

#### Phone Verification

The phone number used for a phone verification must be capable of receiving an SMS.

To initialize a user verification with a phone number:

1. Initialize a `PhoneNumber` object, passing in the user's country code and phone number as strings:

	**Note:**
	
	* The country code for the United States is `1`.
	* The `PHONE_NUMBER` value should only contain numerical values.

```kotlin
var phoneNumber = PhoneNumber("COUNTRY_CODE", "PHONE_NUMBER")
```

2. Pass the `PhoneNumber` object into the `startPhoneVerification` method:

```kotlin
AptoPlatform.startPhoneVerification(phoneNumber) {
	it.either({ error ->
		// Do something with the error
	}) { verification ->
		// The verification started and the user received an SMS with a single use code (OTP).
	}
}
```

* When the method succeeds, a `Verification` object is included in the callback:
	* The user will receive a single use code (OTP) via SMS. Use that code and the returned `Verification` object to [complete verification for the user's primary credential](#user-content-complete-a-verification).
	* *(Optional)* Add verification handling within the success callback.
* When the method fails, error handling can be included within the `error` response. Errors can include an incorrect OTP code submission or connection problems to the API.

#### Email Verification

Email verification is only available for Enterprise Programs. To set the primary credential to use the user's email address, please [contact us](mailto:developers@aptopayments.com).

To initialize a user verification with an email address, use the `startEmailVerification` method and pass in the user's email as a string:

```kotlin
AptoPlatform.startEmailVerification(email) {
  it.either({ error ->
    // Do something with the error
  },
  { verification ->
    // The verification started and the user received an email with a single use code (OTP).
    this.verification = verification
  })
}
```

* When the method succeeds, a `Verification` object is included in the callback:
	* The user will receive a single use code (OTP) via email. Use that code and the `Verification` object to [complete verification for the user's primary credential](#user-content-complete-a-verification).
	* *(Optional)* Add verification handling within the success callback.
* When the method fails, error handling can be included within the `error` response. Errors can include an incorrect OTP code submission or connection problems to the API.

### Complete a Verification

To complete a verification, the user will need to enter their OTP code or date of birth, depending on the type of verification.

1. Set the `secret` property on the `Verification` object:

	* If verifying a phone number, replace `OTP_CODE_OR_BIRTHDATE` with the OTP code received via SMS from the [Start a New Verification](#user-content-start-a-new-verification) process. **Note:** Use only numerical values.
	* If verifying an email address, replace `OTP_CODE_OR_BIRTHDATE` with the OTP code received via email from the [Start a New Verification](#user-content-start-a-new-verification) process. **Note:** Use only numerical values.
	* If verifying a date of birth, replace `OTP_CODE_OR_BIRTHDATE` with the user's date of birth, using the format `YYYY-MM-DD`. For example, `1999-01-01`.

```kotlin
verification.secret = "OTP_CODE_OR_BIRTHDATE"
```

2. Complete the verification by passing the `Verification` object into the `completeVerification` method.

```kotlin
AptoPlatform.completeVerification(verification) {
	it.either(
		{ error ->
			// Do something with the error
		},
		{ verification ->
		
			if (verification.status == VerificationStatus.PASSED) {
				// The verification succeeded. If it belongs to an existing user, it will contain a non null `secondaryCredential`.				
			} else {
				// The verification failed: the secret is invalid.			
			}
		})
	}
}
```

* When the method succeeds, a `Verification` object is included in the callback:
	* If the verification's `status` property is set to `VerificationStatus.PASSED`, the verification is successful. 
		* For primary verifications, if the verification belongs to an existing user, the response will contain a non-null `secondaryCredential` value. Use the `secondaryCredential.secret` value to [complete verification for the user's secondary credential](#user-content-complete-a-verification).
		* For secondary verifications, the user is now fully verified and [login](#user-content-login-with-an-existing-user) can proceed.
	* Otherwise, the verification failed due to an invalid `secret` value. If this is primary verification, you can add error handling and [restart the primary verification](#user-content-restart-a-primary-verification) for the user to re-enter their OTP code. 
* When the method fails, error handling can be included within the `error` response.


### Restart a Primary Verification

If you previously initialized a phone or email verification, you can restart a verification by passing in the primary verification object into the `restartVerification` method:

```kotlin
AptoPlatform.restartVerification(primaryVerification) {
	it.either({ error ->
		// Do something with the error
	}, { verification ->
		// The verification started and the user received a new OTP code via email or phone.		
	})
}

```

* When the method succeeds, a `Verification` object is included in the callback:
	* The user will receive a single use code (OTP) via SMS or email (depending on the type of verification). Use that code and the returned `Verification` object to [complete verification for the user's primary credential](#user-content-complete-a-verification).
	* *(Optional)* Add verification handling within the success callback.
* When the method fails, error handling can be included within the `error` response.


## Authenticate a User

A user session token is required for authentication. A session token is generated once you successfully sign up a new user or log a user into the SDK. The session token is valid until:

* The user is logged out of the SDK.
* A timeout occurs. IE The user doesn't interact with the app for one year in Production mode or one month in Sandbox mode.

	**Note:** The session length may be configured to a custom duration. Please [contact us](mailto:developers@aptopayments.com) if you'd like to customize the session length.

For [Instant Issuance programs](http://docs.aptopayments.com/docs/instant-issuance-programs), the session token is created by the SDK after [verifying the user](#user-content-verify-a-user-credential-to-obtain-a-user-token).

Enterprise Programs, have access to our Mobile API which enables you to leverage an existing session token for a user. Use the `setUserToken` method and replace `USER_TOKEN` with your existing user token. This enables the user to bypass the requirement to enter an OTP code they receive via SMS or email.

**Note:** Ensure your user token is passed in as a string.

```kotlin
AptoPlatform.setUserToken("USER_TOKEN")
```

**Note:** This is optional. If a session token is not provided, the SDK will obtain one by [verifying the user](#user-content-verify-a-user-credential-to-obtain-a-user-token).

## Manage Users

The SDK enables you to:

* [Create a New User](#user-content-create-a-new-user)
* [Login with an Existing User](#user-content-login-with-an-existing-user)
* [Update a User's Info](#user-content-update-a-users-info)
* [Close a User Session](#user-content-close-a-user-session)

### Create a New User

A user's primary credential must be verified prior to creating a new user. Once the [primary credential verification is complete](#user-content-complete-a-verification), if a user already exists, a new user can not be created. See the [User Verification Process](#user-content-user-verification-process) for more info.

Once a user's primary credential is verified and a new user can be created, use the `createUser` method to create a new user.

1. Create a primary credential object and set its `verification` property.

	This example, creates a `PhoneNumber` object for project configurations that use a phone number as the primary credential.

```kotlin
val primaryCredential = PhoneNumber(countryCode, phoneNumber)
primaryCredential.verification = verification // The verification obtained before.
```

2. Create a `DataPointList` object and add the `primaryCredential`; then pass the `DataPointList` object into the `createUser` method to create a new user.

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

* When the method succeeds, a `User` object is returned containing the user ID and user session token.
* When the method fails, error handling can be included within the `error` response.

**Note:** The `createUser` method can accept the following optional parameters:

* `custodianUid` - Use this parameter to send Apto the user's ID for inclusion in future webhook notifications. This simplifies the process of matching the user with the notification event.
* `metadata` - Use this parameter to send Apto metadata about the new user (Max 256 chars).

```kotlin
val custodianUid = "custodian_uid"
AptoPlatform.createUser(DataPointList().add(primaryCredential), custodianUid) {
  ...
}
```

### Login with an Existing User

Once the primary and secondary credentials are verified, you log the user in with the `loginUserWith` method and obtain a user token for the existing user.

```kotlin
AptoPlatform.loginUserWith(listOf(primaryVerification, verification) as List<Verification>) {
	it.either(
		{ error ->
			// Do something with the error
		}, 
		{ user ->
			// The user logged in. The user variable contains the user id and the user session token.
		}
	)
}
```

* When the method succeeds, a `User` object is returned containing the user ID and user session token.
* When the method fails, error handling can be included within the `error` response.

### Update a User's Info

You can update the user's info, with the `updateUserInfo` method:

1. Create a `DataPointList` object:

```kotlin
val userDataList = listOf<DataPoint>(birthdateDataPoint, addressDataPoint, idDataPoint, ...)
val userData = DataPointList(userDataList)
```

2. Pass the `userData` into the `updateUserInfo` method:

```kotlin
// Add to user PII (Personal Identifiable Information) the datapoints that you want to update
AptoPlatform.updateUserInfo(userData) {
  it.either({ error ->
    // Do something with the error
  },
  { user ->
    // Update successful.
  })
}
```

* When the method succeeds, the update is successful and a `User` object is returned containing the updated user information.
* When the method fails, error handling can be included within the `error` response.

### Close a User Session

When a session is closed, it will log the user out of all sessions on all devices. 

To close the current user's session, use the `logout` SDK method:

```kotlin
AptoPlatform.logout()
```

**Note:** If the user closes the app without invoking the `logout` method (i.e. the user jumps to another app, manually closes the app, etc.), the user's session will still be active.

## Manage Card Programs

The SDK enables you to manage card programs. This document explains how to use the SDK to:

* [Get Available Card Programs](#user-content-get-available-card-programs)
* [Get Card Program Details](#user-content-get-card-program-details)

**Note:** Card Programs used to be called Card Products, so you may see the SDK methods reflect the term *products*. These method names may change in the future to match the correct term *programs*.

### Get Available Card Programs

To retrieve a list of all the available card programs, use the `fetchCardProducts` method:

```kotlin
AptoPlatform.fetchCardProducts {
	it.either(
		{ error ->
			// Do something with the error
		}, { cardProducts ->
			// cardProducts is a list of CardProduct objects.
		}
	)
}
```

* When the method succeeds, a list of `CardProduct` objects are returned, containing details for each card program.
* When the method fails, error handling can be included within the `error` response.

### Get Card Program Details

To retrieve a specific card program, pass a card program ID and `forceRefresh` value into the `fetchCardProduct` method:

`forceRefresh` is a boolean value indicating if transactions should be retrieved from the Apto Mobile API or the local cached transaction list. If true, transactions are retrieved from the Apto Mobile API.

```kotlin
val forceRefresh = false
AptoPlatform.fetchCardProduct(cardProduct, forceRefresh) {
	it.either(
		{ error ->
			// Do something with the error
		}, { cardProduct ->
			// cardProduct is a CardProduct object.
		}
	)
}
```

* When the method succeeds, a `CardProduct` object is returned containing details for the card program.
* When the method fails, error handling can be included within the `error` response.

## Manage Cards

The SDK enables you to manage cards. This feature is only available for Enterprise Programs.

**Note:** [Instant Issuance programs](http://docs.aptopayments.com/docs/instant-issuance-programs) only support one card per user.

This document explains how to use the SDK to:

* [Issue a Card](#user-content-issue-a-card)
* [Get Cards](#user-content-get-cards)
* [Activate a Physical Card](#user-content-activate-a-physical-card)
* [Change a Card PIN](#user-content-change-a-card-pin)
* [Lock / Unlock a Card](#user-content-lock--unlock-a-card)

### Get Cards

To retrieve a list of cards for the current user, pass the following into the `fetchCards` method:

* `pagination` - `PageFilters`, which filters the cards for the requested page.

```kotlin
AptoPlatform.fetchCards(pagination) {
  it.either({ error ->
    // Do something with the error
  },
  { cards ->
    // cards contains an array of Card objects
  })
}
```

* When the method succeeds, a `cards` object is returned, containing a list of `Card` objects.
* When the method fails, error handling can be included within the `error` response.

### Issue a Card

To issue a card for the current user, use the `issueCard` method:

1. Generate an `OAuthCredential` object.

```kotlin
val credential = OAuthCredential("TOKEN", "refresh_token")
```

2. Retrieve a list of all the card programs using the `fetchCardProducts` method.

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

3. with the returned `cardProducts` value, pass the card program you want to issue, into the `issueCard` method.

	If you have more than one card program, you may need to enable the user to select a specific card program. This example demonstrates how to issue a card for the first card program.
	
	**Note:** To retrieve details about the card program, use the `fetchCardProduct` method and pass in the card program's ID and `OAuthCredential` object.

```kotlin
AptoPlatform.issueCard(cardProducts[0].id, credential) {
	it.either({ error ->
		// Do something with the error
	}, { card ->
		// card is the issued Card object
	})
}
```
	
* When the method succeeds, a `Card` object is returned containing the issued card details.
* When the method fails, error handling can be included within the `error` response.
	

#### Issue a Card with Additional Info

Enterprise Programs have the ability to issue cards
with additional information. Use the following two optional parameters of the `issueCard` to add additional information:

* `additionalFields` - Use this parameter to send additional data that may be required for card issuance, but may not have been captured during the user creation process. For a list of allowed fields and values [contact us](mailto:developers@aptopayments.com).

* `initialFundingSourceId` - Use this parameter to specify the wallet ID connected to the issued card.
	
	**Note:** This only applies to debit card programs. For additional information regarding this parameter, [contact us](mailto:developers@aptopayments.com).

```kotlin
val additionalFields = mapOf<String, Any>("field1" to "value1", "field2" to 2)
val initialFundingSourceId = "initial_funding_source_id"

AptoPlatform.issueCard(cardProducts[0].id, credential,
                       additionalFields = additionalFields, initialFundingSourceId = initialFundingSourceId) {
  ...
}
```

### Activate a Physical Card

Physical cards need to be activated using the provided activation code sent to the cardholder.

For [Instant Issuance programs](http://docs.aptopayments.com/docs/instant-issuance-programs), physical cards are shipped to user in a card carrier envelope. This carrier contains:

* The physical card
* The card’s Activation Code
* The issuing statement and the name of your program.

For more information about physical cards, see the [Apto Developer Guides](http://docs.aptopayments.com/docs/cards/#card-carrier).

To activate a physical card, use the `activatePhysicalCard` method and pass in the card's account ID `accountID` and the user's Activation Code `PHYSICAL_CARD_ACTIVATION_CODE` as a string:

```kotlin
val code = "PHYSICAL_CARD_ACTIVATION_CODE"

AptoPlatform.activatePhysicalCard(accountID, code) {
	it.either(
		{ error ->
			// Do something with the error
		}, { result ->
			// Result contains information about the activation process and, if it failed, the reason why it failed.
		}
	)
}
```

* When the method succeeds, a `result` object is returned containing information about the activation process. If the activation fails, the `result` object will contain the reason for the failure.
* When the method fails, an `error` object will contain the reason for the failure. Error handling can be included within the `error` response. Reasons for failure may include: internet connection issues, inoperable API, bad code, etc.

### Change a Card PIN

If your app enables users to change their card pin, we recommend asking the user to confirm their current PIN prior to entering and confirming a new PIN.

To set or change the card pin, use the `changeCardPin` method and pass in the card's `accountID` and new `pin` value as a 4-digit string:

```kotlin
val pin = "1234"
AptoPlatform.changeCardPin(accountID, pin) {
	it.either(
		{ error ->
			// Do something with the error
		}, { card ->
			// Operation successful
		}
	)
}
```

* When the method succeeds, a `Card` object is returned containing information about the updated card.
* When the method fails, error handling can be included within the `error` response.


### Lock / Unlock a Card

You can enable users to lock and unlock their cards.

Transactions using a locked card will be rejected by the merchant's POS, and therefore we recommend to lock cards only if a user makes an explicit request to lock their card.

#### Lock a Card

To lock a card, pass the card's `accountID` into the `lockCard` method:

```kotlin
AptoPlatform.lockCard(accountID) {
	it.either(
		{ error ->
			// Do something with the error
		}, { card ->
			// Operation successful
		}
	)
}
```

* When the method succeeds:
	* A `Card` object is returned containing information about the locked card, and the card's `state` property will be listed as `INACTIVE`.
	* The registered user will receive an email confirmation that the card has been deactivated.
* When the method fails, error handling can be included within the `error` response.

#### Unlock a Card

To unlock a card, pass the card's `accountID` into the `unlockCard` method:

```kotlin
AptoPlatform.unlockCard(accountID) {
	it.either(
		{ error ->
			// Do something with the error
		}, { card ->
			// Operation successful
		}
	)
}
```

* When the method succeeds, a `Card` object is returned containing information about the unlocked card, and the card's `state` property will be listed as `ACTIVE`. **Note:** Unlike the `lock` method, the registered user will NOT receive an email confirmation that the card has been activated.
* When the method fails, error handling can be included within the `error` response.

## View Card Transactions and Spending

The SDK provides methods to:

* [View Card Transactions](#user-content-view-card-transactions)
* [View Monthly Spending Stats](#user-content-view-monthly-spending-stats)

### View Card Transactions

To retrieve a list of transactions, pass the following into the `fetchCardTransactions` method:

* `accountID` - The card's account ID
* `filters` - A `TransactionListFilters` object, which filters the transactions by page, row, date, mcc, type, etc.
	* `page` - This is the page number, specifying the portion of the list of filters.
	* `rows` - This is the number of filters to display per page.
* `forceRefresh` - A boolean value indicating if transactions should be retrieved from the Apto Mobile API or the local cached transaction list. If true, transactions are retrieved from the Apto Mobile API.
* `clearCachedValues` - A boolean value indicating if the old cached data should be removed and replaced with the new API values.

```kotlin
val page = 0
val rows = 10
val filters = TransactionListFilters(page, rows)
val forceRefresh = false
val clearCachedValues = false

AptoPlatform.fetchCardTransactions(accountID, filters, forceRefresh, clearCachedValues) {
  it.either({ error ->
    // Do something with the error
  },
  { transactions ->
    // transactions contains a list of Transaction objects.
  })
}
```

* When the method succeeds, a list of `Transaction` objects are returned.
* When the method fails, error handling can be included within the `error` response.

### View Monthly Spending Stats

The SDK enables you to view the monthly spending for cards, classified by category.

To retrieve information about the monthly spending for a card, use the `cardMonthlySpending` method with the following parameters:

* `accountID` - This is the card's account ID.
* `month` - This is the spending month, specified as the full month name. For example, `January`, `February`, `March`, etc.
* `year` - This is the four-digit spending year, specified as a string. For example `2018`, `2019`, `2020`, etc.

```kotlin
val month = "January"
val year = "2020"

AptoPlatform.cardMonthlySpending(accountID, month, year) {
	it.either(
		{ error ->
			// Do something with the error
		}, { monthlySpending ->
			// Returns a MonthlySpending object containing the stats of the given month.
		}
	)
}
```

* When the method succeeds, a `MonthlySpending` object is returned containing the stats for the provided month. The spending is classified by transaction category, and the difference from the previous month.
* When the method fails, error handling can be included within the `error` response.

## Manage Card Payment Sources / Load Funds

Funds loaded onto a user's card is automatically deducted from the payment source. The balance is maintained on the user's card, managed by Apto Payments. You can load money onto a user's card using a valid debit card. 

* [Get Payment Sources](#user-content-get-payment-sources)
* [Add a Payment Source](#user-content-add-a-payment-source)
* [Delete a Payment Source](#user-content-delete-a-payment-source)
* [Add Funds to a Card](#user-content-add-funds-to-a-card)

### Get Payment Sources

A list of payment sources can be retrieved using the `getPaymentSources` method. The method accepts three optional parameters:

Parameter|Type|Description
---|---|---
`limit`|`Int`|This value specifies the upper limit of the number of payment sources returned per call. The maximum value for this parameter is `50`.
`startingAfter`|`String`|If this value is set, only payment sources ID's larger than this value are returned.
`endingBefore`|`String`|If this value is set, only payment sources ID's less than this value are returned.

**Note:** The `startingAfter` and `endingBefore` parameters are mutually exclusive. Only one of these parameters may be present per call. If both parameters are set, an error will be thrown.

```kotlin
AptoPlatform.getPaymentSources(limit, startingAfter, endingBefore) {
	it.either(
		{ error ->
			// Do something with the error
		}, { paymentSources ->
			// paymentSources contains a list of PaymentSource objects.
		}
	)
}
```

* When the method succeeds, a `paymentSources` object is returned containing a list of `PaymentSource` objects.
* When the method fails, error handling can be included within the `error` response.

### Add a Payment Source

To add a payment source:

1. Create a `NewCard` object:

	**Note:** Currently only cards are accepted.

```kotlin
val paymentSource = NewCard(
            description = "test description",
            pan = "4242424242424242",
            cvv = "123",
            expirationMonth = "12",
            expirationYear = "22",
            zipCode = "12345"
        )
```

2. Pass `paymentSource` into the `addPaymentSource` method:

	**Note:** Added payment sources are automatically marked as `preferred`.

```kotlin
AptoPlatform.addPaymentSource(paymentSource) {
  it.either({ error ->
    // Do something with the error
  },
  { source ->
    // source contains a PaymentSource object.
  })
}
```

* When the method succeeds, the added `PaymentSource` object is returned without the PII (Personal Identifiable Information).
* When the method fails, error handling can be included within the `error` response.

### Delete a Payment Source

To delete a payment source, pass the payment source ID into the `deletePaymentSource` method:

**Note:** A preferred payment source can not be deleted.

```kotlin
AptoPlatform.deletePaymentSource(paymentSourceId) {
	it.either(
		{ error ->
			// Do something with the error
		}, { source ->
			// source contains a PaymentSource object.
		}
	)
}
```

* When the method succeeds, a deleted `PaymentSource` object`PaymentSource` object is returned without the PII (Personal Identifiable Information).
* When the method fails, error handling can be included within the `error` response.

### Add Funds to a Card

To add funds to a card, pass the following parameters into the `deletePaymentSource` method:

Parameter|Type|Description
---|---|---
`balanceId`|`String`|This is the ID for the card balance you want to fund. See [Get Payment Sources](#user-content-get-payment-sources) to retreive the ID property.
`paymentSourceId`|`String`|This is the Payment Source ID that will be used to add funds.
`amount`|`Money`|This object specifies the amount and currency that will be added to the balance.

```kotlin
val money = Money("USD", 100)
AptoPlatform.pushFunds(balanceId, paymentSourceId, money) {
	it.either(
		{ error ->
			// Do something with the error
		}, { payment ->
			// result contains a Payment object.
		}
	)
}
```

* When the method succeeds, a `Payment` object is returned with the completed payment information.
* When the method fails, error handling can be included within the `error` response.

## Manage Card Funding Sources for On-demand Debits (Enterprise only)

Funding sources may be used for on-demand debiting. When a user spends using their card, the amount can be debited from a specified funding source, rather than using the [funds loaded onto their card](#user-content-manage-card-payment-sources-for-loading-funds). A card can have more than one funding source. 

**Note:** Only Enterprise Programs may use this feature to connect a funding source to a card. [Instant Issuance programs](http://docs.aptopayments.com/docs/instant-issuance-programs) may only [load funds onto a card](#user-content-manage-card-payment-sources-for-loading-funds). 

The Mobile SDK enables you to:

* [Get a Card's Default Funding Source](#user-content-get-a-cards-default-funding-source)
* [List a Card's Available Funding Sources](#user-content-list-a-cards-available-funding-sources)
* [Connect a Funding Source to a Card](#user-content-connect-a-funding-source-to-a-card)

### Get a Card's Default Funding Source

To retrieve a card's default funding source, pass the following parameters into the `fetchCardFundingSource` method.

* `accountID` - The card's account ID
* `forceRefresh` - Boolean value indicating if transactions should be retrieved from the Apto Mobile API or the local cached transaction list. If true, transactions are retrieved from the Apto Mobile API.


**Note:** If no default funding source is specified, the first available funding source will be used as the default funding source.

```kotlin
val forceRefresh = false

AptoPlatform.fetchCardFundingSource(accountID, forceRefresh) {
	it.either(
		{ error ->
			// Do something with the error
		}, { fundingSource ->
			// Returns a FundingSource object containing details about the funding source.
		}
	)
}
```

* When the method succeeds, a `FundingSource` object is returned containing information about the funding source.
* When the method fails, error handling can be included within the `error` response.

### List a Card's Available Funding Sources

Apto cards may have multiple funding sources. To retrieve a list of available funding sources for a card, pass the following parameters into the `fetchCardFundingSources` method:

* `accountID` - This is the card's account ID.
* `page` - This is the page number, specifying the portion of the list of funding sources.
* `rows` - This is the number of funding sources to display per page.
* `forceRefresh` - Boolean value indicating if transactions should be retrieved from the Apto Mobile API or the local cached transaction list. If true, transactions are retrieved from the Apto Mobile API.

```kotlin
val page = 0
val rows = 10
val forceRefresh = false

AptoPlatform.fetchCardFundingSources(accountID, page, rows, forceRefresh) {
	it.either(
		{ error ->
			// Do something with the error
		}, { fundingSources ->
			// fundingSources contains a list of FundingSource objects.
		}
	)
}
```

* When the method succeeds, a list of `FundingSource` objects are returned.
* When the method fails, error handling can be included within the `error` response.

### Connect a Funding Source to a Card

Enterprise Programs can issue cards connected to a funding source not controlled by Apto Payments. Therefore, only Enterprise Programs can manage funding sources.

For example, when using the Coinbase card program, users connect their Coinbase wallet and select whether the card is Bitcoin or Ethereum using these funding source management methods.

To connect a funding source to a card, pass the [card](#user-content-get-cards) `accountID` and [funding source](#user-content-list-a-cards-available-funding-sources) `id` into the `setCardFundingSource` method:

```kotlin
AptoPlatform.setCardFundingSource(accountID, fundingSource.id) {
	it.either(
		{ error ->
			// Do something with the error
		}, { fundingSource ->
			// Operation successful.
		}
	)
}
```

* When the method succeeds, a `FundingSource` object is returned containing information for the funding source.
* When the method fails, error handling can be included within the `error` response.


## Set Notification Preferences

Push notifications for users are available for several events (transactions, card status changes, etc.). The SDK can enable the users to decide how they receive some of these notifications.

![Notifications](readme_images/notifications.png)

This section demonstrates how to:

* [Get Notification Preferences](#user-content-get-notification-preferences)
* [Update Notification Preferences](#user-content-update-notification-preferences)

### Get Notification Preferences

To retrieve the current user's notification preferences, use the `fetchNotificationPreferences` method.

```kotlin
AptoPlatform.fetchNotificationPreferences {
	it.either(
		{ error ->
			// Do something with the error
		}, { notificationPreferences ->
			// Returns a NotificationPreferences object containing all the information regarding the current user preferences.
		}
	)
}
```

* When the method succeeds, a `NotificationPreferences` object is returned containing the information for current user's notification preferences. Its `preferences` property contains a list of `NotificationGroup` objects, containing details for each notification.
* When the method fails, error handling can be included within the `error` response.

### Update Notification Preferences

To update the current user's notification preferences:

1. Create a `NotificationPreferences` object.

```kotlin
val existingNotificationGroup = notificationPreferences.preferences?.first()
val notificationGroup = NotificationGroup(existingNotificationGroup?.categoryId, existingNotificationGroup?.groupId, NotificationGroup.State.DISABLED, existingNotificationGroup?.activeChannels)
val preferences = NotificationPreferences(listOf(notificationGroup))
```

2. Pass the `preferences` object into the `updateNotificationPreferences` method.

```kotlin
AptoPlatform.updateNotificationPreferences(preferences) {
	it.either(
		{ error ->
			// Do something with the error
		}, { notificationPreferences ->
			// Operation successful
		}
	)
}
```

* When the method succeeds, a `notificationPreferences` object is returned containing the information for current user's notification preferences.
* When the method fails, error handling can be included within the `error` response.


## Contributions & Development

We look forward to receiving your feedback, including new feature requests, bug fixes and documentation improvements.

If you would like to help: 

1. Refer to the [issues](https://github.com/AptoPayments/apto-sdk-android/issues) section of the repository first, to ensure your feature or bug doesn't already exist (The request may be ongoing, or newly finished task).
2. If your request is not in the [issues](https://github.com/AptoPayments/apto-sdk-android/issues) section, please feel free to [create one](https://github.com/AptoPayments/apto-sdk-android/issues/new). We'll get back to you as soon as possible.

If you want to help improve the SDK by adding a new feature or bug fix, we'd be happy to receive [pull requests](https://github.com/AptoPayments/apto-sdk-android/compare)!
