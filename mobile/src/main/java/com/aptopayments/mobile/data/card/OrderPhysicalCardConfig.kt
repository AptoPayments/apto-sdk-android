package com.aptopayments.mobile.data.card

import com.aptopayments.mobile.data.user.AddressDataPoint

/**
 * This object holds the current configuration to Order a defined Physical card
 *
 * @param issuanceFee [Money] contains the cost of issuing the card
 * @param userAddress [AddressDataPoint] contains the address that the card will be shipped to
 */
data class OrderPhysicalCardConfig(
    val issuanceFee: Money,
    val userAddress: AddressDataPoint? = null
)
