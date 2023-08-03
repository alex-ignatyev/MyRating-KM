package utils.answer

import utils.answer.Answer.Companion
import utils.answer.Answer.Failure

class Answer<out T>(val value: Any?) {

    val isSuccess: Boolean
        get() = value !is Failure

    val isFailure: Boolean
        get() = value is Failure

    fun errorOrNull(): Failure? = when (value) {
        is Failure -> value
        else -> null
    }

    override fun toString(): String =
        when (value) {
            is Failure -> value.toString()
            else -> "Success($value)"
        }

    companion object {
        fun <T> success(value: T): Answer<T> = Answer(value)
        fun <T> failure(code: ErrorCode, message: String = ""): Answer<T> = Answer(Failure(code, message))
    }

    class Failure(val code: ErrorCode, val message: String) {
        override fun equals(other: Any?): Boolean = other is Failure && message == other.message
        override fun hashCode(): Int = message.hashCode()
        override fun toString(): String = "Code(${code}) message(${message})"
    }
}

inline fun <T> Answer<T>.onFailure(action: (error: Failure) -> Unit): Answer<T> {
    errorOrNull()?.let { action(it) }
    return this
}

inline fun <T> Answer<T>.onSuccess(action: (value: T) -> Unit): Answer<T> {
    if (isSuccess) action(value as T)
    return this
}

inline fun <R, T> Answer<T>.mapResult(transform: (value: T) -> Answer<R>): Answer<R> {
    return fold(
        onSuccess = { transform(it) },
        onFailure = { Companion.failure(it.code, it.message) }
    )
}

inline fun <R, T> Answer<T>.map(transform: (value: T) -> R): Answer<R> {
    return when {
        isSuccess -> Companion.success(transform(value as T))
        else -> Answer(value)
    }
}

inline fun <R, T> Answer<T>.fold(
    onSuccess: (value: T) -> R,
    onFailure: (exception: Failure) -> R
): R {
    return when (val error = errorOrNull()) {
        null -> onSuccess(value as T)
        else -> onFailure(error)
    }
}
