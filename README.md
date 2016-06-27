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

* Java version: 7+
* Android version: 4.0.3+

## Developer Guides

### Dependencies

1. Install the [Ledge Common](https://github.com/itabulous/ledgecommon_android) library.
1. Install the [Ledge Link API](https://github.com/itabulous/ledgelinkapi_android) libraries.

### Installation

1. Create a `gradle.properties` file in the project root directory.
  1. In this file, define the `HOME` property that point to your user's home directory, i.e. `HOME=C:/Users/Wijnand`.
1. Install the SDK project: `./gradlew --parallel --configure-on-demand -p sdk install`.
1. Install the UI project: `./gradlew --parallel --configure-on-demand -p ui install`.
1. Install the various image loaders:
  1. Picasso: `./gradlew --parallel -PexcludeExample=true -p imageloaders\imageloader-picasso install`. The `-PexcludeExample=true` part is important here! Without it the installation of the project will fail.
  1. Volley: `./gradlew --parallel -PexcludeExample=true -p imageloaders\imageloader-volley install`.
1. Install the various API response handlers:
  1. EventBus: `./gradlew --parallel -PexcludeExample=true -p handlers\handler-eventbus install`.
  1. Otto: `./gradlew --parallel -PexcludeExample=true -p handlers\handler-otto install`.

**NOTE:** When you run any of the above Gradle commands for the second time, you can exclude the `--configure-on-demand` and `-PexcludeExample=true` options.

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

To install the example app, run `./gradlew --parallel -p example installDebug`.

# License

All rights reserved Ledge Inc (C) 2015-2016. See the [LICENSE](LICENSE.md) file for more info.
