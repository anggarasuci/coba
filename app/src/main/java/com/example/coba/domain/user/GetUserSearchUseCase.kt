package com.example.coba.domain.user

import com.example.coba.util.Constant
import com.example.coba.data.model.user.RequestUserSearch
import com.example.coba.data.model.user.ResponseUserSearch
import com.example.coba.data.repository.UserRepository
import com.example.coba.domain.UseCase
import com.example.coba.domain.Result


class GetUserSearchUseCase(
    private val userRepository: UserRepository
) : UseCase<ResponseUserSearch, RequestUserSearch>() {

    override suspend fun build(params: RequestUserSearch?): Result<ResponseUserSearch> {
        requireNotNull(params) { Constant.REQUIRED_PARAMS }
        return userRepository.getUserSearch(params)
    }

    fun createParams(keyword: String, pages: Int, rows: Int = Constant.PageSize): RequestUserSearch {
        return RequestUserSearch(
            keyword = keyword,
            pages = pages,
            rows = rows
        )
    }
}
