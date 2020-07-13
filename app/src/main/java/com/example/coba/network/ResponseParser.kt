package com.example.coba.network

import com.example.coba.domain.Result
import com.example.coba.data.model.Response
import kotlinx.coroutines.Deferred

interface ResponseParserAdapter {
    fun <T> create(): ResponseParser<T>
}

/**
 * should be adjustment by response api
 */
class ResponseParser<T> {
    suspend fun parseResult(result: Deferred<Response<T>>): Result<Response<T>> {
        val r = result.await()
        @Suppress("SENSELESS_COMPARISON")
        return when {
            (r.contents != null) || (r.content != null) -> Result.Success(r)
            r.errors != null -> Result.Error(
                r.message,
                HttpStatus.UNPROCESSABLE_ENTITY,
                hashMapOf(r.errors.first().field to r.errors.first().code)
            )
            r.message != null ->
                Result.Error(
                    r.message,
                    HttpStatus.MULTI_STATUS,
                    emptyMap()
                )
            r.status == HttpStatus.FORBIDDEN ->
                Result.Error("Forbidden", HttpStatus.FORBIDDEN, emptyMap())
            else -> //sometimes service return outside the model format e.g.: only return {"message": "Invalid Authorization"}
                Result.Error("Unknown Error", HttpStatus.EXPECTATION_FAILED, emptyMap())
        }
    }

    companion object {
        val Factory: ResponseParserAdapter = object : ResponseParserAdapter {
            override fun <TT> create(): ResponseParser<TT> {
                return ResponseParser()
            }
        }

    }
}