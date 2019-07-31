package com.aptopayments.core.analytics

enum class Event(val event: String) {
    AuthInputPhone("auth.input_phone.start"),
    AuthInputEmail("auth.input_email.start"),
    AuthVerifyPhone("auth.verify_phone.start"),
    AuthVerifyEmail("auth.verify_email.start"),
    AuthVerifyBirthdate("auth.verify_birthdate.start"),
    SelectBalanceStoreOauthLogin("select_balance_store.login.start"),
    SelectBalanceStoreOauthConfirm("select_balance_store.oauth_confirm.start"),
    SelectBalanceStoreLoginConnectTap("select_balance_store.login.connect"),
    SelectBalanceStoreOauthConfirmTap("select_balance_store.oauth_confirm.confirm"),
    SelectBalanceStoreOauthConfirmCountryUnsupported("select_balance_store.oauth_confirm.country_unsupported"),
    SelectBalanceStoreOauthConfirmRegionUnsupported("select_balance_store.oauth_confirm.region_unsupported"),
    SelectBalanceStoreOauthConfirmAddressUnverified("select_balance_store.oauth_confirm.address_unverified"),
    SelectBalanceStoreOauthConfirmCurrencyUnsupported("select_balance_store.oauth_confirm.currency_unsupported"),
    SelectBalanceStoreOauthConfirmCannotCaptureFunds("select_balance_store.oauth_confirm.cannot_capture_funds"),
    SelectBalanceStoreOauthConfirmInsufficientFunds("select_balance_store.oauth_confirm.insufficient_funds"),
    SelectBalanceStoreOauthConfirmBalanceNotFound("select_balance_store.oauth_confirm.balance_not_found"),
    SelectBalanceStoreOauthConfirmAccessTokenInvalid("select_balance_store.oauth_confirm.access_token_invalid"),
    SelectBalanceStoreOauthConfirmScopesRequired("select_balance_store.oauth_confirm.scopes_required"),
    SelectBalanceStoreOauthConfirmLegalNameMissing("select_balance_store.oauth_confirm.legal_name_missing"),
    SelectBalanceStoreOauthConfirmDobMissing("select_balance_store.oauth_confirm.dob_missing"),
    SelectBalanceStoreOauthConfirmDobInvalid("select_balance_store.oauth_confirm.dob_invalid"),
    SelectBalanceStoreOauthConfirmAddressMissing("select_balance_store.oauth_confirm.address_missing"),
    SelectBalanceStoreOauthConfirmEmailMissing("select_balance_store.oauth_confirm.email_missing"),
    SelectBalanceStoreOauthConfirmRefreshDetailsTap("select_balance_store.oauth_confirm.refresh_details"),
    SelectBalanceStoreOauthConfirmConfirmBackTap("select_balance_store.oauth_confirm.back"),
    SelectBalanceStoreOauthConfirmEmailError("select_balance_store.oauth_confirm.email_error"),
    SelectBalanceStoreOauthConfirmUnknownError("select_balance_store.oauth_confirm.unknown_error"),
    SelectBalanceStoreOauthConfirmEmailSendsDisabled("select_balance_store.oauth_confirm.email_sends_disabled"),
    SelectBalanceStoreOauthConfirmInsufficientApplicationLimit("select_balance_store.oauth_confirm.insufficient_application_limit"),
    SelectBalanceStoreOauthConfirmIdentityNotVerified("select_balance_store.oauth_confirm.identity_not_verified"),
    Waitlist("waitlist.waitlist.start"),
    Disclaimer("disclaimer.disclaimer.start"),
    DisclaimerAccept("disclaimer.disclaimer.accept"),
    DisclaimerReject("disclaimer.disclaimer.reject"),
    IssueCard("issue_card.issue_card.start"),
    CollectUserDataPersonalInfo("collect_user_data.personal_info.start"),
    CollectUserDataDateOfBirth("collect_user_data.dob.start"),
    CollectUserDataAddress("collect_user_data.address.start"),
    ManageCard("manage_card.manage_card.start"),
    ManageCardKycStatus("manage_card.kyc.start"),
    ManageCardActivatePhysicalCard("manage_card.activate_physical_card.start"),
    ManageCardActivatePhysicalCardOverlay("manage_card.activate_physical_card_overlay.start"),
    ManageCardGetPinNue("manage_card.get_pin_nue.start"),
    ManageCardSetPin("manage_card.set_pin.start"),
    ManageCardConfirmPin("manage_card.set_pin.confirm"),
    ManageCardFundingSourceSelector("manage_card.funding_source_selector.start"),
    ManageCardCardSettings("manage_card.card_settings.start"),
    ManageCardVoipCallStarted("manage_card.get_pin_voip.call_started"),
    ManageCardVoipCallEnded("manage_card.get_pin_voip.call_ended"),
    ManageCardVoipCallError("manage_card.get_pin_voip.call_error"),
    AccountSettings("account_settings.account_settings.start"),
    AccountSettingsNotificationPreferences("account_settings.notification_preferences.start"),
    MonthlySpending("stats.monthly_spending.start"),
    TransactionList("transaction_list.transaction_list.start"),
    NoNetwork("no_network.no_network.start"),
    Maintenance("maintenance.maintenance.start"),
    TransactionDetail("transaction_details.transaction_details.start"),
    LoginUser("auth.login_user.success"),
    CreateUser("auth.create_user.success"),
    LogoutUser("auth.logout_user.success"),
    CardProductSelectorCountrySelectorShown("select_card_product.country_picker.start"),
    CardProductSelectorProductSelected("select_card_product.country_picker.confirm"),
    CardProductSelectorCountrySelectorClosed("select_card_product.country_picker.back"),
    IssueCardInsufficientFunds("issue_card.issue_card.insufficient_funds"),
    IssueCardEmailSendsDisabled("issue_card.issue_card.email_sends_disabled"),
    IssueCardInsufficientApplicationLimit("issue_card.issue_card.insufficient_application_limit"),
    IssueCardUnknownError("issue_card.issue_card.unknown_error")
}
