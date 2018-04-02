[![Build Status](https://travis-ci.com/itabulous/ledgelinksdk_android.svg?token=qo11VUxzPNUqYf96JsWf)](https://travis-ci.com/itabulous/ledgelinksdk_android)
[![Coverage Status](https://coveralls.io/repos/github/itabulous/ledgelinksdk_android/badge.svg?branch=master&t=CnCHgb)](https://coveralls.io/github/itabulous/ledgelinksdk_android?branch=master)

# Ledge Link
Ledge Link Android SDK.

For more information, see the [website](https://developer.ledge.me).

## Download

Maven:

```xml
<dependency>
  <groupId>me.ledge.link.sdk</groupId>
  <artifactId>ui</artifactId>
  <version>(insert latest version)</version>
  <type>pom</type>
</dependency>
```

Or Gradle:

```groovy
compile 'me.ledge.link.sdk:ui:+'
```

## Requirements

* Java version: 8+
* Android version: 4.0.3+

## Developer Guides

### Installation

1. Create a `gradle.properties` file in the project root directory.
   * In this file, define the `HOME` property that point to your user's home directory i.e. on Windows: `HOME=C:/Users/adrian` or on Mac: `HOME=/Users/adrian`
1. Install the SDK project: `./gradlew --parallel --configure-on-demand -p sdk install`.
1. Install the UI project: `./gradlew --parallel --configure-on-demand -p ui install`.

**NOTE:** When you run any of the above Gradle commands for the second time, you can exclude the `--configure-on-demand`.

### Running tests

#### SDK Project

To run Unit tests and generate a JaCoCo coverage report, run `./gradlew --parallel -p sdk testDebugUnitTestCoverage`.

* Unit test report: `sdk/build/reports/tests/debug/index.html`.
* Coverage report: `sdk/build/reports/jacoco/testDebugUnitTestCoverage/html/index.html`.

#### UI Project

To run Unit tests and generate a JaCoCo coverage report, run `./gradlew --parallel -p ui testDebugUnitTestCoverage`.

* Unit test report: `ui/build/reports/tests/debug/index.html`.
* Coverage report: `ui/build/reports/jacoco/testDebugUnitTestCoverage/html/index.html`.

### Example apps

There are two example apps which make use of the SDK. You can install the example app to check out the Link flow or the cardExample app which shows the Shift Card flow. 

1. In the `src/main/res/values` folder create a new file called `strings_configuration.xml`.\*
  * Define the `ledge_link_environment`.
  * Define the `ledge_link_developer_key_dev`.
  * Define the `ledge_link_project_token`
1. Make sure you have an Android Emulator running or a connected device.
1. To install the Link example app, run `./gradlew --parallel -p example installDebug`.
1. To install the Shift card example app, run `./gradlew --parallel -p cardExample installDebug`.
    

\* Example of `strings_configuration.xml`:
```xml
<?xml version="1.0" encoding="utf-8"?>
<resources>
    <string name="ledge_link_environment">local_emulator</string>
    <string name="ledge_link_developer_key_dev">myApiKey</string>
    <string name="ledge_link_project_token">myProjectToken</string>
</resources>
```

### Using the SDKs

To run the SDK first you need to set it up with your keys and the current context:
```java
LedgeLinkUi.setupLedgeLink(this, "my_developer_key", "my_project_key");
```
Optionally, you can configure if you want to enable certificate pinning, if you want to trust self-signed ceritificates, and which environment you want to target (local_device, local_emulator, dev, stg, sbx, prd)
```java
LedgeLinkUi.setupLedgeLink(this, "my_developer_key", "my_project_key", true, true, "sbx");
```

After you have done the setup, you can launch the desired SDK passing in the context.
For the Link flow use:
```java
LedgeLinkUi.startLinkProcess(this);
```
For the Shift card flow use:
```java
LedgeLinkUi.startCardProcess(this);
```

Aditionally, you can initialize the SDK with the datapoints you already have:
```java
LedgeLinkUi.startLinkProcess(this, myUserData, myLoanData);
```
```java
LedgeLinkUi.startLinkProcess(this, myUserData, myCardData);
```

Where `myUserData` is a `DataPointList`, `myLoanData` is a `LoanDataVo` containing the loan amount and the loan purpose and `myCardData` is a `Card`.

### Github pages

The Github pages website is stored in the `dev-gh-pages` branch.

To generate the Javadocs, run `./gradlew --parallel -p ui generateReleaseJavadoc` from the `dev` branch. The docs are published in `build/reports/ui-javadocs`.

*(For some reason you need to run the Gradle task twice for the Javadocs to be generated. :confused:)*

# License

All rights reserved Ledge Inc (C) 2015-2016. See the [LICENSE](LICENSE.md) file for more info.
