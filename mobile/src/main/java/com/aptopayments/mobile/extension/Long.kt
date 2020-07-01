package com.aptopayments.mobile.extension

import org.threeten.bp.*

internal fun Long.toLocalDateTime(zone: ZoneId = ZoneOffset.UTC): LocalDateTime =
    Instant.ofEpochMilli(this).atZone(zone).toLocalDateTime()

internal fun Long.toZonedDateTime(zone: ZoneId = ZoneOffset.UTC): ZonedDateTime =
    Instant.ofEpochMilli(this).atZone(zone)
