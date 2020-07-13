package com.example.coba.data.remote

import com.example.coba.BuildConfig
import com.example.coba.data.model.Response
import com.example.coba.data.model.user.RequestUserSearch
import com.example.coba.data.model.user.User
import com.example.coba.data.service.UserService
import com.example.coba.domain.Result
import com.example.coba.network.ResponseParser
import com.example.coba.util.Param

class UserRemoteDataSource(
    private val userService: UserService
) {
    private var baseUrl = BuildConfig.APP_SERVICE_BASE_URL

    suspend fun getUserSearch(requestUserSearch: RequestUserSearch): Result<Response<List<User>>> {
        val paramsMap = hashMapOf(
            Param.SearchParam to requestUserSearch.keyword,
            Param.PageParam to requestUserSearch.pages.toString(),
            Param.PerPageParam to requestUserSearch.rows.toString()
        )
        val url = baseUrl.plus(UserService.ENDPOINT_SEARCH)
        return ResponseParser<List<User>>().parseResult(
            userService.getUserSearchAsync(url, paramsMap))
    }
}