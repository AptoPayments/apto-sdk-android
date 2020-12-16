package com.aptopayments.mobile.utils

import kotlin.math.pow
import kotlin.math.roundToLong

private const val DEFAULT_TOTAL_MAX_RETRY = 10 * 1000L // 10 seconds total delay
private const val DEFAULT_MAX_DELAY = 3 * 1000L // 3 seconds max between retries
private const val DEFAULT_MIN_DELAY = 25L // Almost immediate retry
private const val DEFAULT_RETRY_MULTIPLIER = 1.5

object RetryMechanism {

    fun <T> retryWithBackOff(
        delayCalculator: (Attempt) -> Long? = createDelayCalculator(),
        delayOperation: (Long) -> Unit = { Thread.sleep(it) },
        retryCondition: (Attempt) -> Boolean = { true },
        action: () -> T
    ): T {
        var current = Attempt()
        while (true) {
            try {
                return action()
            } catch (e: Exception) {
                current = current.copy(exception = e)
            }

            if (!retryCondition(current)) throw current.exception!!

            val newDelay = delayCalculator(current)

            if (newDelay == null) {
                throw current.exception!!
            } else {
                delayOperation(newDelay)
            }

            current = current.copy(
                count = current.count + 1,
                lastDelay = newDelay,
                totalDelay = current.totalDelay + newDelay
            )
        }
    }

    private fun createDelayCalculator(
        maxTotalDelay: Long = DEFAULT_TOTAL_MAX_RETRY,
        maxDelay: Long = DEFAULT_MAX_DELAY,
        minDelay: Long = DEFAULT_MIN_DELAY,
        multiplier: Double = DEFAULT_RETRY_MULTIPLIER
    ): (Attempt) -> Long? = { attempt ->
        if (attempt.totalDelay > maxTotalDelay) {
            null
        } else {
            val exp = 2.0.pow(attempt.count.toDouble())
            val calculatedDelay = (multiplier * exp).roundToLong()

            val newDelay = if (calculatedDelay > attempt.lastDelay) {
                (attempt.lastDelay..calculatedDelay).random()
            } else {
                (calculatedDelay..attempt.lastDelay).random()
            }

            newDelay.coerceAtMost(maxDelay).coerceAtLeast(minDelay)
        }
    }

    data class Attempt(
        val count: Int = 1,
        val totalDelay: Long = 0L,
        val lastDelay: Long = 0L,
        val exception: Exception? = null
    )
}
