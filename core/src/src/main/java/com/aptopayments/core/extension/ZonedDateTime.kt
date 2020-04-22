package com.aptopayments.core.extension

import org.threeten.bp.LocalDateTime
import org.threeten.bp.ZoneId
import org.threeten.bp.ZoneOffset
import org.threeten.bp.ZonedDateTime
import org.threeten.bp.format.DateTimeFormatter

private val formatterTransactionList = DateTimeFormatter.ofPattern("dd MMM '-' h:mm a")
private val formatterTransactionDetails = DateTimeFormatter.ofPattern("MMM d, YYYY h:mm a")

fun ZonedDateTime.formatForTransactionList(): String =
    this.toLocalDateTimeAtDefaultZone().format(formatterTransactionList)

fun ZonedDateTime.formatForTransactionDetails(): String =
    this.toLocalDateTimeAtDefaultZone().format(formatterTransactionDetails)

fun ZonedDateTime.toLocalDateTimeAtTimeZone(zone: ZoneId): LocalDateTime =
    this.withZoneSameInstant(zone).toLocalDateTime()

fun ZonedDateTime.toLocalDateTimeAtDefaultZone(): LocalDateTime =
    this.toLocalDateTimeAtTimeZone(ZoneOffset.systemDefault())
