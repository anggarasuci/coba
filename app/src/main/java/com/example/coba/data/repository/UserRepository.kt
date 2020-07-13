package com.example.coba.data.repository

import com.example.coba.data.model.user.RequestUserSearch
import com.example.coba.data.model.user.ResponseUserSearch
import com.example.coba.data.remote.UserRemoteDataSource
import com.example.coba.domain.Result

class UserRepository(
    private val userRemoteDataSource: UserRemoteDataSource
) {
    suspend fun getUserSearch(params: RequestUserSearch): Result<ResponseUserSearch> {
        return when (val result = userRemoteDataSource.getUserSearch(params)) {
            is Result.Success -> {
                val response = result.value.let {
                    ResponseUserSearch(
                        items = it.contents,
                        totalCount = it.totalCount,
                        incompleteResults = it.incompleteResults
                    )
                }
                Result.Success(response)
            }
            is Result.Error -> result
        }
    }
}

