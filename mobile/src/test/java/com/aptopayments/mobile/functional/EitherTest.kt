package com.aptopayments.mobile.functional

import com.aptopayments.mobile.functional.Either.Left
import com.aptopayments.mobile.functional.Either.Right
import org.junit.Test
import kotlin.test.assertEquals
import kotlin.test.assertFalse
import kotlin.test.assertNull
import kotlin.test.assertTrue

private const val STRING_INPUT = "test"
private const val MAPPED_STRING_INPUT = "hello test"
private const val INTEGER_INPUT = 3
private const val SECOND_INTEGER_INPUT = 11

class EitherTest {

    private val stringMapFunction = { input: String -> "hello $input" }

    @Test
    fun `Either Right should return correct type`() {
        val result = Right("ironman")

        assertTrue(result.isRight)
        assertFalse(result.isLeft)
        result.either(
            {},
            { right ->
                assertEquals(right, "ironman")
            }
        )
    }

    @Test
    fun `Either Left should return correct type`() {
        val result = Left("ironman")

        assertFalse(result.isRight)
        assertTrue(result.isLeft)
        result.runIfLeft { assertEquals(it, "ironman") }
    }

    @Test
    fun `map Either is not changed when is Left`() {
        val sut = Left(STRING_INPUT)

        val result = sut.map(stringMapFunction)

        result.either({ assertEquals(it, STRING_INPUT) }, { throw RuntimeException() })
    }

    @Test
    fun `map Either is changed when is Right`() {
        val sut = Right(STRING_INPUT)

        val result = sut.map(stringMapFunction)

        result.either({ throw RuntimeException() }, { assertEquals(it, MAPPED_STRING_INPUT) })
    }

    @Test
    fun `exists is false when Either is Left`() {
        val sut = Left(INTEGER_INPUT) as Either<Int, Int>

        val result = sut.exists { it > 10 }

        assertFalse(result)
    }

    @Test
    fun `exists is false when it doesn't matches right condition`() {
        val sut = Right(INTEGER_INPUT)

        val result = sut.exists { it > 10 }

        assertFalse(result)
    }

    @Test
    fun `exists is true when it matches right condition`() {
        val sut = Right(11)

        val result = sut.exists { it > 10 }

        assertTrue(result)
    }

    @Test
    fun `runIfRight predicate is Executed when it matches right condition`() {
        val sut = Right(11)
        var executed = false
        sut.runIfRight {
            assertEquals(11, it)
            executed = true
        }

        assertTrue(executed)
    }

    @Test
    fun `runIfRight predicate is not executed when it matches left condition`() {
        val sut = Left(11)

        sut.runIfRight { throw RuntimeException() }
    }

    @Test
    fun `flatMap Either is not changed when is Left`() {
        val sut = Left(STRING_INPUT) as Either<String, Int>

        val result = sut.flatMap { Right(it % 2) }

        result.either({ assertEquals(it, STRING_INPUT) }, { throw RuntimeException() })
    }

    @Test
    fun `flatMap Either is changed when is Right`() {
        val sut = Right(INTEGER_INPUT)

        val result: Either<*, Int> = sut.flatMap { Right(it % 2) }

        result.either({ throw RuntimeException() }, { assertTrue(it == 1) })
    }

    @Test
    fun `getOrElse returns right value when is right`() {
        val sut = Right(INTEGER_INPUT)

        val result = sut.getOrElse { SECOND_INTEGER_INPUT }

        assertEquals(result, INTEGER_INPUT)
    }

    @Test
    fun `getOrElse returns given value when is left`() {
        val sut = Left(INTEGER_INPUT)

        val result = sut.getOrElse { SECOND_INTEGER_INPUT }

        assertEquals(result, SECOND_INTEGER_INPUT)
    }

    @Test
    fun `orNull returns right value when is right`() {
        val sut = Right(INTEGER_INPUT)

        val result = sut.orNull()

        assertEquals(result, INTEGER_INPUT)
    }

    @Test
    fun `orNull returns null value when is left`() {
        val sut = Left(INTEGER_INPUT)

        val result = sut.orNull()

        assertNull(result)
    }

    @Test
    fun `contains returns true when right value is equal`() {
        val sut = Right(INTEGER_INPUT)

        val result = sut.contains(INTEGER_INPUT)

        assertTrue(result)
    }

    @Test
    fun `contains returns true when right value is different`() {
        val sut = Right(INTEGER_INPUT)

        val result = sut.contains(SECOND_INTEGER_INPUT)

        assertFalse(result)
    }

    @Test
    fun `contains returns false when Left`() {
        val sut = Left(INTEGER_INPUT)

        val result = sut.contains(INTEGER_INPUT)

        assertFalse(result)
    }

    @Test
    fun `left return Left with given value inside`() {
        val sut = INTEGER_INPUT.left()

        assertTrue(sut.isLeft)

        sut.either({ assertEquals(it, INTEGER_INPUT) }, { throw RuntimeException() })
    }

    @Test
    fun `right return Right with given value inside`() {
        val sut = INTEGER_INPUT.right()

        assertTrue(sut.isRight)

        assertTrue { sut.getOrElse { -1 } == INTEGER_INPUT }
    }

    @Test
    fun `rightIfNotNull return right when value given`() {
        val sut = INTEGER_INPUT.rightIfNotNull { STRING_INPUT }

        assertTrue(sut.isRight)
        assertEquals(sut.getOrElse { -1 }, INTEGER_INPUT)
    }

    @Test
    fun `rightIfNotNull return left when null given`() {
        val sut = null.rightIfNotNull { STRING_INPUT }

        assertTrue(sut.isLeft)

        sut.either({ assertEquals(it, STRING_INPUT) }, { throw RuntimeException() })
    }
}
