package com.aptopayments.mobile.extension

import com.aptopayments.mobile.functional.Either
import kotlin.test.assertEquals
import kotlin.test.assertTrue

fun <L, R> Either<L, R>.shouldBeRightAndEqualTo(value: R) {
    assertTrue(this.isRight)
    runIfRight { assertEquals(value, it) }
}

fun <L, R> Either<L, R>.shouldBeLeftAndEqualTo(value: L) {
    assertTrue(this.isLeft)
    this.runIfLeft { assertEquals(value, it) }
}

fun <L, R> Either<L, R>.shouldBeLeftAndInstanceOf(value: Class<*>) {
    assertTrue(this.isLeft)
    this.runIfLeft { assertTrue(value.isInstance(it)) }
}

fun <L, R> Either<L, R>.shouldBeRightAndInstanceOf(value: Class<*>) {
    assertTrue(this.isRight)
    this.runIfRight { assertTrue(value.isInstance(it)) }
}
