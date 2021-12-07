## CHANGELOG

# 2021-12-7 Mobile Version 3.17.0 Ui 3.17.0
- feat(ui): Moved Card PIN update to PCI-SDK
- feat(ui): Start migration to ViewBinding
- chore(ui): Remove emojis from copies
- fix(ui): Remove memory leak con Main Screen - Transaction List
- fix(ui): Set background color for main activity
- fix(ui): Reconnect funding source
- feat(mobile&ui): P2P - Catch Recipient Self Error
- chore(mobile): Remove deprecated fetchCard method

# 2021-11-10 Mobile Version 3.16.0 Ui 3.16.0
- feat(ui): Add Exported value to Activities to support Android 12 (ACQ-2194)
- fix(ui): Prepare IVR Phone Call for Android 12 (ACQ-2298)
- feat(ui-mobile): P2P payments
- chore(mobile): Remove deprecated IssueCard method (ACQ-1201)
- chore(all): Update Gradle plugin to 7.0.3
- chore(all): Remove jCenter | Twillio 5.7.2 | Places 2.4.0 | inputMask 6.1.0 | ccp 2.4.9 (ACQ-1983)
- chore(all): Reduce compile time warnings (ACQ-2186)

# 2021-10-13 Version 3.15.0 Ui 3.15.0
- feat(mobile): Added format field to Card object (ACQ-2142)
- feat(ui): Remove payment source
- feat(ui): Change old PDF library for a new one (ACQ-1952)

# 2021-09-29 Version Ui 3.14.1
- fix(ui): Fix Card logo placement

# 2021-09-15 Version Mobile 3.14.1
- fix(mobile): Add Keep annotation to ExtraModule

# 2021-09-15 Version 3.14.0 Ui 3.14.0
- feat(mobile): Improve Card Error copies (ACQ-1392)
- feat(ui): Shipping status row (RAIN-792)
- chore(ui): Update PCI-SDK to v3 (ACQ-1667)
- chore(ui): Load Funds - Enable button when input equals the limit
- chore(ui): Disable rotation on activities
- fix(ui): Card logo placement & scale (ACQ-1214)
- fix(ui): Error not being shown when activating physical card (ACQ-1887)
- fix(ui): Order of transactions after background refresh (ACQ-1881)
- build(all): Change some dependencies to start to move away from jCenter (ACQ-1417)
- build(all): Update Mockito version to official 3.x
- build(all): Update Kotlin to 1.5.21 (ACQ-1845)
- build(all): Update ktlint to 0.41.0
- build(all): Update gradle to 7.0.2 (ACQ-1814)

# 2021-06-09 Version 3.13.0 Ui 3.13.0
- feat(ui): Collect User Address - Show zip code when selected from list(ACQ-1189)
- feat(ui): AddFunds screen - Added possibility to add cents (ACQ-1130)
- fix(ui): CountrySelector & Activate card - Topbar color fixed
- refactor(ui): AddFunds screen - Added tests also (ACQ-125)
- tests(all): Moved from jUnit4 to jUnit5 (ACQ-1130)
- feat(mobile): Money - Show Euro and Pound symbols always - Dollar when Us - fallback on System(ACQ-1189)

# 2021-05-21 Version 3.12.0 Ui 3.12.0
- fix(ui): Monthly Statement Save (RAIN-225)
- fix(ui): Collect Name Fragment - Improve visibility on small phones
- fix(ui): WebBrowserFragment - Set toolbar background color
- fix(ui): catch SendEmail Error (ACQ-1041)
- fix(ui): Fix startManageCardFlow (RAIN-675)
- chore(ui): Update AppCompat to 1.1.0 (ACQ-737)
- chore(ui): Update Material libs to 1.2.1
- chore(ui): Remove KoinComponent from BaseFragment
- chore(ui): Change default for AuthenticateOnStartup (ACQ-1077)
- fix(ui): AddFundsResult was not created correctly (ACQ-1065)
- fix(ui): Remove memory leak in TwilioVoip (ACQ-1064)
- chore(ui): Remove Zendesk Chat (RAIN-570)
- chore(ui): Update ConstraintLayout to 2.0.4

