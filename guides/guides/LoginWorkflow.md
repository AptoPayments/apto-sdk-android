## Mobile API Calls Login

Loggin in


 1. Get Configuration
 2. Verify Credentials
 3. Login


## 1 - Get Configuration


The first step is getting the configuration for your specific program.


```kotlin
AptoPlatform.fetchContextConfiguration(false) { result ->
    result.either({ 
    	// handle failure  
     }, { configuration ->
        // save configuration
    })
}
```

In your project there are different types of credentials that can be configured for loggin in:
- Phone number (can be used as primary and secondary)
- Email (primary & secondary)
- Date of birth (only secondary)


### Check your configuration


The log in operation requires the verification of two user credentials. Your program configuration contains the type of the credentials to be verified.

To know the credential types that are required to conduct a login operation:

```kotlin
configuration.projectConfiguration.primaryAuthCredential
configuration.projectConfiguration.secondaryAuthCredential
```

For the Primary credential, we send a message to the user that has to be verified, this concludes in two steps:
- Start a verification
- Complete a verification with the code provided

In contrast to this, to verify the Secondary credential the only step is Complete Verification.


## 2 - Verify Credentials


We will do the example for:
- Primary: Phone
- Secondary: Date of Birth


First you should call the Start Phone Verification method

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

You should ask the user to enter in a screen the SMS that has been sent to the phone number provided

```kotlin
verification.secret = smsCode
aptoPlatform.completeVerification(verification) { result ->
	result.either({
		// handle failure  
	}, { verification ->  
		if (verification.status == VerificationStatus.PASSED) {
			// The sms was verified correctly
            val primaryDataPoint = PhoneDataPoint(verification, phoneNumber)
		} else {
			// Show Wrong code error
		}
    })  
}
```

Then we should check the verification object

```kotlin
val secondaryCredential = verification.secondaryCredential
if (secondaryCredential == null) {
    // Sign up flow
} else {
   // Sign in flow: verify the secondary credential according to secondary.verificationType, in this example it will be BIRTHDATE
}

```

After this you should ask the user to enter in a screen the birthdate in the sign up flow.

```kotlin
val request = Verification(
    verificationId = secondaryCredential.verificationId,
    secret = date, // Format the date as a string: "yyyy-mm-dd"
    verificationType = "BIRTHDATE"
)

aptoPlatform.completeVerification(request) { result ->
    result.either({
		// handle failure  
	}, { secondaryVerification ->  
		if (response.status == VerificationStatus.PASSED) {
            // Continue to next step
        } else {
            // The user entered a wrong verification
        }
    })  
}
```

## 3 - Login User

Once the two verifications succeed, you can use the login endpoint to retrieve a user session token:

```kotlin
aptoPlatform.loginUserWith(listOf(primaryVerification, secondaryVerification)) { result ->
   result.either({
		// handle failure  
	}, { user ->  
		// Login correct!
	})  
}
```

The user object provides a userToken that will be used from this point on until we logout in every API automatically for user authentication.
This token is valid for a year since the last interaction on production environment and for 15 minutes in sandbox environment.
