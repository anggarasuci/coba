package com.example.coba.data.service

import com.example.coba.data.model.Response
import com.example.coba.data.model.user.User
import kotlinx.coroutines.Deferred
import retrofit2.http.GET
import retrofit2.http.QueryMap
import retrofit2.http.Url

interface UserService {
    companion object {
        const val ENDPOINT_SEARCH = "search/users"
    }

    @GET
    fun getUserSearchAsync(@Url url: String = "", @QueryMap params: Map<String, String>): Deferred<Response<List<User>>>
}