- chore: Update Junit to 4.13.2
- refactor: Moved some Strings from UiSdk to MobileSdk(ACQ-1083)
- tests: Remove useless test doubles (ACQ-1066)
- build: Update Kotlin Gradle Plugin - 1.4.32

- fix(mobile): Change Verification fallback to Failed (RAIN-571)
- refactor(mobile): Retain Error message in ServerError (ACQ-1117)
- chore(mobile): Remove Android extensions
- chore(mobile): Remove deprecated fetchFinancialAccount (ACQ-1017)
- chore(mobile): Remove FetchCardDetails endpoint (ACQ-1015)
- chore(mobile): remove Additional fields parameter (ACQ-1002)
- chore(mobile): remove deprecated cardMonthlySpending (ACQ-1020)
- tests(mobile): Add test to Koin modules

# 2021-04-26 Version 3.11.0 Ui 3.11.0
- feat: Add issue card design new parameters
- feat(ui): Add Foreign exchange rates
- feat: Show correct error on duplicate card
- fix: DateFormatOrder bug on BLU phones
- refactor: Separate ManageCardFlow in two (ManageCard & settings)
- refactor: Change trackings from viewLoaded to init
- build: Update Kotlin to 1.4.32

# 2021-04-08 Version 3.10.0 Ui 3.10.0
- feat: Order Physical Card
- feat(ui): Add SSN Validation on US
- feat(ui): Add Birthdate validation over 18 years
- build(ui): Update branch to version
- build: Update TargetSDK to 30
- build(ui): Update Glide to 4.12.0
- build(ui): Update CCP to 2.4.7
- build(ui): Update PCI-SDK to 2.1.0
- build(ui): Update Biometrics to 1.1.0
- build(mobile): Update Room to 2.2.6
- refactor: Failure
- refactor(ui): SectionSwitch
- refactor(mobile): Remove UseCaseWrapper
- chore: Added Detekt
- fix(ui): Collect Id data when onboarding
- fix(ui): Manage card empty state
- fix(mobile): logout crash on old devices

# 2021-03-15 Version 3.9.1 Ui 3.9.1
- fix: Card Settings contact button
- fix: Collect user address on dark mode
- refactor: Card service to new architecture

# 2021-03-04 Version 3.9.0 Ui 3.9.0
- feat: ACH account
- Upgraded Twilio SDK to 5.6.2
- chore(mobile): Deprecate issueCard method without applicationId

# 2021-02-23 Version 3.8.2 Ui 3.8.2
- fix(ui): Show error when no sim inserted and call to IVR
- fix(ui): Load Funds - Add Card - Remove conflicting style

# 2021-02-17 Version 3.8.1 Ui 3.8.1
- fix(mobile): Removed library used to save Card
- fix(ui): NPE on some screens
- chore(ui): Text improvements as product asked

# 2020-12-31 Version 3.8.0 Ui 3.8.0
- docs: moved docs to external repo
- feat(ui): Custodian UiD on UiSDK (Breaking change)

# 2020-12-31 Version 3.7.0 Ui 3.7.0
- feat: Chatbot

# 2020-12-16 Version 3.6.0 Ui 3.6.0
- feat: 3dsV2
- feat: authenticateOnPCI - Added None state (Breaking)

# 2020-12-16 Version 3.5.0 Ui 3.5.0

- feat: Http 429 - rate limiter
- refactor: Transaction list
- fix: Rollback Kotlin version to 1.4.0
- fix: CardView - Colors bug
- fix: BooleanListenersPool notify crash
- fix: Crash when logging out
- style: removed unnecessary constructors, lambda unit, Ktlint 0.39.0
- docs: Documentation improvements

# 2020-11-24 Version 3.4.3 Ui 3.4.3

