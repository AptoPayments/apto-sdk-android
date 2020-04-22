package com.aptopayments.core.extension

fun Int.stringFromTimeInterval(): String {
    return if (this > 3600) {
        String.format("%02d:%02d:%02d", this / 3600, this / 60, this % 60)
    } else {
        String.format("%02d:%02d", this / 60, this % 60)
    }
}
