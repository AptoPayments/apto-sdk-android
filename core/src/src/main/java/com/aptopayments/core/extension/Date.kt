package com.aptopayments.core.extension

import android.annotation.SuppressLint
import java.text.DateFormat
import java.text.ParseException
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Date
import java.util.Locale

object ISO8601 {

    private val dateFormatter = SimpleDateFormat("yyyy-MM-dd", Locale.US)

    fun formatDate(date: Date) = dateFormatter.format(date)

    @Throws(ParseException::class)
    fun parseDate(iso8601string: String): Date {
        return dateFormatter.parse(iso8601string)
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
