package com.comst.data.network

import com.comst.data.dto.ErrorResponse
import com.comst.domain.util.DomainResult
import com.comst.model.exception.BadRequestException
import com.comst.model.exception.BaseServerError
import com.comst.model.exception.ForbiddenException
import com.comst.model.exception.NetworkException
import com.comst.model.exception.NotFoundException
import com.comst.model.exception.UnknownException
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

sealed interface ApiResult<out T> {
    data class Success<T>(val data: T) : ApiResult<T>

    sealed interface Failure : ApiResult<Nothing> {
        data class HttpError(val code: Int, val message: String, val body: String) : Failure
        data class NetworkError(val throwable: Throwable) : Failure
        data class UnknownApiError(val throwable: Throwable) : Failure

        fun safeThrowable(): Throwable =
            when (this) {
                is HttpError -> handleHttpError(this)
                is NetworkError -> throwable
                is UnknownApiError -> throwable
            }
    }

    val isSuccess: Boolean
        get() = this is Success

    val isFailure: Boolean
        get() = this is Failure

    fun getOrThrow(): T {
        throwFailure()
        return (this as Success).data
    }

    fun getOrNull(): T? =
        when (this) {
            is Success -> data
            else -> null
        }

    fun failureOrThrow(): Failure {
        throwOnSuccess()
        return this as Failure
    }

    fun exceptionOrNull(): Throwable? =
        when (this) {
            is Failure -> safeThrowable()
            else -> null
        }

    companion object {
        fun <R> successOf(result: R): ApiResult<R> = Success(result)
    }
}

inline fun <T> ApiResult<T>.onSuccess(
    action: (value: T) -> Unit,
): ApiResult<T> {
    if (isSuccess) action(getOrThrow())
    return this
}

inline fun <T> ApiResult<T>.onFailure(
    action: (error: ApiResult.Failure) -> Unit,
): ApiResult<T> {
    if (isFailure) action(failureOrThrow())
    return this
}

internal fun ApiResult<*>.throwOnSuccess() {
    if (this is ApiResult.Success) throw IllegalStateException("Cannot be called under Success conditions.")
}

internal fun ApiResult<*>.throwFailure() {
    if (this is ApiResult.Failure) {
        throw safeThrowable()
    }
}


private fun handleHttpError(httpError: ApiResult.Failure.HttpError): Exception {
    val errorResponse = MoshiProvider.getErrorBody(httpError.body)
    return errorResponse?.run {
        handleServerError(this)
    } ?: run {
        handleNonServerError(httpError.code)
    }
}

private fun handleServerError(errorResponse: ErrorResponse): Exception = runCatching {
    BaseServerError.valueOf(errorResponse.errorType).exception
}.getOrNull() ?: handleNonServerError(400)


private fun handleNonServerError(httpStatusCode: Int): Exception = when (httpStatusCode) {
    400 -> BadRequestException()
    403 -> ForbiddenException()
    404 -> NotFoundException()
    500, 501, 502, 503, 504, 505 -> NetworkException()
    else -> UnknownException()
}

fun <T, R> Flow<ApiResult<T>>.mapToDomainResult(transform: (T) -> R): Flow<DomainResult<R>> {
    return this.map { apiResult ->
        when (apiResult) {
            is ApiResult.Success -> DomainResult.Success(transform(apiResult.data))
            is ApiResult.Failure -> DomainResult.Failure(apiResult.safeThrowable())
        }
    }
}