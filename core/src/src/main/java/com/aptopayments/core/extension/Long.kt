package com.aptopayments.core.extension

import org.threeten.bp.*

fun Long.toLocalDateTime(zone: ZoneId = ZoneOffset.UTC): LocalDateTime =
    Instant.ofEpochMilli(this).atZone(zone).toLocalDateTime()

fun Long.toZonedDateTime(zone: ZoneId = ZoneOffset.UTC): ZonedDateTime =
    Instant.ofEpochMilli(this).atZone(zone)
