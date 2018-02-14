[![Build Status](https://travis-ci.com/itabulous/ledgelinksdk_android.svg?token=qo11VUxzPNUqYf96JsWf)](https://travis-ci.com/itabulous/ledgelinksdk_android)
[![Coverage Status](https://coveralls.io/repos/github/itabulous/ledgelinksdk_android/badge.svg?branch=master&t=CnCHgb)](https://coveralls.io/github/itabulous/ledgelinksdk_android?branch=master)

# Ledge Link
Ledge Link Android SDK.

For more information, see the [website](https://itabulous.github.io/ledgelinksdk_android/).

## Download

Maven:

```xml
<dependency>
  <groupId>me.ledge.link.sdk</groupId>
  <artifactId>ui</artifactId>
  <version>(insert latest version)</version>
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

### Example app

1. In the `src/main/res/values` folder create a new file called `strings_configuration.xml`.\*
  * Define the `ledge_link_environment`.
  * Define the `ledge_link_developer_key_dev`.
  * Define the `ledge_link_project_token`
1. To install the example app, run `./gradlew --parallel -p example installDebug`. Make sure you have an Android Emulator running or a connected device.

\* Example of `strings_configuration.xml`:
```xml
<?xml version="1.0" encoding="utf-8"?>
<resources>
    <string name="ledge_link_environment">local</string>
    <string name="ledge_link_developer_key_dev">myApiKey</string>
    <string name="ledge_link_project_token">myProjectToken</string>
</resources>
```

### Github pages

The Github pages website is stored in the `dev-gh-pages` branch.

To generate the Javadocs, run `./gradlew --parallel -p ui generateReleaseJavadoc` from the `dev` branch. The docs are published in `build/reports/ui-javadocs`.

*(For some reason you need to run the Gradle task twice for the Javadocs to be generated. :confused:)*

# License

All rights reserved Ledge Inc (C) 2015-2016. See the [LICENSE](LICENSE.md) file for more info.