- feat: Update kotlin to 1.4.20
- fix(ui): NoNetworkFragment error on go to background
- refactor: TransactionRepository moved to new architecture
- docs: Improvements in documentation

# 2020-11-17 Version 3.4.2 Ui 3.4.2

- fix(ui) Card pin keep working when going to background
- fix(ui) Card rounded corners
- fix(ui) Text misspellings
- fix(ui) Change hide card ttl to 3 minutes

# 2020-11-14 Version 3.4.1 Ui 3.4.1

- feat(ui): Add Test cards recognition on SBX
- fix(ui): Update security library to avoid crash
- fix(ui): Metadata in startCardFlow
- ci: Ignore readme file modifications
- build: Rollback Gradle to 4.0.2 to re-enable codecov
- docs(mobile): Docs Enhancement Sprint

# 2020-11-04 Version 3.4.0 Ui 3.4.0

- fix: Changed metadata from applyToCard to issueCard
- fix(ui): Fixed double clicks
- feat(ui): Add Start apply to card flow from Outside
- feat(ui): startManageCardFlow with ID
- feat: startCardFlow Add Metadata
- docs: Android Sdk Readme
- chore: Kotlin 1.4.10 & remove unnecessary kotlin dependencies
- chore: Update Kotlin coroutines to 1.4.0
- fix: Load funds approval code
- feat(mobile): Add metadata to card application

# 2020-09-29 Version 3.3.0 Ui 3.3.0
- `[UPDATED]` Gradle 4.1.0
- `[UPDATED]` Documentation Improvements
- `[FIXED]` Load Funds: Removed Cache, Expiration date VGS
- `[ADDED]` Metadata: Create User metadata
- `[DEPRECATED]` cardMonthlySpending method with Month name, fetchCards with no pagination, fetchFinancialAccount
- `[ADDED]` cardMonthlySpending with Month Number
- `[ADDED]` fetchCards with pagination
- `[FIXED]` Color overrides on Dark Mode
- `[UPDATED]` PCI-SDK v2

# 2020-09-29 Version 3.2.2 Ui 3.2.2
- `[UPDATED]` PCI-SDK reveal timer, Changed from 60 to 90 seconds
- `[FIXED]` Hide Keyboard on App going to background
- `[ADDED]` Collect Id: Document gets focused when type change

# 2020-09-28 Version 3.2.1 Ui 3.2.1
- `[UPDATED]` Test App: Changed Privacy & Tos Links
- `[UPDATED]` Mobile Readme
- `[UPDATED]` PCI-Sdk alert text
- `[FIXED]` Funding feature nullability
- `[FIXED]` Authentication screen now disappear when logged out from server

# 2020-09-23 Version 3.2.0 Ui 3.2.0
- Mobile Readme reviewed
- Ui changes: unified divider line colors(divider_line_color), SectionOptionWithSubtitle
- CardConfig: Moved logic to viewModel
- Gradle 4.0.1
- Kotlin 1.4
- Migrated more repositories to new architecture (Stats, MonthlyStatements)
- Funding Source Dialog: Added restrictions to show it

# 2020-09-03 Version 3.1.0 Ui 3.1.0
- Load Funds flow
- Login Guide
- Collect address now forces to have postal code
- PCI-Sdk to replace card
- Repositories refactors
- Privacy&Tos on example app
- "Bearer" on ApiKey Interceptor
- Remove unused colors & dimensions  
- Get Payment Sources Pagination

# 2020-07-01 Version 3.0.0 Ui 3.0.0
- Renamed Core Sdk to Mobile & ui references
- Server Error problems: Fixed connection, forced to http1, insert with replace on db
- Publish documentation only in PRD & publish md

# 2020-06-16 Version 2.11.4 Ui 2.8.4
- Enable Android Lint
- Improved Documentation
- Deploy to appcenter on PR to master
- Embedded Mode : X on main screen & input phone

