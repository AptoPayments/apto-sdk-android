[![Build Status](https://travis-ci.com/itabulous/ledgelinksdk_android.svg?token=qo11VUxzPNUqYf96JsWf)](https://travis-ci.com/itabulous/ledgelinksdk_android)
[![Coverage Status](https://coveralls.io/repos/github/itabulous/ledgelinksdk_android/badge.svg?branch=master&t=CnCHgb)](https://coveralls.io/github/itabulous/ledgelinksdk_android?branch=master)

# Shift SDK
Shift Android SDK.

For more information, see the [website](https://developer.ledge.me).

## Download

Maven:

```xml
<dependency>
  <groupId>com.shiftpayments.android</groupId>
  <artifactId>shift-sdk</artifactId>
  <version>(insert latest version)</version>
  <type>pom</type>
</dependency>
```

Or Gradle:

```groovy
compile 'com.shiftpayments.android:shift-sdk:+'
```

## Requirements

* Java version: 8+
* Android version: 4.0.3+

## Developer Guides

### Installation

1. Create a `gradle.properties` file in the project root directory.
   * In this file, define the `HOME` property that point to your user's home directory i.e. on Windows: `HOME=C:/Users/adrian` or on Mac: `HOME=/Users/adrian`
1. Install the SDK project: `./gradlew --parallel --configure-on-demand -p sdk install`.
1. Install the UI project: `./gradlew --parallel --configure-on-demand -p shift-sdk install`.

**NOTE:** When you run any of the above Gradle commands for the second time, you can exclude the `--configure-on-demand`.

### Running tests

#### SDK Project

To run Unit tests and generate a JaCoCo coverage report, run `./gradlew --parallel -p sdk testDebugUnitTestCoverage`.

* Unit test report: `sdk/build/reports/tests/debug/index.html`.
* Coverage report: `sdk/build/reports/jacoco/testDebugUnitTestCoverage/html/index.html`.

#### UI Project

To run Unit tests and generate a JaCoCo coverage report, run `./gradlew --parallel -p shift-sdk testDebugUnitTestCoverage`.

* Unit test report: `shift-sdk/build/reports/tests/debug/index.html`.
* Coverage report: `shift-sdk/build/reports/jacoco/testDebugUnitTestCoverage/html/index.html`.

### Example apps

There are two example apps which make use of the SDK. You can install the example app to check out the Link flow or the cardExample app which shows the Shift Card flow. 

1. In the `src/main/res/values` folder create a new file called `strings_configuration.xml`.\*
  * Define the `shift_environment`.
  * Define the `shift_developer_key`.
  * Define the `shift_project_token`
1. Make sure you have an Android Emulator running or a connected device.
1. To install the Link example app, run `./gradlew --parallel -p example installDebug`.
1. To install the Shift card example app, run `./gradlew --parallel -p cardExample installDebug`.
    

\* Example of `strings_configuration.xml`:
```xml
<?xml version="1.0" encoding="utf-8"?>
<resources>
    <string name="shift_environment">local_emulator</string>
    <string name="shift_developer_key">myApiKey</string>
    <string name="shift_project_token">myProjectToken</string>
    <string name="shift_branch_key">branch_key</string>
    <string name="shift_coinbase_client_id">coinbase_id</string>
    <string name="shift_coinbase_client_secret">coinbase_secret</string>
</resources>
```

### Using the SDKs

To run the SDK first you need to set it up with your keys and the current context:
```java
ShiftPlatform.initialize(this, "my_developer_key", "my_project_key");
```
This is required for both the Link SDK and the Card SDK.
Optionally, you can configure if you want to enable certificate pinning, if you want to trust self-signed certificates, 
which environment you want to target ("sbx" or "prd"), a callback to be executed when there is no internet connection and additional options to configure the SDK.

To generate the options you can use the ShiftSdkOptionsBuilder.
The list of options are: showTransactionList, showAccountManagement, showChangePIN, showLockCard,
showActivateCardButton and showAddFundingSourceButton. They are by default true.
```java
ShiftSdkOptions options = new ShiftSdkOptionsBuilder()
                                            .enableCardActivation(false)
                                            .enableAddingFundingSources(false)
                                            .buildOptions();
ShiftPlatform.initialize(this, "my_developer_key", "my_project_key", true, true, "sbx", onNoInternetCallback, options);
```

After you have done the setup, you can launch the desired SDK passing in the context.
For the Link flow use:
```java
ShiftPlatform.startLinkFlow(this);
```
For the Shift card flow use:
```java
ShiftPlatform.startCardFlow(this);
```

Aditionally, you can initialize the SDK with the datapoints you already have:
```java
ShiftPlatform.startLinkFlow(this, myUserData, myLoanData);
```
```java
ShiftPlatform.startCardFlow(this, myUserData, myCardData);
```

Where `myUserData` is a `DataPointList`, `myLoanData` is a `LoanDataVo` containing the loan amount and the loan purpose and `myCardData` is a `Card`.

### Github pages

The Github pages website is stored in the `dev-gh-pages` branch.

To generate the Javadocs, run `./gradlew --parallel -p shift-sdk generateReleaseJavadoc` from the `dev` branch. The docs are published in `build/reports/shift-sdk-javadocs`.

*(For some reason you need to run the Gradle task twice for the Javadocs to be generated. :confused:)*

# License

All rights reserved Shift Financial, Inc (C) 2018. See the [LICENSE](LICENSE) file for more info.
