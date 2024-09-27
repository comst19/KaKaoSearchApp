package com.comst.domain.util

sealed class DomainResult<out T> {
    data class Success<T>(val data: T) : DomainResult<T>()
    data class Failure(val exception: Throwable) : DomainResult<Nothing>()

    val isSuccess: Boolean get() = this is Success<T>
    val isFailure: Boolean get() = this is Failure

    fun getOrThrow(): T {
        if (this is Success) return data
        else throw (this as Failure).exception
    }

    fun getOrNull(): T? = (this as? Success)?.data
}

inline fun <T> DomainResult<T>.onSuccess(action: (T) -> Unit): DomainResult<T> {
    if (this is DomainResult.Success) action(data)
    return this
}

inline fun <T> DomainResult<T>.onFailure(action: (Throwable) -> Unit): DomainResult<T> {
    if (this is DomainResult.Failure) action(exception)
    return this
}