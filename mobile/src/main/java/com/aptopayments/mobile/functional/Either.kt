package com.aptopayments.mobile.functional

/**
 * Represents a value of one of two possible types (a disjoint union).
 * Instances of [Either] are either an instance of [Left] or [Right].
 * FP Convention dictates that [Left] is used for "failure"
 * and [Right] is used for "success".
 *
 * @see Left
 * @see Right
 */
sealed class Either<out L, out R> {
    /** * Represents the left side of [Either] class which by convention is a "Failure". */
    data class Left<out L>(val a: L) : Either<L, Nothing>()

    /** * Represents the right side of [Either] class which by convention is a "Success". */
    data class Right<out R>(val b: R) : Either<Nothing, R>()

    val isRight get() = this is Right<R>
    val isLeft get() = this is Left<L>

    fun <L> left(a: L) = Left(a)
    fun <R> right(b: R) = Right(b)

    fun <T> either(ifLeft: (L) -> T, ifRight: (R) -> T): T =
        when (this) {
            is Left -> ifLeft(a)
            is Right -> ifRight(b)
        }

    /**
     * The given function is applied if this is a [Right].
     *
     * * Example:
     * ```
     * Right(12).map { "flower" } // Result: Right("flower")
     * Left(12).map { "flower" }  // Result: Left(12)
     * ```
     */
    fun <T> map(f: (R) -> T): Either<L, T> = either({ Left(it) }, { Right(f(it)) })

    /**
     * Returns `false` if [Left] or returns the result of the application of
     * the given predicate to the [Right] value.
     *
     * ```
     * Example:
     *
     * Right(12).exists { it > 10 } // Result: true
     * Right(7).exists { it > 10 }  // Result: false
     *
     * val left: Either<Int, Int> = Left(12)
     * left.exists { it > 10 }      // Result: false
     * ```
     */
    fun exists(predicate: (R) -> Boolean): Boolean =
        either({ false }, { predicate(it) })

    /**
     * Execute the predicate if [Right]
     *
     * ```
     * Example:
     *
     * Right(12).runIfRight { doSomeAction() } // doSomeAction is executed
     *
     * Left(12).runIfRight { doSomeAction() }      // doSomeAction is not executed
     * ```
     */
    fun runIfRight(predicate: (R) -> Unit): Either<L, R> {
        either({ }, { predicate(it) })
        return this
    }

    /**
     * Execute the predicate if [Left]
     *
     * ```
     * Example:
     *
     * Right(12).runIfLeft { doSomeAction() } // doSomeAction is not executed
     *
     * Left(12).runIfLeft { doSomeAction() }      // doSomeAction is executed
     * ```
     */
    fun runIfLeft(predicate: (L) -> Unit): Either<L, R> {
        either({ predicate(it) }, { })
        return this
    }

    companion object {
        fun <L, R> cond(test: Boolean, ifTrue: () -> R, ifFalse: () -> L): Either<L, R> =
            if (test) Right(ifTrue()) else Left(ifFalse())

        /**
         * Lets us do the computation of a suspend function and
         * transform the result into a [Left] if the error was NonFatal
         * or a [Right] if it finished successfully
         */
        suspend fun <L, R> catch(fe: (Throwable) -> L, f: suspend () -> R): Either<L, R> =
            try {
                f().right()
            } catch (t: Throwable) {
                fe(t.nonFatalOrThrow()).left()
            }

        suspend fun <R> catch(f: suspend () -> R): Either<Throwable, R> = catch({ identity(it) }, f)
    }
}

/**
 * Applies the given function `f` if this is a [Either.Right], otherwise returns this if this is a [Either.Left].
 *  * Example:
 * ```
 * Right(12).flatMap { Right(it % 2) } // Result: Either.Right(0)
 * Right(12).flatMap { Left(it % 2) } // Result: Either.Left(0)
 * ```
 */
fun <T, L, R> Either<L, R>.flatMap(fn: (R) -> Either<L, T>): Either<L, T> =
    when (this) {
        is Either.Left -> Either.Left(a)
        is Either.Right -> fn(b)
    }

/**
 * Returns the value from this [Either.Right] or the given argument if this is a [Either.Left].
 *
 * ```
 * Example:
 *
 * Right(12).getOrElse(17) // Result: 12
 * Left(12).getOrElse(17)  // Result: 17
 * ```
 */
fun <R> Either<*, R>.getOrElse(default: () -> R): R =
    when (this) {
        is Either.Left -> default()
        is Either.Right -> b
    }

/**
 * Returns the value from this [Either.Right] or null if this is a [Either.Left].
 *
 * ```
 * Example:
 *
 * Right(12).orNull() // Result: 12
 * Left(12).orNull()  // Result: null
 * ```
 */
fun <B> Either<*, B>.orNull(): B? = getOrElse { null }

fun <L, R> Either<L, R>.contains(elem: R): Boolean =
    when (this) {
        is Either.Left -> false
        is Either.Right -> b == elem
    }

/**
 * Creates a [Either.Left] out of any object
 *
 * ```
 * Example:
 *
 * 2.left() // is the same as Either.Left(2)
 * ```
 */
fun <L> L.left(): Either<L, Nothing> = Either.Left(this)

/**
 * Creates a [Either.Right] out of any object
 *
 * ```
 * Example:
 *
 * 2.right() // is the same as Either.Right(2)
 * ```
 */
fun <R> R.right(): Either<Nothing, R> = Either.Right(this)

/**
 * Returns [Either.Right] if the value of type B is not null, otherwise the specified A value wrapped into an
 * [Either.Left].
 *
 * ```
 * Example:
 *
 * "value".rightIfNotNull { "left" } // Right(b="value")
 * null.rightIfNotNull { "left" }    // Left(a="left")
 * ```
 */
fun <L, R> R?.rightIfNotNull(default: () -> L): Either<L, R> = when (this) {
    null -> Either.Left(default())
    else -> Either.Right(this)
}

/**
 * Applies the given function `f` if this is a [Either.Left], otherwise returns this if this is a [Either.Right].
 * This is like `flatMap` for the exception.
 */
fun <L, R> Either<L, R>.handleErrorWith(f: (L) -> Either<L, R>): Either<L, R> = when (this) {
    is Either.Left -> f(a)
    is Either.Right -> this
}

fun <A> identity(a: A): A = a
