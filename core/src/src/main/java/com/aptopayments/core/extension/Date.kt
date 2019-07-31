package com.aptopayments.core.extension

import android.annotation.SuppressLint
import java.text.DateFormat
import java.text.ParseException
import java.text.SimpleDateFormat
import java.util.*

object ISO8601 {

    private val dateFormatter = SimpleDateFormat("yyyy-MM-dd")
    private val dateTimeFormatter = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ssZ")

    fun formatDate(date: Date) = dateFormatter.format(date)

    fun formatDateTime(date: Date) = dateTimeFormatter.format(date)

    @Throws(ParseException::class)
    fun parseDate(iso8601string: String): Date {
        return dateFormatter.parse(iso8601string)
    }

    @Throws(ParseException::class)
    fun parseDateTime(iso8601string: String): Date {
        var s = iso8601string.replace("Z", "+00:00")
        try {
            s = s.substring(0, 22) + s.substring(23)  // to get rid of the ":"
        } catch (e: IndexOutOfBoundsException) {
            throw ParseException("Invalid length", 0)
        }
        return dateTimeFormatter.parse(s)
    }
}

@SuppressLint("SimpleDateFormat")
fun Date.toTransactionListFormat(): String = SimpleDateFormat("dd MMM '-' h:mm a").format(this)

fun Date.toTransactionDetailsFormat(): String = DateFormat.getDateTimeInstance(DateFormat.MEDIUM, DateFormat.SHORT).format(this)

fun parseISO8601Date(iso8601string: String): Date {
    return ISO8601.parseDate(iso8601string)
}

fun Date.getMonthYear(): Pair<Int, Int> {
    val calendar = Calendar.getInstance()
    calendar.time = this
    return Pair(calendar.get(Calendar.MONTH), calendar.get(Calendar.YEAR))
}

fun Date.add(field: Int, count: Int): Date {
    val calendar = Calendar.getInstance()
    calendar.time = this
    calendar.add(field, count)
    return calendar.time
}

fun Int.stringFromTimeInterval(): String {
    return if (this > 3600) {
        String.format("%02d:%02d:%02d", this / 3600, this / 60, this % 60)
    } else {
        String.format("%02d:%02d", this / 60, this % 60)
    }
}
