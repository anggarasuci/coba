package com.example.coba.data.model

import com.google.gson.Gson
import com.google.gson.annotations.SerializedName

interface ResponseBodyInterface {
    fun toErrorJson(status: Int, message: String): String
}

/**
 * should be adjustment based on from json object api
 */
data class Response<T>(
    val message: String,
    val status: Int,
    val content: T,
    @SerializedName("items")
    val contents: T,
    val errors: List<Errors>,
    val totalCount: Int = 0,
    val incompleteResults: Boolean = false
)

data class Errors(
    val resource: String = "",
    val field: String = "",
    val code: String = ""
)

data class ErrorModel(
    val code: String = "",
    val message: String = "",
    val reasons: Map<String, String> = emptyMap()
)

data class ResponseError(
    val rid: String = "",
    val status: Int = 0,
    val error: ErrorModel = ErrorModel()
): ResponseBodyInterface {
    override fun toErrorJson(status: Int, message: String): String {
        return Gson().toJson(
            ResponseError(
                status = status,
                error = ErrorModel(message = message)
            )
        )
    }
}