# 2020-06-11 Version 2.11.3 Ui 2.8.3
- Gradle Version update to 4.0
- Updated Dependencies: Coroutines, okhttp, retrofit, recyclerview, android annotations, twilio, security, places
- Fix logout leaks
- Documentation: Dokka, GH Actions automation, Readme, classes visibility, etc
- NetworkHandler close socket

# 2020-05-29 Version 2.11.2 Ui 2.8.2
- Fixed error logging out

# 2020-05-29 Version 2.11.1 Ui 2.8.1
- Oncall - Added issueCard additional fields
- Oncall - Track connection errors

# 2020-05-27 Version 2.11.0 Ui 2.8.0
- Added Service Tests
- Applied KtLint
- Toolbar - Moved control to fragment
- Replaced Connectivity checker
- CardView - Matched interfaces
- Additional Fields for Issuing Card

# 2020-05-06 Version 2.10.1 Ui 2.7.1
- Remove memory leak on toolbar
- Refactor: remove unused SupressLint, shift names, room anotation processing, ui readme, separation of concerns of Api Catalog
- Gradle 3.6.3

# 2020-04-22 Version 2.10.0 Ui 2.7.0
- Collect User Data
- Branch integration

# 2020-04-22 Version 2.9.5 Ui 2.6.6
- Issue card new error management

# 2020-04-08 Ui 2.6.5
- Fix issue card problem

# 2019-04-02 Version 2.9.4 & Ui 2.6.4
- Upgrade to Gradle plugin 3.6.2
- Fix: Increase timeout to avoid problems issuing card

# 2020-04-01 Version 2.9.3 & Ui 2.6.3
- Upgrade to Gradle plugin 3.6.1 & Kotlin 1.3.71
- Update many dependencies & reduced build time
- Remove theme 1

# 2020-03-16 Version 2.9.2 & Ui 2.6.2
- Card Expiration date - Accept new format
- Enhance Either
- New Publish scripts
- Display app version - fixes
- Biometric auth - fixes

# 2020-02-28 Version 2.9.1 & Ui 2.6.1
- Verify Email on Theme 2
- Added App version on UiSdk settings
- Removed Sign on operation details

# 2020-02-19 Version 2.9.0 & Ui 2.6.0
- Dark Mode
- Biometrics & Passcode
- Fix monthly statements share

# 2020-01-24 Version 2.8.3 &Ui 2.5.3
- Improved Monthly Stats behaviour
- Improved Ui on KYC 
- OAuth - removed extra call
- Changed provider name to install two apps

# 2020-01-17 Version 2.8.2 &Ui 2.5.2
- Biometrics: Changed Behaviour when authenticateWithPINOnPCI flag is off

# 2020-01-17 Version 2.8.1 &Ui 2.5.1
- Biometrics: Fixed Errors
- Android API 29
- Changed Markdown Library

# 2019-12-12 Version 2.8.0 &Ui 2.5.0
- Biometrics & Pin Auth
- Replaced Biometrics Lib for new one
- Birthday Date picker locale aware
- AptoTextView: Take some localisation code from views back to XML

# 2019-11-20 Version 2.7.1 &Ui 2.4.2
- Monthly stats - Improved the speed it's shown

# 2019-11-19 Version Ui 2.4.1
- Fixed background transactions pooling

# 2019-11-18 Version Core 2.7.0 & Ui 2.4.0
- Remove Dev Environment
- Bugfixes: NPE on MonthlyStats - Serialization
- Added AptoUiSdk - Changed the way Koin is initialized

# 2019-11-11 Version Core 2.6.2 & Ui 2.3.2
- Monthly Stats - Fixed Chart & Duplicated requests

# 2019-11-07 Version Core 2.6.1 & Ui 2.3.1
- DataPointParser: Fixed when unknown data point
- ID datapoint shown in UI
- Menu item tint on Old Android

# 2019-11-06 Version Core 2.6.0 & Ui 2.3.0
- Updated Library versions & Java 8
- IssueCard Error: More data is logged
