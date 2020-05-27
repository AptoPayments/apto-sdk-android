fastlane documentation
================
# Installation

Make sure you have the latest version of the Xcode command line tools installed:

```
xcode-select --install
```

Install _fastlane_ using
```
[sudo] gem install fastlane -NV
```
or alternatively using `brew cask install fastlane`

# Available Actions
## Android
### android test
```
fastlane android test
```
Runs all the tests
### android release_core
```
fastlane android release_core
```
Release Core SDK
### android release_ui
```
fastlane android release_ui
```
Release UI SDK
### android release_iap
```
fastlane android release_iap
```
Release maven iap
### android release
```
fastlane android release
```
Release Core and UI SDKs, publish to Bintray and to the public repo
### android publish_all_maven_local
```
fastlane android publish_all_maven_local
```
Publish all sdks in MavenLocal

----

This README.md is auto-generated and will be re-generated every time [fastlane](https://fastlane.tools) is run.
More information about fastlane can be found on [fastlane.tools](https://fastlane.tools).
The documentation of fastlane can be found on [docs.fastlane.tools](https://docs.fastlane.tools).
